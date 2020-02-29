package com.example.moviedirectory.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviedirectory.Activities.MovieDetailActivity;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        movieList = movies;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//TODO inflate movie_row.xml to make it view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false); //this view contains the movie layout


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//TODO bind the view(created in oncreateViewHolder) with data from internet
        Movie movie = movieList.get(position);
        String posterLink = movie.getPoster();//contains the image link to the movie

        holder.title.setText(movie.getTitle());//get all the items of the movie and ste with the data
        holder.type.setText(movie.getMovieType());

        Picasso.with(context)     //Picasso gets the link of the image and turns it into image
                .load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now) //Placeholder posts a default image that we set if there is no image in the response
                .into(holder.poster); //with into we post the image to the target image holder

        holder.year.setText(movie.getYear());

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView poster;
        public TextView title;
        public TextView year;
        public TextView type;

        public ViewHolder(@NonNull View itemView, final Context ctx) {
            //TODO instantiate the views(MovieImage, title,rdate, category)
            super(itemView);
            context = ctx;

            title = itemView.findViewById(R.id.tv_movieTitle);
            poster = itemView.findViewById(R.id.iv_movieImage);
            year = itemView.findViewById(R.id.tv_movieReleasedDate);
            type = itemView.findViewById(R.id.tv_movieCategory);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) { // this method allows to click on a row & take the user from Main activity to Details activity
                    //Create a movie object
                    Movie movie = movieList.get(getAdapterPosition());

                    //Create intent to carry movie from Main to Details
                    Intent intent = new Intent(context, MovieDetailActivity.class);

                    //Put movie into intent
                    intent.putExtra("movie", movie);

                    ctx.startActivity(intent);

                }
            });

        }

        @Override
        public void onClick(View view) {

        }
    }
}
