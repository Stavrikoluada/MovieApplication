package com.example.movieapplication.adapters

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.squareup.picasso.Transformation

class RoundedCornersTransformation(private val radius: Float) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())

        paint.isAntiAlias = true
        canvas.drawRoundRect(rect, radius, radius, paint)
        paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)

        canvas.drawBitmap(source, 0f, 0f, paint)
        if (source != output) {
            source.recycle()  // Освобождаем исходный Bitmap
        }
        return output
    }

    override fun key(): String {
        return "rounded($radius)"
    }
}