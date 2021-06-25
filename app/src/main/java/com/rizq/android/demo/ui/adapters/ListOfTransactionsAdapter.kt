package com.rizq.android.demo.ui.adapters

import android.view.*
import androidx.recyclerview.widget.*
import com.rizq.android.demo.databinding.*

class ListOfTransactionsAdapter(val onClickOnTransaction: (sku: String) -> Unit) :
  ListAdapter<String, ListOfTransactionsAdapter.ItemsForTransferViewHolder>(object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
  }) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsForTransferViewHolder =
    ItemsForTransferViewHolder(ItemTransactionsGnbBinding.inflate(LayoutInflater.from(parent.context), parent, false))

  override fun onBindViewHolder(holder: ItemsForTransferViewHolder, position: Int) {
    holder.onBind(getItem(position))
  }

  inner class ItemsForTransferViewHolder(val binding: ItemTransactionsGnbBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(item: String) {
      with(binding) {
        setNameOfSku = item
        root.setOnClickListener { onClickOnTransaction(item) }
      }
    }
  }
}