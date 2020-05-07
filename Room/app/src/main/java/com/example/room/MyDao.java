package com.example.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStudent(Student student);

    @Query("DELETE FROM student_table")
    void deleteAllStudent();

    @Query("SELECT * FROM student_table")
    LiveData<List<Student>> getAllStudents();

    @Query("SELECT * FROM student_table WHERE name = :sname")
    LiveData<List<Student>> selectStduentByName(String sname);

    @Delete
    void deleteStudent(Student... student); // primary key is used to find the student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeacher(Teacher teacher);

    //@Query("SELECT ")
}

