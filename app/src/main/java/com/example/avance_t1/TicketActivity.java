package com.example.avance_t1;

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

        // Obtener datos del Intent
        String userName = getIntent().getStringExtra("USER_NAME");
        String eventName = getIntent().getStringExtra("EVENT_NAME");

        // Mostrar datos usando formatos definidos en strings.xml
        tvTicketUser.setText(getString(R.string.ticket_user_format, userName));
        tvTicketEvent.setText(getString(R.string.ticket_event_format, eventName));

        btnBack.setOnClickListener(v -> finish());
    }
}
