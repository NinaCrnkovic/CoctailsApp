package hr.algebra.coctailsapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.coctailsapp.model.Item

private const val DB_NAME = "items.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "items"
private val CREATE_TABLE = "create table $TABLE_NAME( " +
        "${Item::id.name} integer primary key autoincrement, " +
        "${Item::drinkName.name} TEXT NOT NULL, " +
        "${Item::category.name} TEXT NOT NULL, " +
        "${Item::instructions.name} TEXT NOT NULL, " +
        "${Item::picturePath.name} TEXT NOT NULL, " +
        "${Item::ingredient1.name} TEXT, " +
        "${Item::ingredient2.name} TEXT, " +
        "${Item::ingredient3.name} TEXT, " +
        "${Item::ingredient4.name} TEXT, " +
        "${Item::ingredient5.name} TEXT, " +
        "${Item::measure1.name} TEXT, " +
        "${Item::measure2.name} TEXT, " +
        "${Item::measure3.name} TEXT, " +
        "${Item::measure4.name} TEXT, " +
        "${Item::measure5.name} TEXT" +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class CocktailsSqlHelper(context: Context?) : SQLiteOpenHelper(
    context, DB_NAME, null, DB_VERSION
), Repository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }
    override fun delete(selection: String?, selectionArgs: Array<String>?)
    =writableDatabase.delete(
        TABLE_NAME,
        selection,
        selectionArgs
    )

   override fun insert(values: ContentValues?)
       =writableDatabase.insert(
           TABLE_NAME,
           null,
           values
       )

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun update(
         values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(
        TABLE_NAME,
        values,
        selection,
        selectionArgs
    )
}