package com.expensexpert.expensexpert;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Group {
    private int id;
    private String name;
    private String note;
    private boolean isactive;
    private LocalDateTime lastupdate;

    public Group(int id, String name, String note, boolean isactive, LocalDateTime lastupdate) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.isactive = isactive;
        this.lastupdate = lastupdate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Group(String name, String note) {
        this.id = -1;
        this.name = name;
        this.note = note;
        this.isactive = true;
        this.lastupdate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", isactive=" + isactive +
                ", lastupdate=" + lastupdate +
                '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getisActive() {
        return isactive;
    }

    public void setisActive(boolean isactive) {
        this.isactive = isactive;
    }

    public LocalDateTime getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(LocalDateTime lastupdate) {
        this.lastupdate = lastupdate;
    }
}
