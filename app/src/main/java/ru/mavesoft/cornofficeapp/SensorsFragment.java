package ru.mavesoft.cornofficeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.mavesoft.cornofficeapp.adapters.SensorsAdapter;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_sensors, container, false);

        swipeRefreshLayout = fragmentView.findViewById(R.id.sensorsSwipeRefreshLayout);
        RecyclerView sensorsRecView = fragmentView.findViewById(R.id.sensors_rec_view);
        swipeRefreshLayout.setOnRefreshListener(this);

        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(fragmentView.getContext(), 2,
                                      GridLayoutManager.VERTICAL, false);


        List<Sensor> sensors = new ArrayList<>();
        sensorsAdapter = new SensorsAdapter(getContext(), sensors);
        getAndUpdateSensors(sensorsAdapter);
        sensorsRecView.setLayoutManager(gridLayoutManager);
        sensorsRecView.setAdapter(sensorsAdapter);

        return fragmentView;
    }

    List<Sensor> getAndUpdateSensors(SensorsAdapter sensorsAdapter) {
        List<Sensor> sensors = new ArrayList<>();

        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 4 + 20)), "TemperatureSensor", "Workspace", true));
        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 5 + 48)), "HumiditySensor", "Workspace", true));
        sensors.add(new Sensor(Integer.toString((int) (Math.random() * 4 + 20)), "TemperatureSensor", "Kitchen", true));

        sensorsAdapter.setSensorList(sensors);
        sensorsAdapter.notifyDataSetChanged();

        return sensors;
    }

    @Override
    public void onRefresh() {
        getAndUpdateSensors(sensorsAdapter);
        this.swipeRefreshLayout.setRefreshing(false);
    }
}