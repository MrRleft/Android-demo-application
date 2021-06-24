package com.rizq.android.usecases

import com.rizq.android.data.repositories.GNBRepository
import com.rizq.android.domain.core.Either
import com.rizq.android.domain.models.local.*
import com.rizq.android.usecases.gnb.GetAllTransactionsUC
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllTransactionsUCTest {

  @Mock
  lateinit var repository: GNBRepository

  lateinit var useCase: GetAllTransactionsUC

  val mockTransactions = TransactionsLM(1.1, Currency.EUR, "Ex")

  @Before
  fun setUp() {
    useCase = GetAllTransactionsUC(repository)
  }

  @Test
  fun useCaseTest() {

    runBlocking {
      Mockito.`when`(repository.getAllTransactions()).thenReturn(Either.Right(listOf(mockTransactions)))

      useCase(GetAllTransactionsUC.Params).collect() { result ->
        result.fold({
          Unit
        }, {
          assert(it.contains(mockTransactions))
        })
      }

      Mockito.verify(repository).getAllTransactions()
    }
  }

}