package com.rizq.android.demo.ui.views.gnb.productselection

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizq.android.demo.R
import com.rizq.android.demo.databinding.*
import com.rizq.android.demo.ui.common.adapters.*
import com.rizq.android.domain.models.local.TransactionsLM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IndividualProductDialog(private val transactions: List<TransactionsLM>, private val amount: String) :
  DialogFragment(R.layout.dialog_individual_product_gnb) {

  private lateinit var binding: DialogIndividualProductGnbBinding
  private lateinit var recyclerAdapter: ListOfParticularSkuAdapter

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DialogIndividualProductGnbBinding.inflate(layoutInflater)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.configureUI()
  }

  private fun DialogIndividualProductGnbBinding.configureUI() {
    setTotalOfSku = amount + "€"
    recyclerAdapter = ListOfParticularSkuAdapter()
    binding.dialogRecyclerView.adapter = recyclerAdapter
    binding.dialogRecyclerView.layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
    recyclerAdapter.submitList(transactions)
  }
}