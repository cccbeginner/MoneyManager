package com.example.moneymanager.model.subcategory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.user.User

@Database(entities = [Subcategory::class, User::class, Category::class], version = 1, exportSchema = false)
abstract class SubcategoryDatabase : RoomDatabase(){
    abstract fun getDao(): SubcategoryDao

    companion object{
        private var instance: SubcategoryDatabase? = null

        fun getInstance(context: Context): SubcategoryDatabase {
            if(instance == null){
                synchronized(SubcategoryDatabase::class){
                    instance = Room.databaseBuilder(context, SubcategoryDatabase::class.java, MyApplication.databaseName).build()
                }
            }
            return instance!!
        }
    }
}