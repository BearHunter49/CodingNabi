package com.example.codingnabi.utils

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import timber.log.Timber

object CodingBlockUtils {
    fun getBlock(context: Context, block: String): View = BlockFactory.makeBlockView(context, block)

    fun calculatePosition(linearLayout: LinearLayout, y: Float): Int {
        val childCount = linearLayout.childCount

        if (childCount == 0) return 0  // First block

        val positionList: MutableList<Pair<Int, Float>> = mutableListOf()
        linearLayout.children.forEachIndexed { i, child ->
            Timber.d("${child.x + child.width / 2}, ${child.y + child.height / 2}")

            positionList.add(Pair(i, child.y + child.height / 2))
        }

        if (y < positionList.first().second) return 0  // 맨 위
        if (y > positionList.last().second) return childCount  // 맨 아래

        for (i in 0 until childCount - 1) {
            if (positionList[i].second < y && y < positionList[i + 1].second) return i + 1
        }

        return 0
    }
}