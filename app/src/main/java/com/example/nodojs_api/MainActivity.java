package com.example.nodojs_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nodojs_api.model.Data;
import com.example.nodojs_api.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    private TextView tvTitle;

    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tvTitle);
        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabAdd = findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(v -> {
            postApi();
        });
        callApi();

    }

    private void callApi() {
        ApiService.apiService.getUser().enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        List<Data> dataList = user.getData();

                        userAdapter = new UserAdapter(dataList, MainActivity.this);
                        recyclerView.setAdapter(userAdapter);
                        tvTitle.setText("Danh sách người dùng (" + dataList.size() + ")");

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Gọi API không thành công 1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postApi() {
        String name = "Nguyen Van A";
        String address = "Ha Noi";
        String phone = "0123456789";
        String image = "https://i.imgur.com/4f0gC9B.jpg";

        // Tạo JSONObject từ dữ liệu người dùng
        JSONObject userObject = new JSONObject();
        try {
            userObject.put("name", name);
            userObject.put("address", address);
            userObject.put("phone", phone);
            userObject.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo RequestBody từ JSONObject
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), userObject.toString());

        // Gọi API postUser với RequestBody
        ApiService.apiService.postUser(requestBody).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Data data = response.body();
                    if (data != null) {
                        userAdapter.addUser(data);
                        tvTitle.setText("Danh sách người dùng (" + userAdapter.getDataListSize() + ")");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}
