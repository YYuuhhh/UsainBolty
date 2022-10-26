package com.example.usainbolty;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class welcome_active extends AppCompatActivity {


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
                         Intent intent = new Intent(welcome_active.this,MainActivity.class);
                         startActivity(intent);
                     }

                     @Override
                     public void onPermissionDenied(List<String> deniedPermissions) {
                         Toast.makeText(welcome_active.this, "пошел нахуй\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                     }


                 };
                 TedPermission.create()
                         .setPermissionListener(permissionlistener)
                         .setDeniedMessage("Иди\n\n нахуй, врубай чурка [Setting] > [Permission]")
                         .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                         .check();
             }
         }
     };
     thread.start();

    }
}