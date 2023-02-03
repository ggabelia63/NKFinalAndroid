package com.example.countriesapp.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.countriesapp.data.model.CountryEntity
import com.example.countriesapp.data.model.CountryResponse

class DatabaseHelper(
    context: Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        private const val CREATE_COUNTRY_TABLE =
            "CREATE TABLE IF NOT EXISTS Country" + "(" + "CountryName Text PRIMARY KEY," +
                    "Region TEXT," +
                    "Flag Text)"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(CREATE_COUNTRY_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS Country");
        onCreate(p0);
    }

    fun insertCountry(countries: List<CountryResponse>) {
        val db = this.writableDatabase
        db.beginTransaction()
        try {
            countries.forEach {
                val contentValues = ContentValues()
                contentValues.put("CountryName", it.name.common)
                contentValues.put("Region", it.region)
                contentValues.put("Flag", it.flags.png)
                db.insert("Country", null, contentValues)
            }
            db.setTransactionSuccessful()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
            db.close()
        }
    }

    @SuppressLint("Range")
    fun getCountries(): List<CountryEntity> {
        val countries = mutableListOf<CountryEntity>()
        val db = readableDatabase
        val selectQuery = "SELECT * FROM Country"
        val cursor = db.rawQuery(selectQuery, null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    val countryName = cursor.getString(0)
                    val region = cursor.getString(1)
                    val flag = cursor.getString(2)
                    countries.add(CountryEntity(countryName, region, flag))

                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return countries
    }
}