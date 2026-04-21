package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicketActivity extends AppCompatActivity {

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

        // Mostrar datos usando formatos definidos en strings.xml
        tvTicketUser.setText(getString(R.string.ticket_user_format, userName));
        tvTicketEvent.setText(getString(R.string.ticket_event_format, eventName));

        // Botón para regresar a la pantalla anterior
        btnBack.setOnClickListener(v -> finish());

        // Botón para cerrar sesión y volver al Login
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TicketActivity.this, LoginActivity.class);
            // Flags para limpiar el stack de actividades y asegurar un inicio limpio del login
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
