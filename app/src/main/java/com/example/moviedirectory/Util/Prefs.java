package com.example.moviedirectory.Util;

//This class holds the result from the last search

import android.app.Activity;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setSearch(String search) {
        // This method saves the result from search to db(Shared Preferences)
        sharedPreferences.edit().putString("search", search).commit();

    }

    public String getSearch() {
        //returns Batman as default first time when the app is opened or if the user doesn't search anything
        return sharedPreferences.getString("search", "Batman");
    }

}
