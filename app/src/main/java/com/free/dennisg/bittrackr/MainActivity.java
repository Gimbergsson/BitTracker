package com.free.dennisg.bittrackr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtJson;
    ProgressDialog pd;

    PieChartView pieChartView;
    PieChartData pieChartData;

    LineChartView lineChartView;
    LineChartData lineChartData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton getUSDValueBtn = (FloatingActionButton) findViewById(R.id.getUSDValueBtn);
        getUSDValueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonTask().execute("https://blockchain.info/ticker");
                Snackbar.make(view, "Getting JSON", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        FloatingActionButton getPieChartBtn = (FloatingActionButton) findViewById(R.id.getPieChartBtn);
        getPieChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonTask().execute("https://blockchain.info/pools?format=json");
                Snackbar.make(view, "Getting JSON", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        FloatingActionButton getLineChartBtn = (FloatingActionButton) findViewById(R.id.getLineChartBtn);
        getLineChartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JsonTask().execute("https://blockchain.info/charts/market-price?format=json");
                Snackbar.make(view, "Getting JSON", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        pieChartView = (PieChartView) findViewById(R.id.chart);

        int numValues = 6;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        pieChartData = new PieChartData(values);
        pieChartData.setHasLabels(true);

        pieChartView.setPieChartData(pieChartData);
        */

        txtJson = (TextView) findViewById(R.id.tvJsonItem);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent preferencesActivity = new Intent(this, Preferences.class);
            startActivity(preferencesActivity);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    @NonNull
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }
                return stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            if (pd.isShowing()) {
                pd.dismiss();
            }

            // Gets this json from https://blockchain.info/ticker and gets the values of the USD json object and print them out in a TextView
            /*
            try {
                JSONObject jObject = new JSONObject(result);
                JSONObject currencyUSDObject = jObject.getJSONObject("USD");

                double delayed15mUSD = currencyUSDObject.getDouble("15m");
                double lastUSD = currencyUSDObject.getDouble("last");
                double buyUSD = currencyUSDObject.getDouble("buy");
                double sellUSD = currencyUSDObject.getDouble("sell");
                String symbolUSD = currencyUSDObject.getString("symbol");

                txtJson.setText(symbolUSD + "\n15m: " + String.valueOf(delayed15mUSD) + "\nLast: " + String.valueOf(lastUSD)
                        + "\nBuy: " + String.valueOf(buyUSD) + "\nSell: " + String.valueOf(sellUSD));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            */

            // Gets a json from https://blockchain.info/pools?format=json then displays the values as a pie chart with the happycharts library
            /*
            try {
                JSONObject jObject = new JSONObject(result);

                int jObjectLength = jObject.length();
                pieChartView = (PieChartView) findViewById(R.id.pieChartView);

                List<SliceValue> values = new ArrayList<SliceValue>();
                for (int i = 0; i < jObjectLength; ++i) {


                    int jObjectValue = jObject.getInt(jObjectName.toString());

                    SliceValue sliceValue = new SliceValue((float) jObjectValue, ChartUtils.pickColor());
                    sliceValue.setLabel(jObjectName.toString());
                    values.add(sliceValue);
                }

                pieChartData = new PieChartData(values);
                pieChartData.setHasLabels(true);
                pieChartView.setPieChartData(pieChartData);
                txtJson.setText(String.valueOf(jObjectLength));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            */


            try{
                final JSONObject jObject = new JSONObject(result);

                //int jObjectLength = jObject.length();

                final int numberOfLines = 1;
                //int numberOfPoints = 12;

                lineChartView = (LineChartView) findViewById(R.id.linearChartView);

                final JSONArray jObjectArray = jObject.getJSONArray("values");
                final int jObjectArrayLength = jObjectArray.length();
                final int numberOfPoints = jObjectArrayLength / 2;
                Log.e("TAG jObjectArrayLength", String.valueOf(jObjectArrayLength));

                for (int i = 0; i < numberOfPoints; ++i) {

                    JSONObject getPointsObject = jObjectArray.getJSONObject(i);
                    Log.e("TAG xValueDouble", String.valueOf(getPointsObject.getDouble("x")));
                    float xValue = (float) getPointsObject.getDouble("x");
                    float yValue = (float) getPointsObject.getDouble("y");
                    Log.e("TAG xValue", String.valueOf(xValue + ", " + yValue));
                    Log.e("TAG i", String.valueOf(i));

                    /*for (int x = 0; x < numberOfLines; ++x) {

                    }*/

                    List<PointValue> values = new ArrayList<PointValue>();

                    //jObjectArrayLength == numberOfPoints
                    for (int j = 0; j < 12; ++j) {
                        values.add(new PointValue(xValue, yValue));
                    }

                    Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
                    line.setStrokeWidth(3);

                    List<Line> lines = new ArrayList<Line>();
                    lines.add(line);

                    final Viewport v = new Viewport(lineChartView.getMaximumViewport());
                    v.bottom = -5;
                    v.top = xValue + 100;
                    // You have to set max and current viewports separately.
                    lineChartView.setMaximumViewport(v);
                    // I changing current viewport with animation in this case.
                    lineChartView.setCurrentViewportWithAnimation(v);

                    lineChartData = new LineChartData(lines);
                    lineChartView.setLineChartData(lineChartData);

                }
                txtJson.setText(String.valueOf(jObjectArrayLength));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
