package com.example.wallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wallet.adapter.BudgetAdapter
import com.example.wallet.data.AppDataBase
import com.example.wallet.data.BudgetItem
import com.example.wallet.databinding.ActivityScrollingBinding


class ScrollingActivity :
    AppCompatActivity(), BudgetItemDialog.BudgetItemDialogHandler {

    lateinit var binding: ActivityScrollingBinding

    companion object {
        const val KEY_EDIT = "KEY_EDIT"
    }

    private lateinit var adapter: BudgetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title=title

        initRecyclerView()

        binding.fab.setOnClickListener {
            BudgetItemDialog().show(supportFragmentManager, "TAG_SHOP_DIALOG")
        }

    }


    private fun initRecyclerView() {
        Thread {
            var budgetItemList =
                AppDataBase.getInstance(this@ScrollingActivity).budgetDao().getAllBudget()

            runOnUiThread {
                adapter = BudgetAdapter(this, budgetItemList)
                binding.recyclerBudget.adapter = adapter
            }
        }.start()
    }


    var editIndex = -1

    fun showEditDialog(budgetItem: BudgetItem, adapterPosition: Int) {
        editIndex = adapterPosition

        val editDialog = BudgetItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_EDIT, budgetItem)
        editDialog.arguments = bundle

        editDialog.show(supportFragmentManager, "EDITDIALOG")
    }


    override fun budgetItemCreated(item: BudgetItem) {
        Thread {
            var newId = AppDataBase.getInstance(this@ScrollingActivity).budgetDao()
                .insertBudget(item)
            item.itemId = newId
            runOnUiThread {
                adapter.addItem(item)
            }
        }.start()
    }

    override fun budgetItemUpdated(item: BudgetItem) {
        Thread {
            AppDataBase.getInstance(this@ScrollingActivity).budgetDao()
                .updateBudget(item)
            runOnUiThread {
                adapter.updateItem(item, editIndex)
            }
        }.start()
    }


}