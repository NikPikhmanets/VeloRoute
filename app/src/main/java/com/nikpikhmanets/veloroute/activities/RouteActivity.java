package com.nikpikhmanets.veloroute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.nikpikhmanets.veloroute.R;
import com.nikpikhmanets.veloroute.interfaces.OnVoteListener;
import com.nikpikhmanets.veloroute.route.Route;
import com.nikpikhmanets.veloroute.utils.DialogUtils;
import com.nikpikhmanets.veloroute.utils.FirebaseUtils;

import java.util.Locale;

public class RouteActivity extends AppCompatActivity implements View.OnClickListener, OnVoteListener, View.OnTouchListener {

    final String GROUND = "Грунт:";
    final String ASPHALT = "Асфальт:";
    final String GROUND_ASPHALT = "Грунт/асфальт:";
    private static final String VALUE_MARKED = "marked";

//    private static final String TAG = "tag";

    private final String INTENT_ROUTE = "ROUTE";

    private TextView nameRoute;
    private TextView lengthRoute;
    private TextView roadRoute;
    private TextView roadRouteLabel;
    private TextView tvRouteDescription;
    private ImageView imageRoute;
    private RatingBar rbRating;
    private TextView tvRating;
    private Button showOnMapsBtn;

    private DatabaseReference ratingReference;
    private DatabaseReference votesReference;
    private FirebaseUser currentUser = FirebaseUtils.getCurrentFirebaseUser();
    private DatabaseReference currentRouteMarkedByUserRef;



    private Route route;
    private float rating;
    private int votes;
    private boolean isMarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        setTitle(getString(R.string.description_route));

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        route = getIntent().getParcelableExtra(INTENT_ROUTE);

        ratingReference = FirebaseUtils.getRatingReference(route.getKey());
        votesReference = FirebaseUtils.getVotesReference(route.getKey());
        currentRouteMarkedByUserRef = FirebaseUtils.getMarkedRouteReference(route.getKey());

        ratingReference.addValueEventListener(ratingValueEventListener);
        votesReference.addValueEventListener(votesValueEventListener);
        currentRouteMarkedByUserRef.addValueEventListener(routeMarkedEventListener);

        setViewDescriptionRoute();

    }

    @Override
    protected void onResume() {
        super.onResume();

        RelativeLayout Rl = (RelativeLayout) findViewById(R.id.headLayout);
        Rl.measure(0, 0);
        int i = Rl.getMeasuredHeight();

        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(imageRoute.getLayoutParams());
        marginParams.setMargins(0, i, 0, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(marginParams);
        imageRoute.setLayoutParams(layoutParams);
    }

    @Override
    protected void onDestroy() {
        ratingReference.removeEventListener(ratingValueEventListener);
        votesReference.removeEventListener(votesValueEventListener);
        currentRouteMarkedByUserRef.removeEventListener(routeMarkedEventListener);
        super.onDestroy();

    }

    private void initView() {
        nameRoute = (TextView) findViewById(R.id.nameRouteLabel);
        roadRouteLabel = (TextView) findViewById(R.id.groundRouteLabel);
        lengthRoute = (TextView) findViewById(R.id.distance);
        roadRoute = (TextView) findViewById(R.id.roadRoute);
        imageRoute = (ImageView) findViewById(R.id.imageRoute);
        tvRouteDescription = (TextView) findViewById(R.id.tv_description);
        rbRating = (RatingBar) findViewById(R.id.rb_label_rating);
        tvRating = (TextView) findViewById(R.id.tv_label_rating);

        showOnMapsBtn = (Button) findViewById(R.id.showOnMaps);
        showOnMapsBtn.setOnClickListener(this);
        rbRating.setOnTouchListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setViewDescriptionRoute() {
        initView();

        if (route != null) {
            nameRoute.setText(route.getName_ru());

            Glide.with(this).load(route.getImageURL()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageRoute);

            tvRouteDescription.setText(route.getDescription());

            lengthRoute.setText(String.format("%s км", route.getLength()));
            if (route.getRoad() == 0) {
                roadRouteLabel.setText(GROUND);
                roadRoute.setText(String.format("%s", route.getDirt()));
            } else if (route.getDirt() == 0) {
                roadRouteLabel.setText(ASPHALT);
                roadRoute.setText(String.format("%s", route.getRoad()));
            } else {
                roadRouteLabel.setText(GROUND_ASPHALT);
                roadRoute.setText(String.format("%s / %s", route.getDirt(), route.getRoad()));
            }
        } else {
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showOnMaps:
                Intent intent = new Intent(this, MapsActivity.class);
                intent.putExtra(INTENT_ROUTE, route);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onVoted(int rate) {
        votes++;
        votesReference.setValue(votes);
        float delta = rate - rating;
        rating += delta / votes;
        ratingReference.setValue(rating);
        currentRouteMarkedByUserRef.setValue(VALUE_MARKED);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (currentUser != null && !currentUser.isAnonymous()) {

                if (!isMarked) {
                    DialogUtils.getRatingDialog(this, this).show();
                    return true;
                } else {
                    Toast.makeText(this, "вы уже оценили этот маршрут", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "нужно авторизироваться чтобы оценить", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private ValueEventListener ratingValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            rating = Float.parseFloat(dataSnapshot.getValue().toString());
            rbRating.setRating(rating);
            tvRating.setText(String.format(Locale.US, "%.1f", rating));
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener votesValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            votes = Integer.parseInt(dataSnapshot.getValue().toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    private ValueEventListener routeMarkedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Object value = dataSnapshot.getValue();
            isMarked = value != null && VALUE_MARKED.equals(value.toString());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
