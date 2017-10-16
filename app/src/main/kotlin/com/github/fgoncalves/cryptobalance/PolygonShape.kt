package com.github.fgoncalves.cryptobalance

import android.graphics.Paint
import android.graphics.Path

// Thanks to android and how onDraw is suppose to work, we cannot allocate objects. Therefore
// both these classes are mutable so they can be instantiated once and changed

data class Polygon(
        var x: Float = 0f,
        var y: Float = 0f,
        var radius: Float = 1f,
        var numberOfPoints: Int = 1)

class PolygonShape {
    val paint = Paint()
    val path = Path()
    var strokeColor
        get() = paint.color
        set(value) {
            paint.color = value
        }
    var strokeWidth
        get() = paint.strokeWidth
        set(value) {
            paint.strokeWidth = value
            paint.style = Paint.Style.STROKE
        }
    var polygon = Polygon()
        set(value) {
            val section = 2.0 * Math.PI / value.numberOfPoints

            path.run {
                reset()
                moveTo(value.x + value.radius, value.y)

                for (i in 1 until value.numberOfPoints) {
                    path.lineTo(
                            (value.x + value.radius * Math.cos(section * i)).toFloat(),
                            (value.x + value.radius * Math.sin(section * i)).toFloat())
                }

                path.close()
            }
        }
}
