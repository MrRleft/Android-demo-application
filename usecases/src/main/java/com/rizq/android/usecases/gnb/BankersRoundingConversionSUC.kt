package com.rizq.android.usecases.gnb

import com.rizq.android.domain.bankersRounding
import com.rizq.android.domain.core.SimpleUseCase

class BankersRoundingConversionSUC: SimpleUseCase<Double, BankersRoundingConversionSUC.Params>() {

  data class Params(val value: Double, val conversionFactor: Double)

  override fun run(params: Params): Double {
    return (params.value * params.conversionFactor).bankersRounding()
  }
}