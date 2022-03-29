package com.example.newsapp.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.BR


class GenericAdapter<T>(private val mData: ArrayList<T>,
                        private val mContext: Context,
                        private val iCellRes: Int,
                        private val mCellClicked: (v: View, o: T) -> Unit) : RecyclerView.Adapter<GenericViewHolder<T>>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T>
    {
        val b = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(mContext), iCellRes, parent, false
        )

        return GenericViewHolder<T>(b, mContext)
    }

    override fun getItemCount(): Int
    {
        return  mData.size
    }

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int)
    {
        val p = mData[position]

        holder.bind(p)
        holder.cellClicked = mCellClicked
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}

class GenericViewHolder<T>(val binding: ViewDataBinding, val mContext: Context) : RecyclerView.ViewHolder(binding.root)
{
    var mValue: T? = null

    lateinit var cellClicked: (v: View, o: T) -> Unit

    fun bind(v: T)
    {
        mValue = v

        binding.setVariable(BR.item, v)
        binding.setVariable(BR.holder, this)
    }

    fun OnCellClick(v: View, o: T)
    {
        cellClicked(v, o)
    }
}