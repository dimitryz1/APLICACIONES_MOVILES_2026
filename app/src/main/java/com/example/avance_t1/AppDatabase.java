package com.example.avance_t1;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TicketDao ticketDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "tickets_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Nota: En apps reales usar hilos/coroutines. KISS para este ejemplo.
                    .build();
        }
        return instance;
    }
}
