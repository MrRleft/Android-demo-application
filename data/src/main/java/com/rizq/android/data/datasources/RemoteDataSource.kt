package com.rizq.android.data.datasources

import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.*

interface RemoteDataSource {
  suspend fun getTransactionsGNB(): Either<Failure, List<TransactionsLM>>
  suspend fun getRatesGNB(): Either<Failure, List<RatesLM>>
}