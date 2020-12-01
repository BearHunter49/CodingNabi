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

/***
 * LongClick Listener for usable blocks.
 */
class UsableBlockLongClickListener : View.OnLongClickListener{
    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v?.tag as CharSequence)
        val dragData = ClipData("usableBlock", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        v.startDragAndDrop(dragData, View.DragShadowBuilder(v), v, 0)

        return true
    }
}

/***
 * LongClick Listener for block that moved to Coding Layout.
 */
class CodingBlockLongClickListener : View.OnLongClickListener{
    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v?.tag as CharSequence)
        val dragData = ClipData("codingBlock", arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
        v.startDragAndDrop(dragData, View.DragShadowBuilder(v), v, 0)

        v.visibility = View.INVISIBLE
        return true
    }
}

/***
 * Drag Listener for Coding Layout.
 * @param context: need to get resources.
 */
class CodingLayoutDragListener(val context: Context) : View.OnDragListener{
    private val originColor = context.resources.getColor(R.color.transparent, null)
    private val canBeDropped = context.resources.getColor(R.color.can_be_dropped, null)
    private val nowOnDropLayout = context.resources.getColor(R.color.now_on_drop_layout, null)

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        return when(event?.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                Timber.d("Drag Started")

                when(event.clipDescription.label){
                    "usableBlock" -> {
                        Timber.i("drag started usableBlock")
                        v?.setBackgroundColor(canBeDropped)
                        v?.invalidate()
                        true
                    }
                    "codingBlock" -> {
                        Timber.i("drag started codingBlock")
                        v?.setBackgroundColor(canBeDropped)
                        v?.invalidate()
                        true
                    }
                    else -> false
                }
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Timber.i("Drag Entered")
                v?.setBackgroundColor(nowOnDropLayout)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Timber.i("Drag Exited")
                v?.setBackgroundColor(canBeDropped)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                Timber.i("Drag Dropped")

                when(event.clipDescription.label){
                    "usableBlock" -> {
                        Timber.i("Drag Dropped usableBlock")

                        val item = event.clipData.getItemAt(0)
                        val dragData = item.text

                        Timber.d("CodingLayout: Dropped data: $dragData")
                        Timber.d("${event.x}, ${event.y}")

                        // Create new block according to tag
                        val newBlock = BlockFactory.makeBlockView(context, dragData.toString())
                        newBlock.setOnLongClickListener(CodingBlockLongClickListener())

                        // Get position to insert block
                        val contentLayout = v as LinearLayout
                        val position = CodingBlockUtils.calculatePosition(contentLayout, event.y)

                        contentLayout.addView(newBlock, position)
                        v.invalidate()
                    }
                    "codingBlock" -> {
                        Timber.i("Drag Dropped codingBlock")

                        val item = event.clipData.getItemAt(0)
                        val dragData = item.text
                        Timber.d("Dropped data: $dragData")

                        val originBlock = event.localState as View
                        val parentView = originBlock.parent as ViewGroup

                        // Remove relation from parent view group
                        parentView.removeView(originBlock)

                        // Get position to insert block
                        val contentLayout = v as LinearLayout
                        val position = CodingBlockUtils.calculatePosition(contentLayout, event.y)

                        contentLayout.addView(originBlock, position)

                        // Set original block visible
                        originBlock.visibility = View.VISIBLE
                        v.invalidate()
                    }
                }
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Timber.i("Drag Ended")
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

/***
 * Drag Listener for Delete Block Layout.
 * @param context: need to get resources.
 */
class DeleteImageDragListener(val context: Context) : View.OnDragListener{
    private val originColor = context.resources.getColor(R.color.transparent, null)
    private val canBeDropped = context.resources.getColor(R.color.can_be_dropped, null)
    private val nowOnDropLayout = context.resources.getColor(R.color.now_on_drop_layout, null)

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        return when(event?.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                Timber.i("Drag Started")

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
                Timber.i("Drag Entered")
                v?.setBackgroundColor(nowOnDropLayout)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Timber.i("Drag Exited")
                v?.setBackgroundColor(canBeDropped)
                v?.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                Timber.i("Drag Dropped")

                val item = event.clipData.getItemAt(0)
                val dragData = item.text
                Timber.d("Dropped data: $dragData")

                // Just remove from parent view group
                val originBlock = event.localState as View
                val parentView = originBlock.parent as ViewGroup
                parentView.removeView(originBlock)

                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                Timber.i("Drag Ended")
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