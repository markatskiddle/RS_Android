package com.example.markprime.rs_android.scan;

import android.widget.EditText;

public interface FragmentInteractionListener {

    void showLoader();
    void dismissLoader();
    void setLoginFrag();
    void dismissKeyboard(EditText editText);

}
