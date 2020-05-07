package com.example.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "teacher_table")
public class Teacher {

    @PrimaryKey(autoGenerate = true)
    int id;

    @NonNull @ColumnInfo(name = "name")
    private String mName;

    @NonNull @ColumnInfo(name = "position")
    private String mPosition;

    Teacher(@NonNull String name, @NonNull String position) {
        mName = name;
        mPosition = position;
    }

    public String getName() {return mName;}
    public String getPosition() {return mPosition;}
}
