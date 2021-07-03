package com.example.moneymanager.ui.categories

import android.content.Context
import android.widget.Toast

class ToastResponse {
    companion object{
        fun cancel(context: Context){
            Toast.makeText(
                context,
                "取消",
                Toast.LENGTH_SHORT
            ).show()
        }
        fun insertSuccess(context: Context){
            Toast.makeText(
                context,
                "新增成功",
                Toast.LENGTH_SHORT
            ).show()
        }
        fun insertConflict(context: Context){
            Toast.makeText(
                context,
                "能重複喔",
                Toast.LENGTH_SHORT
            ).show()
        }
        fun updateSuccess(context: Context){
            Toast.makeText(
                context,
                "更新成功",
                Toast.LENGTH_SHORT
            ).show()
        }
        fun updateConflict(context: Context){
            Toast.makeText(
                context,
                "名字不能重複喔",
                Toast.LENGTH_SHORT
            ).show()
        }
        fun deleteSuccess(context: Context){
            Toast.makeText(
                context,
                "刪除成功",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}