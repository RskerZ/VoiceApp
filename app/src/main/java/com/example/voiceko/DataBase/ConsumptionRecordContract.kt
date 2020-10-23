package com.example.voiceko.DataBase

import android.provider.BaseColumns

object ConsumptionRecordContract {
    private object ConsumptionRecordEntry: BaseColumns{
        const val TABLE_NAME = "ConsumptionRecord"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_AMOUNT = "AMOUNT"
        const val COLUMN_CATEGORY = "CATEGORY"
        const val COLUMN_SUB_CATEGORY = "SUB_CATEGORY"
        const val COLUMN_REMARKS = "REMARKS"
    }

    private const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ConsumptionRecordEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ConsumptionRecordEntry.COLUMN_DATE} INTEGER  NOT NULL," +
                "${ConsumptionRecordEntry.COLUMN_AMOUNT} INTEGER NOT NULL),"+
                "${ConsumptionRecordEntry.COLUMN_CATEGORY} TEXT  NOT NULL),"+
                "${ConsumptionRecordEntry.COLUMN_SUB_CATEGORY} TEXT),"+
                "${ConsumptionRecordEntry.COLUMN_REMARKS} TEXT)"

    private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ConsumptionRecordEntry.TABLE_NAME}"

    

}
