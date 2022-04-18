package ru.mavesoft.cornofficeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

import ru.mavesoft.cornofficeapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    enum Fragments {
        Sensors,
        Devices,
        Booking,
        Hub
    }

    ActivityMainBinding binding;
    FrameLayout fragmentContainer;
    FloatingActionButton btnScanQR;

    Map<Integer, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentMap = createFragments();

        fragmentContainer = binding.fragmentContainer;
        btnScanQR = binding.btnScanQR;

        switchFragment(R.id.menu_hub);
        binding.bottomNavBar.setSelectedItemId(R.id.menu_hub);

        binding.bottomNavBar.setOnItemSelectedListener(item -> {

            switchFragment(item.getItemId());

            return true;
        });

        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ScannerActivity.class);
                startActivity(intent);
            }
        });
    }

    private Map<Integer, Fragment> createFragments() {
        Map<Integer, Fragment> fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.menu_sensors, new SensorsFragment());
        fragmentMap.put(R.id.menu_devices, new DevicesFragment());
        fragmentMap.put(R.id.menu_booking, new BookingFragment());
        fragmentMap.put(R.id.menu_hub, new HubFragment());

        return fragmentMap;
    }

    private void switchFragment(int fragmentID) {
        if (fragmentMap.containsKey(fragmentID)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, fragmentMap.get(fragmentID));
            fragmentTransaction.commit();
        }
    }
}