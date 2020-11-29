package com.bitxflow.funny.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameDB::class],version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase()
{
    abstract fun gameDao():GameDao

    companion object{
        private var INSTANCE : GameDatabase? = null

        fun getInstance(context : Context): GameDatabase? {
            if(INSTANCE == null){
                synchronized(GameDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        GameDatabase::class.java,
                        "MemberDatabase.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }

        fun destroyInstace(){
            INSTANCE = null
        }
    }


}