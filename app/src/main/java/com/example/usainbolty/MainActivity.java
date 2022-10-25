package com.example.usainbolty;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {
    int b=0;
    TipsFrag tipsFrag = new TipsFrag();
    TxtFrag txtFrag = new TxtFrag();
    CalcFrag calcFrag = new CalcFrag();
    MapFrag mapFrag = new MapFrag();
    public static Menu menu = null;
    private FirebaseAnalytics mFirebaseAnalytics;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem param1MenuItem) {
            switch (param1MenuItem.getItemId()) {
                case R.id.navigation_calc:
                    menu.findItem(R.id.arrow_back).setEnabled(false);
                    menu.findItem(R.id.arrow_forward).setEnabled(false);
                    getSupportActionBar().setTitle("Выбор устройства");
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, calcFrag).commit();
                    return true;
                case R.id.navigation_tips:
                    menu.findItem(R.id.arrow_back).setEnabled(false);
                    menu.findItem(R.id.arrow_forward).setEnabled(false);
                    getSupportActionBar().setTitle("Советы");
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, tipsFrag).commit();
                    return true;
                case R.id.navigation_txt:
                    menu.findItem(R.id.arrow_back).setEnabled(false);
                    menu.findItem(R.id.arrow_forward).setEnabled(false);
                    getSupportActionBar().setTitle("Информация");
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, txtFrag).commit();
                    return true;
                case R.id.navigation_map:
                    menu.findItem(R.id.arrow_back).setEnabled(false);
                    menu.findItem(R.id.arrow_forward).setEnabled(false);
                    getSupportActionBar().setTitle("Карта");
                    MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, mapFrag).commit();
                    return true;
            }
            return true;
        }
    };

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.main_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Ahtung dialog = new Ahtung();
        dialog.show(getSupportFragmentManager(), "custom");
        getSupportActionBar().setTitle("Выбор устройства");
        ((BottomNavigationView) findViewById(R.id.nav_view)).setOnItemSelectedListener(this.navListener);
        if (paramBundle == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CalcFrag()).commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calc_menu,menu);
        MainActivity.menu = menu;

        menu.findItem(R.id.arrow_back).setEnabled(false);
        menu.findItem(R.id.arrow_forward).setEnabled(false);
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
                menu.findItem(R.id.arrow_back).setEnabled(true);
                menu.findItem(R.id.arrow_forward).setEnabled(true);
                CalcFrag.viewPager.setCurrentItem(CalcFrag.viewPager.getCurrentItem() - 1,false);
                switch (CalcFrag.viewPager.getCurrentItem()){
                    case 0:
                        getSupportActionBar().setTitle("Выбор устройства");
                        item.setEnabled(false);
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Время и расстояние");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + CalcFrag.viewPager.getCurrentItem());
                }
                return true;
            case R.id.arrow_forward:
                menu.findItem(R.id.arrow_back).setEnabled(true);
                menu.findItem(R.id.arrow_forward).setEnabled(true);
                CalcFrag.viewPager.setCurrentItem(CalcFrag.viewPager.getCurrentItem() + 1,false);
                switch (CalcFrag.viewPager.getCurrentItem()){
                    case 1:
                        getSupportActionBar().setTitle("Время и расстояние");
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Результат");
                        item.setEnabled(false);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + CalcFrag.viewPager.getCurrentItem());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


