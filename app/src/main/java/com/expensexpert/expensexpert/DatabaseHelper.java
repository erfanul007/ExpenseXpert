package com.expensexpert.expensexpert;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String GROUP_TABLE = "GROUP_TABLE";
    public static final String GROUP_COL_ID = "GROUP_COL_ID";
    public static final String GROUP_COL_NAME = "GROUP_COL_NAME";
    public static final String GROUP_COL_NOTE = "GROUP_COL_NOTE";
    public static final String GROUP_COL_ISACTIVE = "GROUP_COL_ISACTIVE";
    public static final String GROUP_COL_LASTUPDATE = "GROUP_COL_LASTUPDATE";

    public static final String EXPENSE_TABLE = "EXPENSE_TABLE";
    public static final String EXPENSE_COL_ID = "EXPENSE_COL_ID";
    public static final String EXPENSE_COL_GROUPID = "EXPENSE_COL_GROUPID";
    public static final String EXPENSE_COL_NAME = "EXPENSE_COL_NAME";
    public static final String EXPENSE_COL_AMOUNT = "EXPENSE_COL_AMOUNT";
    public static final String EXPENSE_COL_CATEGORY = "EXPENSE_COL_CATEGORY";
    public static final String EXPENSE_COL_NOTE = "EXPENSE_COL_NOTE";
    public static final String EXPENSE_COL_ISEXPENSE = "EXPENSE_COL_ISEXPENSE";
    public static final String EXPENSE_COL_CREATEDATE = "EXPENSE_COL_CREATEDATE";

    public static final String CONTRIB_TABLE = "CONTRIB_TABLE";
    public static final String CONTRIB_COL_ID = "CONTRIB_COL_ID";
    public static final String CONTRIB_COL_GROUPID = "CONTRIB_COL_GROUPID";
    public static final String CONTRIB_COL_NAME = "CONTRIB_COL_NAME";
    public static final String CONTRIB_COL_NOTE = "CONTRIB_COL_NOTE";
    public static final String CONTRIB_COL_CREATEDATE = "CONTRIB_COL_CREATEDATE";

    public static final String EXGROBUT_TABLE = "EXGROBUT_TABLE";
    public static final String EXGROBUT_COL_GROUPID = "EXGROBUT_COL_GROUPID";
    public static final String EXGROBUT_COL_EXPENSEID = "EXGROBUT_COL_EXPENSEID";
    public static final String EXGROBUT_COL_CONTRIBID = "EXGROBUT_COL_CONTRIBID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "expensexpert.dp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateGroupQuery = "CREATE TABLE " + GROUP_TABLE + " (" + GROUP_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GROUP_COL_NAME + " TEXT, " + GROUP_COL_NOTE + " TEXT," + GROUP_COL_ISACTIVE + " BOOL, " + GROUP_COL_LASTUPDATE + " TEXT)";
        db.execSQL(CreateGroupQuery);

        String CreateExpenseQuery = "CREATE TABLE " + EXPENSE_TABLE + " (" + EXPENSE_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EXPENSE_COL_GROUPID + " INTEGER, " + EXPENSE_COL_NAME + " TEXT, " + EXPENSE_COL_AMOUNT + " REAL, " + EXPENSE_COL_CATEGORY + " TEXT, " + EXPENSE_COL_NOTE + " TEXT, " + EXPENSE_COL_ISEXPENSE + " BOOL, " + EXPENSE_COL_CREATEDATE + " TEXT)";
        db.execSQL(CreateExpenseQuery);

        String CreateContributorsQuery = "CREATE TABLE " + CONTRIB_TABLE + " (" + CONTRIB_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CONTRIB_COL_GROUPID + " INTEGER, " + CONTRIB_COL_NAME + " TEXT, " + CONTRIB_COL_NOTE + " TEXT, " + CONTRIB_COL_CREATEDATE + " TEXT)";
        db.execSQL(CreateContributorsQuery);

        String CreateExGroButorsQuery = "CREATE TABLE " + EXGROBUT_TABLE + " (" + EXGROBUT_COL_GROUPID + " INTEGER, " + EXGROBUT_COL_EXPENSEID + " INTEGER, " + EXGROBUT_COL_CONTRIBID + " INTEGER)";
        db.execSQL(CreateExGroButorsQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    boolean add_Group(Group group){
        ContentValues cv = new ContentValues();
        cv.put(GROUP_COL_NAME, group.getName());
        cv.put(GROUP_COL_NOTE, group.getNote());
        cv.put(GROUP_COL_ISACTIVE, group.getisActive());
        cv.put(GROUP_COL_LASTUPDATE, String.valueOf(group.getLastupdate()));

        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(GROUP_TABLE, null, cv);
        db.close();

        if(insert==-1) return false;
        return true;
    }

    boolean update_group(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(GROUP_COL_ID, group.getId());
        cv.put(GROUP_COL_NAME, group.getName());
        cv.put(GROUP_COL_NOTE, group.getNote());
        cv.put(GROUP_COL_ISACTIVE, group.getisActive());
        cv.put(GROUP_COL_LASTUPDATE, String.valueOf(group.getLastupdate()));

        int update = db.update(GROUP_TABLE, cv, GROUP_COL_ID + " = ?", new String[]{String.valueOf(group.getId())});
        db.close();
        if(update==-1) return false;
        return true;
    }

    boolean delete_group(Group group){
        String gqq = Integer.toString(group.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + GROUP_TABLE + " WHERE " + GROUP_COL_ID + " = "+ gqq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean add_Expense(Expense expense){
        ContentValues cv = new ContentValues();
        cv.put(EXPENSE_COL_GROUPID, expense.getGroupId());
        cv.put(EXPENSE_COL_NAME, expense.getName());
        cv.put(EXPENSE_COL_AMOUNT, expense.getAmount());
        cv.put(EXPENSE_COL_CATEGORY, expense.getCategory());
        cv.put(EXPENSE_COL_NOTE, expense.getNote());
        cv.put(EXPENSE_COL_ISEXPENSE, expense.getisExpense());
        cv.put(EXPENSE_COL_CREATEDATE, String.valueOf(expense.getCreatedate()));

        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(EXPENSE_TABLE, null, cv);
        db.close();

        if(insert==-1) return false;
        return true;
    }

    boolean update_expense(Expense expense){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXPENSE_COL_ID, expense.getId());
        cv.put(EXPENSE_COL_GROUPID, expense.getGroupId());
        cv.put(EXPENSE_COL_NAME, expense.getName());
        cv.put(EXPENSE_COL_AMOUNT, expense.getAmount());
        cv.put(EXPENSE_COL_CATEGORY, expense.getCategory());
        cv.put(EXPENSE_COL_NOTE, expense.getNote());
        cv.put(EXPENSE_COL_ISEXPENSE, expense.getisExpense());
        cv.put(EXPENSE_COL_CREATEDATE, String.valueOf(expense.getCreatedate()));

        int update = db.update(EXPENSE_TABLE, cv, EXPENSE_COL_ID + " = ?", new String[]{ String.valueOf(expense.getId()) });
        db.close();
        if(update==-1) return false;
        return true;
    }

    boolean delete_expense(Expense expense){
        String eqq = Integer.toString(expense.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_ID + " = "+ eqq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean delete_expense_bygroup(int groupid){
        String qq = Integer.toString(groupid);
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_GROUPID + " = "+ qq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean add_Contributor(Contributors contrib){
        ContentValues cv = new ContentValues();
        cv.put(CONTRIB_COL_GROUPID, contrib.getGroupId());
        cv.put(CONTRIB_COL_NAME, contrib.getName());
        cv.put(CONTRIB_COL_NOTE, contrib.getNote());
        cv.put(CONTRIB_COL_CREATEDATE, String.valueOf(contrib.getCreatedate()));

        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(CONTRIB_TABLE, null, cv);
        db.close();

        if(insert==-1) return false;
        return true;
    }

    boolean update_contributor(Contributors contrib){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CONTRIB_COL_ID, contrib.getId());
        cv.put(CONTRIB_COL_GROUPID, contrib.getGroupId());
        cv.put(CONTRIB_COL_NAME, contrib.getName());
        cv.put(CONTRIB_COL_NOTE, contrib.getNote());
        cv.put(CONTRIB_COL_CREATEDATE, String.valueOf(contrib.getCreatedate()));

        int update = db.update(CONTRIB_TABLE, cv, CONTRIB_COL_ID + " = ?", new String[]{String.valueOf(contrib.getId())});
        db.close();
        if(update==-1) return false;
        return true;
    }

    boolean delete_contributor(Contributors contributors){
        String cqq = Integer.toString(contributors.getId());
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + CONTRIB_TABLE + " WHERE " + CONTRIB_COL_ID + " = "+ cqq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean delete_contributor_bygroup(int groupid){
        String qq = Integer.toString(groupid);
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + CONTRIB_TABLE + " WHERE " + CONTRIB_COL_GROUPID + " = "+ qq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean add_ExGroButors(ExGroButors exGroButors){
        ContentValues cv = new ContentValues();
        cv.put(EXGROBUT_COL_GROUPID, exGroButors.getGroupID());
        cv.put(EXGROBUT_COL_EXPENSEID, exGroButors.getExpenseID());
        cv.put(EXGROBUT_COL_CONTRIBID, exGroButors.getContributorID());

        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.insert(EXGROBUT_TABLE, null, cv);
        db.close();

        if(insert == -1) return false;
        return true;
    }

    boolean delete_ExGroButors_bygroup(int groupid){
        String qq = Integer.toString(groupid);
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + EXGROBUT_TABLE + " WHERE " + EXGROBUT_COL_GROUPID + " = "+ qq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean delete_ExGroButors_byexpense(int expenseid){
        String qq = Integer.toString(expenseid);
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + EXGROBUT_TABLE + " WHERE " + EXGROBUT_COL_EXPENSEID + " = "+ qq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    boolean delete_ExGroButors_bycontributor(int contribid){
        String qq = Integer.toString(contribid);
        SQLiteDatabase db = this.getWritableDatabase();
        String querystring = "DELETE FROM " + EXGROBUT_TABLE + " WHERE " + EXGROBUT_COL_CONTRIBID + " = "+ qq;
        Cursor cursor = db.rawQuery(querystring, null);
        if(cursor.moveToFirst()){
            db.close();
            cursor.close();
            return true;
        }
        db.close();
        cursor.close();
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Group> get_Group_active(){
        List<Group> returnlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querystring = "SELECT * FROM " + GROUP_TABLE + " WHERE " + GROUP_COL_ISACTIVE + " = 1 ORDER BY " + GROUP_COL_LASTUPDATE + " DESC";
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int groupid = cursor.getInt(cursor.getColumnIndex(GROUP_COL_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(GROUP_COL_NAME));
                String groupnote = cursor.getString(cursor.getColumnIndex(GROUP_COL_NOTE));
                LocalDateTime lastupdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(GROUP_COL_LASTUPDATE)));
                Group groups = new Group(groupid, groupname, groupnote, true, lastupdate);
                returnlist.add(groups);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Group> get_Group_deactive(){
        List<Group> returnlist = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String querystring = "SELECT * FROM " + GROUP_TABLE + " WHERE " + GROUP_COL_ISACTIVE + " = 0 ORDER BY " + GROUP_COL_LASTUPDATE + " DESC";
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int groupid = cursor.getInt(cursor.getColumnIndex(GROUP_COL_ID));
                String groupname = cursor.getString(cursor.getColumnIndex(GROUP_COL_NAME));
                String groupnote = cursor.getString(cursor.getColumnIndex(GROUP_COL_NOTE));
                LocalDateTime lastupdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(GROUP_COL_LASTUPDATE)));
                Group groups = new Group(groupid, groupname, groupnote, false, lastupdate);
                returnlist.add(groups);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Group get_Group_specific(int groupid){
        String gqq = Integer.toString(groupid);

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + GROUP_TABLE + " WHERE "+ GROUP_COL_ID +" = "+ gqq;
        Cursor cursor = db.rawQuery(querystring, null);

        Group group = new Group("", "");

        if(cursor.moveToFirst()){
            String groupname = cursor.getString(cursor.getColumnIndex(GROUP_COL_NAME));
            String groupnote = cursor.getString(cursor.getColumnIndex(GROUP_COL_NOTE));
            boolean groupstatus = cursor.getInt(cursor.getColumnIndex(GROUP_COL_ISACTIVE)) == 1;
            LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(GROUP_COL_LASTUPDATE)));

            group = new Group(groupid, groupname, groupnote, groupstatus, createdate);
        }

        cursor.close();
        db.close();
        return group;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Expense> get_Expense_active(int groupid){
        String gqq = Integer.toString(groupid);
        List<Expense> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_GROUPID + " = " + gqq + " AND " + EXPENSE_COL_ISEXPENSE + " = 1 ORDER BY " + EXPENSE_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int expenseid = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ID));
                String expensename = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NAME));
                double amount = cursor.getDouble(cursor.getColumnIndex(EXPENSE_COL_AMOUNT));
                String expensecategroy = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CATEGORY));
                String expensenote = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CREATEDATE)));

                Expense expenses = new Expense(expenseid, groupid, expensename, amount, expensecategroy, expensenote, true, createdate);
                returnlist.add(expenses);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Expense> get_Expense_deactive(int groupid){
        String gqq = Integer.toString(groupid);
        List<Expense> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_GROUPID + " = " + gqq + " AND " + EXPENSE_COL_ISEXPENSE + " = 0 ORDER BY " + EXPENSE_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int expenseid = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ID));
                String expensename = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NAME));
                double amount = cursor.getDouble(cursor.getColumnIndex(EXPENSE_COL_AMOUNT));
                String expensecategroy = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CATEGORY));
                String expensenote = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CREATEDATE)));

                Expense expenses = new Expense(expenseid, groupid, expensename, amount, expensecategroy, expensenote, false, createdate);
                returnlist.add(expenses);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Expense get_Expense_specific(int groupid, int expenseid){
        String gqq = Integer.toString(groupid);
        String cqq = Integer.toString(expenseid);

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_GROUPID + " = " + gqq + " AND "+ EXPENSE_COL_ID +" = "+ cqq;
        Cursor cursor = db.rawQuery(querystring, null);

        Expense expense = new Expense(groupid, "", 0, "", "", false);

        if(cursor.moveToFirst()){
            String expensename = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NAME));
            double expensamount = cursor.getDouble(cursor.getColumnIndex(EXPENSE_COL_AMOUNT));
            String expensecategory = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CATEGORY));
            String expensenote = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NOTE));
            boolean expensestatus = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ISEXPENSE)) == 1;
            LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(CONTRIB_COL_CREATEDATE)));

            expense = new Expense(expenseid, groupid, expensename, expensamount, expensecategory, expensenote, expensestatus, createdate);
        }

        cursor.close();
        db.close();
        return expense;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Contributors> get_Contributors(int groupid){
        String gqq = Integer.toString(groupid);
        List<Contributors> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + CONTRIB_TABLE + " WHERE " + CONTRIB_COL_GROUPID + " = " + gqq + " ORDER BY " + CONTRIB_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int contribid = cursor.getInt(cursor.getColumnIndex(CONTRIB_COL_ID));
                String contribname = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NAME));
                String contribnote = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(CONTRIB_COL_CREATEDATE)));

                Contributors contrib = new Contributors(contribid, groupid, contribname, contribnote, createdate);
                returnlist.add(contrib);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Contributors get_Contributor_specific(int groupid, int contribid){
        String gqq = Integer.toString(groupid);
        String cqq = Integer.toString(contribid);

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + CONTRIB_TABLE + " WHERE " + CONTRIB_COL_GROUPID + " = " + gqq + " AND "+ CONTRIB_COL_ID +" = "+ cqq;
        Cursor cursor = db.rawQuery(querystring, null);

        Contributors contributor = new Contributors(groupid, "", "");

        if(cursor.moveToFirst()){
            String contribname = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NAME));
            String contribnote = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NOTE));
            LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(CONTRIB_COL_CREATEDATE)));

            contributor = new Contributors(contribid, groupid, contribname, contribnote, createdate);
        }

        cursor.close();
        db.close();
        return contributor;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Expense> get_Contributor_Expense_active(int groupid, int contribid){
        String gqq = Integer.toString(groupid);
        String cqq = Integer.toString(contribid);
        List<Expense> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_ID + " IN (SELECT "+ EXGROBUT_COL_EXPENSEID +" FROM "+ EXGROBUT_TABLE +" WHERE "+ EXGROBUT_COL_GROUPID +" = "+ gqq +" AND "+ EXGROBUT_COL_CONTRIBID +" = "+ cqq +") AND "+ EXPENSE_COL_ISEXPENSE +" = 1  ORDER BY " + EXPENSE_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int expenseid = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ID));
                String expensename = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NAME));
                double amount = cursor.getDouble(cursor.getColumnIndex(EXPENSE_COL_AMOUNT));
                String expensecategroy = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CATEGORY));
                String expensenote = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CREATEDATE)));

                Expense expenses = new Expense(expenseid, groupid, expensename, amount, expensecategroy, expensenote, true, createdate);
                returnlist.add(expenses);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Expense> get_Contributor_Expense_deactive(int groupid, int contribid){
        String gqq = Integer.toString(groupid);
        String cqq = Integer.toString(contribid);
        List<Expense> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_ID + " IN (SELECT "+ EXGROBUT_COL_EXPENSEID +" FROM "+ EXGROBUT_TABLE +" WHERE "+ EXGROBUT_COL_GROUPID +" = "+ gqq +" AND "+ EXGROBUT_COL_CONTRIBID +" = "+ cqq +") AND "+ EXPENSE_COL_ISEXPENSE +" = 0  ORDER BY " + EXPENSE_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int expenseid = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ID));
                String expensename = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NAME));
                double amount = cursor.getDouble(cursor.getColumnIndex(EXPENSE_COL_AMOUNT));
                String expensecategroy = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CATEGORY));
                String expensenote = cursor.getString(cursor.getColumnIndex(EXPENSE_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(EXPENSE_COL_CREATEDATE)));

                Expense expenses = new Expense(expenseid, groupid, expensename, amount, expensecategroy, expensenote, false, createdate);
                returnlist.add(expenses);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Contributors> get_Expense_Contributors(int groupid, int expenseid){
        String gqq = Integer.toString(groupid);
        String eqq = Integer.toString(expenseid);
        List<Contributors> returnlist = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + CONTRIB_TABLE + " WHERE " + CONTRIB_COL_ID + " IN (SELECT "+ EXGROBUT_COL_CONTRIBID +" FROM "+ EXGROBUT_TABLE +" WHERE "+ EXGROBUT_COL_GROUPID +" = "+ gqq +" AND "+ EXGROBUT_COL_EXPENSEID +" = "+ eqq +") ORDER BY " + CONTRIB_COL_ID;
        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            do{
                int contribid = cursor.getInt(cursor.getColumnIndex(CONTRIB_COL_ID));
                String contribname = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NAME));
                String contribnote = cursor.getString(cursor.getColumnIndex(CONTRIB_COL_NOTE));
                LocalDateTime createdate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex(CONTRIB_COL_CREATEDATE)));

                Contributors contributors = new Contributors(contribid, groupid, contribname, contribnote, createdate);
                returnlist.add(contributors);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return returnlist;
    }

    int getlastExpenseId(int GroupId){
        String qq = Integer.toString(GroupId);

        SQLiteDatabase db = this.getReadableDatabase();

        String querystring = "SELECT * FROM " + EXPENSE_TABLE + " WHERE " + EXPENSE_COL_GROUPID + " = " + qq + " ORDER BY " + EXPENSE_COL_ID + " DESC";

        Cursor cursor = db.rawQuery(querystring, null);

        if(cursor.moveToFirst()){
            int expenseid = cursor.getInt(cursor.getColumnIndex(EXPENSE_COL_ID));
            cursor.close();
            db.close();
            return expenseid;
        }
        cursor.close();
        db.close();
        return -1;
    }

    double get_Expense_Amount(List<Expense> expenseList){
        double amount = 0;
        for(int i=0; i<expenseList.size(); i++){
            amount += expenseList.get(i).getAmount();
        }
        return amount;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    double get_Expense_Amount_div(List<Expense> expenseList){
        double amount = 0;
        for(int i=0; i<expenseList.size(); i++){
            int expenseid = expenseList.get(i).getId();
            int groupid = expenseList.get(i).getGroupId();
            List<Contributors> contributorsList = this.get_Expense_Contributors(groupid, expenseid);
            double temp = expenseList.get(i).getAmount();
            amount += temp / (double) contributorsList.size();
        }
        return amount;
    }
}
