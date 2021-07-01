package com.example.moneymanager.model.subcategory

import androidx.room.*
import com.example.moneymanager.model.category.Category
import com.example.moneymanager.model.user.User

@Entity(tableName = "tbl_subcategory", indices = [
    Index(value = ["user_id", "category_id", "title"], unique = true)
], foreignKeys = [ForeignKey(
    entity = User::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("user_id"),
    onDelete = ForeignKey.CASCADE
), ForeignKey(
    entity = Category::class,
    parentColumns = arrayOf("_id"),
    childColumns = arrayOf("category_id"),
    onDelete = ForeignKey.CASCADE
)]
)
class Subcategory(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") var id: Long?,
    @ColumnInfo(name = "user_id") var userId: Long,
    @ColumnInfo(name = "category_id") var categoryId: Long,
    var title: String,
    var expense: Long
){
    constructor(userId: Long, categoryId: Long, title: String):this(null, userId, categoryId, title, 0)
}
