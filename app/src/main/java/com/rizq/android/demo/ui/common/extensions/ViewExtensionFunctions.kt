package com.rizq.android.demo.ui.common.extensions

import android.content.Context
import android.graphics.*
import com.bumptech.glide.Glide
import com.rizq.android.demo.R
import kotlinx.coroutines.*

suspend fun getBitmapFromUrl(context: Context, url: String): Bitmap = withContext(Dispatchers.IO) {
  val bitmap: Bitmap = Glide.with(context).asBitmap().timeout(30000).load(url).submit().get()
  val allCorner: Float = context.resources.getDimension(R.dimen.borderRadius)
  val topRightCorner: Float = context.resources.getDimension(R.dimen.borderRadius)
  val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
  val canvas = Canvas(output)
  val paint = Paint()
  val rect = Rect(0, 0, bitmap.width, bitmap.height)
  val path = Path()
  paint.isAntiAlias = true
  canvas.drawARGB(0, 0, 0, 0)
  paint.color = Color.WHITE
  path.addRoundRect(RectF(rect),
    floatArrayOf(allCorner, allCorner, topRightCorner, topRightCorner, allCorner, allCorner, allCorner, allCorner),
    Path.Direction.CW)
  canvas.drawPath(path, paint)
  paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
  canvas.drawBitmap(bitmap, rect, rect, paint)
  output
}