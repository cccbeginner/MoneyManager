package com.example.moneymanager.model.user

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moneymanager.MyApplication

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase(){
    abstract fun getDao(): UserDao

    companion object{
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            instance ?: synchronized(UserDatabase::class){
                    instance = Room.databaseBuilder(context, UserDatabase::class.java, MyApplication.databaseName)
                        .addMigrations(MIGRATION)
                        .build()
                }
            return instance!!
        }

        var MIGRATION: Migration = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                /*database.execSQL("CREATE TABLE tbl_user (\n" +
                        "\t_id INTEGER PRIMARY KEY,\n" +
                        "\tname TEXT NOT NULL,\n" +
                        "\temail TEXT NOT NULL,\n" +
                        "\tPhotoUriPath TEXT NOT NULL,\n" +
                        "\ttotalBudget INTEGER NOT NULL,\n" +
                        "\ttotalExpense INTEGER NOT NULL\n" +
                        ");")*/
            }
        }
    }
}