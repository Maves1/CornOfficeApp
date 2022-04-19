package ru.mavesoft.cornofficeapp.modules;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ModuleAPI {
    @GET("sensor/all")
    Call<List<Sensor>> getSensors();

    @GET("device/all")
    Call<List<Device>> getDevices();

    @GET("device/bulb/{id}")
    Call<Void> switchBulb(@Path("id") String id);

    @GET("device/kettle/boil/{id}")
    Call<Void> boilKettle(@Path("id") String id);

    @GET("gadgets/{email}")
    Call<List<IStatus>> getUserGadgets(@Path("email") String email);
}
