package com.example.moneymanager.model.category

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.moneymanager.model.user.User

@Entity(tableName = "tbl_category", indices = [
    Index(value = ["user_id", "title", "type"], unique = true)
], foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("user_id"),
    onDelete = CASCADE
)]
)
data class Category(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") var id: Long?,
    @ColumnInfo(name = "user_id") var userId: Long,
    var title: String,
    var type: Int,
    var budget: Long,
    var expense: Long
){
    constructor(user_id: Long, title: String, type: Int) : this(null, user_id, title, type, 0, 0)

    companion object {
        const val Income = 15696
        const val Outcome = 56329
    }
}
