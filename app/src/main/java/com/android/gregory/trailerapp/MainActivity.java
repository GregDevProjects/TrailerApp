package com.android.gregory.trailerapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;



import android.content.Intent;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;



public class MainActivity extends ActionBarActivity {

    ListView listViewMovies;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DBAdapter db = new DBAdapter(this);



        //UNCOMMENT EVERYTHING BELOW HERE AFTER FIRST RUN AND LAUNCH AGAIN TOO SEE DATABASE FUNCTIONALITY

//            db.deleteMovie("");
//
//            db.addMovie(new Movies("The Avengers", "Crime Fighting!", "avengerstrailer", "avengerspic", "5"));
//            db.addMovie(new Movies("The Fighter", "A Movie about a boxer", "thefightertrailer", "fighterpic", "4"));
//            db.addMovie(new Movies("Godzilla", "A Movie about a giant lizard", "godzillatrailer", "godzillapic", "2"));
     //       db.addMovie(new Movies("DELETEME", "AIM GONE", "TrailerLoc", "notfound", "2"));

        //END UNCOMMENT

//        db.updateMovie(new Movies("The Update", "A Movie that was updated","TrailerLoc","R.drawable.notfound","0"),"10");

        List<Movies> MovieList = null;

        try{

            MovieList = db.getAllMovies();

        }catch(Exception e)
        {

            db.addMovie(new Movies("No Movie Data", "","","notfound",""));
            MovieList = db.getAllMovies();

        }

        int dbSize =  MovieList.size();

        String[][] movieArray = new String[dbSize][6];

        for(int i = 0; i<dbSize; i++)
        {

            String currentId = Integer.toString(MovieList.get(i).getId());
            String currentName = MovieList.get(i).getName();
            String currentDesc = MovieList.get(i).getDescription();
            String thumbLoc = MovieList.get(i).getThumbLocation();
            String trailerLoc = MovieList.get(i).getTrailerLocation();
            String rating = MovieList.get(i).getRating();

            movieArray[i][0] = currentId;
            movieArray[i][1] = currentName;
            movieArray[i][2] = currentDesc;
            movieArray[i][3] = trailerLoc;
            movieArray[i][4] = thumbLoc;
            movieArray[i][5] = rating;

        }



        //       db.deleteMovie("");


        //START LIST VIEW


        listViewMovies = (ListView)findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[movieArray.length];
        String[] MovieIDs = new String[movieArray.length];

        //get values from dbarray and put them into values for list view array
        for(int i = 0; i<movieArray.length;i++)
        {

            values[i] = movieArray[i][1]  + "  " + movieArray[i][2] ;
            MovieIDs[i] = movieArray[i][0];

        }



        Integer[] imageId = new Integer[movieArray.length];

        //fills integer array with movie pictures
        for(int i = 0; i<movieArray.length; i++)
        {
            //getResources().getIdentifier("us","drawable","com.app");
            int resID = getResources().getIdentifier(movieArray[i][4], "drawable", getPackageName());

            imageId[i]= resID;

        }




        final CustomList adapter = new
                CustomList(MainActivity.this, values, imageId,MovieIDs);




        // Assign adapter to ListView
        listViewMovies.setAdapter(adapter);

        // ListView Item Click Listener
        listViewMovies.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                String selectedMovieID = adapter.getMovieID(position);
                // ListView Clicked item value
                String  itemValue    = (String) listViewMovies.getItemAtPosition(position);

                //            Show Alert
                Toast.makeText(getApplicationContext(),
                        itemValue, Toast.LENGTH_SHORT)
                        .show();


                //check if there are any trailers left
                if(!(itemValue.equals("No Movie Data  ")))
                {
                    Intent iVideo = new Intent(view.getContext(), VideoActivity.class);
                    iVideo.putExtra("passedRating",selectedMovieID);
                    startActivity(iVideo);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "No Movie Data Left!" , Toast.LENGTH_LONG)
                            .show();
                }

            }

        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
