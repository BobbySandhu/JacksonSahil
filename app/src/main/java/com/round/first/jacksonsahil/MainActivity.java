package com.round.first.jacksonsahil;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.round.first.jacksonsahil.adapter.AlbumAdapter;
import com.round.first.jacksonsahil.model.AlbumInfoModel;
import com.round.first.jacksonsahil.util.UtilMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AlbumAdapter.OnItemClicked {

    // defining variables
    @BindView(R.id.rv_albumlist)
    RecyclerView mAlbumRecycler;
    private Context mContext;
    private List<AlbumInfoModel> albums;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;

        // checking network connection
        NetworkInfo localNetworkInfo = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {
            // fetching and processing album in the background
            new AlbumAsyncTask().execute();
        } else {
            Toast.makeText(mContext, "Check your internet connection", Toast.LENGTH_SHORT).show();
            Log.d("aaa","bbb");
        }
    }

    // class processing data in the background
    private class AlbumAsyncTask extends AsyncTask<String, Void, Void> {

        String response = null;

        @Override
        protected void onPreExecute() {

            // saving fetched json objects here
            albums = new ArrayList<>();
            Toast.makeText(MainActivity.this, "Connecting with iTunes...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            // fetching itunes json
            response = UtilMethods.fetchJson();


            // Logs
            /*int length = response.length();
            for (int i = 0; i < length; i += 1024) {
                if (i + 1024 < length)
                    Log.d("JSON OUTPUT", response.substring(i, i + 1024));
                else
                    Log.d("JSON OUTPUT", response.substring(i, length));
            }*/


            // fetching required information from the fetched iTunes json
            if (response != null) {
                try {
                    JSONObject cc = new JSONObject(response);
                    JSONArray jsonArray = cc.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        AlbumInfoModel albumInfo = new AlbumInfoModel();
                        JSONObject c = jsonArray.getJSONObject(i);
                        albumInfo.setTrackName(c.getString("trackName"));
                        albumInfo.setArtistName(c.getString("artistName"));
                        albumInfo.setCollectionName(c.getString("collectionName"));
                        albumInfo.setPrimaryGenreName(c.getString("primaryGenreName"));
                        albumInfo.setArtworkUrl60(c.getString("artworkUrl100"));
                        albums.add(albumInfo);

                    }

                    Log.d("jacksonSahil-list", "" + albums.size());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "oops!! Something went wrong", Toast.LENGTH_SHORT).show();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // setting up adapter for displaying album list
            AlbumAdapter albumAdapter = new AlbumAdapter(albums, mContext);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            mAlbumRecycler.setLayoutManager(mLayoutManager);
            mAlbumRecycler.setAdapter(albumAdapter);
            albumAdapter.setOnClick(MainActivity.this); // Bind the listener
        }
    }


    // this method fetches the selected album data, currently fetching most important one
    @Override
    public void onItemClick(int position) {
        AlbumInfoModel aim = albums.get(position);
        Intent in = new Intent(this, AlbumDetailsActivity.class);

        String trackName = aim.getTrackName();
        String collectionName = aim.getCollectionName();
        String artistName = aim.getArtistName();
        String genreName = aim.getPrimaryGenreName();
        String albumArt = aim.getArtworkUrl60();

        in.putExtra("track", trackName);
        in.putExtra("collection", collectionName);
        in.putExtra("artist", artistName);
        in.putExtra("genre", genreName);
        in.putExtra("albumart", albumArt);
        startActivity(in);

    }
}
