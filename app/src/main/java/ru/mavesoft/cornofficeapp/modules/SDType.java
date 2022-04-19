package ru.mavesoft.cornofficeapp.modules;

import com.google.gson.annotations.SerializedName;

public enum SDType {
    @SerializedName("temperature_sensor")
    TemperatureSensor,

    @SerializedName("humidity_sensor")
    HumiditySensor
}
