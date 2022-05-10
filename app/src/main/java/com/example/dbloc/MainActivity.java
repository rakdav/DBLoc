package com.example.dbloc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbloc.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    public static final String NAME_EXTRA="name";
    public static final String CAPITAL_EXTRA="capital";
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ArrayList<Country> states = new ArrayList<Country>();
    private RecyclerView recyclerView;
    private StateAdapter adapter;
    private DBCountries db;
    private StateAdapter.OnCountryClickListener onStateClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        db=new DBCountries(this);
        recyclerView = findViewById(R.id.list);

        onStateClickListener=new StateAdapter.OnCountryClickListener() {
            @Override
            public void onCountryClick(Country state, int position) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                intent.putExtra(NAME_EXTRA,state.getName());
                intent.putExtra(CAPITAL_EXTRA,state.getCapital());
                startActivity(intent);
            }
        };
        UpdateUI();
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddActivity.class);
                mStartForResult.launch(intent);
            }
        });
    }

    private void UpdateUI()
    {
        states=db.selectAll();
        adapter = new StateAdapter(this, states,onStateClickListener);
        recyclerView.setAdapter(adapter);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        String name = intent.getStringExtra(NAME_EXTRA);
                        String capital = intent.getStringExtra(CAPITAL_EXTRA);
                        db.Insert(name,capital);
                        UpdateUI();
                    }
                }
            });
}