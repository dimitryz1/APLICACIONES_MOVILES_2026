package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TicketActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        TextView tvTicketUser = findViewById(R.id.tvTicketUser);
        TextView tvTicketEvent = findViewById(R.id.tvTicketEvent);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnLogout = findViewById(R.id.btnLogout);

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

        // Configurar el fragmento del mapa
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

        // Coordenadas de ejemplo (Plaza de Armas de Lima)
        LatLng eventLocation = new LatLng(-12.046374, -77.042793);
        mMap.addMarker(new MarkerOptions().position(eventLocation).title("Lugar del Evento"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 15f));
    }
}
