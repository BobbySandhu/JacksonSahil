package com.round.first.jacksonsahil.util;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UtilMethods {

    // this method can be used to fetch any json from url
    public static String fetchJson() {
        String json = null;
        try {
            URL url = new URL(ConstVals.ITUNES_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            // Reading response
            InputStream input = new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            StringBuilder sb = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                json = sb.toString();
                Log.d("response---> ", json);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    // closing stream
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
