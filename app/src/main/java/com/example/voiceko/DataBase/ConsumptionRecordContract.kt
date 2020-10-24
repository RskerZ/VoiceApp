package com.example.voiceko.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
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

}
