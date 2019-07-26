package com.example.simpleecommerce.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.simpleecommerce.R
import com.example.simpleecommerce.model.network.Category
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category_stuff.*

class StuffCategoryAdapter (
    private val context: Context,
    private val items: List<Category>
) : RecyclerView.Adapter<StuffCategoryAdapter.StuffCategoryHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        StuffCategoryHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_category_stuff, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: StuffCategoryHolder, p1: Int) {
        p0.bindItem(context, items[p1])
    }


    class StuffCategoryHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(context: Context, item: Category){
            tv_name_category.text = item.name

            Glide.with(context)
                .load(item.imageUrl)
                .into(img_stuff_category)
        }
    }
}