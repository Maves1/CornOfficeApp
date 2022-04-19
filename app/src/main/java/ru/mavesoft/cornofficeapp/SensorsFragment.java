package ru.mavesoft.cornofficeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import ru.mavesoft.cornofficeapp.adapters.SensorsAdapter;
import ru.mavesoft.cornofficeapp.modules.ModuleAPI;
import ru.mavesoft.cornofficeapp.modules.Sensor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SensorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SensorsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Retrofit retrofit;
    private String baseUrl = "https://beecoder-qr-code-entrance.herokuapp.com/";

    private SwipeRefreshLayout swipeRefreshLayout;
    private SensorsAdapter sensorsAdapter;

    public SensorsFragment() {

    }

    public static SensorsFragment newInstance(String param1, String param2) {
        SensorsFragment fragment = new SensorsFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_sensors, container, false);

        swipeRefreshLayout = fragmentView.findViewById(R.id.sensorsSwipeRefreshLayout);
        RecyclerView sensorsRecView = fragmentView.findViewById(R.id.sensors_rec_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        this.swipeRefreshLayout.setRefreshing(true);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(fragmentView.getContext(), 2,
                                      GridLayoutManager.VERTICAL, false);


        List<Sensor> sensors = new ArrayList<>();
        sensorsAdapter = new SensorsAdapter(getContext(), sensors);
        sensorsRecView.setLayoutManager(gridLayoutManager);
        sensorsRecView.setAdapter(sensorsAdapter);
        updateSensors(sensorsAdapter, this);

        return fragmentView;
    }

    void updateSensors(SensorsAdapter sensorsAdapter, SensorsFragment sensorsFragment) {
/*        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 4 + 20)), "TemperatureSensor", "Workspace", true));
        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 5 + 48)), "HumiditySensor", "Workspace", true));
        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 4 + 20)), "TemperatureSensor", "Kitchen", true));*/

        ModuleAPI moduleAPI = retrofit.create(ModuleAPI.class);
        Call<List<Sensor>> sensorsCall = moduleAPI.getSensors();
        sensorsCall.enqueue(new Callback<List<Sensor>>() {
            @Override
            public void onResponse(Call<List<Sensor>> call, Response<List<Sensor>> response) {
                Log.d("ResponseSensors", response.body().get(0).getLocation());
                sensorsAdapter.setSensorList(response.body());
                sensorsAdapter.notifyDataSetChanged();
                sensorsFragment.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Sensor>> call, Throwable t) {
                Log.e("Sensors Update", t.toString());
                sensorsFragment.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        updateSensors(sensorsAdapter, this);
    }
}