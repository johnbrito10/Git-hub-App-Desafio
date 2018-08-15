package com.example.professor.githubpesquisalistview;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import com.example.professor.githubpesquisalistview.Items;
import com.example.professor.githubpesquisalistview.Usuario;
import com.example.professor.githubpesquisalistview.RestClient;
import com.example.professor.githubpesquisalistview.ConstantUtil;
import com.example.professor.githubpesquisalistview.Adapter;

@ContentView(R.layout.activity_lista_usuario)
public class ListaUsuarioActivity extends AppCompatActivity {
    @InjectView(R.id.container_srl)
    private SwipeRefreshLayout mContainerSwipeRefreshLayout;

    @InjectView(R.id.user_lv)
    private ListView mUserLv;

    private Adapter adapter ;

    private List<Items> mItems;

    private Items mItem;

    public static final String ITEMS = "ITEM";

    private static final String TAG_LOG = ListaUsuarioActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = new ArrayList<Items>();

        mItem = new Items();

        LoadDados();

        // Evento para atualizar a lista
        mContainerSwipeRefreshLayout.setOnRefreshListener(new
                                                                  SwipeRefreshLayout.OnRefreshListener() {
                                                                      @Override
                                                                      public void onRefresh() {

                                                                          LoadDados();
                                                                      }
                                                                  });

        // Configure the refreshing colors
        mContainerSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //Onclick listener da lista
        mUserLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListaUsuarioActivity.this, UserDetailActivity.class);
                mItem = mItems.get(i);
                intent.putExtra(ITEMS, mItem);
                startActivity(intent);
            }
        });
    }

    private void LoadDados(){
        final ProgressDialog dialog = ProgressDialog.show(this, "", getString(R.string.loading));
        RestClient.ApiInterface mCliente = RestClient.getClient();
        Call<User> call = mCliente.getUsersNamedTom(ConstantUtil.NAME_USER);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response) {
                dialog.dismiss();
                Log.d(TAG_LOG, "Status Code = " + response.code());
                if (response.isSuccess()) {
                    // request successful (status code 200, 201)
                    User result = response.body();
                    Log.d(TAG_LOG, "response = " + new Gson().toJson(result));

                    mItems = result.getItems();

                    Log.d(TAG_LOG, "Items = " + mItems.size());
                    adapter = new Adapter(ListaUsuarioActivity.this, mItems);
                    mUserLv.setAdapter(adapter);
                    mContainerSwipeRefreshLayout.setRefreshing(false);
                } else {
                    Toast.makeText(ListaUsuarioActivity.this, R.string.conexion, Toast.LENGTH_LONG);
                    // response received but request not successful (like 400,401,403 etc)
                    //Handle errors
                }
            }
            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onBackPressed() {
        finishAffinity();
    }
}

