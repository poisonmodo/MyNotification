package com.poisonmodo.mynotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CountryCodePicker;
import com.poisonmodo.mynotification.json.UserRequestJson;
import com.poisonmodo.mynotification.json.UserResponseJson;
import com.poisonmodo.mynotification.utils.APIClient;
import com.poisonmodo.mynotification.utils.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText phone, pass;
    private CountryCodePicker ccp;
    private Button loginbtn;
    private String token="";
    LinearLayout rl_progress;
    private String TAG="FCM";
    TextView txttoken;

    @SuppressLint({"MissingInflatedId", "StringFormatInvalid"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

                        // Log and toast
                        txttoken.setText(token);
                        Log.d(TAG, token);

                    }
                });

        rl_progress = findViewById(R.id.rl_progress);
        ccp = findViewById(R.id.countrycode);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        loginbtn = findViewById(R.id.loginbtn);
        txttoken = findViewById(R.id.token);

        rl_progress.setVisibility(View.GONE);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_progress.setVisibility(View.VISIBLE);
                UserRequestJson req = new UserRequestJson();

                if(phone.getText().toString().isEmpty()) {
                    rl_progress.setVisibility(View.GONE);
                    send_alert("Please fill email address");
                }
                else if(pass.getText().toString().isEmpty()) {
                    rl_progress.setVisibility(View.GONE);
                    send_alert("Please fill password");
                }
                else {
                    String tmp_phone = phone.getText().toString();
                    tmp_phone=tmp_phone.replaceAll("[^0-9]","");
                    

                    req.countrycode= ccp.getFullNumber()
                    req.phone= phone.getText().toString();
                    req.password=pass.getText().toString();

                    UserService userService = APIClient.getClient().create(UserService.class);

                    Call<UserResponseJson> call = userService.login(req);
                    call.enqueue(new Callback<UserResponseJson>() {
                        @Override
                        public void onResponse(retrofit2.Call<UserResponseJson> call, Response<UserResponseJson> response) {
                            if (response.isSuccessful()) {
                                if (response.body().statuscode == 200) {
                                    rl_progress.setVisibility(View.GONE);
                                    send_alert(response.body().message);
                                }
                            } else {
                                rl_progress.setVisibility(View.GONE);
                                try {
                                    JSONObject json = new JSONObject(response.errorBody().string());
                                    send_alert(json.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<UserResponseJson> call, Throwable t) {

                        }
                    });

                }
            }
        });


    }

    private void send_alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent j = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(j);
                        finish();
                    }
                })
                .setTitle(getResources().getString(R.string.app_name));
        AlertDialog alert = builder.create();
        alert.show();
    }


}