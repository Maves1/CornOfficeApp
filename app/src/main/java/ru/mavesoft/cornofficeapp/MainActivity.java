package ru.mavesoft.cornofficeapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
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

    int qrEntryRequestCode = 3;

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
                startActivityForResult(intent, qrEntryRequestCode);
            }
        });

        btnScanQR.setBackgroundColor(getColor(R.color.purple_200));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == qrEntryRequestCode && resultCode == RESULT_OK) {
            btnScanQR.setBackgroundColor(
                    getResources().getColor(com.google.android.material.R.color.design_default_color_background));

        }
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