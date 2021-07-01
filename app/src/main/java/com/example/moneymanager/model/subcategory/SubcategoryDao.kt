package com.example.moneymanager.model.subcategory

import androidx.room.*
import com.example.moneymanager.model.subcategory.Subcategory

@Dao
interface SubcategoryDao {

    @Insert
    fun justInsert(subcategory: Subcategory)

    @Update
    fun justUpdate(subcategory: Subcategory)

    @Delete
    fun delete(subcategory: Subcategory)

    @Query("SELECT * FROM tbl_subcategory WHERE _id = :id")
    fun getById(id: Long): Subcategory?

    @Query("SELECT * FROM tbl_subcategory WHERE user_id = :userId and category_id = :categoryId  and title = :title")
    fun getTheSubcategory(userId: Long, categoryId: Long, title: String): Subcategory?

    @Transaction
    fun insert(subcategory: Subcategory): Boolean{
        val exist: Subcategory? = getTheSubcategory(subcategory.userId, subcategory.categoryId, subcategory.title)
        return if (exist == null){
            justInsert(subcategory)
            true
        }else{
            false
        }
    }

    @Transaction
    fun update(subcategory: Subcategory): Boolean{
        val exist: Subcategory? = getTheSubcategory(subcategory.userId, subcategory.categoryId, subcategory.title)
        return if (exist == null){
            justUpdate(subcategory)
            true
        }else{
            false
        }
    }
}