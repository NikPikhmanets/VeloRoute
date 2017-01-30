package com.nikpikhmanets.veloroute.emulRoute;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nikpikhmanets.veloroute.R;

import java.util.ArrayList;
import java.util.List;

public class EmulListRoute {

    private int id;
    private String nameRoute;
    private int imageRoute;
    private String ground;
    private String distance;
    private Bitmap bmpImage;

    public EmulListRoute(Context ctx) {
        this.ctx = ctx;
    }

    private Context ctx;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public Bitmap getImageRoute() {
        return bmpImage;
    }

    public void setImageRoute(int imageRoute) {

//        this.imageRoute = imageRoute;
        Bitmap bmp = BitmapFactory.decodeResource(ctx.getResources(), imageRoute);
        bmpImage = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth()*0.5), (int)(bmp.getHeight()*0.5), true);
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List <EmulListRoute> getRouteList() {
        List <EmulListRoute> route = new ArrayList<>();
        initDataRoute(route);
        return route;
    }

    private void initDataRoute(List<EmulListRoute> listRoute) {

        int id = 1;

        EmulListRoute route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Софиин стовп");
        route.setDistance("45 км");
        route.setGround("асфальт 90 % / грунт 10 %");
        route.setImageRoute(R.drawable.image_route_sofiin_stovp);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Будище");
        route.setDistance("60 км");
        route.setGround("асфальт 100 %");
        route.setImageRoute(R.drawable.image_route_budische);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Буки");
        route.setDistance("25 км");
        route.setGround("асфальт 100 %");
        route.setImageRoute(R.drawable.image_route_buky);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Камянский каньон");
        route.setDistance("100 км");
        route.setGround("асфальт 100 %");
        route.setImageRoute(R.drawable.image_route_kam_canyon);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Канев");
        route.setDistance("80 км");
        route.setGround("асфальт 80 % / грунт 20 %");
        route.setImageRoute(R.drawable.image_route_kaniv);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Малосмелянский карьер");
        route.setDistance("50 км");
        route.setGround("асфальт 80 % / грунт 20 %");
        route.setImageRoute(R.drawable.image_route_malo_smila_karyer);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Орбита");
        route.setDistance("110 км");
        route.setGround("асфальт 70 % / грунт 30 %");
        route.setImageRoute(R.drawable.image_route_orbita);
        listRoute.add(route);

        route = new EmulListRoute(ctx);
        route.setId(id++);
        route.setNameRoute("Живун");
        route.setDistance("120 км");
        route.setGround("асфальт 70 % / грунт 30 %");
        route.setImageRoute(R.drawable.image_route_zyvun);
        listRoute.add(route);
    }

}
