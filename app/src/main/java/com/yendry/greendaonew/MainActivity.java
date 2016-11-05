package com.yendry.greendaonew;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yendry.greendaonew.db.DaoMaster;
import com.yendry.greendaonew.db.DaoSession;
import com.yendry.greendaonew.db.User;
import com.yendry.greendaonew.db.UserDao;

import java.util.List;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private EditText name;
    private EditText phone;
    private Button btn;
    UserDao userDao;
    LinearLayout lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.editText);
        phone = (EditText)findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "User", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        userDao = daoSession.getUserDao();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User person = new User();
                person.setName(name.getText().toString());
                person.setPhone(Integer.parseInt(phone.getText().toString()));
               userDao.insert(person);
                name.setText("");
                phone.setText("");
                listUser();
            }
        });

        listUser();

    }

    private void listUser() {

        lv = (LinearLayout) findViewById(R.id.lvID);
        lv.removeAllViews();
        List<User> list = userDao.loadAll();

        for (final User u:list){
            Button btn = new Button(this);
            Button btnEdit = new Button(this);
            btn.setText("del");
            btnEdit.setText("Edit");
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUserBtn(u);
                }
            });
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteUser(u);
                }
            });
            TextView newTv = new TextView(this);

            newTv.setText(u.getName()+" "+String.valueOf(u.getPhone()));
            lv.addView(newTv);
            lv.addView(btn);
            lv.addView(btnEdit);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REQUEST_CODE) {
            if(resultCode == EditActivity.RESULT_OK){
                String userName=data.getStringExtra("name");
                int userPhone = Integer.parseInt(data.getStringExtra("phone"));
                long id = Integer.parseInt(data.getStringExtra("id"));

                User u = new User();
                u.setName(userName);
                u.setPhone(userPhone);
                u.setId(id);
                userDao.update(u);
                listUser();
            }
            if (resultCode == EditActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void deleteUser(User u){
        userDao.delete(u);
        listUser();
    }
    public void updateUserBtn(User u){

        Intent i = new Intent(this, EditActivity.class);
        String userName = u.getName();
        String phone = u.getPhone().toString();
        String id = u.getId().toString();
        i.putExtra("name", userName);
        i.putExtra("phone", phone);
        i.putExtra("id", id);

        startActivityForResult(i, REQUEST_CODE);


    }


}
