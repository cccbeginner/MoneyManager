package com.example.moneymanager.model.user

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * FROM tbl_user")
    fun getAll(): List<User>

    @Query("SELECT * FROM tbl_user WHERE _id = :id")
    fun getById(id: Long): User?

    @Query("SELECT * FROM tbl_user WHERE email = :email")
    fun getByEmail(email: String): User?

    @Transaction
    fun insertWithCheck(user: User): Boolean{
        // check by username
        val exist: User? = getByEmail(user.email)
        return if (exist == null){
            // if user is not exist, insert it
            insert(user)
            true
        }else{
            false
        }
    }

    @Transaction
    fun getWithInsert(user: User): User {
        insertWithCheck(user)
        return getByEmail(user.email)!!
    }
}