package com.example.avance_t1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    public interface OnTicketLongClickListener {
        void onLongClick(Ticket ticket, int position);
    }

    private final List<Ticket> tickets;
    private final OnTicketLongClickListener longClickListener;

    public TicketAdapter(List<Ticket> tickets, OnTicketLongClickListener longClickListener) {
        this.tickets = tickets;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        holder.bind(ticket);
        holder.itemView.setOnLongClickListener(v -> {
            longClickListener.onLongClick(ticket, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    // Elimina el item de la lista y notifica al RecyclerView
    public void removeAt(int position) {
        tickets.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tickets.size());
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvItemEventName;
        private final TextView tvItemUserName;
        private final TextView tvItemTicketId;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemEventName = itemView.findViewById(R.id.tvItemEventName);
            tvItemUserName = itemView.findViewById(R.id.tvItemUserName);
            tvItemTicketId = itemView.findViewById(R.id.tvItemTicketId);
        }

        public void bind(Ticket ticket) {
            tvItemEventName.setText(ticket.eventName);
            tvItemUserName.setText("Usuario: " + ticket.userName);
            tvItemTicketId.setText("#" + ticket.id);
        }
    }
}
