package com.example.usainbolty;



import static com.example.usainbolty.allMassives.Massiv2.LL2;
import static com.example.usainbolty.allMassives.Massiv1.LL1;
import static com.example.usainbolty.allMassives.Massiv3.LL3;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MapFrag extends Fragment implements UserLocationObjectListener {

    private MapView mapview;
    private UserLocationLayer userLocationLayer;
    public MapFrag (){ }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MapKitFactory.initialize(getContext());
        MapKit mapKit = MapKitFactory.getInstance();
        mapview = getActivity().findViewById(R.id.mapview);


        int i=0;
        int j=0;

        mapview.getMap().setNightModeEnabled(MainActivity.b == 1);
        mapview.getMap().move(
                new CameraPosition(new Point(55.160607, 61.370242), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        userLocationLayer = mapKit.createUserLocationLayer(mapview.getMapWindow());
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
        i=0;

        for(int q=0;q<2000;q++){
            mapview.getMap().getMapObjects().addPlacemark(new Point(LL1[i], LL1[i+1]));
            mapview.getMap().getMapObjects().addPlacemark(new Point(LL2[i], LL2[i+1]));
            mapview.getMap().getMapObjects().addPlacemark(new Point(LL3[i], LL3[i+1]));
            i=i+2;


        }

        mapview.getMap().getMapObjects().addPlacemark(new Point(55.160607, 61.370242),ImageProvider.fromResource(getContext(),R.drawable.redcrcl,true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.map_frag, container, false);
        return view;
    }
    @Override
    public void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }


    @Override
    public void onObjectAdded(UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.5)),
                new PointF((float)(mapview.getWidth() * 0.5), (float)(mapview.getHeight() * 0.83)));

        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                getContext(), R.drawable.ic_baseline_accessible_24));
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                getContext(), R.drawable.ic_baseline_accessible_24));
        userLocationView.getAccuracyCircle().setFillColor(Color.RED);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
