package com.rizq.android.demo

import com.rizq.android.data.datasources.RemoteDataSource
import com.rizq.android.data.repositories.GNBRepository
import com.rizq.android.domain.models.local.RatesLM
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GNBRepositoryTest {

  lateinit var gnbRepository: GNBRepository

  @Mock
  lateinit var remoteDataSource: RemoteDataSource

  private val mockResponse: List<RatesLM>

  @Before
  fun setUp() {
    gnbRepository = GNBRepository(remoteDataSource)
    mockResponse.add(RatesLM())
    mockResponse.add(RatesLM())
    mockResponse.add(RatesLM())
  }

  @Test
  fun useCaseFailure() {

    runBlocking {
      useCase(CheckIfInternationalIBANUC.Params(inValidIban)).collect { result ->
        result.fold({
          assert(it is TransfersFailure.TransfersBICFailure)
        }, {
          assert(false)
        })
      }
    }
  }

  @Test
  fun useCaseSuccessNational() {
    runBlocking {
      useCase(CheckIfInternationalIBANUC.Params(localIban)).collect { result ->
        result.fold({}, {
          assert(!it)
        })
      }
    }
  }

  @Test
  fun useCaseSuccessInternational() {
    runBlocking {
      useCase(CheckIfInternationalIBANUC.Params(internationalIban)).collect { result ->
        result.fold({}, {
          assert(it)
        })
      }
    }
  }

}