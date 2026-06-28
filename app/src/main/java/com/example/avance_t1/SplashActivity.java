package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION_MS = 2200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Animación del contenido central
        LinearLayout splashContent = findViewById(R.id.splashContent);

        AnimationSet animSet = new AnimationSet(true);

        ScaleAnimation scale = new ScaleAnimation(
                0.5f, 1f, 0.5f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(800);

        AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
        alpha.setDuration(800);

        animSet.addAnimation(scale);
        animSet.addAnimation(alpha);
        animSet.setInterpolator(new DecelerateInterpolator());
        splashContent.startAnimation(animSet);

        // Navegar al Login después de SPLASH_DURATION_MS
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // evitar volver al splash con el botón Atrás
        }, SPLASH_DURATION_MS);
    }
}
