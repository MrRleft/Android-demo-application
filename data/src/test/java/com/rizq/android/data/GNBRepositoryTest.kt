package com.rizq.android.demo

import com.rizq.android.data.datasources.RemoteDataSource
import com.rizq.android.data.repositories.GNBRepository
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.*
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GNBRepositoryTest {

  lateinit var gnbRepository: GNBRepository

  @Mock
  lateinit var remoteDataSource: RemoteDataSource

  private val mockResponse: List<RatesLM> =
    listOf(RatesLM(Currency.USD, 1.22, Currency.EUR), RatesLM(Currency.AUD, 0.72, Currency.CAD), RatesLM(Currency.CAD, 1.85, Currency.USD))

  private val expectedRatesResult: List<RatesLM> = listOf(RatesLM(Currency.USD, 1.22, Currency.EUR),
    RatesLM(Currency.AUD, 0.72, Currency.CAD),
    RatesLM(Currency.CAD, 1.85, Currency.USD),
    RatesLM(Currency.AUD, 1.62504, Currency.EUR),
    RatesLM(Currency.CAD, 2.257, Currency.EUR))

  @Before
  fun setUp() {
    gnbRepository = GNBRepository(remoteDataSource)
  }

  @Test
  fun getRatesFullTest() {

    runBlocking {
      Mockito.`when`(remoteDataSource.getRatesGNB()).thenReturn(Either.Right(mockResponse))

      val realResult = gnbRepository.getRatesFull()


      assert(realResult.isRht)

      if (realResult.isRht) {
        val resultList = (realResult as Either.Right<List<RatesLM>>).b
        resultList.forEach {
          expectedRatesResult.contains(it)
        }
      }
    }
  }
}