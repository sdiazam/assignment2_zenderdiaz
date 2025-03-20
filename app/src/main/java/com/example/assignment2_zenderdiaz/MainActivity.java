package com.example.assignment2_zenderdiaz;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2_zenderdiaz.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    ArrayList<Item> itemList;

    ActivityMainBinding binding;

    RecyclerView recyclerView;

    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemList = new ArrayList<Item>();
        Item item1 = new Item(0, "Fruits", "Fresh Fruits from the Garden");
        Item item2 = new Item(0, "Vegetables", "Delicious Vegetables ");
        Item item3 = new Item(0, "Bakery", "Bread, Wheat and Beans");
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        myAdapter = new MyAdapter(getApplicationContext(), itemList);
        binding.recyclerView.setAdapter(myAdapter);
        myAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View v, int pos) {
        Toast.makeText(this, "You Choose: "+ itemList.get(pos).getItemName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}