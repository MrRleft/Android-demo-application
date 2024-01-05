package com.rizq.android.usecases.gnb

import com.rizq.android.data.repositories.local.RoomRepository
import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.TransactionsLM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetAllTransactionsUC(
  private val gNBRepository: GNBRepository,
  private val roomRepository: RoomRepository
  ): UseCaseFlow<GetAllTransactionsUC.Params, List<TransactionsLM>>(){

  object Params

  override suspend fun execute(params: Params): Flow<Either<Failure, List<TransactionsLM>>> {

    return when (val serverResult = gNBRepository.getAllTransactions()) {
      is Either.Right<*> -> serverResult
      is Either.Left<*> -> roomRepository.getAllTransactions()
    }.asFlow().flowOn(Dispatchers.IO)
  }

}