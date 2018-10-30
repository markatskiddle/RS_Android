package com.example.markprime.rs_android.scan.loginFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.android.volley.VolleyError;
import com.example.markprime.rs_android.R;
import com.example.markprime.rs_android.scan.FragmentInteractionListener;
import com.example.markprime.rs_android.scan.ScannerActivity;
import com.example.markprime.rs_android.scan.networking.NetworkManager;
import com.example.markprime.rs_android.scan.networking.VolleySingletonErrorListener;
import com.example.markprime.rs_android.scan.networking.VolleySingletonListener;
import com.example.markprime.rs_android.scan.networking.VolleySingletonTimeOutListener;
import org.json.JSONObject;


public class LoginFragment extends Fragment {

    private TextView txt_login_title, txt_login_by, txt_login_to_skiddle,
            txt_login_new_account, txt_login_pc_url, txt_forgotten_password, txt_error;
    private EditText et_email, et_password;
    private ImageView iv_rs_logo, iv_skiddle_logo;
    private Button btn_login;
    private LinearLayout ll_main, ll_logo_container, ll_by_skiddle, ll_title_container;
    private boolean emailTouched = false, passwordTouched = false;
    private Context context;

    SharedPreferences sharedPreferences;

    FragmentInteractionListener fragmentInteractionListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        fragmentInteractionListener = (FragmentInteractionListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        isNetworkAvailable();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        setupTextViews(view);
        setupButtons(view);
        setupEditTexts(view);
        setupImageViews(view);

        ll_main = view.findViewById(R.id.ll_main);
        ll_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                fragmentInteractionListener.dismissKeyboard(et_email);
                fragmentInteractionListener.dismissKeyboard(et_password);

                if (emailTouched) {
                    if (!et_email.getText().toString().equals("") && !et_password.getText().toString().equals("")) {
                        if (!isValidEmail(et_email.getText().toString())) {

                        } else {

                        }
                    } else {

                    }
                }
                if (passwordTouched) {
                    if (et_password.getText().toString().length() < 8) {

                    } else {

                    }
                }

                return false;
            }
        });

        et_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                emailTouched = true;
                return false;
            }
        });


        et_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                passwordTouched = true;
                return false;
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentInteractionListener.dismissKeyboard(et_email);
        fragmentInteractionListener.dismissKeyboard(et_password);
    }

    private void setupTextViews(View view) {
        txt_login_title = view.findViewById(R.id.txt_login_title);
        txt_login_by = view.findViewById(R.id.txt_login_by);
        txt_login_to_skiddle = view.findViewById(R.id.txt_login_to_skiddle);
        txt_login_new_account = view.findViewById(R.id.txt_login_new_account);
        txt_login_pc_url = view.findViewById(R.id.txt_login_pc_url);
        txt_forgotten_password = view.findViewById(R.id.txt_forgotten_password);
        txt_error = view.findViewById(R.id.txt_error);

        txt_forgotten_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://promotioncentre.co.uk/recover-password.php"));
                startActivity(intent);
            }
        });
    }

    private void setupButtons(View view) {
        btn_login = view.findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInteractionListener.dismissKeyboard(et_email);
                fragmentInteractionListener.dismissKeyboard(et_password);
                String URL = "https://www.skiddle.com/api/v1/promoter/authenticate/?api_key=5490be161354c5c440beff5bef88175a";
                txt_error.setVisibility(View.INVISIBLE);
                if (validateFields()) {
                    fragmentInteractionListener.showLoader();
                    NetworkManager.getInstance(getContext().getApplicationContext()).loginPostRequest(URL, new VolleySingletonListener<JSONObject>() {
                        @Override
                        public void onResult(JSONObject object) {
//                            BaseActivity.printOut("Login Network Call", object);
                            try {
//                                saveUserDetails(object.getJSONObject("results"));
                                loadScanner();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                switch (object.getInt("error")) {
                                    case 0:
                                        //There is no error, continue
//                                        saveUserDetails(object.getJSONObject("results"));
                                        break;
                                    case 1:
                                        //There was an error
//                                        BaseActivity.printOut("Login object", object.toString());
                                        txt_error.setVisibility(View.VISIBLE);
                                        txt_error.setText(getActivity().getString(R.string.login_details_error));
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, et_email.getText().toString(), et_password.getText().toString(), txt_error, getActivity(),
                            new VolleySingletonErrorListener() {
                        @Override
                        public void onErrorResult(VolleyError object) {
                            object.printStackTrace();
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentInteractionListener.dismissLoader();
                                    }
                                });
                            }
                        }
                    }, new VolleySingletonTimeOutListener() {
                        @Override
                        public void onTimeoutResult(Exception object) {
                            object.printStackTrace();
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fragmentInteractionListener.dismissLoader();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

    }

    private void setupEditTexts(View view) {
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
    }

    private void setupImageViews(View view) {
        iv_rs_logo = view.findViewById(R.id.iv_rs_logo);
        iv_skiddle_logo = view.findViewById(R.id.iv_skiddle_logo);
    }

//    private void saveUserDetails(JSONObject object) throws Exception {
//
//
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("PROMOTER_ID", object.getInt("promoterid"));
//        editor.putString("PACCESS_TOKEN", object.getString("paccess_token"));
//
//        editor.putBoolean("LOGGED_IN", true);
//        editor.putBoolean("FIRST_LOAD", false);
//        editor.apply();
//    }

    private void loadScanner(){



    }

    private boolean validateFields() {

        if (!et_email.getText().toString().equals("") && !et_password.getText().toString().equals("")) {

            if (isValidEmail(et_email.getText().toString())) {

                if (et_password.getText().toString().length() > 7) {
                    return true;
                } else {
                    txt_error.setVisibility(View.VISIBLE);
                    txt_error.setText(getActivity().getString(R.string.login_password_advice));
                    return false;
                }
            } else {
                txt_error.setVisibility(View.VISIBLE);
                txt_error.setText(getActivity().getString(R.string.login_email_advice));
                return false;
            }

        } else {
            txt_error.setVisibility(View.VISIBLE);
            txt_error.setText(getActivity().getString(R.string.login_error_empty_fields));
            return false;
        }
    }

    private boolean isValidEmail(String target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    private boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connectivityManager.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

}