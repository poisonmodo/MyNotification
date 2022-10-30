package com.poisonmodo.mynotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hbb20.CountryCodePicker;
import com.poisonmodo.mynotification.json.UserRequestJson;
import com.poisonmodo.mynotification.json.UserResponseJson;
import com.poisonmodo.mynotification.utils.APIClient;
import com.poisonmodo.mynotification.utils.services.UserService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText phone, pass,text1, text2, text3, text4, text5, text6;
    private CountryCodePicker ccp;
    private Button loginbtn,verifybtn;
    private String token="";
    LinearLayout rl_progress;
    private String TAG="FCM";
    String mobile;
    TextView txttoken;
    FirebaseAuth auth;
    private String verificationCode;
    private int is_verify;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    ViewFlipper vieweflipper;

    @SuppressLint({"MissingInflatedId", "StringFormatInvalid"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vieweflipper = findViewById(R.id.viewotp);
        vieweflipper.setVisibility(View.GONE);
        rl_progress = findViewById(R.id.rl_progress);
        ccp = findViewById(R.id.countrycode);
        phone = findViewById(R.id.phone);
        pass = findViewById(R.id.pass);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        text5 = findViewById(R.id.text5);
        text6 = findViewById(R.id.text6);
        loginbtn = findViewById(R.id.loginbtn);
        verifybtn = findViewById(R.id.verifybtn);
        txttoken = findViewById(R.id.token);
        text1.setText(null);
        text2.setText(null);
        text3.setText(null);
        text4.setText(null);
        text5.setText(null);
        text6.setText(null);
        is_verify=0;
        rl_progress.setVisibility(View.GONE);

        //Get Firebase Token
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

        text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    text2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    text3.requestFocus();
                }
                else if(charSequence.length()<1) {
                    text1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    text4.requestFocus();
                }
                else if(charSequence.length()<1) {
                    text2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    text5.requestFocus();
                }
                else if(charSequence.length()<1) {
                    text3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0) {
                    text6.requestFocus();
                }
                else if(charSequence.length()<1) {
                    text4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        text6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()<1) {
                    text5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                    auth = FirebaseAuth.getInstance();
                    String tmp_phone = phone.getText().toString();
                    tmp_phone=tmp_phone.replaceAll("[^0-9]","");

                    req.countrycode= ccp.getSelectedCountryCodeWithPlus().toString();
                    req.phone= phone.getText().toString();
                    req.password=pass.getText().toString();
                    mobile=ccp.getSelectedCountryCodeWithPlus().toString()+phone.getText().toString();
                    Log.e("Telepon",mobile);

                    if(is_verify==0) {
                        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(MainActivity.this, "verification completed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                Toast.makeText(MainActivity.this, "verification fialed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                Toast.makeText(MainActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                            }
                        };

                        PhoneAuthOptions options =
                                PhoneAuthOptions.newBuilder(auth)
                                        .setPhoneNumber(mobile)       // Phone number to verify
                                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                        .setActivity(MainActivity.this)                 // Activity (for callback binding)
                                        .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                                        .build();
                        PhoneAuthProvider.verifyPhoneNumber(options);                   // OnVerificationStateChangedCallbacks

                        vieweflipper.setVisibility(View.VISIBLE);
                    }


//                    UserService userService = APIClient.getClient().create(UserService.class);
//
//                    Call<UserResponseJson> call = userService.login(req);
//                    call.enqueue(new Callback<UserResponseJson>() {
//                        @Override
//                        public void onResponse(retrofit2.Call<UserResponseJson> call, Response<UserResponseJson> response) {
//                            if (response.isSuccessful()) {
//                                if (response.body().statuscode == 200) {
//                                    rl_progress.setVisibility(View.GONE);
//                                    send_alert(response.body().message);
//                                }
//                            } else {
//                                rl_progress.setVisibility(View.GONE);
//                                try {
//                                    JSONObject json = new JSONObject(response.errorBody().string());
//                                    send_alert(json.getString("message"));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(retrofit2.Call<UserResponseJson> call, Throwable t) {
//
//                        }
//                    });

                }
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = text1.getText().toString() + text2.getText().toString() + text3.getText().toString();
                otp = otp + text4.getText().toString() + text5.getText().toString() + text6.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                auth.signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    is_verify=1;
                                    Toast.makeText(MainActivity.this,"Correct OTP",Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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