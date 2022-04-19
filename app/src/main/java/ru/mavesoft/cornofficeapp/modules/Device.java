package ru.mavesoft.cornofficeapp.modules;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Device {

    @SerializedName("name")
    private String name;

    @SerializedName("uid")
    private String id;

    @SerializedName("value")
    private String value;

    @SerializedName("type")
    private String type;

    @SerializedName("place")
    private String location;

    @SerializedName("status")
    private boolean isActive;

    public Device(String name, String id, String value, String type, String place,
                  boolean isActive) {
        this.name = name;
        this.id = id;
        this.value = value;
        this.type = type;
        this.location = place;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }
    public String getID() {
        return id;
    }
    public String getValue() {
        return value;
    }
    public String getType() {
        return type;
    }
    public String getLocation() {
        return location;
    }
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                '}';
    }
}
