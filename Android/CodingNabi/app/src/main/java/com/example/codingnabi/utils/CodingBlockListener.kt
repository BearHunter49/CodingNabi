package com.example.codingnabi.utils

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.codingnabi.R
import timber.log.Timber

class UsableBlockLongClickListener : View.OnLongClickListener{
    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v?.tag as CharSequence)
        val dragData = ClipData("usableBlock", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        v.startDragAndDrop(dragData, View.DragShadowBuilder(v), v, 0)

        return true
    }
}

class CodingBlockLongClickListener : View.OnLongClickListener{
    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v?.tag as CharSequence)
        val dragData = ClipData("codingBlock", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        v.startDragAndDrop(dragData, View.DragShadowBuilder(v), v, 0)

        v.visibility = View.INVISIBLE
        return true
    }
}

class CodingLayoutDragListener(val context: Context) : View.OnDragListener{
    private val originColor = context.resources.getColor(R.color.transparent, null)
    private val canBeDropped = context.resources.getColor(R.color.can_be_dropped, null)
    private val dropLayoutOn = context.resources.getColor(R.color.drop_layout_on, null)

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        return when(event?.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                Timber.d("CodingLayout: Drag Stated")
                if (event.clipDescription.label == "usableBlock") {
                    v?.setBackgroundColor(canBeDropped)
                    v?.invalidate()
                    true
                } else {
                    // Can not be dropped place.
                    false
                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Timber.i("CodingLayout: Drag Entered")
                v?.setBackgroundColor(dropLayoutOn)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Timber.i("CodingLayout: Drag Exited")
                v?.setBackgroundColor(canBeDropped)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                Timber.i("CodingLayout: Drag Dropped")

                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Timber.d("CodingLayout: Dropped data: $dragData")

                val newBlock = BlockFactory.getBlockView(context, dragData.toString())
                newBlock.setOnLongClickListener(CodingBlockLongClickListener())

                (v as LinearLayout).addView(newBlock)
                v.invalidate()

                true

            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Timber.i("CodingLayout: Drag Ended")
                v?.setBackgroundColor(originColor)
                v?.invalidate()

                when (event.result) {
                    true -> Timber.d("${v?.tag}: drop was handled.")
                    else -> Timber.d("${v?.tag}: drop wasn't handled.")
                }
                true
            }
            else -> {
                Timber.e("Unknown action in CodingLayoutDragListener")
                false
            }
        }
    }
}

class DeleteImageDragListener(val context: Context) : View.OnDragListener{
    private val originColor = context.resources.getColor(R.color.transparent, null)
    private val canBeDropped = context.resources.getColor(R.color.can_be_dropped, null)
    private val dropLayoutOn = context.resources.getColor(R.color.drop_layout_on, null)

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        return when(event?.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                Timber.d("DeleteImage: Drag Stated")
                if (event.clipDescription.label == "codingBlock") {
                    v?.setBackgroundColor(canBeDropped)
                    v?.invalidate()


                    true
                } else {
                    // Can not be dropped place.
                    false
                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Timber.i("DeleteImage: Drag Entered")
                v?.setBackgroundColor(dropLayoutOn)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Timber.i("DeleteImage: Drag Exited")
                v?.setBackgroundColor(canBeDropped)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                Timber.i("DeleteImage: Drag Dropped")

                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Timber.d("DeleteImage: Dropped data: $dragData")

                val originBlock = event.localState as View
                val parentView = originBlock.parent as ViewGroup
                parentView.removeView(originBlock)

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Timber.i("DeleteImage: Drag Ended")
                v?.setBackgroundColor(originColor)
                v?.invalidate()

                when (event.result) {
                    true -> Timber.d("${v?.tag}: drop was handled.")
                    else -> {
                        Timber.d("${v?.tag}: drop wasn't handled.")
                        val originBlock = event.localState as View
                        originBlock.visibility = View.VISIBLE
                    }
                }
                true
            }
            else -> {
                Timber.e("Unknown action in DeleteImageDragListener")
                false
            }
        }
    }
}