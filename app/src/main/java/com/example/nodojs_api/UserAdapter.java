package com.example.nodojs_api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nodojs_api.model.Data;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    public List<Data> dataList;

    private Context context;


    public UserAdapter(List<Data> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Data data = dataList.get(position);
        holder.txtId.setText("ID: " + data.getId()); // "ID: 1
        holder.txtName.setText("Name: " + data.getName());
        holder.txtAddress.setText("Address: " + data.getAddress());
        holder.txtPhone.setText("Phone: " + data.getPhone());
        Picasso.get().load(data.getImage()).into(holder.imgUser);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Data data = dataList.get(position);
                    ApiService.apiService.deleteUser(data.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                dataList.remove(position);
                                notifyItemRemoved(position);
                            } else {
                                Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView txtId, txtName, txtAddress, txtPhone;
        public ImageView imgUser;

        public Button btnDelete, btnUpdate;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txtId);
            txtName = itemView.findViewById(R.id.txtName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhone = itemView.findViewById(R.id.txtPhone);
            imgUser = itemView.findViewById(R.id.imgUser);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }


    public int getDataListSize() {
        return dataList.size();
    }
    public void addUser(Data data) {
        if (dataList != null) {
            dataList.add(data);
            notifyItemInserted(dataList.size() - 1);
        }
    }

}
