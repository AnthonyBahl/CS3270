package edu.weber.cs.w01113559.cs3270a9;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetCourseAssignments extends AsyncTask<String, Integer, String> {

    private String rawJSON;
    private onAssignmentListComplete mCallback;

    public interface onAssignmentListComplete { void processAssignmentList(Assignment[] assignments); }

    public void setmCallback(onAssignmentListComplete mCallback) { this.mCallback = mCallback; }

    @Override
    protected String doInBackground(String... strings) {

        try {

            URL url = new URL("https://weber.instructure.com/api/v1/courses/" + strings[0] + "/assignments");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Authorization.AUTH_TOKEN);

            connection.connect();

            int status = connection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();
                    break;
            }

        } catch (MalformedURLException e) {
            Log.d("Exception", "BAD URL. Unable to connect.");
        } catch (IOException e) {
            Log.d("Exception", "Unable to connect. Do you have I/O?");
        }

        return rawJSON;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Assignment[] assignments;

        try {
            assignments = parseJSON();
            if (assignments != null) {
                if (mCallback != null && mCallback instanceof onAssignmentListComplete) {
                    mCallback.processAssignmentList(assignments);
                } else {
                    throw new Exception("Must implement onAssignmentListComplete interface");
                }
            }
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

    }

    private Assignment[] parseJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Assignment[] assignments = null;

        try {
            assignments = gson.fromJson(rawJSON, Assignment[].class);
        } catch (JsonSyntaxException e) {
            Log.d("test", e.getMessage());
        }

        return assignments;
    }
}
