package com.github.fgoncalves.cryptobalance

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates


class PolygonView : View {
    companion object {
        const val DEFAULT_NUMBER_OF_POINTS = 3
        const val DEFAULT_RADIUS_RATIO = .5f
        const val DEFAULT_STROKE_COLOR = Color.WHITE
        const val DEFAULT_STROKE_WIDTH = 3f
    }

    private val polygonShape = PolygonShape()
    private val polygon = Polygon()

    var numberOfPoints by Delegates.observable(DEFAULT_NUMBER_OF_POINTS) { _, _, newValue ->
        invalidate()
    }
    var radiusRatio by Delegates.observable(DEFAULT_RADIUS_RATIO) { _, _, _ ->
        invalidate()
    }
    var strokeColor by Delegates.observable(DEFAULT_STROKE_COLOR) { _, _, _ ->
        invalidate()
    }
    var strokeWith by Delegates.observable(DEFAULT_STROKE_WIDTH) { _, _, _ ->
        invalidate()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeFromAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initializeFromAttrs(context, attrs)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (width == 0 && height == 0) return

        val x = width.toFloat() / 2f
        val y = height.toFloat() / 2f
        val radius = if (width > height)
            height * radiusRatio
        else
            width * radiusRatio

        polygon.x = x
        polygon.y = y
        polygon.radius = radius
        polygon.numberOfPoints = numberOfPoints

        polygonShape.strokeColor = strokeColor
        polygonShape.strokeWidth = strokeWith
        polygonShape.polygon = polygon

        canvas?.drawPath(polygonShape.path, polygonShape.paint)
    }

    private fun initializeFromAttrs(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.PolygonView,
                0, 0)

        try {
            numberOfPoints = a.getInt(R.styleable.PolygonView_numberOfPoints, DEFAULT_NUMBER_OF_POINTS)
        } finally {
            a.recycle()
        }
    }
}
