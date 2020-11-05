package com.example.voiceko.DataBase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns

object ConsumptionRecordContract {
     private object ConsumptionRecordEntry: BaseColumns{
        const val TABLE_NAME = "ConsumptionRecord"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_AMOUNT = "AMOUNT"
        const val COLUMN_CATEGORY = "CATEGORY"
        const val COLUMN_SUB_CATEGORY = "SUB_CATEGORY"
        const val COLUMN_REMARKS = "REMARKS"
        const val COLUMN_TYPE = "TYPE"
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ConsumptionRecordEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ConsumptionRecordEntry.COLUMN_DATE} INTEGER  NOT NULL," +
                "${ConsumptionRecordEntry.COLUMN_AMOUNT} INTEGER NOT NULL,"+
                "${ConsumptionRecordEntry.COLUMN_CATEGORY} TEXT  NOT NULL,"+
                "${ConsumptionRecordEntry.COLUMN_SUB_CATEGORY} TEXT,"+
                "${ConsumptionRecordEntry.COLUMN_REMARKS} TEXT,"+
                "${ConsumptionRecordEntry.COLUMN_TYPE} TEXT NOT NULL)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ConsumptionRecordEntry.TABLE_NAME}"

    class ConsumptionRecordDbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ENTRIES)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ENTRIES)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 2
            const val DATABASE_NAME = "ConsumptionRecord.db"
        }

    }
    class DBMgr(){
        private lateinit var consumptionRecordDbHelper : ConsumptionRecordDbHelper
        private lateinit var activity: Activity
        constructor(activity: Activity): this(){
            this.activity = activity
            openDB()

        }
        //openDB
        private fun openDB(){
            consumptionRecordDbHelper = ConsumptionRecordDbHelper(activity)
        }

        private fun closeDB(){
            consumptionRecordDbHelper.close()
        }

        public fun writeRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
            try {
                var db = consumptionRecordDbHelper.writableDatabase
                var values = ContentValues()
                values.put(ConsumptionRecordEntry.COLUMN_DATE,date)
                values.put(ConsumptionRecordEntry.COLUMN_AMOUNT,amount)
                values.put(ConsumptionRecordEntry.COLUMN_CATEGORY,cate)
                values.put(ConsumptionRecordEntry.COLUMN_SUB_CATEGORY,sub_cate)
                values.put(ConsumptionRecordEntry.COLUMN_REMARKS,remark)
                values.put(ConsumptionRecordEntry.COLUMN_TYPE, type)
                db.insert("ConsumptionRecord",null,values)
                this.closeDB()
                return true

            }catch ( e: SQLiteException){
                this.closeDB()
                return false
            }

        }

        public fun readRecord():ArrayList<MutableMap<String,String>>{
            var db = consumptionRecordDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                ConsumptionRecordEntry.COLUMN_DATE,
                ConsumptionRecordEntry.COLUMN_AMOUNT,
                ConsumptionRecordEntry.COLUMN_CATEGORY,
                ConsumptionRecordEntry.COLUMN_SUB_CATEGORY,
                ConsumptionRecordEntry.COLUMN_REMARKS,
                ConsumptionRecordEntry.COLUMN_TYPE
            )

            val cursor = db.query(
                ConsumptionRecordEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            val records = arrayListOf<MutableMap<String,String>>()
            with(cursor) {
                while (moveToNext()) {
                    val recordId = getString(getColumnIndexOrThrow(BaseColumns._ID))
                    val recordDate = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_DATE))
                    val recordAmount = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_AMOUNT))
                    val recordCate = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_CATEGORY))
                    val recordSubCate = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_SUB_CATEGORY))
                    val recordRemark = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_REMARKS))
                    val recordType =  getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_TYPE))
                    var temp= mutableMapOf<String,String>()
                    temp["ID"]=recordId
                    temp["Date"] = recordDate
                    temp["Amount"] = recordAmount
                    temp["Category"] = recordCate
                    temp["SubCategory"] = recordSubCate
                    temp["Remark"] = recordRemark
                    temp["Type"]=recordType

                    records.add(temp)
                }
            }
           return records
        }
    }

}
