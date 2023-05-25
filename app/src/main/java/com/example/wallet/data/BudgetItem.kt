package com.example.wallet.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "budgetitems")
    data class BudgetItem(
    @PrimaryKey(autoGenerate = true) var itemId: Long?,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "value") var value: Number,
   // @ColumnInfo(name = "position") var selectedItemPosition: Int,
    @ColumnInfo(name = "type") var category: Boolean
): Serializable
