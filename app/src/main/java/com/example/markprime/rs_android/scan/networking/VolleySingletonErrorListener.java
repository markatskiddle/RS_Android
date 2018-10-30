package com.example.markprime.rs_android.scan.networking;

import com.android.volley.VolleyError;

public interface VolleySingletonErrorListener<Exception> {
    void onErrorResult(VolleyError object);
}