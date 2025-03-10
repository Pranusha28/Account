package com.example.pdfviewer.views.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfviewer.R
import com.example.pdfviewer.data.model.Account
import com.example.pdfviewer.viewModel.AccountViewModel

class AccountAdapter(
    private var accounts: List<Account>,
    private val viewModel: AccountViewModel,
) : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.txtAccountName)
        val alternateName: EditText = view.findViewById(R.id.txtAlternateName)
        val saveButton: Button = view.findViewById(R.id.btnSave)
        val deleteButton: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = accounts[position]
        holder.name.text = account.accountName
        holder.alternateName.setText(account.alternateName ?: "")
        holder.saveButton.setOnClickListener {
            val updateAlternateName = holder.alternateName.text.toString()
            if (updateAlternateName.isNotEmpty()) {
                val updateAccount = account.copy(alternateName = updateAlternateName)
                viewModel.updateAccount(updateAccount)
            }
        }

        holder.deleteButton.setOnClickListener {
            viewModel.deleteAccount(account)
        }
    }

    override fun getItemCount() = accounts.size


    fun updateAccounts(newAccounts: List<Account>) {
        this.accounts = newAccounts
        notifyDataSetChanged()
    }


}
