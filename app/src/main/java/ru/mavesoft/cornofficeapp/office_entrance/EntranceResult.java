package ru.mavesoft.cornofficeapp.office_entrance;

import com.google.gson.annotations.SerializedName;

public class EntranceResult {

    @SerializedName("key")
    int key;

    @SerializedName("status")
    int status;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
