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

        // Restaurando la lógica de los componentes
        EditText etUserName = findViewById(R.id.etUserName);
        Spinner spEvents = findViewById(R.id.spEvents);
        Button btnBuyTicket = findViewById(R.id.btnBuyTicket);

        btnBuyTicket.setOnClickListener(v -> {
            String userName = etUserName.getText().toString().trim();
            String selectedEvent = (spEvents.getSelectedItem() != null) ? spEvents.getSelectedItem().toString() : "";

            if (userName.isEmpty()) {
                Toast.makeText(this, "Por favo, ingresa tu nombre", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, TicketActivity.class);
                intent.putExtra("USER_NAME", userName);
                intent.putExtra("EVENT_NAME", selectedEvent);
                startActivity(intent);
            }
        });
    }
}
