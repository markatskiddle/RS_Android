package com.example.markprime.rs_android.scan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.markprime.rs_android.R;
import com.example.markprime.rs_android.scan.eventsFragment.EventsFragment;
import com.example.markprime.rs_android.scan.loginFragment.LoginFragment;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;


import java.util.List;

public class HomeActivity extends AppCompatActivity implements FragmentInteractionListener {


    private DrawerLayout dl_home;
    private FrameLayout fl_home;
    private LinearLayout ll_menu_home, ll_menu, ll_menu_main, ll_profile, ll_profile_container, ll_menu_scanner_container,
            ll_menu_my_events_container, ll_menu_tickets_container, ll_menu_statistics_container,
            ll_menu_setting_container, ll_menu_help_container;
    private TextView tv_profile_name, menu_tv_scanner, menu_tv_my_events, menu_tv_tickets,
            menu_tv_statistics, menu_tv_settings, menu_tv_help;
    private RelativeLayout rl_menu;

    public FragmentManager fm;
    private FragmentTransaction transaction;

    private Context mContext;

    public Fragment fragment_login;

    private boolean drawerOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = getApplicationContext();


//        setupButtons();
//        setDrawerLayout();


        setupFM();

        setupViews();

        setupMenu();

        setInitialFragment();



    }

    private void setupFM(){
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                List<Fragment> frags = fm.getFragments();
                final int len = frags.size();
                Fragment f;

                for (int i = 0; i < len; i++) {
                    try {
                        f = frags.get(i);
                        if (f != null) {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setDrawerLayout() {
        dl_home.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                float moveFactor = (ll_menu.getWidth() * slideOffset);
                ll_menu.setTranslationX(moveFactor);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() { super.onPause(); }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setInitialFragment(){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(fl_home.getId(), LoginFragment.newInstance());
        transaction.commit();
    }

    private void setupViews(){
        dl_home = findViewById(R.id.dl_home);
        fl_home = findViewById(R.id.fl_home);
        rl_menu = findViewById(R.id.rl_menu);
        ll_menu_home = findViewById(R.id.ll_menu_home);
        ll_menu = findViewById(R.id.ll_menu);
        ll_menu_main = findViewById(R.id.ll_menu_main);
        ll_profile = findViewById(R.id.ll_profile);
        ll_profile_container = findViewById(R.id.ll_profile_contaner);
        ll_menu_scanner_container = findViewById(R.id.ll_menu_scanner_container);
        ll_menu_my_events_container = findViewById(R.id.ll_menu_my_events_container);
        ll_menu_tickets_container = findViewById(R.id.ll_menu_tickets_container);
        ll_menu_statistics_container=findViewById(R.id.ll_menu_statistics_container);
        ll_menu_setting_container= findViewById(R.id.ll_menu_setting_container);
        ll_menu_help_container=findViewById(R.id.ll_menu_help_container);
        tv_profile_name = findViewById(R.id.tv_profile_name);
        menu_tv_scanner = findViewById(R.id.menu_tv_scanner);
        menu_tv_my_events = findViewById(R.id.menu_tv_my_events);
        menu_tv_tickets = findViewById(R.id.menu_tv_tickets);
        menu_tv_statistics = findViewById(R.id.menu_tv_statistics);
        menu_tv_settings = findViewById(R.id.menu_tv_settings);
        menu_tv_help = findViewById(R.id.menu_tv_help);
    }

    private void openDrawer() { dl_home.openDrawer(GravityCompat.START); }

    private void closeDrawer() { dl_home.closeDrawer(GravityCompat.START); }



    private void setupMenu() {
        ll_menu_scanner_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
                startScanner();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_menu_my_events_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
//                    setMenuPage(1);
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_menu_tickets_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
//                    setMenuPage();
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll_menu_statistics_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
//                    setMenuPage(0);
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll_menu_setting_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
//                    setMenuPage();
                   } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        ll_menu_help_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer();
                try {
                    Intent help = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.skiddle.com/help"));
                    startActivity(help);
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void startScanner() {
        Intent scanner = new Intent(this, ScannerActivity.class);
        startActivity(scanner);

    }

    private void setMenuPage(final int page) throws Exception {
        transaction = fm.beginTransaction();
        switch (page) {
            case 0:
                for(int i = 0; i < fm.getBackStackEntryCount(); i++){
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                setInitialFragment();
                break;
            case 1:
                EventBus.getDefault().post(new FragmentEvent("Events", null));
                break;
            case 2:
                EventBus.getDefault().post(new FragmentEvent("Tickets", null));
            case 3:
                EventBus.getDefault().post(new FragmentEvent("Settings", null));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNavigationEvent(FragmentEvent event) {
        transaction = fm.beginTransaction();
        switch (event.id) {
            case "Login":
                transaction.replace(fl_home.getId(), LoginFragment.newInstance());
                return;
            case "My Events":
                transaction.replace(fl_home.getId(), EventsFragment.newInstance());
                break;
//            case "My Tickets":
//                transaction.replace(fl_home.getId(), TicketsFragment.newInstance());
//                break;
//            case "Settings":
//                transaction.replace(fl_home.getId(), SettingsFragment.newInstance());
//                break;
        }

        try {
            transaction.addToBackStack(null);
            transaction.commit();
        } catch (Exception ee) {

        }
    }


    @Override
    public void showLoader() { }

    @Override
    public void dismissLoader() { }

    @Override
    public void setLoginFrag() { }

    @Override
    public void dismissKeyboard(EditText editText) { }



    @Override
    public void openEventsFragment() {
        fl_home.setVisibility(View.VISIBLE);
        transaction = fm.beginTransaction();
        transaction.replace(fl_home.getId(), EventsFragment.newInstance());
//                .addToBackStack(null);
        transaction.commit();
    }


}
