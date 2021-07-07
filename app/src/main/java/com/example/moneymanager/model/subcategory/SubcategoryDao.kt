package com.example.moneymanager.model.subcategory

import androidx.room.*
import com.example.moneymanager.model.category.Category
import java.util.*

@Dao
interface SubcategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun justInsert(subcategory: Subcategory)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun justUpdate(subcategory: Subcategory)

    @Delete
    suspend fun delete(subcategory: Subcategory)

    @Query("SELECT * FROM tbl_subcategory WHERE _id = :id")
    fun getById(id: Long): Subcategory?

    @Query("SELECT * FROM tbl_category WHERE _id = :categoryId")
    suspend fun getCategoryById(categoryId: Long): Category

    @Query("SELECT * FROM tbl_subcategory WHERE user_id = :userId and category_id = :categoryId")
    fun getByCategory(userId: Long, categoryId: Long): Array<Subcategory>

    @Query("SELECT * FROM tbl_subcategory WHERE user_id = :userId and category_id = :categoryId  and title = :title")
    fun getTheSubcategory(userId: Long, categoryId: Long, title: String): Subcategory?

    @Transaction
    suspend fun insert(subcategory: Subcategory): Boolean{
        val exist: Subcategory? = getTheSubcategory(subcategory.userId, subcategory.categoryId, subcategory.title)
        return if (exist == null){
            justInsert(subcategory)
            true
        }else{
            false
        }
    }

    @Transaction
    suspend fun update(subcategory: Subcategory): Boolean{
        val exist: Subcategory? = getTheSubcategory(subcategory.userId, subcategory.categoryId, subcategory.title)
        return if (exist == null){
            justUpdate(subcategory)
            true
        }else{
            false
        }
    }
}