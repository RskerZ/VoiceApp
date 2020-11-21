package com.example.voiceko.DataBase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import java.time.Month

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

    private object BudgetEntry: BaseColumns{
        const val TABLE_NAME = "Budget"
        const val COLUMN_NAME = "NAME"
        const val COLUMN_Budget = "BUDGET"
    }

    private object PeriodRecordsTypeEntry: BaseColumns{
        const val TABLE_NAME = "PeriodRecords"
        const val COLUMN_WORKID = "WorkID"
        const val COLUMN_CYCLE = "CYCLE"
        const val COLUMN_DATE = "DATE"
        const val COLUMN_AMOUNT = "AMOUNT"
        const val COLUMN_CATEGORY = "CATEGORY"
        const val COLUMN_SUB_CATEGORY = "SUB_CATEGORY"
        const val COLUMN_REMARKS = "REMARKS"
        const val COLUMN_TYPE = "TYPE"
    }


    private const val SQL_CREATE_ConsumptionRecordEntry =
        "CREATE TABLE ${ConsumptionRecordEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ConsumptionRecordEntry.COLUMN_DATE} TEXT  NOT NULL," +
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

    private const val SQL_CREATE_BudgetEntry =
        "CREATE TABLE ${BudgetEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${BudgetEntry.COLUMN_NAME} TEXT ,"+
                "${BudgetEntry.COLUMN_Budget} INTEGER NOT NULL DEFAULT 0)"

    private const val SQL_CREATE_PeriodRecordsTypeEntry =
        "CREATE TABLE ${PeriodRecordsTypeEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${PeriodRecordsTypeEntry.COLUMN_WORKID} TEXT NOT NULL,"+
                "${PeriodRecordsTypeEntry.COLUMN_CYCLE} INTEGER NOT NULL,"+
                "${PeriodRecordsTypeEntry.COLUMN_DATE} TEXT  NOT NULL," +
                "${PeriodRecordsTypeEntry.COLUMN_AMOUNT} INTEGER NOT NULL,"+
                "${PeriodRecordsTypeEntry.COLUMN_CATEGORY} TEXT  NOT NULL,"+
                "${PeriodRecordsTypeEntry.COLUMN_SUB_CATEGORY} TEXT,"+
                "${PeriodRecordsTypeEntry.COLUMN_REMARKS} TEXT,"+
                "${PeriodRecordsTypeEntry.COLUMN_TYPE} TEXT NOT NULL)"

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
                "('TOTAL','other'),"+
                "('薪水','收入'),"+
                "('投資','收入'),"+
                "('獎金','收入'),"+
                "('小確幸','收入'),"+
                "('其他收入','收入')"

    private const val SQL_INIT_BudgetEntry=
        "INSERT INTO ${BudgetEntry.TABLE_NAME} (${BudgetEntry.COLUMN_NAME}) VALUES "+
                "('飲食'),"+
                "('服飾'),"+
                "('居家'),"+
                "('交通'),"+
                "('教育'),"+
                "('交際娛樂'),"+
                "('交流通訊'),"+
                "('醫療保健'),"+
                "('生活用品'),"+
                "('金融保險'),"+
                "('美容美髮'),"+
                "('運動用品'),"+
                "('3C產品'),"+
                "('稅金/日常費用'),"+
                "('寵物百貨'),"+
                "('其他支出'),"+
                "('TOTAL')"

    private const val SQL_DELETE_ConsumptionRecordEntry = "DROP TABLE IF EXISTS ${ConsumptionRecordEntry.TABLE_NAME}"
    private const val SQL_DELETE_AccountTypeEntry = "DROP TABLE IF EXISTS ${AccountTypeEntry.TABLE_NAME}"
    private const val SQL_DELETE_SubAccountTypeEntry = "DROP TABLE IF EXISTS ${SubAccountTypeEntry.TABLE_NAME}"
    private const val SQL_DELETE_BudgetEntry = "DROP TABLE IF EXISTS ${BudgetEntry.TABLE_NAME}"
    private const val SQL_DELETE_PeriodRecordsTypeEntry = "DROP TABLE IF EXISTS ${PeriodRecordsTypeEntry.TABLE_NAME}"

    class VoiceKoDbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_ConsumptionRecordEntry)
            db.execSQL(SQL_CREATE_AccountTypeEntry)
            db.execSQL(SQL_INIT_AccountTypeEntry)
            db.execSQL(SQL_CREATE_BudgetEntry)
            db.execSQL(SQL_INIT_BudgetEntry)
            db.execSQL(SQL_CREATE_SubAccountTypeEntry)
            db.execSQL(SQL_CREATE_PeriodRecordsTypeEntry)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_ConsumptionRecordEntry)
            db.execSQL(SQL_DELETE_AccountTypeEntry)
            db.execSQL(SQL_DELETE_SubAccountTypeEntry)
            db.execSQL(SQL_DELETE_PeriodRecordsTypeEntry)
            db.execSQL(SQL_DELETE_BudgetEntry)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        companion object {
            // If you change the database schema, you must increment the database version.
            const val DATABASE_VERSION = 2
            const val DATABASE_NAME = "VoiceKo.db"
        }

    }

    class DBMgr(){
        private lateinit var voiceKoDbHelper : VoiceKoDbHelper
        private lateinit var activity: Context
        constructor(activity: Context): this(){
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
                if (!subCateIsExist(sub_cate,cate)){
                    insertNewSubCategory(sub_cate,cate)
                }

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
            val order = "LENGTH(${ConsumptionRecordEntry.COLUMN_DATE}), ${ConsumptionRecordEntry.COLUMN_DATE}"

            val cursor = db.query(
                ConsumptionRecordEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                order
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

        public fun updateRecord(recordID:Int,date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
            try {
                var db = voiceKoDbHelper.writableDatabase
                var values = ContentValues()
                values.put(ConsumptionRecordEntry.COLUMN_DATE,date)
                values.put(ConsumptionRecordEntry.COLUMN_AMOUNT,amount)
                values.put(ConsumptionRecordEntry.COLUMN_CATEGORY,cate)
                values.put(ConsumptionRecordEntry.COLUMN_SUB_CATEGORY,sub_cate)
                values.put(ConsumptionRecordEntry.COLUMN_REMARKS,remark)
                values.put(ConsumptionRecordEntry.COLUMN_TYPE, type)
                val selection = "_id = ${recordID}"

                val count = db.update(
                    ConsumptionRecordEntry.TABLE_NAME,
                    values,
                    selection,
                    null
                )
                this.closeDB()
                return count>0
            }catch ( e: SQLiteException){
                this.closeDB()
                return false
            }
        }

        public fun deleteRecord(recordID: Int):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val selection = "_id = ${recordID}"
                val count = db.delete(
                    ConsumptionRecordEntry.TABLE_NAME,
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

        public fun readSubCateName(parent: String):ArrayList<String>{
            var db = voiceKoDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                SubAccountTypeEntry.COLUMN_NAME,
                SubAccountTypeEntry.COLUMN_PARENT
            )
            val selection = "${SubAccountTypeEntry.COLUMN_PARENT} = ?"
            val selectionArgs = arrayOf(parent)

            val cursor = db.query(
                SubAccountTypeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            val subCateList = arrayListOf<String>()
            with(cursor){
                while (moveToNext()){
                    val subCateName = getString(getColumnIndexOrThrow(SubAccountTypeEntry.COLUMN_NAME))
                    subCateList.add(subCateName)
                }
            }
            this.closeDB()
            return subCateList
        }

        public fun subCateIsExist(subCate:String,parent: String):Boolean{
            var db = voiceKoDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                SubAccountTypeEntry.COLUMN_NAME
            )
            val selection = "${SubAccountTypeEntry.COLUMN_NAME} = ? AND ${SubAccountTypeEntry.COLUMN_PARENT} = ?"
            val selectionArgs = arrayOf(subCate,parent)

            val cursor = db.query(
                SubAccountTypeEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )
            var result = false
            with(cursor) {
                if (moveToNext()){
                    result =  true
                }

            }
            this.closeDB()
            return result
        }

        public fun insertNewSubCategory(subCate:String,parent: String):Int{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val values = ContentValues()
                values.put(SubAccountTypeEntry.COLUMN_NAME,subCate)
                values.put(SubAccountTypeEntry.COLUMN_PARENT, parent)
                db.insert(SubAccountTypeEntry.TABLE_NAME,null,values)
                this.closeDB()
                return 200
            }catch ( e: SQLiteException){
                this.closeDB()
                return 400
            }

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
                    val values2 = ContentValues()
                    values2.put(BudgetEntry.COLUMN_NAME,cate)
                    db.insert(BudgetEntry.TABLE_NAME,null,values2)
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
            var result = false
            with(cursor) {
                if (moveToNext()){
                    result =  true
                }

            }
            this.closeDB()
            return result
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

        public fun deleteCate(cateID:String):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val selection = "_id = ${cateID}"
                val count = db.delete(
                    AccountTypeEntry.TABLE_NAME,
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

        public fun insertNewPeriodRecord(workID:String,cycle:Long,date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
            try {
                var db = voiceKoDbHelper.writableDatabase
                var values = ContentValues()
                values.put(PeriodRecordsTypeEntry.COLUMN_WORKID, workID)
                values.put(PeriodRecordsTypeEntry.COLUMN_CYCLE, cycle)
                values.put(PeriodRecordsTypeEntry.COLUMN_DATE,date)
                values.put(PeriodRecordsTypeEntry.COLUMN_AMOUNT,amount)
                values.put(PeriodRecordsTypeEntry.COLUMN_CATEGORY,cate)
                values.put(PeriodRecordsTypeEntry.COLUMN_SUB_CATEGORY,sub_cate)
                values.put(PeriodRecordsTypeEntry.COLUMN_REMARKS,remark)
                values.put(PeriodRecordsTypeEntry.COLUMN_TYPE, type)
                db.insert(PeriodRecordsTypeEntry.TABLE_NAME,null,values)
                this.closeDB()
                return true

            }catch ( e: SQLiteException){
                this.closeDB()
                return false
            }
        }

        public fun readPeriodRecord():ArrayList<MutableMap<String,String>>{
            var db = voiceKoDbHelper.readableDatabase
            var projection = arrayOf(BaseColumns._ID,
                PeriodRecordsTypeEntry.COLUMN_WORKID,
                PeriodRecordsTypeEntry.COLUMN_CYCLE,
                PeriodRecordsTypeEntry.COLUMN_DATE,
                PeriodRecordsTypeEntry.COLUMN_AMOUNT,
                PeriodRecordsTypeEntry.COLUMN_CATEGORY,
                PeriodRecordsTypeEntry.COLUMN_SUB_CATEGORY,
                PeriodRecordsTypeEntry.COLUMN_REMARKS,
                PeriodRecordsTypeEntry.COLUMN_TYPE
            )
            val order = PeriodRecordsTypeEntry.COLUMN_CYCLE
            val cursor = db.query(
                PeriodRecordsTypeEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                order
            )

            var periodRecordsList = arrayListOf<MutableMap<String,String>>()
            with(cursor){
                while (moveToNext()){
                    val temp = mutableMapOf<String,String>()
                    val workID = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_WORKID))
                    val date = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_DATE))
                    val amount = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_AMOUNT))
                    val cate = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_CATEGORY))
                    val subCate = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_SUB_CATEGORY))
                    val cycle = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_CYCLE))
                    val remark = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_REMARKS))
                    val type = getString(getColumnIndexOrThrow(PeriodRecordsTypeEntry.COLUMN_TYPE))
                    temp["workID"] = workID
                    temp["date"] = date
                    temp["amount"] = amount
                    temp["cate"] = cate
                    temp["cycle"] = cycle
                    temp["remark"] = remark
                    temp["subCate"] = subCate
                    temp["type"] = type
                    periodRecordsList.add(temp)


                }
            }
            this.closeDB()
            return periodRecordsList
        }

        public fun updatePeriodRecord(workID:String,cycle:Long,date:String, amount :Int, cate : String, sub_cate:String, remark:String, type:String):Boolean{
            try {
                var db = voiceKoDbHelper.writableDatabase
                var values = ContentValues()
                values.put(PeriodRecordsTypeEntry.COLUMN_CYCLE, cycle)
                values.put(PeriodRecordsTypeEntry.COLUMN_DATE,date)
                values.put(PeriodRecordsTypeEntry.COLUMN_AMOUNT,amount)
                values.put(PeriodRecordsTypeEntry.COLUMN_CATEGORY,cate)
                values.put(PeriodRecordsTypeEntry.COLUMN_SUB_CATEGORY,sub_cate)
                values.put(PeriodRecordsTypeEntry.COLUMN_REMARKS,remark)
                values.put(PeriodRecordsTypeEntry.COLUMN_TYPE, type)
                val selection = "${PeriodRecordsTypeEntry.COLUMN_WORKID} = ?"
                val selectionArgs = arrayOf(workID)

                val count = db.update(
                    PeriodRecordsTypeEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
                this.closeDB()

                return count>0



            }catch ( e: SQLiteException){
                this.closeDB()
                return false
            }
        }

        public fun deletePeriodRecord(workID: String):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val selection = "${PeriodRecordsTypeEntry.COLUMN_WORKID} = ?"
                val selectionArgs = arrayOf(workID)
                val count = db.delete(
                    PeriodRecordsTypeEntry.TABLE_NAME,
                    selection,
                    selectionArgs)
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

        public fun updateBudget(cateName:String,budget:Long):Boolean{
            try {
                val db = voiceKoDbHelper.writableDatabase
                val values = ContentValues().apply {
                    put(BudgetEntry.COLUMN_Budget, budget)
                }
                val selection = "${BudgetEntry.COLUMN_NAME} = ?"
                val selectionArgs = arrayOf(cateName)

                val count = db.update(
                    BudgetEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs
                )
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

        public fun getBudget(cateName: String):Long{
            val db = voiceKoDbHelper.readableDatabase
            val projection = arrayOf(BaseColumns._ID,
                BudgetEntry.COLUMN_NAME,
                BudgetEntry.COLUMN_Budget
            )
            val selection = "${BudgetEntry.COLUMN_NAME} = ?"
            val selectionArgs = arrayOf(cateName)

            val cursor = db.query(
                BudgetEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
            )

            var result:Long = 0
            with(cursor) {
                while (moveToNext()) {
                    result = getLong(getColumnIndexOrThrow(BudgetEntry.COLUMN_Budget))
                }
            }
            this.closeDB()
            return result
        }

        public fun readEachCateAmount(type: String,year:String,month: String):ArrayList<MutableMap<String,String>>{
            val db = voiceKoDbHelper.readableDatabase
            val projection = arrayOf(BaseColumns._ID,
                ConsumptionRecordEntry.COLUMN_CATEGORY,
                "SUM(${ConsumptionRecordEntry.COLUMN_AMOUNT})"
            )
            val group =ConsumptionRecordEntry.COLUMN_CATEGORY
            val selection = "${ConsumptionRecordEntry.COLUMN_TYPE} = ? AND ${ConsumptionRecordEntry.COLUMN_DATE} LIKE '${year}/${month}/%'"
            val selectionArgs = arrayOf(type)
            val cursor = db.query(
                ConsumptionRecordEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                group,
                null,
                null
            )

            var eachCateAmount = arrayListOf<MutableMap<String,String>>()
            with(cursor){
                while (moveToNext()){
                    val temp =mutableMapOf<String,String>()
                    val cate = getString(getColumnIndexOrThrow(ConsumptionRecordEntry.COLUMN_CATEGORY))
                    val amount = getInt(getColumnIndexOrThrow("SUM(${ConsumptionRecordEntry.COLUMN_AMOUNT})"))
                    temp.put("cate",cate)
                    temp.put("amount",amount.toString())
                    eachCateAmount.add(temp)
                }
            }
            this.closeDB()
            return eachCateAmount
        }
    }

}
