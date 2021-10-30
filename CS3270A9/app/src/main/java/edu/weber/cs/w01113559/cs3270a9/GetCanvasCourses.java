package edu.weber.cs.w01113559.cs3270a9;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import edu.weber.cs.w01113559.cs3270a9.db.Course;

public class GetCanvasCourses extends AsyncTask<String, Integer, String> {

    private String rawJSON;
    private OnCourseListComplete mCallback;

    public interface OnCourseListComplete { void processCourseList(Course[] courses); }

    public void setOnCourseListComplete(OnCourseListComplete listener) {
        this.mCallback = listener;
    }

    // Work that task will do in the background
    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("https://weber.instructure.com/api/v1/courses");
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

    // After Task Executes
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Course[] courses;

        try {
            courses = parseJson(result);
            if (courses != null) {
                if (mCallback != null && mCallback instanceof OnCourseListComplete) {
                    mCallback.processCourseList(courses);
                } else {
                    throw new Exception("Must implement OnCourseListComplete interface.");
                }
            }
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

    }

    private Course[] parseJson(String result) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        Course[] courses = null;

        try {
            courses = gson.fromJson(rawJSON, Course[].class);
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }
        return courses;
    }
}
