package com.example.avance_t1;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets")
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String userName;
    public String eventName;

    public Ticket(String userName, String eventName) {
        this.userName = userName;
        this.eventName = eventName;
    }
}
