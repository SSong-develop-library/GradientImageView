package com.ssong_develop.lib

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlin.math.roundToInt

class GradientImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    enum class GradientDirection(val value: Int) {
        LEFT_TO_RIGHT(0),
        RIGHT_TO_LEFT(1),
        TOP_TO_BOTTOM(2),
        BOTTOM_TO_TOP(3),
        LEFT_TOP_TO_RIGHT_BOTTOM(4),
        LEFT_BOTTOM_TO_RIGHT_TOP(5),
        RIGHT_TOP_TO_LEFT_BOTTOM(6),
        RIGHT_BOTTOM_TO_LEFT_TOP(7)
    }

    private val gradientRectF = RectF()
    private val gradientPaint = Paint()

    private var startColor = Color.BLACK
    private var endColor = Color.WHITE
    private var gradientAlpha = 1.0f
    private var direction: GradientDirection = GradientDirection.LEFT_TO_RIGHT

    private var cornerRadius by OnChangeProperty(context.dpToPixelFloat(0)) {
        updateBackground()
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.GradientImageView, 0, 0).use {
            startColor = it.getColor(
                R.styleable.GradientImageView_gradient_start_color,
                startColor
            )
            endColor = it.getColor(
                R.styleable.GradientImageView_gradient_end_color,
                endColor
            )
            gradientAlpha =
                it.getFloat(R.styleable.GradientImageView_gradient_alpha, gradientAlpha)
            cornerRadius =
                it.getDimension(R.styleable.GradientImageView_corner_radius, cornerRadius)
            direction = it.getInt(
                R.styleable.GradientImageView_gradient_direction,
                GradientDirection.LEFT_TO_RIGHT.value
            ).toGradientDirection()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        initializeGradientRectF()
        initializePaint()
        canvas.drawRoundRect(gradientRectF, cornerRadius, cornerRadius, gradientPaint)
    }

    private fun initializeGradientRectF() {
        gradientRectF.apply {
            top = 0f
            left = 0f
            right = width.toFloat()
            bottom = height.toFloat()
        }
    }

    private fun initializePaint() {
        gradientPaint.apply {
            shader = when (direction) {
                GradientDirection.LEFT_TO_RIGHT -> {
                    LinearGradient(
                        0f,
                        0f,
                        width.toFloat(),
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.RIGHT_TO_LEFT -> {
                    LinearGradient(
                        width.toFloat(),
                        0f,
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.TOP_TO_BOTTOM -> {
                    LinearGradient(
                        0f,
                        0f,
                        0f,
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.BOTTOM_TO_TOP -> {
                    LinearGradient(
                        0f,
                        height.toFloat(),
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.LEFT_TOP_TO_RIGHT_BOTTOM -> {
                    LinearGradient(
                        0f,
                        0f,
                        width.toFloat(),
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.LEFT_BOTTOM_TO_RIGHT_TOP -> {
                    LinearGradient(
                        0f,
                        height.toFloat(),
                        width.toFloat(),
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.RIGHT_TOP_TO_LEFT_BOTTOM -> {
                    LinearGradient(
                        width.toFloat(),
                        0f,
                        0f,
                        height.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
                GradientDirection.RIGHT_BOTTOM_TO_LEFT_TOP -> {
                    LinearGradient(
                        width.toFloat(),
                        height.toFloat(),
                        0f,
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
            }
            alpha = (gradientAlpha * 255).toInt()
        }
    }

    private fun updateBackground() {
        background =
            MaterialShapeDrawable(ShapeAppearanceModel().withCornerSize(cornerRadius)).apply {
                fillColor = ColorStateList.valueOf(Color.WHITE)
            }
    }
}

fun Int.toGradientDirection(): GradientImageView.GradientDirection =
    when (this) {
        0 -> GradientImageView.GradientDirection.LEFT_TO_RIGHT
        1 -> GradientImageView.GradientDirection.RIGHT_TO_LEFT
        2 -> GradientImageView.GradientDirection.TOP_TO_BOTTOM
        3 -> GradientImageView.GradientDirection.BOTTOM_TO_TOP
        4 -> GradientImageView.GradientDirection.LEFT_TOP_TO_RIGHT_BOTTOM
        5 -> GradientImageView.GradientDirection.LEFT_BOTTOM_TO_RIGHT_TOP
        6 -> GradientImageView.GradientDirection.RIGHT_TOP_TO_LEFT_BOTTOM
        7 -> GradientImageView.GradientDirection.RIGHT_BOTTOM_TO_LEFT_TOP
        else -> throw IllegalArgumentException("This value is not supported for GradientDirection : $this")
    }

fun Int.adjustAlpha(factor: Float): Int {
    val alpha = (Color.alpha(this) * factor).roundToInt()
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, red, green, blue)
}