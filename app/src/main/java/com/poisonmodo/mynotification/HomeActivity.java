package com.poisonmodo.mynotification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.poisonmodo.mynotification.json.CityResponseJson;
import com.poisonmodo.mynotification.utils.APIClient;
import com.poisonmodo.mynotification.utils.services.UserService;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private ListView CityList;
    LinearLayout rl_progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rl_progress = findViewById(R.id.rl_progress);
        CityList = findViewById(R.id.citylist);
        rl_progress.setVisibility(View.VISIBLE);
        UserService userService = APIClient.getClient().create(UserService.class);

        Call<CityResponseJson> call = userService.get_city();
        call.enqueue(new Callback<CityResponseJson>() {
            @Override
            public void onResponse(retrofit2.Call<CityResponseJson> call, Response<CityResponseJson> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatuscode() == 200) {
                        //rl_progress.setVisibility(View.GONE);
                        List<String> citylist = new ArrayList<>();
                        int n = response.body().getData().size();
                        if(n>0) {
                            for(int a=0;a<n;a++) {
                                citylist.add(response.body().getData().get(a).getCity());
                            }
                            ArrayAdapter arr = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1,citylist);
                            CityList.setAdapter(arr);
                            rl_progress.setVisibility(View.GONE);
                        }

                    }
                } else {
                    //rl_progress.setVisibility(View.GONE);
                    try {
                        JSONObject json = new JSONObject(response.errorBody().string());
                        send_alert(json.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    rl_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CityResponseJson> call, Throwable t) {

            }
        });
    }

    private void send_alert(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setTitle(getResources().getString(R.string.app_name));
        AlertDialog alert = builder.create();
        alert.show();
    }
}