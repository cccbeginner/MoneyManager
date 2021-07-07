package com.example.moneymanager.model.subcategory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.moneymanager.MyApplication
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.user.User

@Database(entities = [Subcategory::class, User::class, Category::class], version = 3, exportSchema = false)
abstract class SubcategoryDatabase : RoomDatabase(){
    abstract fun getDao(): SubcategoryDao

    companion object{
        private var instance: SubcategoryDatabase? = null

        fun getInstance(context: Context): SubcategoryDatabase {
            if(instance == null){
                synchronized(SubcategoryDatabase::class){
                    instance = Room.databaseBuilder(context, SubcategoryDatabase::class.java, MyApplication.databaseName)
                        .addMigrations(MIGRATION)
                        .build()
                }
            }
            return instance!!
        }

        var MIGRATION: Migration = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //database.execSQL("ALTER TABLE tbl_subcategory RENAME TO tbl_subcategory_old")
                /*database.execSQL("CREATE TABLE tbl_subcategory (\n" +
                        "\t_id INTEGER PRIMARY KEY,\n" +
                        "\ttitle TEXT NOT NULL,\n" +
                        "\texpense INTEGER NOT NULL,\n" +
                        "\tuser_id INTEGER NOT NULL,\n" +
                        "\tcategory_id INTEGER NOT NULL,\n" +
                        "\tCONSTRAINT fk_user\n" +
                        "\t\tFOREIGN KEY (user_id)\n" +
                        "\t\tREFERENCES tbl_user(_id)\n" +
                        "\t\tON DELETE CASCADE,\n" +
                        "\tCONSTRAINT fk_category\n" +
                        "\t\tFOREIGN KEY (category_id)\n" +
                        "\t\tREFERENCES tbl_category(_id)\n" +
                        "\t\tON DELETE CASCADE\n" +
                        ");")
                database.execSQL("DROP INDEX IF EXISTS index_tbl_subcategory_user_id_category_id_title")
                database.execSQL("CREATE UNIQUE INDEX index_tbl_subcategory_user_id_category_id_title \n" +
                        "ON tbl_subcategory(user_id, category_id, title);")*/
                //database.execSQL("INSERT INTO tbl_subcategory SELECT * FROM tbl_subcategory_old;")
                //database.execSQL("DROP TABLE tbl_subcategory_old")
            }
        }
    }
}