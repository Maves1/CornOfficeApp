package ru.mavesoft.cornofficeapp.modules;

import com.google.gson.annotations.SerializedName;

public enum SDType {
    @SerializedName("temperature_sensor")
    temperature_sensor,

    @SerializedName("humidity_sensor")
    humidity_sensor
}
