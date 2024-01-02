package com.rizq.android.usecases.gnb

import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.TransactionsLM
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUC(private val gNBRepository: GNBRepository): UseCaseFlow<GetAllTransactionsUC.Params, List<TransactionsLM>>(){

  object Params

  override suspend fun execute(params: Params): Flow<Either<Failure, List<TransactionsLM>>> {
    return gNBRepository.getAllTransactions().asFlow()
  }

}