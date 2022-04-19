package ru.mavesoft.cornofficeapp.modules;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IStatus {
    String getID();
    String getValue();
    String getType();
    String getLocation();
    boolean isActive();
}
