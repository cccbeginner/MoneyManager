package com.example.moneymanager.model.category

import androidx.room.*

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun justInsert(category: Category)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun justUpdate(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM tbl_category WHERE _id = :id")
    fun getById(id: Long): Category?

    @Query("SELECT * FROM tbl_category WHERE user_id = :userId and type = :type")
    fun getByType(userId: Long, type: Int): Array<Category>

    @Query("SELECT * FROM tbl_category WHERE user_id = :userId and title = :title and type = :type")
    fun getTheCategory(userId: Long, title: String, type: Int): Category?

    @Transaction
    suspend fun insert(category: Category): Boolean{
        val exist: Category? = getTheCategory(category.userId, category.title, category.type)
        return if (exist == null){
            justInsert(category)
            true
        }else{
            false
        }
    }

    @Transaction
    suspend fun update(category: Category): Boolean{
        val exist: Category? = getTheCategory(category.userId, category.title, category.type)
        return if (exist == null){
            justUpdate(category)
            true
        }else{
            false
        }
    }
}