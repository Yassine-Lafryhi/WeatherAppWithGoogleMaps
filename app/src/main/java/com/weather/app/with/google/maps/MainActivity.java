package com.weather.app.with.google.maps;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView city;
    TextView temperature;
    TextView minimumTemperature;
    TextView maximumTemperature;
    TextView pressure;
    TextView humidity;
    TextView date;
    public static Double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = findViewById(R.id.img);
        imageView.setImageResource(R.drawable.meteo);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button view = findViewById(R.id.viewMap);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("latitude", String.valueOf(latitude));
                intent.putExtra("longitude", String.valueOf(longitude));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final Context context = this;

        city = findViewById(R.id.txtville);
        temperature = findViewById(R.id.temp);
        minimumTemperature = findViewById(R.id.tempmin);
        maximumTemperature = findViewById(R.id.tempmax);
        pressure = findViewById(R.id.pression);
        humidity = findViewById(R.id.humid);
        date = findViewById(R.id.date);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                city.setText(query);
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://api.openweathermap.org/data/2.5/weather?q=" + query + "&appid=e457293228d5e1465f30bcbe1aea456b";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject coordinates = jsonObject.getJSONObject("coord");
                            latitude = coordinates.getDouble("lat");
                            longitude = coordinates.getDouble("lon");
                            Date date = new Date(jsonObject.getLong("dt") * 1000);
                            SimpleDateFormat simpleDateFormat =
                                    new SimpleDateFormat("dd-MMM-yyyy' T 'HH:mm");
                            String dateString = simpleDateFormat.format(date);

                            JSONObject main = jsonObject.getJSONObject("main");

                            int Temp = (int) (main.getDouble("temp") - 273.15);
                            int TempMin = (int) (main.getDouble("temp_min") - 273.15);
                            int TempMax = (int) (main.getDouble("temp_max") - 273.15);
                            int Pression = (int) (main.getDouble("pressure"));
                            int Humidite = (int) (main.getDouble("humidity"));

                            JSONArray weather = jsonObject.getJSONArray("weather");
                            String meteo = weather.getJSONObject(0).getString("main");

                            MainActivity.this.date.setText(dateString);
                            temperature.setText(Temp + "°C");
                            minimumTemperature.setText(TempMin + "°C");
                            maximumTemperature.setText(TempMax + "°C");
                            pressure.setText(Pression + " hPa");
                            humidity.setText(Humidite + "%");

                            setImage(meteo);
                            Toast.makeText(context, meteo, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,
                                        "City not fond", Toast.LENGTH_LONG).show();
                            }
                        });

                queue.add(stringRequest);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    public void setImage(String s) {
        ImageView imageView = findViewById(R.id.img);
        if (s.equals("Rain")) {
            imageView.setImageResource(R.drawable.rainy);
        } else if (s.equals("Clear")) {
            imageView.setImageResource(R.drawable.clear);
        } else if (s.equals("Thunderstorm")) {
            imageView.setImageResource(R.drawable.thunderstorm);
        } else if (s.equals("Clouds")) {
            imageView.setImageResource(R.drawable.cloudy);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
