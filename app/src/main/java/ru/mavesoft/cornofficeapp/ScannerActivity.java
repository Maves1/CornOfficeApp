package ru.mavesoft.cornofficeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import ru.mavesoft.cornofficeapp.office_entrance.EntranceClient;
import ru.mavesoft.cornofficeapp.office_entrance.EntranceRequest;
import ru.mavesoft.cornofficeapp.office_entrance.EntranceResult;

public class ScannerActivity extends AppCompatActivity {

    private final int cameraRequestCode = 1;
    private final String email = "s.v@mail.ru";
    private String baseUrl = "https://beecoder-qr-code-entrance.herokuapp.com/";
    Retrofit retrofit;

    private CodeScanner mCodeScanner;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (!(requestCode == cameraRequestCode && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
        } else {
            startQRScanner();
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void startQRScanner() {
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Log.d("Key: ", result.getText());

                        EntranceClient entranceClient = retrofit.create(EntranceClient.class);

                        String json = "{\n\"email\":\"s.v@mail.ru\",\n" +
                                " \"key\":" + result.getText().toString() + "}";
                        Call<EntranceResult> entranceResultCall = entranceClient.getEntranceResult(json);

                        entranceResultCall.enqueue(new Callback<EntranceResult>() {
                            @Override
                            public void onResponse(Call<EntranceResult> call, Response<EntranceResult> response) {
//                                Log.d("Request", call.request().toString());
//                                Log.d("Response", response.toString());
                                EntranceResult result = response.body();
                                Log.d("EntranceResult", Integer.toString(response.body().getStatus()));

                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<EntranceResult> call, Throwable t) {
                                mCodeScanner.startPreview();
                            }
                        });
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}