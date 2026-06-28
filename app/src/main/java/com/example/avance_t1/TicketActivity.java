package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class TicketActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        TextView tvTicketUser = findViewById(R.id.tvTicketUser);
        TextView tvTicketEvent = findViewById(R.id.tvTicketEvent);
        TextView tvStatus = findViewById(R.id.tvStatus);
        MaterialButton btnBack = findViewById(R.id.btnBack);
        MaterialButton btnLogout = findViewById(R.id.btnLogout);

        // Obtener datos del Intent
        String userName = getIntent().getStringExtra("USER_NAME");
        String eventName = getIntent().getStringExtra("EVENT_NAME");

        // Mostrar datos
        if (userName != null) {
            tvTicketUser.setText(getString(R.string.ticket_user_format, userName));
        }
        if (eventName != null) {
            tvTicketEvent.setText(getString(R.string.ticket_event_format, eventName));
        }

        // Animación del badge VÁLIDO (aparece después de 400ms con fade + scale)
        AnimationSet validAnim = new AnimationSet(false);
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(600);
        fadeIn.setStartOffset(400);
        fadeIn.setFillAfter(true);

        ScaleAnimation scaleIn = new ScaleAnimation(
                0.7f, 1f, 0.7f, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleIn.setDuration(600);
        scaleIn.setStartOffset(400);
        scaleIn.setFillAfter(true);
        scaleIn.setInterpolator(new DecelerateInterpolator());

        validAnim.addAnimation(fadeIn);
        validAnim.addAnimation(scaleIn);
        validAnim.setFillAfter(true);
        tvStatus.startAnimation(validAnim);

        // Mapa
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        btnBack.setOnClickListener(v -> finish());

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TicketActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng eventLocation = new LatLng(-12.046374, -77.042793);
        mMap.addMarker(new MarkerOptions().position(eventLocation).title("Lugar del Evento"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15f));
    }
}
