package com.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button updateBtn;
    private EditText cityNameEdt;

    private TextView timeDataTxt;
    private TextView tempTxt;
    private TextView statusWeatherTxt;
    private TextView speedWindTxt;
    private TextView rainFallTxt;
    private TextView percentHumidityTxt;
    private TextView percentCloudsTxt;
    private TextView timeSunSetTxt;
    private TextView timeSunRiseTxt;
    private TextView tempMaxTxt;
    private TextView tempMinTxt;

    private TextView nameWindTxt;
    private TextView nameRSTxt;
    private TextView nameHumidityTxt;
    private TextView nameSunRiseTxt;
    private TextView nameSunSetTxt;
    private TextView nameCloudsTxt;
    private TextView unitSpeedWindTxt;
    private TextView unitVolumeRSTxt;
    private TextView unitHumidityTxt;
    private TextView unitTimeSunRiseTxt;
    private TextView unitTimeSunSetTxt;
    private TextView unitCloudsTxt;

    private ImageView imageStatusWeather;
    private RelativeLayout backgroundWeather;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateBtn = findViewById(R.id.update);
        cityNameEdt = findViewById(R.id.nameCity);

        timeDataTxt = findViewById(R.id.timeData);
        tempTxt = findViewById(R.id.degrees);
        tempMaxTxt = findViewById(R.id.degreesMax);
        tempMinTxt = findViewById(R.id.degreesMin);
        statusWeatherTxt = findViewById(R.id.textStatusWeather);

        nameWindTxt = findViewById(R.id.nameWind);
        speedWindTxt = findViewById(R.id.speedWind);
        unitSpeedWindTxt = findViewById(R.id.mhWind);

        nameRSTxt = findViewById(R.id.nameRain);
        rainFallTxt = findViewById(R.id.percentRain);
        unitVolumeRSTxt = findViewById(R.id.mmRain);

        nameHumidityTxt = findViewById(R.id.nameHumidity);
        percentHumidityTxt = findViewById(R.id.percentHumidity);
        unitHumidityTxt = findViewById(R.id.percent1);

        nameCloudsTxt = findViewById(R.id.nameClouds);
        percentCloudsTxt = findViewById(R.id.percentClouds);
        unitCloudsTxt = findViewById(R.id.percent2);

        nameSunRiseTxt = findViewById(R.id.nameSunrise);
        timeSunRiseTxt = findViewById(R.id.timeSunrise);
        unitTimeSunRiseTxt = findViewById(R.id.timeDay1);

        nameSunSetTxt = findViewById(R.id.nameSunrise);
        timeSunSetTxt = findViewById(R.id.timeSunset);
        unitTimeSunSetTxt = findViewById(R.id.timeDay2);

        imageStatusWeather = findViewById(R.id.imageStatusWeather);
        backgroundWeather = findViewById(R.id.mainContainer);

        updateBtn.setOnClickListener(v -> {
            if (cityNameEdt.getText().toString().trim().equals("")) {
                Toast.makeText(MainActivity.this,R.string.notEnterCity, Toast.LENGTH_LONG).show();
            } else {
                String language = "en";
                String mode = "metric";
                String city = cityNameEdt.getText().toString();
                String key = "48c08539d2de6cb0a1440dd91cbe4da8";
                String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=" + mode +"&lang=" + language;

                new GetURLData().execute(url);

            }
        });
    }

    private class GetURLData extends AsyncTask<String, String, String> {

        @SuppressLint("SetTextI18n")
        protected void onPreExecute() {
            super.onPreExecute();
            updateBtn.setText("Finding...");
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");
                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected  void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONObject jsonData = null;
            try {
                if (result == null)
                    timeDataTxt.setText("City not found!");
                else
                {
                    jsonData = new JSONObject(result);

                    if (jsonData.getInt("cod") != 200)
                        timeDataTxt.setText(jsonData.getString("message"));
                    else
                    {
                        JSONObject main = jsonData.getJSONObject("main");
                        JSONObject sys = jsonData.getJSONObject("sys");
                        JSONObject wind = jsonData.getJSONObject("wind");
                        JSONObject weather = jsonData.getJSONArray("weather").getJSONObject(0);

                        String mainStatusWeather = weather.getString("main");
                        String statusWeather = weather.getString("description");

                        int temp = (int) main.getDouble("temp");
                        int tempMax = (int) main.getDouble("temp_max");
                        int tempMin = (int) main.getDouble("temp_min");
                        int humidity = main.getInt("humidity");
                        int clouds = jsonData.getJSONObject("clouds").getInt("all");

                        double mindSpeedWind = wind.getDouble("speed");
                        double rainfall = 0.0;

                        Long sunrise = sys.getLong("sunrise");
                        Long sunset = sys.getLong("sunset");
                        Long updatedAt = jsonData.getLong("dt");

                        // data update
                        timeDataTxt.setText(new SimpleDateFormat("hh:mm a - dd MM yyyy ", Locale.ENGLISH).format(new Date(updatedAt * 1000)));
                        // ikon status Weather
                        if (mainStatusWeather.equals("Snow"))
                        {
                            rainfall = jsonData.getJSONObject("snow").getDouble("1h");
                            if (statusWeather.equals("light snow")) {
                                imageStatusWeather.setImageResource(R.drawable.lightsnow);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_lsnow);
                            }
                            else if (statusWeather.equals("sleet")) {
                                imageStatusWeather.setImageResource(R.drawable.sleet);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_snow);
                            }
                            else {
                                imageStatusWeather.setImageResource(R.drawable.snow);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_snow);
                            }
                        }
                        else if (mainStatusWeather.equals("Rain")) {
                            rainfall = jsonData.getJSONObject("rain").getDouble("1h");
                            if (statusWeather.equals("light rain")) {
                                imageStatusWeather.setImageResource(R.drawable.lightrain);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_rain);
                            }
                            else if (statusWeather.equals("sleet")) {
                                imageStatusWeather.setImageResource(R.drawable.sleet);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_rainclouds);
                            }
                            else {
                                imageStatusWeather.setImageResource(R.drawable.rain);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_hrain);
                            }
                        }
                        else if (mainStatusWeather.equals("Clouds"))
                        {
                            if (statusWeather.equals("light clouds") || statusWeather.equals("few clouds")) {
                                imageStatusWeather.setImageResource(R.drawable.lightclouds);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_clouds);
                            }
                            else {
                                imageStatusWeather.setImageResource(R.drawable.clouds);
                                backgroundWeather.setBackgroundResource(R.drawable.bg_clouds);
                            }
                        }
                        else if (mainStatusWeather.equals("Clear"))
                        {
                            imageStatusWeather.setImageResource(R.drawable.sun);
                            backgroundWeather.setBackgroundResource(R.drawable.bg_sun);
                        }
                        else if (mainStatusWeather.equals("Smoke"))
                        {
                            imageStatusWeather.setImageResource(R.drawable.smoke);
                            backgroundWeather.setBackgroundResource(R.drawable.bg_mits);
                        }

                        // main Weather Information
                        tempTxt.setText(temp + "°");
                        if (tempMax != tempMin)
                        {
                            tempMaxTxt.setText(tempMax + "°");
                            tempMinTxt.setText(tempMin + "°");
                        } else
                        {
                            tempMaxTxt.setText("");
                            tempMinTxt.setText("");
                        }
                        statusWeatherTxt.setText(statusWeather);

                        //details Weather Information
                        nameWindTxt.setText(R.string.wind);
                        speedWindTxt.setText(mindSpeedWind + "");
                        unitSpeedWindTxt.setText(R.string.speedWindmh);

                        nameRSTxt.setText(R.string.rain_snow);
                        rainFallTxt.setText(rainfall + "");
                        unitVolumeRSTxt.setText(R.string.volumeRainSnow);

                        nameHumidityTxt.setText(R.string.humidity);
                        percentHumidityTxt.setText(humidity + "");
                        unitHumidityTxt.setText(R.string.percent);

                        nameSunRiseTxt.setText(R.string.sunrise);
                        timeSunRiseTxt.setText(new SimpleDateFormat("hh:mm", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                        unitTimeSunRiseTxt.setText(new SimpleDateFormat("a", Locale.ENGLISH).format(new Date(sunrise * 1000)));

                        nameSunSetTxt.setText(R.string.sunset);
                        timeSunSetTxt.setText(new SimpleDateFormat("hh:mm", Locale.ENGLISH).format(new Date(sunset * 1000)));
                        unitTimeSunSetTxt.setText(new SimpleDateFormat("a", Locale.ENGLISH).format(new Date(sunset * 1000)));

                        nameCloudsTxt.setText(R.string.clouds);
                        percentCloudsTxt.setText(clouds + "");
                        unitCloudsTxt.setText(R.string.percent);
                    }
                }
                updateBtn.setText(R.string.finBtn);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}