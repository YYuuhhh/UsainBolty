package com.example.usainbolty;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class WelcomeActive extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_active);
     Thread thread = new Thread(){


         @Override
         public void run() {
             super.run();
             try {
                 sleep(3000);
             }
             catch (Exception e){
                 e.printStackTrace();

             }
             finally {
                 PermissionListener permissionlistener = new PermissionListener() {
                     @Override
                     public void onPermissionGranted() {
                         Intent intent = new Intent(WelcomeActive.this,MainActivity.class);
                         startActivity(intent);
                     }

                     @Override
                     public void onPermissionDenied(List<String> deniedPermissions) {
                         Toast.makeText(WelcomeActive.this, "вы не увидите своего положения на карте\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(WelcomeActive.this,MainActivity.class);
                         startActivity(intent);
                     }


                 };
                 TedPermission.create()
                         .setPermissionListener(permissionlistener)
                         .setDeniedMessage("Рекомендуем включить доступ, чтобы получить более полезную для вас информацию\n\n[Setting] > [Permission]")
                         .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                         .setPermissions(Manifest.permission.RECORD_AUDIO)
                         .check();
             }
         }
     };
     thread.start();

    }
}