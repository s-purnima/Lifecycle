package com.example.lifecycle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import com.example.lifecycle.databinding.ActivityMainBinding;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding  binding;
    private int dessertSold = 0, revenue = 0;
    private final List<Dessert> allDesserts = Arrays.asList(
            new Dessert(R.drawable.cupcake, 5, 0),
            new Dessert(R.drawable.donut, 10, 5),
            new Dessert(R.drawable.eclair, 15, 20),
            new Dessert(R.drawable.froyo, 30, 50),
            new Dessert(R.drawable.gingerbread, 50, 100),
            new Dessert(R.drawable.honeycomb, 100, 200),
            new Dessert(R.drawable.icecreamsandwich, 500, 500),
            new Dessert(R.drawable.jellybean, 1000, 1000),
            new Dessert(R.drawable.kitkat, 2000, 2000),
            new Dessert(R.drawable.lollipop,3000 ,4000 ),
            new Dessert(R.drawable.marshmallow ,4000 ,8000 ),
            new Dessert(R.drawable.nougat ,5000 ,16000 ),
            new Dessert(R.drawable.oreo ,6000 ,20000 )
    );

    private Dessert currentDessert = allDesserts.get(0);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle("Dessert Clicker");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","onRestart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart called");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("KEY_REVENUE", revenue);
        outState.putInt("KEY_DESSERT_SOLD", dessertSold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        if(savedInstanceState != null){ //restoring previously stored data before onDestroy()
            revenue = savedInstanceState.getInt("KEY_REVENUE");
            dessertSold = savedInstanceState.getInt("KEY_DESSERT_SOLD");
            showRequiredDessert();
        }

        binding.setRevenue(revenue);
        binding.setAmountSold(dessertSold);
        binding.imageButton.setImageResource(currentDessert.getImageId());
        Log.d("MainActivity","onCreated Called");
    }


    public void dessertClick(View view){
        dessertSold++;
        revenue += currentDessert.getPrice();

        binding.setRevenue(revenue);
        binding.setAmountSold(dessertSold);

        showRequiredDessert();
    }

    public void showRequiredDessert(){
        Dessert newDessert = currentDessert;
        for(Dessert dessert : allDesserts){
            if(dessertSold >= dessert.getStartProductionAmount())
                newDessert = dessert;
            else
                break;
        }

        if(newDessert != currentDessert){
            currentDessert = newDessert;
            binding.imageButton.setImageResource(newDessert.getImageId());
        }
    }

    public void shareBtnClick(MenuItem item){
        if(item.getItemId() == R.id.share_button){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,"Share using"));
        }
    }
}