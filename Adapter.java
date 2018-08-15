package com.example.professor.githubpesquisalistview;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.professor.githubpesquisalistview.Items;
import com.example.professor.githubpesquisalistview.StringUtil;
import com.example.professor.githubpesquisalistview.R;

public class Adapter extends BaseAdapter{

    private List<Items> users ;
    private Context context ;
    private static final String TAG_LOG = Adapter.class.getName();
    public Adapter (Context ctx, List<Items> items) {
        super();
        this.context = ctx ;
        this.users = items ;
    }

    @Override
    public int getCount() {
        return this.users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater mLayoutInflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View mView = mLayoutInflater.inflate(R.layout.item_user, viewGroup, false);
        ImageView mAvatarIv = (ImageView)mView.findViewById(R.id.avatar_iv);
        TextView mLoginTv = (TextView)mView.findViewById(R.id.login_tv);

        Items user = users.get(i);

        Picasso.with(context).load(user.getAvatar_url()).resize(100,100).into(mAvatarIv);
        mLoginTv.setText(StringUtil.capitalize(user.getLogin()));
        Log.d(TAG_LOG, user.getLogin());

        return mView;
    }
}