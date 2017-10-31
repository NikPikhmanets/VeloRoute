package com.nikpikhmanets.veloroute.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ivan on 29-Mar-17.
 */

public abstract class FirebaseUtils {

    private static final String KEY_ROUTES = "routes";
    private static final String KEY_USERS = "users";
    private static final String KEY_MARKED_ROUTES = "markedRoutes";
    private static final String KEY_RATING = "rating";
    private static final String KEY_VOTES = "votes";
    private static final String KEY_PLACES = "place";


    public static FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static DatabaseReference getRoutesReference() {
        return FirebaseDatabase.getInstance().getReference().child(KEY_ROUTES);
    }

    public static DatabaseReference getMarkedRouteReference(String routeKey) {
        return FirebaseDatabase.getInstance()
                .getReference()
                .child(KEY_USERS)
                .child(getCurrentFirebaseUser().getUid())
                .child(KEY_MARKED_ROUTES)
                .child(routeKey);
    }

    public static DatabaseReference getRatingReference(String routeKey) {
        return getRoutesReference().child(routeKey).child(KEY_RATING);
    }

    public static DatabaseReference getVotesReference(String routKey) {
        return getRoutesReference().child(routKey).child(KEY_VOTES);
    }

    public static DatabaseReference getPlacesReference () {
        return FirebaseDatabase.getInstance().getReference().child(KEY_PLACES);
    }

    public static boolean isLoggedIn() {
        return getCurrentFirebaseUser() != null;
    }

}
