package com.example.apiconsumption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

public class information extends AppCompatActivity {
Button back,searcho;
TextView namec,capital,region,subregion,population,borders,languages,textView1,flagurltv;
ImageView flag;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    private RequestBuilder<PictureDrawable> requestBuilder;
    public static final String TAG = "MyTag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        namec=findViewById(R.id.name_tv);
        capital=findViewById(R.id.capital_tv);
        region=findViewById(R.id.region_tv);
        subregion=findViewById(R.id.subregion_tv);
        population=findViewById(R.id.population_tv);
        borders=findViewById(R.id.borders_tv);
        languages=findViewById(R.id.languages_tv);
        textView1=findViewById(R.id.textview1);
        flagurltv=findViewById(R.id.url_tv);
        //flag=findViewById(R.id.flag);



        String code_name=getIntent().getStringExtra("code_name");
        Toast.makeText(this, code_name, Toast.LENGTH_SHORT).show();
        String url="https://restcountries.eu/rest/v2/name/"+code_name+"?fullText=true";
        jsonArray(url);


        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(information.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void jsonArray(String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //textView1.setText("Response is: "+ response.toString());
                try {
                    namec.setText(response.getJSONObject(0).getString("name"));
                    capital.setText(response.getJSONObject(0).getString("capital"));
                    region.setText(response.getJSONObject(0).getString("region"));
                    subregion.setText(response.getJSONObject(0).getString("subregion"));
                    population.setText(String.valueOf(response.getJSONObject(0).getInt("population")));
                    borders.setText(response.getJSONObject(0).getJSONArray("borders").toString());
                    JSONArray lang=response.getJSONObject(0).getJSONArray("languages");
                    String lango="";
                    for(int i=0;i<lang.length();i++){
                        lango =lango+lang.getJSONObject(i).getString("name")+" ";
                    }
                    languages.setText(lango);
                    String flagurl=response.getJSONObject(0).getString("flag").replace("\\","").trim();
                    flagurltv.setText(flagurl);
                    //Glide.with(information.this).load(flagurl2).into(flag);
                    //GlideApp.with(information.this).load( flagurl).centerCrop().into(flag);




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                textView1.setText("Please Enter the correct Name");

            }
        });
        queue.add(jsonArrayRequest);

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

}