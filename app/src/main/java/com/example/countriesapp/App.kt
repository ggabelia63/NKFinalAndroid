package com.example.countriesapp

import android.app.Application
import com.example.countriesapp.data.database.DatabaseHelper

class App : Application() {

    companion object {
        private var app: App? = null
        fun getAppInstance() = app
    }

    var databaseHelper: DatabaseHelper? = null

    override fun onCreate() {
        super.onCreate()
        app = this
        databaseHelper = DatabaseHelper(app!!.applicationContext, "Countries Database", null, 1)
    }
}