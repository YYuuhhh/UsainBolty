package com.example.usainbolty;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class WelcomeSplashScreen extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(getBaseContext(), "вы не увидите своего положения на карте\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                    }


                };
                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("Рекомендуем включить доступ, чтобы получить более полезную для вас информацию\n\n[Setting] > [Permission]")
                        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                        .check();
            }
        },3000);


    }
}
