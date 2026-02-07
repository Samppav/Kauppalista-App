package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kauppalistaapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> items;
    private final DatabaseReference databaseReference;

    public ItemAdapter(Context context, ArrayList<String> items, DatabaseReference databaseReference) {
        super(context, 0, items);
        this.context = context;
        this.items = items;
        this.databaseReference = databaseReference;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        }

        TextView itemText = convertView.findViewById(R.id.itemText);
        Button deleteButton = convertView.findViewById(R.id.deleteButton);

        itemText.setText(item);

        deleteButton.setOnClickListener(v -> {
            databaseReference.orderByValue().equalTo(item).get().addOnSuccessListener(snapshot -> {
                for (com.google.firebase.database.DataSnapshot child : snapshot.getChildren()) {
                    child.getRef().removeValue();
                }
            });
        });

        return convertView;
    }
}
