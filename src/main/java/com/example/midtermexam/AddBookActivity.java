package com.example.midtermexam;

import static com.example.midtermexam.R.id.spnbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner spinner = (Spinner) findViewById(R.id.spnbook);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        getApplicationContext(), R.array.genre_array,
                        android.R.layout.simple_spinner_item);
        assert spinner != null;
        spinner.setAdapter(adapter);

        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etBoookName = findViewById(R.id.etBoookName);
                Spinner spnGenre = findViewById(R.id.spnbook);
                books book1 = new books();
                book1.setNameofBook(etBoookName.getText().toString());
                book1.setGenre(spinner.getSelectedItem().toString());
                Intent ReplyIntent = new Intent();
                ReplyIntent.putExtra("New Book", book1);
                setResult(RESULT_OK, ReplyIntent);
                finish();

            }
        });

    }

}
