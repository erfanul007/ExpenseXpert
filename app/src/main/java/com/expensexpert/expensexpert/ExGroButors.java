package com.expensexpert.expensexpert;

public class ExGroButors {
    private int groupID;
    private int expenseID;
    private int contributorID;

    public ExGroButors(int groupID, int expenseID, int contributorID) {
        this.groupID = groupID;
        this.expenseID = expenseID;
        this.contributorID = contributorID;
    }

    @Override
    public String toString() {
        return "ExGroButors{" +
                "groupID=" + groupID +
                ", expenseID=" + expenseID +
                ", contributorID=" + contributorID +
                '}';
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public int getContributorID() {
        return contributorID;
    }

    public void setContributorID(int contributorID) {
        this.contributorID = contributorID;
    }
}
