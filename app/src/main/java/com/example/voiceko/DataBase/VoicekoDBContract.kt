package com.example.voiceko.DataBase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

object VoicekoDBContract {
    private object ConsumptionRecordEntry: BaseColumns{
        const val TABLE_NAME = "ConsumptionRecord"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_AMOUNT = "AMOUNT"
        const val COLUMN_CATEGORY = "CATEGORY"
        const val COLUMN_SUB_CATEGORY = "SUB_CATEGORY"
        const val COLUMN_REMARKS = "REMARKS"
        const val COLUMN_TYPE = "TYPE"
    }
    private object AccountTypeEntry: BaseColumns{
        const val TABLE_NAME = "AccountType"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_TYPE = "TYPE"
        const val COLUMN_WEIGHT = "WEIGHT"
    }
    private object SubAccountTypeEntry: BaseColumns{
        const val TABLE_NAME = "SubAccountType"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_PARENT = "PARENT"
    }

    private const val SQL_CREATE_ConsumptionRecordEntry =
        "CREATE TABLE ${ConsumptionRecordEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ConsumptionRecordEntry.COLUMN_DATE} INTEGER  NOT NULL," +
                "${ConsumptionRecordEntry.COLUMN_AMOUNT} INTEGER NOT NULL,"+
                "${ConsumptionRecordEntry.COLUMN_CATEGORY} TEXT  NOT NULL,"+
                "${ConsumptionRecordEntry.COLUMN_SUB_CATEGORY} TEXT,"+
                "${ConsumptionRecordEntry.COLUMN_REMARKS} TEXT,"+
                "${ConsumptionRecordEntry.COLUMN_TYPE} TEXT NOT NULL)"

    private const val SQL_CREATE_AccountTypeEntry =
        "CREATE TABLE ${AccountTypeEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${AccountTypeEntry.COLUMN_NAME} TEXT ,"+
                "${AccountTypeEntry.COLUMN_TYPE} TEXT NOT NULL,"+
                "${AccountTypeEntry.COLUMN_WEIGHT} INTEGER NOT NULL DEFAULT 1)"

    private const val SQL_CREATE_SubAccountTypeEntry =
        "CREATE TABLE ${SubAccountTypeEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${SubAccountTypeEntry.COLUMN_NAME} TEXT ,"+
                "${SubAccountTypeEntry.COLUMN_PARENT} TEXT NOT NULL)"

    private const val SQL_INIT_AccountTypeEntry=
        "INSERT INTO ${AccountTypeEntry.TABLE_NAME} (${AccountTypeEntry.COLUMN_NAME}, ${AccountTypeEntry.COLUMN_TYPE}) VALUES "+
                "('飲食','支出'),"+
                "('服飾','支出'),"+
                "('居家','支出'),"+
                "('交通','支出'),"+
                "('教育','支出'),"+
                "('交際娛樂','支出'),"+
                "('交流通訊','支出'),"+
                "('醫療保健','支出'),"+
                "('生活用品','支出'),"+
                "('金融保險','支出'),"+
                "('美容美髮','支出'),"+
                "('運動用品','支出'),"+
                "('3C產品','支出'),"+
                "('稅金/日常費用','支出'),"+
                "('寵物百貨','支出'),"+
                "('其他支出','支出'),"+
                "('薪水','收入'),"+
                "('投資','收入'),"+
                "('獎金','收入'),"+
                "('小確幸','收入'),"+
                "('其他收入','收入')"

    private const val SQL_DELETE_ConsumptionRecordEntry = "DROP TABLE IF EXISTS ${ConsumptionRecordEntry.TABLE_NAME}"
    private const val SQL_DELETE_AccountTypeEntry = "DROP TABLE IF EXISTS ${AccountTypeEntry.TABLE_NAME}"
    private const val SQL_DELETE_SubAccountTypeEntry = "DROP TABLE IF EXISTS ${SubAccountTypeEntry.TABLE_NAME}"

    class VoiceKoDbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ConsumptionRecordEntry)
            db.execSQL(SQL_CREATE_AccountTypeEntry)
            db.execSQL(SQL_INIT_AccountTypeEntry)
            db.execSQL(SQL_CREATE_SubAccountTypeEntry)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ConsumptionRecordEntry)
            db.execSQL(SQL_DELETE_AccountTypeEntry)
            db.execSQL(SQL_DELETE_SubAccountTypeEntry)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 4
            const val DATABASE_NAME = "VoiceKo.db"
        }

    }

    class DBMgr(){
        private lateinit var voiceKoDbHelper : VoiceKoDbHelper
        private lateinit var activity: Activity
        constructor(activity: Activity): this(){
            this.activity = activity
            openDB()
        }
        //openDB
        private fun openDB(){
            voiceKoDbHelper = VoiceKoDbHelper(activity)
        }

        private fun closeDB(){
            voiceKoDbHelper.close()
        }

        public fun writeRecord(date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
            try {
                var db = voiceKoDbHelper.writableDatabase
                var values = ContentValues()
                values.put(ConsumptionRecordEntry.COLUMN_DATE,date)
                values.put(ConsumptionRecordEntry.COLUMN_AMOUNT,amount)
                values.put(ConsumptionRecordEntry.COLUMN_CATEGORY,cate)
                values.put(ConsumptionRecordEntry.COLUMN_SUB_CATEGORY,sub_cate)
                values.put(ConsumptionRecordEntry.COLUMN_REMARKS,remark)
                values.put(ConsumptionRecordEntry.COLUMN_TYPE, type)
                db.insert(ConsumptionRecordEntry.TABLE_NAME,null,values)
                this.closeDB()
                return true

            }catch ( e: SQLiteException){
                this.closeDB()
                return false
            }

        }

        public fun readRecord():ArrayList<MutableMap<String,String>>{
            var db = voiceKoDbHelper.readableDatabase
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
            this.closeDB()
            return records
        }

        public fun readCateName(type: String):ArrayList<ArrayList<String>>{
            var db = voiceKoDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                AccountTypeEntry.COLUMN_NAME,
                AccountTypeEntry.COLUMN_TYPE,
                AccountTypeEntry.COLUMN_WEIGHT
            )
            val selection = "${AccountTypeEntry.COLUMN_TYPE} = ?"
            val selectionArgs = arrayOf(type)
            val order = "${AccountTypeEntry.COLUMN_WEIGHT} DESC"
            val cursor = db.query(
                AccountTypeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                order
            )

            var cateList = arrayListOf<String>()
            var IDList = arrayListOf<String>()
            var weightList = arrayListOf<String>()
            with(cursor){
                while (moveToNext()){
                    val cateName = getString(getColumnIndexOrThrow(AccountTypeEntry.COLUMN_NAME))
                    val cateWeight = getString(getColumnIndexOrThrow(AccountTypeEntry.COLUMN_WEIGHT))
                    val itemId = getString(getColumnIndexOrThrow(BaseColumns._ID))

                    cateList.add(cateName)
                    IDList.add(itemId)
                    weightList.add(cateWeight)
                }
            }
            this.closeDB()
            return arrayListOf(IDList,cateList,weightList)
        }

        public fun insertNewCategory(cate:String,type:String):Int{
            if (cateIsExist(cate,type)){
                this.closeDB()
                return 300
            }else{
                try {
                    val db = voiceKoDbHelper.writableDatabase
                    val values = ContentValues()
                    values.put(AccountTypeEntry.COLUMN_NAME,cate)
                    values.put(AccountTypeEntry.COLUMN_TYPE, type)
                    db.insert(AccountTypeEntry.TABLE_NAME,null,values)
                    this.closeDB()
                    return 200
                }catch ( e: SQLiteException){
                    this.closeDB()
                    return 400
                }
            }

        }

        public fun cateIsExist(cate:String,type:String):Boolean{
            var db = voiceKoDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                AccountTypeEntry.COLUMN_NAME
            )
            val selection = "${AccountTypeEntry.COLUMN_NAME} = ? AND ${AccountTypeEntry.COLUMN_TYPE} = ?"
            val selectionArgs = arrayOf(cate,type)

            val cursor = db.query(
                AccountTypeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            this.closeDB()
            with(cursor) {
                if (moveToNext()){
                    return true
                }
                return false
            }
        }

        public fun changeCateName(cateID:String,newName:String):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(AccountTypeEntry.COLUMN_NAME, newName)
                }
                val selection = "_id = ${cateID}"
                val count = db.update(
                    AccountTypeEntry.TABLE_NAME,
                    values,
                    selection,
                    null)
                this.closeDB()
                if (count > 0){
                    return true
                }
                return false
            }catch (e:SQLiteException){
                this.closeDB()
                return false
            }
        }

        public fun changeCateWeight(cateID:String,newWeight:Int):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(AccountTypeEntry.COLUMN_WEIGHT, newWeight)
                }
                val selection = "_id = ${cateID}"
                val count = db.update(
                    AccountTypeEntry.TABLE_NAME,
                    values,
                    selection,
                    null)
                this.closeDB()
                if (count > 0){
                    return true
                }
                return false
            }catch (e:SQLiteException){
                this.closeDB()
                return false
            }
        }
    }

}
