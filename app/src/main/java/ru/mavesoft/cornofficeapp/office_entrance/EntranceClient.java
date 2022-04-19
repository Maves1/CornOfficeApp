package ru.mavesoft.cornofficeapp.office_entrance;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface EntranceClient {

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<EntranceResult> getEntranceResult(@Body String requestBody);
}
