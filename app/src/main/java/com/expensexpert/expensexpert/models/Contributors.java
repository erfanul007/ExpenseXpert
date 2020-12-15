package com.expensexpert.expensexpert.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Contributors {
    private int id;
    private int groupId;
    private String name;
    private String note;
    private LocalDateTime createdate;

    public Contributors(int id, int groupId, String name, String note, LocalDateTime createdate) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.note = note;
        this.createdate = createdate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contributors(int groupId, String name, String note) {
        this.id = -1;
        this.groupId = groupId;
        this.name = name;
        this.note = note;
        LocalDateTime date = LocalDateTime.now();
        this.createdate = date;
    }

    @Override
    public String toString() {
        return "Contributors{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                ", createdate=" + createdate +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }
}
