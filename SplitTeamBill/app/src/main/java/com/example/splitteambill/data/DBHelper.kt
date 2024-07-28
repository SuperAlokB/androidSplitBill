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
        val teamMemberQuery = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(\n"
                + ID_COL + "\tINTEGER PRIMARY KEY, \n" +
                NAME_COl + "\tTEXT, \n" +
                FOOD_Bill + "\tREAL NOT NULL,\n" +
                DRINKS_Bill + "\tREAL NOT NULL,\n" +
                TotalBill + "\tREAL NOT NULL )")

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
    fun addName(name: String, food_price: Double, drink_price: Double, total: Double) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(NAME_COl, name)
        values.put(FOOD_Bill, food_price)
        values.put(DRINKS_Bill, drink_price)
        values.put(TotalBill, total)
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
    fun addBill(
        food_name: String,
        liquor_name: String,
        food_qty: Double,
        food_price: Double,
        total: Double
    ) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(FOOD_NAME, food_name)
        values.put(FOOD_QTY, food_qty)
        values.put(FOOD_PRICE, food_price)
        values.put(Total_Price, total)
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

    fun updateTeamWithBillDetails(
        food_name: String,
        liquor_name: String,
        contriForEachMember: Double,
        member_name: String
    ) {
        // below we are creating
        // a content values variable
        val db = this.writableDatabase

        val sqlQuery = "select * From team_members where name = '" + member_name + "'"
        var oldFoodContri_value = 0.0
        var oldDrinksContri_value = 0.0

        val cursor = db.rawQuery(sqlQuery, null)
        var FinalTotalBill = 0.0
        if (cursor != null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()

            oldFoodContri_value =
                cursor.getDouble(cursor.getColumnIndex(DBHelper.FOOD_Bill))
            oldDrinksContri_value =
                cursor.getDouble(cursor.getColumnIndex(DBHelper.DRINKS_Bill))



            while (cursor.moveToNext()) {
                oldFoodContri_value =
                    cursor.getDouble(cursor.getColumnIndex(DBHelper.FOOD_Bill))
                oldDrinksContri_value =
                    cursor.getDouble(cursor.getColumnIndex(DBHelper.DRINKS_Bill))

            }
            if ((food_name.isBlank().toString()) != "true") {

                oldFoodContri_value = oldFoodContri_value + contriForEachMember.toDouble()
            } else {
                oldDrinksContri_value = oldDrinksContri_value + contriForEachMember.toDouble()
            }
            FinalTotalBill = oldFoodContri_value + oldDrinksContri_value

        }
        val values = ContentValues()

        values.put(FOOD_Bill, oldFoodContri_value.toDouble())
        values.put(DRINKS_Bill, oldDrinksContri_value.toDouble())
        values.put(TotalBill, FinalTotalBill.toDouble())


        // Which row to update, based on the title
        val selection = "name = ?"
        val selectionArgs = arrayOf(member_name)
        val count = db.update(
            "team_members",
            values,
            selection,
            selectionArgs
        )


        db.close()

    }

    fun updateBillWithTeamName(food_name: String, liquorName: String, member_name: String) {
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        // values.put(FOOD_NAME, food_name)
        // values.put(NAME_COl, member_name)

        //Get member id from Member Name
        val db = this.writableDatabase


        val cursor =
            db.rawQuery("select id From team_members where name = '" + member_name + "'", null)

        //values.put(FOOD_NAME, food_name)

        //Take existing members id


        if (cursor != null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()
            var TeamMember_Id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))

            if (TeamMember_Id == "") {

                while (cursor.moveToNext()) {
                    TeamMember_Id = cursor.getString(cursor.getColumnIndex(DBHelper.ID_COL))
                }
            }
            var sqlQuery = ""
            if (food_name != null) {

                sqlQuery = "select team_members_Ids From Bill where food_name = '" + food_name + "'"
            } else {
                sqlQuery =
                    "select team_members_Ids From Bill where liquor_name = '" + liquorName + "'"
            }
            val cursor1 = db.rawQuery(sqlQuery, null)

            if (cursor1 != null && cursor1.count > 0) {
                // moving the cursor to first position and
                // appending value in the text view
                cursor1!!.moveToFirst()
                var old_TeamMember_Id =
                    cursor1.getString(cursor1.getColumnIndex(DBHelper.Team_Members_IDs))

                if (TeamMember_Id == "") {

                    while (cursor1.moveToNext()) {
                        old_TeamMember_Id =
                            cursor1.getString(cursor1.getColumnIndex(DBHelper.Team_Members_IDs))
                    }
                }
                TeamMember_Id = TeamMember_Id + "," + old_TeamMember_Id
                values.put(Team_Members_IDs, TeamMember_Id)
                // Which row to update, based on the title
                val selection = "food_name = ?"
                val selectionArgs = arrayOf(food_name)
                val count = db.update(
                    "Bill",
                    values,
                    selection,
                    selectionArgs
                )
            }

            db.close()
        }
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

    companion object {
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
        val FOOD_Bill = "food"

        val DRINKS_Bill = "drinks"
        val TotalBill = "total"

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
    fun delName(name: String) {

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
        db.delete(TABLE_NAME, "name=?", arrayOf(name))

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
        if (cursor != null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()

            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

            while (cursor.moveToNext()) {
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
            }
        }

        return membersList
    }

    fun getMembersList() = ArrayList<String>().apply {
        var membersList: ArrayList<String>
        membersList = ArrayList()

        // val db = this.writableDatabase
        // on below line we are adding items to our list
        //membersList.add("Alok")
        val cursor = getName()
        if (cursor != null && cursor.count > 0) {
            // moving the cursor to first position and
            // appending value in the text view
            cursor!!.moveToFirst()

            membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))

            while (cursor.moveToNext()) {
                membersList.add(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_COl)))
            }
        }

        return membersList
    }

    fun resetData() {

        val db = this.writableDatabase

        // all values are inserted into database
        //db.insert(TABLE_NAME, null, values)
        db.delete("Bill", null, null)
        val updatefoodquery = "UPDATE  $TABLE_NAME SET food = 0.0"
        db.execSQL(updatefoodquery)
        val updatedrinksquery = "UPDATE  $TABLE_NAME SET drinks = 0.0"
        db.execSQL(updatedrinksquery)
        val updatetotalquery = "UPDATE  $TABLE_NAME SET total = 0.0"
        db.execSQL(updatetotalquery)
        db.close()


    }
}


//    fun getMemberNameBill() {
//
//        var membersList: ArrayList<User>
//        membersList = ArrayList()
//
////Get food item - below will return cursor from bill table with all values
//        val teamCursor = getName()
//        if(teamCursor !=null && teamCursor.count > 0) {
//            var user = User("",0,0.0,0.0,0.0)
//
//            // moving the cursor to first position and
//            // appending value in the text view
//            teamCursor!!.moveToFirst()
//            user.name = teamCursor.getString(teamCursor.getColumnIndex(DBHelper.NAME_COl))
//            user.id = teamCursor.getString(teamCursor.getColumnIndex(DBHelper.ID_COL))
//            membersList.add(user)
//
//            while (teamCursor.moveToNext()) {
//                user.name = teamCursor.getString(teamCursor.getColumnIndex(DBHelper.NAME_COl))
//                user.id = teamCursor.getString(teamCursor.getColumnIndex(DBHelper.ID_COL))
//                membersList.add(user)
//            }
//        }
//        val cursor = getFoodNames()
//        if(cursor !=null && cursor.count > 0) {
//            // moving the cursor to first position and
//            // appending value in the text view
//            cursor!!.moveToFirst()
//
//            var  TeamMembersfromBill = cursor.getString(cursor.getColumnIndex(DBHelper.Team_Members_IDs)).split(",").toTypedArray()
//            //split members list -totalTeamMembersinFood
//            val  totalTeamMembersInFood = TeamMembersfromBill.count()
//            var foodPriceForEach = cursor.getString(cursor.getColumnIndex(DBHelper.Team_Members_IDs)) / totalTeamMembersInFood
//
//            TeamMembersfromBill.forEach { it ->
//
//                if(it.toString() != null){
//
//                    //got seperate team member name with food/ liquor and total price
//
//                }
//
//            }
//
//            while (cursor.moveToNext()) {
//                TeamMembersfromBill = cursor.getString(cursor.getColumnIndex(DBHelper.Team_Members_IDs))
//            }
//        }
//
//
//
//        // get total price for food item / liquor items etc
//        // get all team members from food item
//        // divid  food item cost in all members
//        //add above values in User objects
//
//
//
//    }



