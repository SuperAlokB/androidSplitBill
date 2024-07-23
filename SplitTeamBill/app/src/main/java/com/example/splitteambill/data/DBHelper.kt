package com.example.splitteambill.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {



    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val teamMemberQuery = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                AGE_COL + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(teamMemberQuery)

        //val TB_Name = "Bill"

        val billInfoQuery = (" CREATE TABLE IF NOT EXISTS \"Bill\" (\n" +
                "            \"Id\"\tINTEGER NOT NULL,\n" +
                "            \"food_name\"\tTEXT NOT NULL,\n" +
                "            \"qty\"\tREAL NOT NULL,\n" +
                "            \"price\"\tREAL NOT NULL,\n" +
                "            \"total\"\tREAL NOT NULL,\n" +
                "            PRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                "        )")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(billInfoQuery)

        // we are calling sqlite
        // method for executing our query
        db.execSQL(teamMemberQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    // This method is for adding data in our database
    fun addName(name : String, age : String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(AGE_COL, age)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }
    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "SplitBills"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "team_members"

        // below is the variable for id column
        val ID_COL = "id"

        // below is the variable for name column
        val NAME_COl = "name"

        // below is the variable for age column
        val AGE_COL = "age"
    }

    // This method is for deleting  data in our database
    fun delName(name : String ){

        // below we are creating
        // a content values variable
        //val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
       // values.put(NAME_COl, name)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        //db.insert(TABLE_NAME, null, values)
        db.delete(TABLE_NAME,"name=?", arrayOf(name))

        // at last we are
        // closing our database
        db.close()
    }

    fun getMemebersList() = ArrayList<String>().apply {
        var membersList: ArrayList<String>
          membersList = ArrayList()

       // val db = this.writableDatabase
        // on below line we are adding items to our list
        //membersList.add("Alok")
        val cursor = getName()
        if(cursor !=null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()

            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

            while (cursor.moveToNext()) {
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
            }
        }

        return   membersList
    }
}


