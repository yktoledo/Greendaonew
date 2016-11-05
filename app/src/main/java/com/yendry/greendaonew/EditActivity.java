package com.yendry.greendaonew;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yendry.greendaonew.db.DaoMaster;
import com.yendry.greendaonew.db.DaoSession;
import com.yendry.greendaonew.db.UserDao;

public class EditActivity extends AppCompatActivity {
    private EditText name;
    private EditText phone;
    private Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        name = (EditText) findViewById(R.id.editText3);
        phone = (EditText) findViewById(R.id.editText4);
        btnUpdate = (Button)findViewById(R.id.button2);
        Intent i = getIntent();
        name.setText(i.getStringExtra("name"));
        phone.setText(i.getStringExtra("phone"));
        final String id = i.getStringExtra("id");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = name.getText().toString();
                String userphone = phone.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("name",username);
                returnIntent.putExtra("phone", userphone);
                returnIntent.putExtra("id", id);
                setResult(EditActivity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }


}
