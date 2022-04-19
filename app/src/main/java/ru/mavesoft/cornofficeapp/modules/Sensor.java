package ru.mavesoft.cornofficeapp.modules;

import com.google.gson.annotations.SerializedName;

public class Sensor {

    @SerializedName("value")
    private String value;

    @SerializedName("type")
    private SDType type;

    @SerializedName("place")
    private String location;

    @SerializedName("status")
    private boolean isActive;

    public Sensor(String value, SDType type, String location, boolean isActive) {
        this.value = value;
        this.type = type;
        this.location = location;
        this.isActive = isActive;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(SDType type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getValue() {
        return value;
    }

    public SDType getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public boolean isActive() {
        return isActive;
    }
}
