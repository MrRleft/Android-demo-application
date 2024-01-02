package com.rizq.android.demo.ui.views.gnb.productselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizq.android.domain.core.ScreenState
import com.rizq.android.domain.models.local.TransactionsLM
import com.rizq.android.usecases.gnb.GetAllTransactionsUC
import com.rizq.android.usecases.gnb.GetCertainTransactionSumUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSelectionGNBFragmentViewmodel @Inject constructor(private val getAllTransactions: GetAllTransactionsUC,
                                                               private val getCertainTransactionSumUC: GetCertainTransactionSumUC) : ViewModel() {

  private val _state = MutableLiveData<ScreenState<ProductSelectionGNBFragmentScreenState>>()
  val state: LiveData<ScreenState<ProductSelectionGNBFragmentScreenState>> get() = _state

  private val listOfTransactions = mutableListOf<TransactionsLM>()

  fun getAllTransactions() {
    viewModelScope.launch {
      _state.value = ScreenState.Loading
      getAllTransactions(GetAllTransactionsUC.Params).collect() { result ->
        result.fold({
          _state.value = (ScreenState.Render(ProductSelectionGNBFragmentScreenState.FailureObtainingData))
          Unit
        }, {
          listOfTransactions.addAll(it)
          val resultList = listOfTransactions.map { it.sku }.distinct()
          _state.value = (ScreenState.Render(ProductSelectionGNBFragmentScreenState.SuccessLoadingTransactions(resultList)))
          Unit
        })
      }
    }
  }

  fun getTransactionSum(sku: String){
    viewModelScope.launch {
      _state.value = ScreenState.Loading
      val transactions = listOfTransactions.filter { it.sku == sku }
      getCertainTransactionSumUC(GetCertainTransactionSumUC.Params(transactions)).collect { result ->
        result.fold({
          _state.value = (ScreenState.Render(ProductSelectionGNBFragmentScreenState.FailureObtainingData))
          Unit
        }, {
          _state.value = (ScreenState.Render(ProductSelectionGNBFragmentScreenState.SuccessCalculatingSum(it.totalValue, it.operations)))
          Unit
        })
      }
    }
  }
}