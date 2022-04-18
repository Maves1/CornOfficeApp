package ru.mavesoft.cornofficeapp.modules;

public class Sensor implements IStatus {

    private String value;
    private String type;
    private String location;
    private boolean isActive;

    public Sensor(String value, String type, String location, boolean isActive) {
        this.value = value;
        this.type = type;
        this.location = location;
        this.isActive = isActive;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setActive(Boolean active) {
        isActive = active;
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
}
