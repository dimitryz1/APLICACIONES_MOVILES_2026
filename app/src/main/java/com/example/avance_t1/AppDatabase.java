package com.example.avance_t1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TicketDao ticketDao();

    private static AppDatabase instance;

    // Executor compartido para todas las operaciones de DB
    public static final ExecutorService dbExecutor = Executors.newSingleThreadExecutor();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "tickets_database")
                    .fallbackToDestructiveMigration()
                    .build(); // sin allowMainThreadQueries()
        }
        return instance;
    }
}
