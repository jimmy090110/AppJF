package com.ortega.jf.core

import android.app.Application
import com.ortega.jf.data.repository.DBConnection
import com.ortega.jf.data.repository.DBRepository
import com.ortega.jf.logic.login.SingIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class My_Applicacion :Application(){

    override fun onCreate() {
        super.onCreate()
        con=DBConnection().getConnection(applicationContext) //OJO
        GlobalScope.launch (Dispatchers.IO){

            SingIn(con).insertUser()

        }

    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    companion object {
        private lateinit var con:DBRepository
        fun getConnectionDB():DBRepository?{
            return con
        }
    }


}