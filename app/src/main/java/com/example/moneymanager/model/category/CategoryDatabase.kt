package com.example.moneymanager.model.category

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.user.User


@Database(entities = [Category::class, User::class], version = 2, exportSchema = false)
abstract class CategoryDatabase : RoomDatabase(){
    abstract fun getDao(): CategoryDao

    companion object{
        private var instance: CategoryDatabase? = null

        fun getInstance(context: Context): CategoryDatabase {
            if(instance == null){
                synchronized(CategoryDatabase::class){
                    instance = Room.databaseBuilder(context, CategoryDatabase::class.java, MyApplication.databaseName)
                        .addMigrations(MIGRATION)
                        .build()
                }
            }
            return instance!!
        }

        var MIGRATION: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // actually its only difference is onDelete = CASCADE
                // but I don't know how to do only edit part of foreign key
                database.execSQL("ALTER TABLE tbl_category RENAME TO tbl_category_old")
                database.execSQL("CREATE TABLE tbl_category (\n" +
                        "\t_id INTEGER PRIMARY KEY,\n" +
                        "\ttitle TEXT NOT NULL,\n" +
                        "\ttype INTEGER NOT NULL,\n" +
                        "\texpense INTEGER NOT NULL,\n" +
                        "\tuser_id INTEGER NOT NULL,\n" +
                        "\tbudget INTEGER NOT NULL,\n" +
                        "\tCONSTRAINT fk_user\n" +
                        "\t\tFOREIGN KEY (user_id)\n" +
                        "\t\tREFERENCES tbl_user(_id)\n" +
                        "\t\tON DELETE CASCADE\n" +
                        ");")
                database.execSQL("DROP INDEX IF EXISTS index_tbl_category_user_id_title_type")
                database.execSQL("CREATE UNIQUE INDEX index_tbl_category_user_id_title_type \n" +
                        "ON tbl_category(user_id, title, type);")
                database.execSQL("INSERT INTO tbl_category SELECT * FROM tbl_category_old;")
                database.execSQL("DROP TABLE tbl_category_old")
            }
        }
    }
}