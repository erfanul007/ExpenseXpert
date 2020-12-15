package com.expensexpert.expensexpert;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;

public class Expense {
    private int id;
    private int groupId;
    private String name;
    private double amount;
    private String category;
    private String note;
    private boolean isExpense;
    private LocalDateTime createdate;

    public Expense(int id, int groupId, String name, double amount, String category, String note, boolean isExpense, LocalDateTime createdate) {
        this.id = id;
        this.groupId = groupId;
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.isExpense = isExpense;
        this.createdate = createdate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Expense(int groupId, String name, double amount, String category, String note, boolean isExpense) {
        this.id = -1;
        this.groupId = groupId;
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.note = note;
        this.isExpense = isExpense;
        this.createdate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", note='" + note + '\'' +
                ", isExpense=" + isExpense +
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean getisExpense() {
        return isExpense;
    }

    public void setisExpense(boolean expense) {
        isExpense = expense;
    }

    public LocalDateTime getCreatedate() {
        return createdate;
    }

    public void setCreatedate(LocalDateTime createdate) {
        this.createdate = createdate;
    }
}
