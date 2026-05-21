package com.example.avance_t1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a los componentes
        EditText etUserName = findViewById(R.id.etUserName);
        Spinner spEvents = findViewById(R.id.spEvents);
        Button btnBuyTicket = findViewById(R.id.btnBuyTicket);

        // Instancia de la base de datos
        AppDatabase db = AppDatabase.getInstance(this);

        btnBuyTicket.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String selectedEvent = (spEvents.getSelectedItem() != null) ? spEvents.getSelectedItem().toString() : "";

            if (userName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu nombre", Toast.LENGTH_SHORT).show();
            } else {
                // --- Lógica de Room ---
                // Guardamos el ticket en la base de datos local
                Ticket newTicket = new Ticket(userName, selectedEvent);
                db.ticketDao().insert(newTicket);
                
                Toast.makeText(this, "Ticket guardado en la DB", Toast.LENGTH_SHORT).show();

                // Navegar a la pantalla del Ticket
                Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("EVENT_NAME", selectedEvent);
                startActivity(intent);
            }
        });
    }
}
