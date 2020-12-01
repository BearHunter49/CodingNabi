package com.example.codingnabi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.widget.TextViewCompat
import com.example.codingnabi.R
import com.google.android.material.button.MaterialButton
import timber.log.Timber

object BlockFactory {

    @SuppressLint("RestrictedApi", "WrongConstant")
    fun getBlockView(context: Context, block: String): View {
        return MaterialButton(
            ContextThemeWrapper(context, R.style.Widget_MaterialComponents_Button)
        ).apply {
            layoutParams = getLayoutParams(context)
            setTextColor(resources.getColor(R.color.black, null))
            setAutoSizeTextTypeWithDefaults(TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

            when (block) {
                "u" -> {
                    text = resources.getString(R.string.block_up)
                    setBackgroundColor(resources.getColor(R.color.block_up_down, null))
                    tag = resources.getString(R.string.tag_up)
                }
                "d" -> {
                    text = resources.getString(R.string.block_down)
                    setBackgroundColor(resources.getColor(R.color.block_up_down, null))
                    tag = resources.getString(R.string.tag_down)
                }
                "l" -> {
                    text = resources.getString(R.string.block_left)
                    setBackgroundColor(resources.getColor(R.color.block_left_right, null))
                    tag = resources.getString(R.string.tag_left)
                }
                "r" -> {
                    text = resources.getString(R.string.block_right)
                    setBackgroundColor(resources.getColor(R.color.block_left_right, null))
                    tag = resources.getString(R.string.tag_right)
                }
                "f" -> {
                    text = resources.getString(R.string.block_forward)
                    setBackgroundColor(resources.getColor(R.color.block_forward_backward, null))
                    tag = resources.getString(R.string.tag_forward)
                }
                "b" -> {
                    text = resources.getString(R.string.block_backward)
                    setBackgroundColor(resources.getColor(R.color.block_forward_backward, null))
                    tag = resources.getString(R.string.tag_backward)
                }
                "lp" -> {
                    text = resources.getString(R.string.block_loop)
                    setBackgroundColor(resources.getColor(R.color.block_loop, null))
                    tag = resources.getString(R.string.tag_loop)
                }
                "fc" -> {
                    text = resources.getString(R.string.block_function)
                    setBackgroundColor(resources.getColor(R.color.block_function, null))
                    tag = resources.getString(R.string.tag_function)
                }
                else -> {
                    Timber.e("There is no block such $block")
                    text = resources.getString(R.string.error)
                    setBackgroundColor(resources.getColor(R.color.white, null))
                    tag = resources.getString(R.string.error)
                }
            }
            setOnLongClickListener(UsableBlockLongClickListener())
        }
    }

    private fun getLayoutParams(context: Context): LinearLayout.LayoutParams{
        return LinearLayout.LayoutParams(
            context.resources.getDimension(R.dimen.codingBlockWidth).toInt(),
            context.resources.getDimension(R.dimen.codingBlockHeight).toInt()
        ).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
    }
}