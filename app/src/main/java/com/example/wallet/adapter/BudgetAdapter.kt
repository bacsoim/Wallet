package com.example.wallet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.example.wallet.ScrollingActivity
import com.example.wallet.data.AppDataBase
import com.example.wallet.data.BudgetItem
import com.example.wallet.databinding.BudgetItemBinding




class BudgetAdapter : RecyclerView.Adapter<BudgetAdapter.ViewHolder> {

    private val items = mutableListOf<BudgetItem>()
    private val context: Context

    constructor(context: Context, itemsList: List<BudgetItem>) : super() {
        this.context = context
        items.addAll(itemsList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val budgetItemBinding=BudgetItemBinding.inflate(
            LayoutInflater.from(context), parent, false
        )

        return ViewHolder(budgetItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[holder.adapterPosition])
    }


    override fun getItemCount(): Int {
        return items.size
    }





    fun deleteItem(position: Int) {
        try {
            var itemToDelete = items.get(position)
            Thread {
                AppDataBase.getInstance(context).budgetDao().deleteBudget(itemToDelete)

                (context as ScrollingActivity).runOnUiThread {
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun addItem(item: BudgetItem) {
        items.add(item)

        notifyItemInserted(items.lastIndex)
    }

    fun updateItem(item: BudgetItem, editIndex: Int) {
        items.set(editIndex, item)
        notifyItemChanged(editIndex)
    }


    inner class ViewHolder(val budgetItemBinding: BudgetItemBinding) :
        RecyclerView.ViewHolder(budgetItemBinding.root) {

        fun bind(budgetItem: BudgetItem) {
            budgetItemBinding.tvName.text = budgetItem.description
            budgetItemBinding.tvPrice.text = budgetItem.value.toString()

            if (budgetItem.category == true) {
                (budgetItem.value == budgetItem.value);
                budgetItemBinding.ivItemLogo.setImageResource(R.drawable.income)
            } else {
                (budgetItem.value == -budgetItem.value);
                budgetItemBinding.ivItemLogo.setImageResource(R.drawable.cash)
            }


            budgetItemBinding.btnDelete.setOnClickListener {
                deleteItem(adapterPosition)

                budgetItemBinding.btnEdit.setOnClickListener {
                    (context as ScrollingActivity).showEditDialog(BudgetItem(adapterPosition))

                }

            }


        }
        }
}



