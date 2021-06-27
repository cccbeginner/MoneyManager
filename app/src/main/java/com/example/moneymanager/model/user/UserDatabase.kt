package com.example.moneymanager.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneymanager.MyApplication

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getDao(): UserDao

    companion object{
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            instance ?: synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(context, UserDatabase::class.java, MyApplication.databaseName)
                            .fallbackToDestructiveMigration()
                            .build()
                }
            return instance!!
        }
    }
}