package com.rizq.android.demo.ui.views.gnb.productselection

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizq.android.demo.R
import com.rizq.android.demo.databinding.*
import com.rizq.android.demo.ui.adapters.ListOfTransactionsAdapter
import com.rizq.android.domain.bankersRounding
import com.rizq.android.domain.core.ScreenState
import com.rizq.android.domain.models.local.TransactionsLM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductSelectionGNBFragment : Fragment(R.layout.fragment_product_selection_gnb) {

  private lateinit var binding: FragmentProductSelectionGnbBinding

  private val recyclerAdapter = ListOfTransactionsAdapter(::onClickOnTransaction)
  private val mViewModel: ProductSelectionGNBFragmentViewmodel by viewModels()

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = FragmentProductSelectionGnbBinding.inflate(layoutInflater)
    val view = binding.root
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    initStates()
    initRecycler()
    mViewModel.getAllTransactions()
  }

  private fun initRecycler() {
    binding.mainRecyclerView.adapter = recyclerAdapter
    binding.mainRecyclerView.layoutManager = LinearLayoutManager(this.context).apply { orientation = LinearLayoutManager.VERTICAL }
  }

  private fun initStates() {
    mViewModel.state.observe(::getLifecycle, ::updateUI)
  }

  private fun updateUI(screenState: ScreenState<ProductSelectionGNBFragmentScreenState>) {
    when (screenState) {
      ScreenState.Loading -> {
      }
      is ScreenState.Render -> renderScreenState(screenState.renderState)
      else -> {
      }
    }
  }

  private fun renderScreenState(renderState: ProductSelectionGNBFragmentScreenState) {
    when (renderState) {
      ProductSelectionGNBFragmentScreenState.FailureObtainingData -> TODO("Create Error Message")
      is ProductSelectionGNBFragmentScreenState.SuccessCalculatingSum -> createDialogIndividualTransaction(renderState.amount,
        renderState.transactions)
      is ProductSelectionGNBFragmentScreenState.SuccessLoadingTransactions -> setTransactionsToRecycler(renderState.transactionsIds)
    }
  }

  private fun setTransactionsToRecycler(transactionsIds: List<String>) {
    recyclerAdapter.submitList(transactionsIds)
  }

  private fun createDialogIndividualTransaction(amount: Double, transactions: List<TransactionsLM>) {
    DialogIndividualProduct(transactions, amount.bankersRounding().toString()).show(this.childFragmentManager, "SKU")
  }

  private fun onClickOnTransaction(sku: String) {
    mViewModel.getTransactionSum(sku)
  }
}
