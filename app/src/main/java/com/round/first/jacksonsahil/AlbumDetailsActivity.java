package com.round.first.jacksonsahil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_details)
    ImageView albumArt;
    @BindView(R.id.tv_details_track_name)
    TextView trackName;
    @BindView(R.id.tv_details_coll_name)
    TextView collectionName;
    @BindView(R.id.tv_details_artist_val)
    TextView artistVal;
    @BindView(R.id.tv_details_genre_val)
    TextView genreVal;

    String trackNameStr = null, collectionNameStr =null, artistNameStr = null, genreStr =null, albumArtStr=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // getting album data saved in intent
        trackNameStr = getIntent().getStringExtra("track");
        collectionNameStr = getIntent().getStringExtra("collection");
        artistNameStr = getIntent().getStringExtra("artist");
        genreStr = getIntent().getStringExtra("genre");
        albumArtStr= getIntent().getStringExtra("albumart");
        Log.d("Values: ",trackNameStr+" "+collectionNameStr+" "+artistNameStr+" "+genreStr+" "+ albumArtStr);


        /* setting data in corresponding place in the details activity */
        // setting image
        Picasso.with(this)
                .load(albumArtStr)
                .error(R.drawable.icn_uncategorised_categories)
                .placeholder(R.drawable.icn_uncategorised_categories)
                .into(albumArt);

        // setting album details
        trackName.setText(trackNameStr);
        collectionName.setText(collectionNameStr);
        artistVal.setText(artistNameStr);
        genreVal.setText(genreStr);
    }

    // Back button method
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
