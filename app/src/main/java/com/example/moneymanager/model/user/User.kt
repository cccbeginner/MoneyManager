package com.example.moneymanager.model.user

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") var id: Long?,
    var name: String?,
    var email: String,
    var PhotoUriPath: String?,
    var totalBudget: Long,
    var totalExpense: Long
){
    constructor(name: String?, email: String, photoUriPath: String):this(null, name, email, photoUriPath, 0, 0)

    fun getPhotoUri(): Uri {
        return Uri.parse(this.PhotoUriPath)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        return result
    }
}
