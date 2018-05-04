package ie.app.volley_test_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RequestQueue requestQueue;
    String CurrentUser, longitude, latitude, username, bodyFat, weight;
    Boolean success;
    private static String TAG = MainActivity.class.getSimpleName();
    private TextView usernameTxtBox, passwordTxtBox;
    private Button loginBtn;
    private ProgressDialog pDialog;


    private String jsonResponse;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        usernameTxtBox = (TextView) findViewById(R.id.usernameTxtBox);
        passwordTxtBox = (TextView) findViewById(R.id.passwordTxtBox);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {
            /*Post data*/
                final Map<String, String> jsonParams = new HashMap<String, String>();

                    jsonParams.put("username", usernameTxtBox.getText().toString());
                    jsonParams.put("password", passwordTxtBox.getText().toString());
                    String URL_Auth = "http://10.0.2.2:2000/api/authenticate";
                JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, URL_Auth,

                        new JSONObject(jsonParams),
                        new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response)
                            {
                                try {
                                    CurrentUser  = response.getString("userId");
                                    success = response.getBoolean("success");
                                    longitude = response.getString("longitude");
                                    latitude = response.getString("latitude");
                                    username = response.getString("username");
                                    bodyFat = response.getString("bodyFat");
                                    weight = response.getString("weight");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(success){
                                    Toast.makeText(MainActivity.this, "Logged in: " + success, Toast.LENGTH_SHORT).show();
                                    Intent loginSuccessIntent = new Intent(MainActivity.this, programs_activity.class);
                                    loginSuccessIntent.putExtra("UserId", CurrentUser);
                                    loginSuccessIntent.putExtra("longitude", longitude);
                                    loginSuccessIntent.putExtra("latitude", latitude);
                                    loginSuccessIntent.putExtra("username", username);
                                    loginSuccessIntent.putExtra("bodyFat", bodyFat);
                                    loginSuccessIntent.putExtra("weight", weight);
                                    startActivity(loginSuccessIntent);
                                }else{
                                    Toast.makeText(MainActivity.this, "Could not be logged in!  " , Toast.LENGTH_LONG).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {

                            public void onErrorResponse(VolleyError error) {
                                System.out.print(error);
                            }
                        })
                {

                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("User-agent", System.getProperty("http.agent"));
                        return headers;
                    }
                };
                requestQueue.add(postRequest);

            }
        });
    }
}
