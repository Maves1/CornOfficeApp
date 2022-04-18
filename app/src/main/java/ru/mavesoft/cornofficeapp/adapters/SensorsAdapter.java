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

import java.util.List;

import ru.mavesoft.cornofficeapp.R;
import ru.mavesoft.cornofficeapp.modules.Sensor;

public class SensorsAdapter extends RecyclerView.Adapter<SensorsAdapter.ViewHolder> {

    // Temp
    private final String temperatureSensorType = "TemperatureSensor";
    private final String humiditySensorType = "HumiditySensor";

    private Context context;
    private List<Sensor> sensorList;

    public SensorsAdapter(Context context, List<Sensor> sensorList) {
        this.context = context;
        this.sensorList = sensorList;
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

        switch (sensorList.get(position).getType()) {
            case humiditySensorType:
                holder.getSensorIcon().setImageDrawable(context.getDrawable(R.drawable.ic_baseline_humidity_24));
                break;
            case temperatureSensorType:
                holder.getSensorIcon().setImageDrawable(context.getDrawable(R.drawable.ic_baseline_device_thermostat_24));
                break;
        }

        holder.getTextViewData().setText(sensorList.get(position).getValue());
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
