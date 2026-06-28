package com.example.avance_t1;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TicketDao {

    @Insert
    void insert(Ticket ticket);

    @Delete
    void delete(Ticket ticket);

    @Query("SELECT * FROM tickets")
    List<Ticket> getAllTickets();

    @Query("SELECT COUNT(*) FROM tickets")
    int getTicketCount();
}
