package com.example.wallet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.wallet.data.BudgetItem



class BudgetItemDialog : DialogFragment() {

    interface BudgetItemDialogHandler {

        fun budgetItemCreated(item: BudgetItem)
        fun budgetItemUpdated(item: BudgetItem)
    }

    private lateinit var budgetItemHandler: BudgetItemDialogHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BudgetItemDialogHandler) {
            budgetItemHandler = context
        } else {
            throw RuntimeException("The Activity does not implement the BudgetItemDialogHandler interface")
        }
    }

    private lateinit var etName: EditText
    private lateinit var etPrice: EditText
    private lateinit var spinnerCategory: Spinner

    private var EDIT_MODE = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("New Item")



        if (this.arguments != null && this.arguments!!.containsKey(ScrollingActivity.KEY_EDIT)) {
            EDIT_MODE = true
        }

        if (EDIT_MODE) {
            builder.setTitle("Edit Item")
        } else {
            builder.setTitle("New Item")
        }

        val rootView = requireActivity().layoutInflater.inflate(
            R.layout.budget_dialog, null
        )

        etName = rootView.etName
        etPrice = rootView.etPrice
       // spinnerCategory = rootView.spinnerCategory
       /* var categoryAdapter = ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.category_array,
            android.R.layout.simple_spinner_item
        )
        categoryAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        spinnerCategory.adapter = categoryAdapter
        */
        builder.setView(rootView)

        if (EDIT_MODE) {
            var budgetItem =
                this.arguments!!.getSerializable(ScrollingActivity.KEY_EDIT) as BudgetItem

            etName.setText(budgetItem.description)
            etPrice.setText(budgetItem.value.toString())
           // spinnerCategory.setSelection(budgetItem.category)
        }

        builder.setPositiveButton("OK") { dialog, which ->
            //... keep empty
        }
        return builder.create()
    }

    override fun onResume() {
        super.onResume()

        val dialog = dialog as AlertDialog
        val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE)

        positiveButton.setOnClickListener {
            if (etName.text.isNotEmpty()) {
                if (etPrice.text.isNotEmpty()) {
                    if (EDIT_MODE) {
                        handleItemUpdate()
                    } else {
                        handleItemCreate()
                    }

                    dialog.dismiss()
                } else {
                    etPrice.error = "This field can not be empty"
                }
            } else {
                etName.error = "This field can not be empty"
            }
        }
    }

    fun handleItemCreate() {
        budgetItemHandler.budgetItemCreated(
            BudgetItem(
                null,
                etName.text.toString(),
                etPrice.text.toString().toInt(),
                //spinnerCategory.selectedItemPosition,
                false,
                //"Demo"
            )
        )
    }

    private fun handleItemUpdate() {
        var budgetItemToEdit = this.requireArguments().getSerializable(ScrollingActivity.KEY_EDIT) as BudgetItem

        budgetItemToEdit.description = etName.text.toString()
        budgetItemToEdit.value = etPrice.text.toString().toInt()
       // budgetItemToEdit.category = spinnerCategory.selectedItemPosition

        budgetItemHandler.budgetItemUpdated(budgetItemToEdit)
    }

}