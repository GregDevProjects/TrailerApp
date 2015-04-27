package com.android.gregory.trailerapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoActivity extends ActionBarActivity{


    DBAdapter db = new DBAdapter(this);
    Player player = new Player();

    //passed by the intent
    private String currentVideoId = "";
    //retrieved from db
    private String currentVideoRating = "";
    private String currentTrailerLocation;


    VideoView mVideoView;
    EditText ratingText;
    Button start;
    Button stop;
    Button pause;
    Button delete;
    Button submitRating;



    //http://code.tutsplus.com/tutorials/streaming-video-in-android-apps--cms-19888

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        //GET VALUES FROM INTENT
        Intent intent = getIntent();
        currentVideoId = intent.getExtras().getString("passedRating");

        //Set Class variables with info from database
        setCurrentMovieValues();

        //load the videoview
        int fileLocation =getResources().getIdentifier(currentTrailerLocation,
                "raw", getPackageName());

        final Uri localUri = Uri.parse("android.resource://" + getPackageName() + "/"+fileLocation);






        submitRating=(Button) findViewById(R.id.buttonSubmitRating);
        start = (Button) findViewById(R.id.buttonStart);
        stop = (Button) findViewById(R.id.buttonStop);
        pause = (Button)findViewById(R.id.buttonPause);
        delete = (Button)findViewById(R.id.buttonDelete);
        ratingText = (EditText)findViewById(R.id.editTextRating);


        mVideoView = (VideoView) findViewById(R.id.videoView1);
        mVideoView.setVideoURI(localUri);


        ratingText.setText(currentVideoRating);



        start.setOnClickListener(new OnClickListener() //LISTENER FOR START
        {
            public void onClick(View arg0)
            {
                mVideoView.setVideoURI(localUri);
                mVideoView.requestFocus();
                mVideoView.start();

            }//end vvoid

        }); // END +


        stop.setOnClickListener(new OnClickListener() //LISTENER FOR STOP
        {
            public void onClick(View arg0)
            {

                mVideoView.stopPlayback();
                mVideoView.clearFocus();



            }//end vvoid

        }); // END +




        pause.setOnClickListener(new OnClickListener() //LISTENER FOR PASUE
        {
            public void onClick(View arg0)
            {

                if(!player.isPaused())
                {
                    mVideoView.pause();
                    player.setPaused(true);
                    player.stopPosition = mVideoView.getCurrentPosition();
                }
                else
                {

                    mVideoView.seekTo(player.stopPosition);
                    mVideoView.start();
                    player.setPaused(false);
                }
            }//end vvoid

        }); // END +



        delete.setOnClickListener(new OnClickListener() //LISTENER FOR DELETE
        {
            public void onClick(View arg0)
            {

                db.deleteMovie(currentVideoId);

                Intent iMain = new Intent(VideoActivity.this, MainActivity.class);
                //  iMain.putExtra("passedRating","RATINGSTRING");
                startActivity(iMain);

            }//end vvoid

        }); // END +


        submitRating.setOnClickListener(new OnClickListener() //LISTENER FOR SUBMIT RATING
        {
            public void onClick(View arg0)
            {

                String input = ratingText.getText().toString();

                if(checkRating(input))
                {

                    Movies currentMovie = db.getMovies(Integer.parseInt(currentVideoId));
                    currentMovie.setRating(input);
                    db.updateMovie(currentMovie, currentVideoId);

                    Intent iMain = new Intent(VideoActivity.this, MainActivity.class);
                    startActivity(iMain);


                }
                else
                {

                    Toast.makeText(getApplicationContext(),
                            "Rating must be between 1-5", Toast.LENGTH_SHORT)
                            .show();

                }

            }//end vvoid

        }); // END +


    }//end ONCREATE


    public void setCurrentMovieValues(){

        Movies currentMovie = db.getMovies(Integer.parseInt(currentVideoId));
        currentVideoRating =currentMovie.getRating();
        currentTrailerLocation = currentMovie.getTrailerLocation();

    }


    boolean checkRating(String input)
    {

        if(input.equals(""))
        {
            return false;
        }

        int inputInt = Integer.parseInt(input);

        if(inputInt <=5)
        {
            if(inputInt>=0)
            {
                return true;
            }
        }

        return false;
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

    /**
     * A placeholder fragment containing a simple view.
     */


}
