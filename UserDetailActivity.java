package com.example.professor.githubpesquisalistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import com.example.professor.githubpesquisalistview.Items;
import com.example.professor.githubpesquisalistview.Url;
import com.example.professor.githubpesquisalistview.RestClient;
public class UserDetailActivity extends AppCompatActivity {




    @InjectView(R.id.detail_user_srl)
    private SwipeRefreshLayout mDetailUserSwipeRefreshLayout;

    @InjectView(R.id.name_user_tv)
    private TextView mUserNameTv;

    @InjectView(R.id.email_tv)
    private TextView mEmailTv;

    @InjectView(R.id.public_repos_tv)
    private TextView mPublicReposTv;

    @InjectView(R.id.public_gists_tv)
    private TextView mPublicGitsTv;

    @InjectView(R.id.followers_tv)
    private TextView mFollowerTv;

    @InjectView(R.id.following_tv)
    private TextView mFollowingTv;

    @InjectView(R.id.created_tv)
    private TextView mCreatedTv;

    @InjectView(R.id.updated_tv)
    private TextView mUpdateTv;

    //ImageView Que mostra Usuario

    @InjectView(R.id.user_iv)
    private ImageView mUserIv;

    public static Items mItems;

    public static final String ITEMS = "ITEM";

    /** Tag para logs **/
    private static final String TAG_LOG = UserDetailActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = (Items) getIntent().getSerializableExtra(ITEMS);

        // Inicializa AsynTask
        new HttpRequestTask().execute();

        // Evento Atualiza Informações
        mDetailUserSwipeRefreshLayout.setOnRefreshListener(new
                                                                   SwipeRefreshLayout.OnRefreshListener() {
                                                                       @Override
                                                                       public void onRefresh() {
                                                                           new HttpRequestTask().execute();
                                                                       }
                                                                   });

        // Configure the refreshing colors
        mDetailUserSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Url> {
        ProgressDialog dialog;
        private HttpRequestTask() {}

        @Override
        protected void onPreExecute(){
            dialog = ProgressDialog.show(UserDetailActivity.this, "",
                    getString(R.string.loading));
        }
        @Override
        protected Url doInBackground(Void... params) {
            try {
                Url infoUser = RestClientUrl.getInfo(mItems.getUrl());
                Log.d(TAG_LOG, "response = " + new Gson().toJson(infoUser));
                return infoUser;
            } catch (Exception e) {
                Log.e(TAG_LOG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Url infoUser) {
            LoadInfo(infoUser);
            dialog.dismiss();
            mDetailUserSwipeRefreshLayout.setRefreshing(false);
        }
    }

    private void LoadInfo(Url mInfoUser){
        mUserNameTv.setText(mInfoUser.getName());
        Picasso.with(this).load(mInfoUser.getAvatar_url()).resize(100,100).into(mUserIv);
        mEmailTv.setText(mInfoUser.getEmail());
        mPublicReposTv.setText(String.valueOf(mInfoUser.getPublic_repos()));
        mPublicGitsTv.setText(String.valueOf(mInfoUser.getPublic_gists()));
        mFollowerTv.setText(String.valueOf(mInfoUser.getFollowers()));
        mFollowingTv.setText(String.valueOf(mInfoUser.getFollowing()));
        mCreatedTv.setText(mInfoUser.getCreated_at());
        mUpdateTv.setText(mInfoUser.getUpdated_at());
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ListaUsuarioActivity.class);
        startActivity(i);
    }
}