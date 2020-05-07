package com.example.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyDao mMyDao;

    private static class InsertTask extends AsyncTask<Student, Void, Void> {
        private MyDao mMyDao;

        InsertTask(MyDao dao) {
            mMyDao = dao;
        }

        @Override
        protected Void doInBackground(Student... params) {
            mMyDao.insertStudent(params[0]);
            return null;
        }
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        private MyDao mMyDao;

        DeleteAllTask(MyDao dao) {
            mMyDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mMyDao.deleteAllStudent();
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Student, Void, Void> {
        private MyDao mMyDao;

        DeleteTask(MyDao dao) {
            mMyDao = dao;
        }

        @Override
        protected Void doInBackground(Student... params) {
            mMyDao.deleteStudent(params[0]);
            return null;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyRoomDatabase db = MyRoomDatabase.getDatabase(this);
        mMyDao = db.getMyDao();
        LiveData<List<Student>> mAllStudents = mMyDao.getAllStudents();

        mAllStudents.observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(@Nullable List<Student> students) {
                if (students == null)
                    return;
                StringBuilder sb = new StringBuilder();

                for (Student s : students) {
                    sb.append(s.id); sb.append("-");
                    sb.append(s.getName());
                    sb.append("\n");
                }
                TextView listview = findViewById(R.id.listview);
                listview.setText(sb.toString());
            }
        });

        Button add = findViewById(R.id.add_student);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit_word = findViewById(R.id.edit_name);
                String name = edit_word.getText().toString();
                new InsertTask(mMyDao).execute(new Student(name));
            }
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText student_id = findViewById(R.id.student_id);
                Student student = new Student("");
                student.id = Integer.valueOf(student_id.getText().toString());
                new DeleteTask(mMyDao).execute(student);
            }
        });

        Button delete_all = findViewById(R.id.deleteall);
        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DeleteAllTask(mMyDao).execute();
            }
        });
    }
}
