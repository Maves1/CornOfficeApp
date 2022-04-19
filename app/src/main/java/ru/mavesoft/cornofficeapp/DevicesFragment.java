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
import ru.mavesoft.cornofficeapp.modules.ModuleAPI;
import ru.mavesoft.cornofficeapp.modules.Sensor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DevicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DevicesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Retrofit retrofit;

    SwipeRefreshLayout swipeRefreshLayout;
    DevicesAdapter devicesAdapter;

    private String baseUrl = "https://beecoder-qr-code-entrance.herokuapp.com/";

    public DevicesFragment() {
        // Required empty public constructor
    }

    public static DevicesFragment newInstance(String param1, String param2) {
        DevicesFragment fragment = new DevicesFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_devices, container, false);

        swipeRefreshLayout = fragmentView.findViewById(R.id.devicesSwipeRefreshLayout);
        RecyclerView devicesRecView = fragmentView.findViewById(R.id.devices_rec_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.setRefreshing(true);

        List<Device> devices = new ArrayList<>();
        devicesAdapter = new DevicesAdapter(getContext(), devices);
        devicesRecView.setLayoutManager(new LinearLayoutManager(fragmentView.getContext()));
        devicesRecView.setAdapter(devicesAdapter);
        updateDevices(devicesAdapter, this);

        return fragmentView;
    }

    @Override
    public void onRefresh() {
        updateDevices(devicesAdapter, this);
    }

    private void updateDevices(DevicesAdapter devicesAdapter, DevicesFragment fragment) {
        ModuleAPI moduleAPI = retrofit.create(ModuleAPI.class);
        Call<List<Device>> devicesCall = moduleAPI.getDevices();
        devicesCall.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                Log.d("ResponseDevices", response.body().get(0).toString());
                devicesAdapter.setDeviceList(response.body());
                devicesAdapter.notifyDataSetChanged();
                fragment.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {

            }
        });
    }
}