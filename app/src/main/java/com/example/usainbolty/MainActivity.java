package com.example.usainbolty;

import static com.example.usainbolty.CalcFrag.viewPager;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Build.VERSION;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.metrics.Event;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.example.usainbolty.radio.MainGUI;
import com.example.usainbolty.radio.com_api;
import com.example.usainbolty.radio.com_uti;
import com.example.usainbolty.radio.gui_gap;
import com.example.usainbolty.radio.svc_aud;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yandex.mapkit.MapKitFactory;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView nav;

    public static int b=0;
    public static int qw=0;
    Toolbar toolbar1;
    public static boolean logged=false;
    TipsFrag tipsFrag = new TipsFrag();
    TxtFrag txtFrag = new TxtFrag();
    CalcFrag calcFrag = new CalcFrag();
    MapFrag mapFrag = new MapFrag();
    ChatFrag chatFrag = new ChatFrag();
    public static Menu menu = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    private static int m_obinits = 0;
    private static int m_creates = 0;

    public static Context mContext;
    public static com_api m_com_api = null;

    private gui_gap mGUI = null;
    private static BroadcastReceiver mBroadcastReceiver = null;


    private final BottomNavigationView.OnItemSelectedListener navListener = param1MenuItem -> {
        switch (param1MenuItem.getItemId()) {
            case R.id.navigation_calc:
                menu.findItem(R.id.arrow_back).setVisible(false);
                menu.findItem(R.id.arrow_forward).setVisible(false);
                getSupportActionBar().setTitle("Счётчик");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, calcFrag).commit();
                return true;
            case R.id.navigation_tips:
                menu.findItem(R.id.arrow_back).setVisible(false);
                menu.findItem(R.id.arrow_forward).setVisible(false);
                getSupportActionBar().setTitle("Советы");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, tipsFrag).commit();
                return true;
            case R.id.navigation_txt:
                menu.findItem(R.id.arrow_back).setVisible(false);
                menu.findItem(R.id.arrow_forward).setVisible(false);
                getSupportActionBar().setTitle("Информация");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, txtFrag).commit();
                return true;
            case R.id.navigation_map:
                menu.findItem(R.id.arrow_back).setVisible(false);
                menu.findItem(R.id.arrow_forward).setVisible(false);
                getSupportActionBar().setTitle("Карта");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,mapFrag ).commit();
                return true;
            case R.id.navigation_chat:
                menu.findItem(R.id.arrow_back).setVisible(false);
                menu.findItem(R.id.arrow_forward).setVisible(false);
                getSupportActionBar().setTitle("Чат");
                MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, chatFrag).commit();
                return true;
        }
        return true;
    };

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle paramBundle) {
        m_creates++;
        com_uti.logd("m_creates: " + m_creates);

        mContext = this;

        com_uti.logd("mContext: " + mContext);
        com_uti.logd("m_com_api: " + m_com_api);
        com_uti.logd("mBroadcastReceiver: " + mBroadcastReceiver);
        com_uti.logd("mGUI: " + mGUI);

        com_uti.logd("SpiritF " + com_uti.getApplicationVersion(mContext));

        com_uti.logd("savedInstanceState: " + paramBundle);
/*
		if (m_com_api == null) { // If a receiver has not initialized yet...
			m_com_api = new com_api(mContext); // Instantiate Common API
			com_uti.logd("m_com_api: " + m_com_api);
		}
*/
        super.onCreate(paramBundle);
        if (m_com_api == null) { // If a receiver has not initialized yet...
            m_com_api = new com_api(mContext); // Instantiate Common API
            com_uti.logd("m_com_api: " + m_com_api);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        nav = findViewById(R.id.nav_view);
        if(!logged){
            TapTargetView.showFor(this,
                    TapTarget.forView(nav,"Панель Навигации","Здесь вы можете моментально выбирать инетересный вам раздел приложения и переходить к нему")
                            .outerCircleColor(R.color.teal_200)
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(30)
                            .titleTextColor(R.color.white)
                            .descriptionTextSize(20)
                            .descriptionTextColor(R.color.black)
                            .textColor(R.color.black)
                            .textTypeface(Typeface.SANS_SERIF)
                            .dimColor(R.color.black)
                            .drawShadow(true)
                            .cancelable(true)
                            .tintTarget(true)
                            .transparentTarget(true)
                            .targetRadius(80),
                    new TapTargetView.Listener(){

                        @Override
                        public void onTargetClick(TapTargetView view) {
                            super.onTargetClick(view);

                        }
                    });
        }

        createNotificationChannel();
        Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long currentTime = System.currentTimeMillis();
        long tensec = 1000*10;
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                currentTime+tensec,pendingIntent);
        if(!logged)
            MapKitFactory.setApiKey("9c0ec42f-f0a8-4782-b454-d9e0c2f42780");
        if(!logged) {
            Ahtung dialog = new Ahtung();
            dialog.show(getSupportFragmentManager(), "custom");
            logged=true;
        }
        getSupportActionBar().setTitle("Выбор устройства");
        setContentView(R.layout.main_activity);
        ((BottomNavigationView) findViewById(R.id.nav_view)).setOnItemSelectedListener(this.navListener);
        if (paramBundle == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CalcFrag()).commit();
        }
        setVolumeControlStream(svc_aud.audio_stream);

        receiverStart(); // Start Common API Broadcast Receiver

//    m_com_api.chass_plug_aud = com_uti.chass_plug_aud_get (mContext);  // Setup Audio Plugin
//    m_com_api.chass_plug_tnr = com_uti.chass_plug_tnr_get (mContext);  // Setup Tuner Plugin

        startGUI();

    }

    private void createNotificationChannel(){
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
           CharSequence name = "reminderChannel";
           String description = "fff";
           int importance =  NotificationManager.IMPORTANCE_DEFAULT;
           NotificationChannel channel = new NotificationChannel("Notify",name,importance);
           channel.setDescription(description);
           NotificationManager notificationManager = getSystemService(NotificationManager.class);
           notificationManager.createNotificationChannel(channel);




        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calc_menu,menu);
        MainActivity.menu = menu;

        menu.findItem(R.id.arrow_back).setVisible(false);
        menu.findItem(R.id.arrow_forward).setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.theme) {
            if(b==0){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                b=1;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                b=0;
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }


        }
        switch (item.getItemId()) {
            case R.id.arrow_back:
                menu.findItem(R.id.arrow_back).setVisible(true);
                menu.findItem(R.id.arrow_forward).setVisible(true);
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1,true);
                switch (viewPager.getCurrentItem()){
                    case 1:
                        getSupportActionBar().setTitle("Выбор устройства");
                        item.setVisible(false);
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Время и расстояние");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + viewPager.getCurrentItem());
                }
                return true;
            case R.id.arrow_forward:
                menu.findItem(R.id.arrow_back).setVisible(true);
                menu.findItem(R.id.arrow_forward).setVisible(true);
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1,true);
                switch (viewPager.getCurrentItem()){
                    case 2:
                        getSupportActionBar().setTitle("Время и расстояние");
                        break;
                    case 3:
                        getSupportActionBar().setTitle("Результат");
                        item.setVisible(false);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + viewPager.getCurrentItem());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    public MainActivity() { // empty constructor ?
        m_obinits++;
        com_uti.logd("m_obinits: " + m_obinits);

        mContext = this;
        com_uti.logd("mContext: " + mContext);
        com_uti.logd("m_com_api: " + m_com_api);
        com_uti.logd("mBroadcastReceiver: " + mBroadcastReceiver);
        com_uti.logd("mGUI: " + mGUI);

        // Disabled due to remaining main thread issues
        // strict_mode_set(true);

        if (m_com_api == null) { // If a receiver has not initialized yet...
            m_com_api = new com_api(this); // Instantiate Common API class
        }

        //    m_com_api.chass_plug_aud = com_uti.chass_plug_aud_get (mContext);  // Setup Audio Plugin
        //    m_com_api.chass_plug_tnr = com_uti.chass_plug_tnr_get (mContext);  // Setup Tuner Plugin
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * !! Resume can happen with the FM power off, so try not to do things needing power on
     */
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * Restart comes between Stop and Start or when returning to the app
     */
    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        // One of these caused crashes; added try / catch below
        receiverStop();
        stopGUI();

        com_uti.logd("com_uti.num_daemon_get:              " + com_uti.num_daemon_get);
        com_uti.logd("com_uti.num_daemon_set:              " + com_uti.num_daemon_set);

        if (m_com_api != null) {
            com_uti.logd("m_com_api.num_key_set:             " + m_com_api.num_key_set);
            com_uti.logd("m_com_api.num_api_service_update:  " + m_com_api.num_api_service_update);
        }

        /*
         * super.onDestroy dismisses any dialogs or cursors the activity was managing. If the logic in onDestroy has
         * something to do with these things, then order may matter.
         */

        super.onDestroy();
    }


    private void startGUI() {
        try {
            // Instantiate UI
            mGUI = new MainGUI(mContext, m_com_api);
            if (mGUI == null) {
                com_uti.loge("mGUI == null");
            } else if (!mGUI.gap_state_set("Start")) {                   // Start UI. If error...
                com_uti.loge("startGUI error");
                mGUI = null;
            } else {
                com_uti.logd("startGUI OK");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void stopGUI() {
        try {
            if (mGUI == null) {
                com_uti.loge("already stopped");
            } else if (!mGUI.gap_state_set("Stop")) {
                com_uti.loge("stopGUI error"); // Stop UI. If error...
            } else {
                com_uti.logd("stopGUI OK");
            }
            mGUI = null;
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * Common API Intent result & notification Broadcast Receiver
     */
    private void receiverStart() {
        if (mBroadcastReceiver == null) {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    com_uti.logv("intent: " + intent + "  action: " + action);
                    if (!action.equals(com_uti.api_result_id)) { // If not for us then done
                        return;
                    }

                    if (m_com_api != null && mGUI != null) {
                        m_com_api.api_service_update(intent);
                        mGUI.gap_service_update(intent);
                    }
                }
            };

            IntentFilter filter = new IntentFilter();
            filter.addAction(com_uti.api_result_id); // Can add more actions if needed
            filter.addCategory(Intent.CATEGORY_DEFAULT);

            Intent last_sticky_state_intent = null;
            if (mContext != null) {
                // No permission, no handler scheduler thread.
                last_sticky_state_intent = mContext.registerReceiver(mBroadcastReceiver, filter, null, null);
            }
            if (last_sticky_state_intent != null) {
                com_uti.logd("bcast intent last_sticky_state_intent: " + last_sticky_state_intent);
                //mBroadcastReceiver.onReceive(mContext, last_sticky_state_intent);// Like a resend of last audio status update
            }
        }
    }

    private void receiverStop() {
        if (mBroadcastReceiver != null && mContext != null) {
            // Remove the State listener
            try {
                // Caused by: java.lang.IllegalArgumentException: Receiver not registered: fm.a2d.sf.MainActivity$1@42549158
                mContext.unregisterReceiver(mBroadcastReceiver);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        mBroadcastReceiver = null;
    }


    // Dialog methods:

    // Create a dialog by calling specific *_dialog_create function    ; Triggered by showDialog (int id);
    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        com_uti.logd("id: " + id + "  args: " + args);
        Dialog dlg_ret = null;
        if (mGUI != null)
            dlg_ret = mGUI.createDialog(id, args);
        com_uti.logd("dlg_ret: " + dlg_ret);
        return (dlg_ret);
    }

    // See res/layout/gui_pg2_layout.xml
    public void onClicked(View v) {
        mGUI.onClicked(v);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
}


