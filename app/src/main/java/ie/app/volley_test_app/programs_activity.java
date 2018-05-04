package ie.app.volley_test_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class programs_activity extends AppCompatActivity {

    RequestQueue requestQueue;
    private TextView txtResponse;
    private String jsonResponse;
    String name, exercise, email,day1, day2, programId;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double longitude, latitude;
    ListView myList;
    ListAdapter myAdapter;
    ArrayList<String> ProgramName;
    ArrayList<String> ProgramDate;

    private static String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_activity);


        requestQueue = Volley.newRequestQueue(this);
        txtResponse = (TextView) findViewById(R.id.txtResponse);
        ProgramName = new ArrayList<>();
        ProgramDate = new ArrayList<>();





        final Bundle myBundle = getIntent().getExtras();
        final String UserId = myBundle.getString("UserId");
        final String MyLongitude = myBundle.getString("longitude");
        final String MyLatitude = myBundle.getString("latitude");
        final String MyUsername = myBundle.getString("username");
        final String MyBodyFat = myBundle.getString("bodyFat");
        final String MyWeight = myBundle.getString("weight");
        String url = "http://10.0.2.2:2000/api/getPrograms/" + UserId ;


                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationListener = new LocationListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onLocationChanged(Location location) {
                        longitude = location.getLongitude();
                        latitude = location.getLatitude();
                        txtResponse.setText("Location Details " +"\n"+ "Longitude: " + longitude +"\n" + "Latitude: " + latitude);            /*Post data*/
                        final Map<String, String> jsonParams = new HashMap<>();


                        requestQueue = Volley.newRequestQueue(programs_activity.this);
                        if(String.valueOf(longitude).equals(MyLongitude)& String.valueOf(latitude).equals(MyLatitude)){

                            Toast.makeText(programs_activity.this, "Its a match" , Toast.LENGTH_LONG).show();

                            jsonParams.put("longitude", String.valueOf(longitude));
                            jsonParams.put("latitude", String.valueOf(latitude));
                            jsonParams.put("userId", UserId);
                            jsonParams.put("username", MyUsername);
                            jsonParams.put("bodyFat", MyBodyFat);
                            jsonParams.put("weight", MyWeight);

                        }
                        String URL_Session = "http://10.0.2.2:2000/api/addSession";

                        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, URL_Session,

                                new JSONObject(jsonParams),
                                new Response.Listener<JSONObject>() {

                                    public void onResponse(JSONObject response)
                                    {
                                        System.out.print(response);

                                    }
                                },
                                new Response.ErrorListener() {

                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {

                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("User-agent", System.getProperty("http.agent"));
                                return headers;
                            }
                        };
                        requestQueue.add(postRequest);
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        } else {
            configureButton();
        }


        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject programs = (JSONObject) response.get(i);

                                String name = programs.getString("name");
                                String date = programs.getString("date");

                                day1 = programs.getString("day1");
                                day2 = programs.getString("day2");
                                programId = programs.getString("_id");

                                ProgramName.add(name);
                                ProgramDate.add(date);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //add request to queue
        requestQueue.add(jsonArrayRequest);



        myAdapter = new CustomAdaptor(this,ProgramName, ProgramDate);
        myList = (ListView) findViewById(R.id.myListView);
        myList.setAdapter(myAdapter);


        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent openShowWorkout = new Intent(programs_activity.this, single_program.class);
                openShowWorkout.putExtra("day1",day1);
                openShowWorkout.putExtra("day2",day2);
                openShowWorkout.putExtra("id",programId);
                startActivity(openShowWorkout);
            }
        });
    }



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 10:
                if(grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED);
                configureButton();
                return;
        }
    }

    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }else{
            locationManager.requestLocationUpdates("gps", 5000, 5, locationListener);
        }



    }
}
