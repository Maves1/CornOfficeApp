package ru.mavesoft.cornofficeapp.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mavesoft.cornofficeapp.R;
import ru.mavesoft.cornofficeapp.modules.SDType;
import ru.mavesoft.cornofficeapp.modules.Sensor;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {

    private Context context;
    private List<Sensor> sensorList;

    private Map<SDType, Integer> icons;

    public SensorsAdapter(Context context, List<Sensor> sensorList) {
        this.context = context;
        this.sensorList = sensorList;

        icons = initializeIcons();
    }

    private Map<SDType, Integer> initializeIcons() {
        Map<SDType, Integer> icons = new HashMap<>();

        icons.put(SDType.temperature_sensor, R.drawable.ic_baseline_device_thermostat_24);
        icons.put(SDType.humidity_sensor, R.drawable.ic_baseline_humidity_24);

        return icons;
    }

    @NonNull
    @Override
    public SensorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sensor_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorsAdapter.ViewHolder holder, int position) {

        holder.getSensorIcon().setImageDrawable(context.getDrawable(icons.get(sensorList.get(position).getType())));

        String adder = "";
        switch (sensorList.get(position).getType()) {
            case temperature_sensor:
                adder = "°C";
                break;
            case humidity_sensor:
                adder = "%";
                break;
        }
        String value = Integer.toString(Math.round(Float.parseFloat(sensorList.get(position).getValue())));
        holder.getTextViewData().setText(value + adder);
        holder.getTextViewLocation().setText(sensorList.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return sensorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewLocation;
        private TextView textViewData;
        private ImageView sensorIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            textViewData = itemView.findViewById(R.id.textViewData);
            sensorIcon = itemView.findViewById(R.id.sensorIcon);
        }

        public TextView getTextViewLocation() {
            return textViewLocation;
        }

        public TextView getTextViewData() {
            return textViewData;
        }

        public ImageView getSensorIcon() {
            return sensorIcon;
        }
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }
}
