package com.example.moviedirectory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.example.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieYear;
    private TextView director;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView runTime;

    private RequestQueue queue;
    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Instantiate queue request.
        queue = Volley.newRequestQueue(this);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        movieId = movie.getImdbId();

        setUpUI();
        getMovieDetails(movieId);
    }

    private void getMovieDetails(String id) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id + Constants.API_KEY,  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("Ratings")) {
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {
                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            rating.setText(source + " : " + value);
                        } else {
                            rating.setText("Rating: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released" + response.getString("Released"));
                        director.setText("Director" + response.getString("Director"));
                        writers.setText("Writer" + response.getString("Writer"));
                        plot.setText("Plot" + response.getString("Plot"));
                        runTime.setText("Runtime: " + response.getString("Runtime: "));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.with(getApplicationContext())
                                .load(response.getString("Poster"))
                                .into(movieImage);

                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error", error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);

    }

    private void setUpUI() {

        movieTitle = findViewById(R.id.tv_mTitleDet);
        movieImage = findViewById(R.id.iv_mImageDet);
        movieYear = findViewById(R.id.tv_mReleasedDet);
        director = findViewById(R.id.tv_directedByDet);
        category = findViewById(R.id.tv_mCategoryDet);
        rating = findViewById(R.id.tv_movieRatingDet);
        writers = findViewById(R.id.tv_writersDet);
        plot = findViewById(R.id.tv_plotDet);
        boxOffice = findViewById(R.id.tv_boxOfficeDet);
        runTime = findViewById(R.id.tv_runtimeDet);
        actors = findViewById(R.id.tv_actorsDet);
    }


}
