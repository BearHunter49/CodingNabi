package com.example.codingnabi.utils

import android.view.View
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.codingnabi.R
import com.example.codingnabi.adapter.ProblemAdapter
import com.example.codingnabi.data.entity.Problem
import timber.log.Timber

fun getPrefixByCategory(category: String): String {
    return when (category) {
        "basic" -> "B"
        "advance" -> "A"
        "loop" -> "L"
        "function" -> "F"
        else -> "ER"
    }
}

fun goCodingDetail(view: View, category: String, level: Int) {
    val bundle = bundleOf("category" to category, "level" to level)
    view.findNavController()
        .navigate(R.id.action_codingLevelSelectFragment_to_codingDetailFragment, bundle)
}

@BindingAdapter("setListItem")
fun setListItemToAdapter(recyclerView: RecyclerView, item: LiveData<List<Problem>>?){
    val adapter: ProblemAdapter = recyclerView.adapter as ProblemAdapter
    item?.value?.let { adapter.setData(it) }
}

//@BindingAdapter("setVisibility")
//fun setVisibilityOfCleared(imageView: ImageView, isCleared: Int) {
//    imageView.visibility =
//        when (isCleared) {
//            0 -> View.GONE
//            else -> View.VISIBLE
//        }
//}