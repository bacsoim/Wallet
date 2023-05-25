package com.example.wallet.data

import androidx.room.*

@Dao
interface BudgetDao{
    @Query("""SELECT * FROM description """)
    fun getAllBudget() : List<BudgetItem>

    @Insert
    fun insertBudget(todo: BudgetItem):Long

    @Delete
    fun deleteBudget(todo: BudgetItem)

    @Update
    fun updateBudget(todo: BudgetItem)

    @Query("DELETE FROM description")
    fun deleteAllBudget()
}