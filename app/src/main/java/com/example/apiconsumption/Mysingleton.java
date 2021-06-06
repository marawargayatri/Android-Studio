package com.example.apiconsumption;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static Mysingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mcon;

    private Mysingleton(Context mcontext){
        mcon=mcontext;
        requestQueue=getRequestQueue();

    }
    public RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue= Volley.newRequestQueue(mcon.getApplicationContext());

        }
        return requestQueue;
    }

    public static synchronized Mysingleton getInstance(Context mcon){
        if(mInstance==null){
            mInstance=new Mysingleton(mcon);
        }
        return mInstance;
    }
    public<T> void addtoRequestqueue(Request<T> request){
        requestQueue.add(request);
    }
}
