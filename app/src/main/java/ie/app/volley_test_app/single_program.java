package ie.app.volley_test_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class single_program extends AppCompatActivity {
    RequestQueue requestQueue;
    private TextView programsTextBox;
    private Button logoutButton;
    String url;



    private String jsonResponse;
    private static String TAG = single_program.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_program);



        Bundle myBundle = getIntent().getExtras();
        final String day1 = myBundle.getString("day1");
        final String day2 = myBundle.getString("day2");
        final String id = myBundle.getString("id");
        requestQueue = Volley.newRequestQueue(this);

         url = "http://10.0.2.2:2000/api/programById/" + id ;

        //Logout button and functions
        logoutButton = (Button) findViewById(R.id.LogOut);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logOut = new Intent(single_program.this, MainActivity.class);
                Toast.makeText(single_program.this, "Logging out!" , Toast.LENGTH_LONG).show();
                startActivity(logOut);
            }
        });


        programsTextBox = (TextView) findViewById(R.id.exercise1day1);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {

                    String muscle1 = response.getString("muscleGroup1");
                    String exercise11 = response.getString("exercise1");
                    int sets1 = Integer.parseInt(response.getString("sets1"));
                    int reps1 = Integer.parseInt(response.getString("reps1"));
                    int restTime1 = Integer.parseInt(response.getString("restTime1"));

                    String muscle2 = response.getString("muscleGroup2");
                    String exercise2 = response.getString("exercise2");
                    int sets2 = Integer.parseInt(response.getString("sets2"));
                    int reps2 = Integer.parseInt(response.getString("reps2"));
                    int restTime2 = Integer.parseInt(response.getString("restTime2"));

                    String muscle3 = response.getString("muscleGroup3");
                    String exercise3 = response.getString("exercise3");
                    int sets3 = Integer.parseInt(response.getString("sets3"));
                    int reps3 = Integer.parseInt(response.getString("reps3"));
                    int restTime3 = Integer.parseInt(response.getString("restTime3"));

                    String muscle4 = response.getString("muscleGroup4");
                    String exercise4 = response.getString("exercise4");
                    int sets4 = Integer.parseInt(response.getString("sets4"));
                    int reps4 = Integer.parseInt(response.getString("reps4"));
                    int restTime4 = Integer.parseInt(response.getString("restTime4"));



                    jsonResponse = "";
                    jsonResponse += "Muscle Group: " + muscle1 + "\n\n";
                    jsonResponse += "Exercise : " + exercise11 + "\n\n";
                    jsonResponse += "Sets: " + sets1 + "\n\n";
                    jsonResponse += "Reps: " + reps1 + "\n\n";
                    jsonResponse += "Rest Time: " + restTime1 + "\n\n\n";

                    jsonResponse += "Muscle Group: " + muscle2 + "\n\n";
                    jsonResponse += "Exercise : " + exercise2 + "\n\n";
                    jsonResponse += "Sets: " + sets2 + "\n\n";
                    jsonResponse += "Reps: " + reps2 + "\n\n";
                    jsonResponse += "Rest Time: " + restTime2 + "\n\n\n";

                    jsonResponse += "Muscle Group: " + muscle3 + "\n\n";
                    jsonResponse += "Exercise : " + exercise3 + "\n\n";
                    jsonResponse += "Sets: " + sets3 + "\n\n";
                    jsonResponse += "Reps: " + reps3 + "\n\n";
                    jsonResponse += "Rest Time: " + restTime3 + "\n\n\n";

                    jsonResponse += "Muscle Group: " + muscle4 + "\n\n";
                    jsonResponse += "Exercise : " + exercise4 + "\n\n";
                    jsonResponse += "Sets: " + sets4 + "\n\n";
                    jsonResponse += "Reps: " + reps4 + "\n\n";
                    jsonResponse += "Rest Time: " + restTime4 + "\n\n";

                    programsTextBox.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog

            }
        });
        requestQueue.add(jsonObjReq);
    }
}
