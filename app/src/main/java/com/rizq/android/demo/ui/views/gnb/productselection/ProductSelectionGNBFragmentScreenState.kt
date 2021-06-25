package com.rizq.android.demo.ui.views.gnb.productselection

import com.rizq.android.domain.models.local.TransactionsLM

sealed class ProductSelectionGNBFragmentScreenState {

  object FailureObtainingData: ProductSelectionGNBFragmentScreenState()

  class SuccessLoadingTransactions(val transactionsIds: List<String>): ProductSelectionGNBFragmentScreenState()
  class SuccessCalculatingSum(val amount: Double, val transactions: List<TransactionsLM>): ProductSelectionGNBFragmentScreenState()
}