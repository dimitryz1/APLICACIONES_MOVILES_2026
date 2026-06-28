package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextView tvTicketCount;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputEditText etUserName = findViewById(R.id.etUserName);
        AutoCompleteTextView actvEvents = findViewById(R.id.actvEvents);
        MaterialButton btnBuyTicket = findViewById(R.id.btnBuyTicket);
        MaterialButton btnHistorial = findViewById(R.id.btnHistorial);
        ImageButton btnLogoutMain = findViewById(R.id.btnLogoutMain);
        tvTicketCount = findViewById(R.id.tvTicketCount);

        // Configurar ExposedDropdownMenu
        String[] events = getResources().getStringArray(R.array.events_array);
        ArrayAdapter<String> eventsAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, events);
        actvEvents.setAdapter(eventsAdapter);
        actvEvents.setText(events[0], false);

        // Pre-llenar nombre desde Login
        String loginUserName = getIntent().getStringExtra("USER_NAME");
        if (loginUserName != null && !loginUserName.isEmpty()) {
            etUserName.setText(loginUserName);
        }

        db = AppDatabase.getInstance(this);

        btnBuyTicket.setOnClickListener(v -> {
            String userName = etUserName.getText() != null ? etUserName.getText().toString().trim() : "";
            String selectedEvent = actvEvents.getText().toString().trim();

            if (userName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            if (selectedEvent.isEmpty()) {
                Toast.makeText(this, "Por favor, selecciona un evento", Toast.LENGTH_SHORT).show();
                return;
            }

            Ticket newTicket = new Ticket(userName, selectedEvent);
            AppDatabase.dbExecutor.execute(() -> {
                db.ticketDao().insert(newTicket);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Ticket guardado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                    intent.putExtra("USER_NAME", userName);
                    intent.putExtra("EVENT_NAME", selectedEvent);
                    startActivity(intent);
                });
            });
        });

        btnHistorial.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistorialActivity.class)));

        btnLogoutMain.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar contador cada vez que se vuelve a esta pantalla
        AppDatabase.dbExecutor.execute(() -> {
            int count = db.ticketDao().getTicketCount();
            runOnUiThread(() -> {
                if (count == 0) {
                    tvTicketCount.setText("Aún no tienes tickets guardados");
                } else if (count == 1) {
                    tvTicketCount.setText("Tienes 1 ticket guardado");
                } else {
                    tvTicketCount.setText("Tienes " + count + " tickets guardados");
                }
            });
        });
    }
}
