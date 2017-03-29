package com.nikpikhmanets.veloroute.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Ivan on 29-Mar-17.
 */

public abstract class FirebaseUtils {

    private static final String ROUTES_KEY = "routes";
    private static final String USERS_KEY = "users";
    private static final String MARKED_ROUTES_KEY = "markedRoutes";
    private static final String RATING_KEY = "rating";
    private static final String VOTES_KEY = "votes";
    private static final String PLACES_KEY = "place";


    public static FirebaseUser getCurrentFirebaseUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static DatabaseReference getRoutesReference() {
        return FirebaseDatabase.getInstance().getReference().child(ROUTES_KEY);
    }

    public static DatabaseReference getMarkedRouteReference(String routeKey) {
        return FirebaseDatabase.getInstance()
                .getReference()
                .child(USERS_KEY)
                .child(getCurrentFirebaseUser().getUid())
                .child(MARKED_ROUTES_KEY)
                .child(routeKey);
    }

    public static DatabaseReference getRatingReference(String routeKey) {
        return getRoutesReference().child(routeKey).child(RATING_KEY);
    }

    public static DatabaseReference getVotesReference(String routKey) {
        return getRoutesReference().child(routKey).child(VOTES_KEY);
    }

    public static DatabaseReference getPlacesReference () {
        return FirebaseDatabase.getInstance().getReference().child(PLACES_KEY);
    }

    public static boolean isLoggedIn() {
        return getCurrentFirebaseUser() != null;
    }

}
