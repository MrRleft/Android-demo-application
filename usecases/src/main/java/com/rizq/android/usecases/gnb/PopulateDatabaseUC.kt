package com.rizq.android.usecases.gnb

import com.rizq.android.data.repositories.local.RoomRepository
import com.rizq.android.data.repositories.server.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.TransactionsLM
import kotlinx.coroutines.flow.Flow

class PopulateDatabaseUC(private val roomRepository: RoomRepository):
    UseCaseFlow<PopulateDatabaseUC.Params, Unit>(){

    data class Params(val rates: String, val transactions: String)

    override suspend fun execute(params: Params): Flow<Either<Failure, Unit>> {
        return roomRepository.populateDatabase(params.rates, params.transactions).asFlow()
    }
}