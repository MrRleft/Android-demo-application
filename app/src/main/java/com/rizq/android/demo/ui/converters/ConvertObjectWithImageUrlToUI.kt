package com.rizq.android.demo.ui.converters

import android.content.Context
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.*
import com.rizq.android.demo.R
import com.rizq.android.demo.ui.extensions.getBitmapFromUrl
import com.rizq.android.demo.ui.models.ItemWithImage
import com.rizq.android.domain.core.*
import com.rizq.android.domain.models.local.FutureObjectWithImage
import kotlinx.coroutines.flow.Flow

class ConvertObjectWithImageUrlToUI(private val context: Context) : UseCaseFlow<ConvertObjectWithImageUrlToUI.Params, List<ItemWithImage>>() {

  data class Params(val objectsToConvert: List<FutureObjectWithImage>)

  override suspend fun execute(params: Params): Flow<Either<Failure, List<ItemWithImage>>> {

    val listComm: MutableList<ItemWithImage> = mutableListOf()

    params.objectsToConvert.forEach { comm ->
      listComm.add(ItemWithImage(comm, RoundedBitmapDrawableFactory.create(context.resources, try {
        getBitmapFromUrl(context, comm.imageURL)
      } catch (ex: Exception) {
        getDrawable(context, R.drawable.error_image)?.toBitmap()
      })))
    }
    return Either.Right(listComm).asFlow()
  }
}