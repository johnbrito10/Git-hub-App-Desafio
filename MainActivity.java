package com.example.professor.githubpesquisalistview;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActionBarActivity {

    private static final int ANIM_DURATION = 1000;

    private static final int SPLASH_DELAY = 1500;

    @InjectView(R.id.ic_logo_iv)
    private ImageView mLogoIv;

    @InjectResource(R.string.transition_view)
    private String mTransitionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fadeAnimation(mLogoIv);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goListUser();
            }
        }, SPLASH_DELAY);
    }

    private void goListUser() {

        Intent loginIntent = new Intent(MainActivity.this,ListaUsuarioActivity.class);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(MainActivity.this, mLogoIv, mTransitionView);
        startActivity(loginIntent, options.toBundle());
    }

    //Cira Objeto ANim
    @NonNull
    private ObjectAnimator createObjectAnimator(View view, String property, float init, float end) {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(view, property, init, end);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(ANIM_DURATION);
        return scaleXAnimation;
    }

    //Inicia Animação
    private void fadeAnimation(View view) {
        ObjectAnimator scaleXAnimation = createObjectAnimator(view, "scaleX", 5.0F, 1.0F);
        ObjectAnimator scaleYAnimation = createObjectAnimator(view, "scaleY", 5.0F, 1.0F);
        ObjectAnimator alphaAnimation = createObjectAnimator(view, "alpha", 0.0F, 1.0F);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.start();
    }
}