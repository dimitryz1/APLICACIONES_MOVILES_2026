package com.example.avance_t1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistorialActivity extends AppCompatActivity {

    private TicketAdapter adapter;
    private LinearLayout emptyState;
    private RecyclerView rvTickets;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        rvTickets = findViewById(R.id.rvTickets);
        emptyState = findViewById(R.id.emptyState);
        db = AppDatabase.getInstance(this);

        cargarTickets();
    }

    private void cargarTickets() {
        AppDatabase.dbExecutor.execute(() -> {
            List<Ticket> tickets = db.ticketDao().getAllTickets();
            List<Ticket> ordenados = new ArrayList<>(tickets);
            Collections.reverse(ordenados);

            runOnUiThread(() -> {
                if (ordenados.isEmpty()) {
                    emptyState.setVisibility(View.VISIBLE);
                    rvTickets.setVisibility(View.GONE);
                } else {
                    emptyState.setVisibility(View.GONE);
                    rvTickets.setVisibility(View.VISIBLE);

                    adapter = new TicketAdapter(ordenados, (ticket, position) -> {
                        // Diálogo de confirmación al hacer long press
                        new AlertDialog.Builder(this)
                                .setTitle("Eliminar ticket")
                                .setMessage("¿Eliminar el ticket de \"" + ticket.eventName + "\"?")
                                .setPositiveButton("Eliminar", (dialog, which) -> {
                                    AppDatabase.dbExecutor.execute(() -> {
                                        db.ticketDao().delete(ticket);
                                        runOnUiThread(() -> {
                                            adapter.removeAt(position);
                                            // Si ya no quedan tickets, mostrar estado vacío
                                            if (adapter.getItemCount() == 0) {
                                                rvTickets.setVisibility(View.GONE);
                                                emptyState.setVisibility(View.VISIBLE);
                                            }
                                        });
                                    });
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                    });

                    rvTickets.setLayoutManager(new LinearLayoutManager(this));
                    rvTickets.setAdapter(adapter);
                }
            });
        });
    }
}
