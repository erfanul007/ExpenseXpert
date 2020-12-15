package com.expensexpert.expensexpert.models;

public class Member {
    private String name;
    int id;
    private double balance, expense;

    public Member(int id, String name, double balance, double expense) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.expense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }
}
