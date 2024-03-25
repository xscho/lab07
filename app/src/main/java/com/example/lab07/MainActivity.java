package com.example.lab07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText txt_key;
    EditText txt_value;

    DB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_key = findViewById(R.id.txt_key);
        txt_value = findViewById(R.id.txt_value);

        mydb = new DB(this, "mybase.db", null, 1);
    }

    public void on_insert_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = txt_value.getText().toString();

        mydb.do_insert(key, value);
    }

    public void on_update_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = txt_value.getText().toString();

        mydb.do_update(key, value);
    }

    public void on_select_click(View v)
    {
        String key = txt_key.getText().toString();
        String value = mydb.do_select(key);

        txt_value.setText(value);
    }

    public void on_delete_click(View v)
    {
        String key = txt_key.getText().toString();

        boolean deleted = mydb.do_delete(key);

        if (deleted) {
            Toast.makeText(MainActivity.this, "Запись успешно удалена", Toast.LENGTH_SHORT).show();
            txt_value.setText("");
        } else {
            Toast.makeText(MainActivity.this, "Запись с указанным ключом не существует", Toast.LENGTH_SHORT).show();
        }
    }
}