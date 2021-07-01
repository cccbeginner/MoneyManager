package com.example.moneymanager.model.category

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.user.User

@Database(entities = [Category::class, User::class], version = 1, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase(){
    abstract fun getDao(): CategoryDao

    companion object{
        private var instance: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            if(instance == null){
                synchronized(CategoryDatabase::class){
                    instance = Room.databaseBuilder(context, CategoryDatabase::class.java, MyApplication.databaseName).build()
                }
            }
            return instance!!
        }
    }
}