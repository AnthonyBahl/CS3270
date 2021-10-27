package edu.weber.cs.w01113559;

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

import edu.weber.cs.w01113559.db.Course;

public class GetCanvasCourses extends AsyncTask<String, Integer, String> {

    private String rawJSON;
    private OnCourseListComplete mCallback;

    public interface OnCourseListComplete
    {
        void processCourseList(Course[] course);
    }

    public void setOnCourseListComplete(OnCourseListComplete listener) {
        mCallback = listener;
    }

    // Everything will happen in a background thread
    @Override
    protected String doInBackground(String... strings) {

        try {
            URL url = new URL("https://weber.instructure.com/api/v1/courses");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            // Set up Header
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Authorization.AUTH_TOKEN);

            // This is where things would stall if we weren't in an Async Task
            connection.connect();

            // 2## means success, 4## means it's broken.
            int status = connection.getResponseCode();

            switch (status) {

                case 200:
                case 201:

                    // Read data in and store it.
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    rawJSON = bufferedReader.readLine();

                    Log.d("test", "rawJson Length: " + rawJSON.length());
                    Log.d("test", "rawJson first 256:: " + rawJSON.substring(0, 256));

                    break;
            }

        } catch (MalformedURLException e) {
            Log.d("test", "BAD URL. Unable to connect.");
        } catch (IOException e) {
            Log.d("test", "Unable to connect. Do you have I/O?");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        Course[] courses;

        try {
            courses = parseJson(result);
            if (courses != null) {
                if (mCallback != null && mCallback instanceof OnCourseListComplete) {
                    // Send
                    mCallback.processCourseList(courses);
                } else {
                    throw new Exception("Must implement OnCourseListComplete interface");
                }
            }
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

    }

    private Course[] parseJson(String result) {
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();

        Course[] courses = null;

        try {
            courses = gson.fromJson(rawJSON, Course[].class);
            Log.d("test", "Course Count: " + courses.length);
        } catch (Exception e) {
            Log.d("test", e.getMessage());
        }

        return courses;
    }
}