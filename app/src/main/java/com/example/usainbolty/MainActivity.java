package com.example.usainbolty;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem param1MenuItem) {
            TipsFrag tipsFrag;
            TxtFrag txtFrag;
            CalcFrag calcFrag;
            switch (param1MenuItem.getItemId()) {
                case R.id.navigation_calc:
                    calcFrag = new CalcFrag();
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, calcFrag).commit();
                    return true;
                case R.id.navigation_tips:
                    tipsFrag = new TipsFrag();
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, tipsFrag).commit();
                    return true;
                case R.id.navigation_txt:
                    txtFrag = new TxtFrag();
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, txtFrag).commit();
                    return true;
            }
            return true;
        }
    };

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.main_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ((BottomNavigationView)findViewById(R.id.nav_view)).setOnNavigationItemSelectedListener(this.navListener);
        if (paramBundle == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CalcFrag()).commit();

    }
}


