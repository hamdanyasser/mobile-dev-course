package com.example.midtermexam;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<books> bookList = new ArrayList<>();
     ArrayAdapter<books> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ListView listview = findViewById(R.id.listview);
        adapter = new ArrayAdapter<books>
                (this, android.R.layout.simple_list_item_1, bookList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(R.id.listview);
                books book = getItem(position);
                textView.setText(books.getNameofBook() + " " + books.getGenre());
                return view;


            }
        };


        ListView.setAdapter(adapter);
        FloatingActionButton fab=findViewById(R.id.fabAddbook);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (getApplicationContext(),AddBookActivity.class);
                startActivityForResult(intent,1);
            }
        });

        }
protected void onActivityResult( int requestCode, int resultCode ,@Nullable Intent data){

    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode ==1 && resultCode == RESULT_OK && data!=null){
        books book =data.getParcelableExtra("New Book");
        bookList.add(book);
        adapter.notifyDataSetChanged();
    }
}
    public ArrayList<books> getBookList() {
        return bookList;
    }

    public ArrayAdapter<books> getAdapter() {
        return adapter;
    }
    listview.set{
        Intent intent= new Intent(Intent.ACTION_VIEW);
        Intent.setData(Uri.parse"http://api.Watsaap.com/send?phone+etPhoneNumber +"&text"=+Uri.encode(textView.getText().toString());
        startActivity(intent);
    }
}












