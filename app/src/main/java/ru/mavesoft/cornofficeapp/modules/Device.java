package ru.mavesoft.cornofficeapp.modules;

import com.google.gson.annotations.SerializedName;

import retrofit2.Call;

public class Device implements IStatus {

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

    public String getName() {
        return name;
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
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
