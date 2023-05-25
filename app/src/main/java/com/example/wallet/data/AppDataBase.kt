package com.example.wallet.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context


@Database(entities = arrayOf(BudgetItem::class), version = 1)
abstract class AppDataBase:RoomDatabase(){

    abstract fun budgetDao(): BudgetDao

    companion object{
        private var INSTANCE: AppDataBase?=null

        fun getInstance(context: Context): AppDataBase{
            if (INSTANCE==null){
                INSTANCE=Room.databaseBuilder(context,
                    AppDataBase::class.java, "budget.db")
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE=null
        }

    }

}



