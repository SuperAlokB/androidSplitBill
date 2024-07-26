package com.example.splitteambill.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {



    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val teamMemberQuery = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_COl + " TEXT," +
                FOOD_COL + " TEXT" +
                DRINKS_COL + "TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(teamMemberQuery)

        //val TB_Name = "Bill"

        val billInfoQuery = (" CREATE TABLE IF NOT EXISTS \"Bill\" (\n" +
                "            \"Id\"\tINTEGER NOT NULL,\n" +
                "            \"food_name\"\tTEXT ,\n" +
                "            \"liquor_name\"\tTEXT ,\n" +
                "            \"food_qty\"\tREAL NOT NULL,\n" +
                "            \"food_price\"\tREAL NOT NULL,\n" +
                "            \"total\"\tREAL,\n" +
                "             \"team_members_Ids\"\tREAL,\n" +
                "            PRIMARY KEY(\"Id\" AUTOINCREMENT)\n" +
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
    fun addName(name : String ){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
       // values.put(FOOD_COL, null)
        //values.put(AGE_COL, age)

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

    // This method is for adding data in our database
    fun addBill(food_name : String , liquor_name:String , food_qty: Double, food_price: Double, total:Double){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(FOOD_NAME, food_name)
        values.put(FOOD_QTY, food_qty)
        values.put(FOOD_PRICE,food_price)
        values.put(Total_Price,total)
        values.put(LIQUOR_NAME, liquor_name)

        // values.put(FOOD_COL, null)
        //values.put(AGE_COL, age)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert("Bill", null, values)

        // at last we are
        // closing our database
        db.close()
    }

    fun updateBillWithTeamName(food_name : String , member_name:String ){
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
       // values.put(FOOD_NAME, food_name)
       // values.put(NAME_COl, member_name)

        //Get member id from Member Name
         val db = this.writableDatabase



        val cursor =  db.rawQuery("select id From team_members where name = '" +  member_name + "'" , null)

        //values.put(FOOD_NAME, food_name)


        if(cursor !=null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            var TeamMember_Id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))

            if(TeamMember_Id == "") {

                while (cursor.moveToNext()) {
                    TeamMember_Id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
                }
            }

            values.put(Team_Members_IDs, TeamMember_Id)
            // Which row to update, based on the title
            val selection =  "food_name = ?"
            val selectionArgs = arrayOf(food_name)
            val count = db.update(
                "Bill",
                values,
                selection,
                selectionArgs)



        }

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


    // below method is to get
    // all data from our database
    fun getFoodNames(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + "Bill", null)

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
        val FOOD_COL = "food"

        val DRINKS_COL = "drinks"
        //Bill Information
        val FOOD_NAME = "food_name"
        val LIQUOR_NAME = "liquor_name"
        val FOOD_QTY = "food_qty"
        val FOOD_PRICE = "food_price"
        val Total_Price = "total"
        //val Team_Members_IDs = "team_members_Ids"
        val Team_Members_IDs = "team_members_Ids"

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

    fun getFoodItems() = ArrayList<String>().apply {
        var membersList: ArrayList<String>
        membersList = ArrayList()

        // val db = this.writableDatabase
        // on below line we are adding items to our list
        //membersList.add("Alok")
        val cursor = getFoodNames()
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

    fun getMembersList() = ArrayList<String>().apply {
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


    fun getMemberNameBill() = ArrayList<String>().apply{

        var membersList: ArrayList<String>
        membersList = ArrayList()




    }
}


