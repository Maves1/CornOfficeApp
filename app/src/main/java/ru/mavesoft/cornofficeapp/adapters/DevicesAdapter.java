package ru.mavesoft.cornofficeapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mavesoft.cornofficeapp.R;
import ru.mavesoft.cornofficeapp.modules.Device;
import ru.mavesoft.cornofficeapp.modules.ModuleAPI;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.ViewHolder> {

    private Context context;
    private List<Device> deviceList;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition = 0;


    private Retrofit retrofit;
    private String baseUrl = "https://beecoder-qr-code-entrance.herokuapp.com/";

    public DevicesAdapter(Context context, List<Device> deviceList) {
        this.context = context;
        this.deviceList = deviceList;

        retrofit = new Retrofit.Builder()
                   .baseUrl(baseUrl)
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (deviceList.get(position).getType()) {
            case "kettle":
                holder.getIvIcon().setImageDrawable(context.getDrawable(R.drawable.ic_baseline_coffee_24));
                holder.switchStatus.setVisibility(View.INVISIBLE);

                final boolean isExpanded = holder.getAdapterPosition() == mExpandedPosition;
                holder.deviceDetails.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                holder.tvDeviceValue.setText(
                        context.getText(R.string.water_temperature)
                        + " " + deviceList.get(holder.getAdapterPosition()).getValue());
                holder.itemView.setActivated(isExpanded);

                if (isExpanded) {
                    previousExpandedPosition = holder.getAdapterPosition();
                }

                holder.btnTurnOn.setEnabled(!deviceList.get(holder.getAdapterPosition()).isActive());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mExpandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                        notifyItemChanged(previousExpandedPosition);
                        notifyItemChanged(holder.getAdapterPosition());
                    }
                });

                holder.btnTurnOn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        retrofit = new Retrofit.Builder()
                                   .baseUrl(baseUrl)
                                   .addConverterFactory(GsonConverterFactory.create())
                                   .build();

                        ModuleAPI moduleAPI = retrofit.create(ModuleAPI.class);
                        Call<Void> boilKettleCall = moduleAPI.boilKettle(deviceList.get(holder.getAdapterPosition())
                                                                                   .getID());
                        boilKettleCall.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
                break;
            case "bulb":
                holder.getIvIcon().setImageDrawable(context.getDrawable(R.drawable.ic_baseline_lightbulb_circle_24));
                break;
        }
        holder.tvRoom.setText(deviceList.get(position).getLocation());
        holder.tvDeviceName.setText(deviceList.get(position).getName());

        holder.switchStatus.setChecked(deviceList.get(position).isActive());

        holder.switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ModuleAPI moduleAPI = retrofit.create(ModuleAPI.class);
                Call<Void> voidCall = moduleAPI.switchBulb(deviceList.get(holder.getAdapterPosition()).getID());
                voidCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("Response", response.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvRoom;
        TextView tvDeviceName;
        Switch switchStatus;

        LinearLayout deviceDetails;
        TextView tvDeviceValue;
        Button btnTurnOn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvDeviceName = itemView.findViewById(R.id.tvDeviceName);
            switchStatus = itemView.findViewById(R.id.switchStatus);

            deviceDetails = itemView.findViewById(R.id.device_details);
            tvDeviceValue = itemView.findViewById(R.id.tvDeviceValue);
            btnTurnOn = itemView.findViewById(R.id.btnTurnOn);
        }

        public ImageView getIvIcon() {
            return ivIcon;
        }

        public TextView getTvRoom() {
            return tvRoom;
        }

        public TextView getTvDeviceName() {
            return tvDeviceName;
        }
        public Switch getSwitchStatus() {
            return switchStatus;
        }

        public LinearLayout getDeviceDetails() {
            return deviceDetails;
        }

        public TextView getTvDeviceValue() {
            return tvDeviceValue;
        }
        public Button getBtnTurnOn() {
            return btnTurnOn;
        }
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }
}
