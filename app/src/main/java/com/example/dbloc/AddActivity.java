package com.example.dbloc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private EditText name,capital;
    private Button add,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name=findViewById(R.id.nameCountry);
        capital=findViewById(R.id.capitalCountry);
        add=findViewById(R.id.Ok);
        cancel=findViewById(R.id.cancel);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String nameExtra = extras.getString(MainActivity.NAME_EXTRA);
            String capitalExtra = extras.getString(MainActivity.CAPITAL_EXTRA);
            name.setText(nameExtra);
            capital.setText(capitalExtra);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(MainActivity.NAME_EXTRA, name.getText().toString());
                data.putExtra(MainActivity.CAPITAL_EXTRA,capital.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}