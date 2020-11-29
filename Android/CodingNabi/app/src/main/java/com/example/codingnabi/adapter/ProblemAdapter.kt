package com.example.codingnabi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codingnabi.data.entity.Problem
import com.example.codingnabi.databinding.ItemCodingLevelBinding

class ProblemAdapter : RecyclerView.Adapter<ProblemViewHolder>() {
    private val data = mutableListOf<Problem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemViewHolder =
        ProblemViewHolder(
            ItemCodingLevelBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProblemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    fun setData(item: List<Problem>) {
        val diffUtil = ProblemDiffUtilCallback(data, item)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)

        data.clear()
        data.addAll(item)
    }
}

class ProblemViewHolder(private val binding: ItemCodingLevelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(problem: Problem) {
        binding.problem = problem
    }
}

class ProblemDiffUtilCallback(
    private val oldData: List<Problem>,
    private val newData: List<Problem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldData[oldItemPosition].category == newData[newItemPosition].category &&
            oldData[oldItemPosition].level == newData[newItemPosition].level
        ) return true

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if (oldData[oldItemPosition].isCleared == newData[newItemPosition].isCleared
        ) return true
        return false
    }
}