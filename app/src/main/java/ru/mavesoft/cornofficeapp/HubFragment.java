package ru.mavesoft.cornofficeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mavesoft.cornofficeapp.adapters.DevicesAdapter;
import ru.mavesoft.cornofficeapp.adapters.SensorsAdapter;
import ru.mavesoft.cornofficeapp.modules.Device;
import ru.mavesoft.cornofficeapp.modules.IStatus;
import ru.mavesoft.cornofficeapp.modules.ModuleAPI;
import ru.mavesoft.cornofficeapp.modules.SDType;
import ru.mavesoft.cornofficeapp.modules.Sensor;

public class HubFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    FirebaseUser firebaseUser;

    Retrofit retrofit;
    private String baseUrl = "https://beecoder-qr-code-entrance.herokuapp.com/";

    List<Sensor> sensorList;
    List<Device> deviceList;

    SensorsAdapter hubSensorsAdapter;
    DevicesAdapter hubDevicesAdapter;

    public HubFragment() {
        // Required empty public constructor
    }

    public static HubFragment newInstance(String param1, String param2) {
        HubFragment fragment = new HubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sensorList = new ArrayList<>();
        deviceList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView =  inflater.inflate(R.layout.fragment_hub, container, false);

        hubSensorsAdapter = new SensorsAdapter(fragmentView.getContext(), sensorList);
        hubDevicesAdapter = new DevicesAdapter(fragmentView.getContext(), deviceList);

        RecyclerView hubSensorsRecView = fragmentView.findViewById(R.id.hub_sensors);
        RecyclerView hubDevicesRecView = fragmentView.findViewById(R.id.hub_devices);

        GridLayoutManager gridLayoutManager
                = new GridLayoutManager(fragmentView.getContext(), 2,
                                        GridLayoutManager.VERTICAL, false);

        hubSensorsRecView.setLayoutManager(gridLayoutManager);
        hubSensorsRecView.setAdapter(hubSensorsAdapter);

        hubDevicesRecView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        hubDevicesRecView.setAdapter(hubDevicesAdapter);

        updateHub();

        return fragmentView;
    }

    private void updateHub() {
        ModuleAPI moduleAPI = retrofit.create(ModuleAPI.class);
        Call<List<IStatus>> hubCall = moduleAPI.getUserGadgets(firebaseUser.getEmail());
        hubCall.enqueue(new Callback<List<IStatus>>() {
            @Override
            public void onResponse(Call<List<IStatus>> call, Response<List<IStatus>> response) {

                if (response.body() != null) {
                    deviceList = new ArrayList<>();
                    sensorList = new ArrayList<>();

                    for (IStatus module : response.body()) {
                        if (module.getType().contains("sensor")) {
                            sensorList.add(new Sensor(module.getValue(), SDType.valueOf(module.getType()),
                                    module.getLocation(), module.isActive()));
                        } else {
                            deviceList.add(new Device(module.getName(), module.getId(), module.getValue(),
                                    module.getType(), module.getLocation(), module.isActive()));
                        }
                    }

                    hubSensorsAdapter.setSensorList(sensorList);
                    hubDevicesAdapter.setDeviceList(deviceList);

                    hubSensorsAdapter.notifyDataSetChanged();
                    hubDevicesAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<IStatus>> call, Throwable t) {

            }
        });
    }

}