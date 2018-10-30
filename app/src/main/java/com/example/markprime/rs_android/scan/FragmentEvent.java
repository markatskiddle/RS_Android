package com.example.markprime.rs_android.scan;

import android.support.annotation.Nullable;

public class FragmentEvent {

    public final String id;
    public final Object payload;

    public FragmentEvent(String id, @Nullable Object payload) {
        this.id = id;
        this.payload = payload;
    }


}
