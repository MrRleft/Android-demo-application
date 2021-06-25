package com.rizq.android.demo.ui.adapters

import android.view.*
import androidx.recyclerview.widget.*
import com.rizq.android.demo.databinding.ItemIndividualTransactionBinding
import com.rizq.android.domain.models.local.TransactionsLM

class ListOfParticularSkuAdapter :
  ListAdapter<TransactionsLM, ListOfParticularSkuAdapter.ItemsForTransferViewHolder>(object : DiffUtil.ItemCallback<TransactionsLM>() {
    override fun areItemsTheSame(oldItem: TransactionsLM, newItem: TransactionsLM): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: TransactionsLM, newItem: TransactionsLM): Boolean = oldItem != newItem
  })  {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsForTransferViewHolder =
    ItemsForTransferViewHolder(ItemIndividualTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ItemsForTransferViewHolder, position: Int) {
    holder.onBind(getItem(position))
  }

  inner class ItemsForTransferViewHolder(val binding: ItemIndividualTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: TransactionsLM) {
      with(binding) {
        setNameOfSku = item.sku
        setMoneyOfSku = item.amount.toString() + item.currency.value
      }
    }
  }
}