package com.example.simpleecommerce.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.simpleecommerce.R
import com.example.simpleecommerce.model.network.ProductPromo
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_simple_stuff.*

class SimpleStuffListAdapter (
    private val context: Context,
    private val items: List<ProductPromo>
) : RecyclerView.Adapter<SimpleStuffListAdapter.SimpleStuffListHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        SimpleStuffListHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_simple_stuff, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: SimpleStuffListHolder, p1: Int) {
        p0.bindItem(context, items[p1])
    }

    class SimpleStuffListHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(context: Context, item: ProductPromo){
            Glide.with(context)
                .load(item.imageUrl)
                .into(img_simple_stuff)

            tv_title_simple_stuff.text = item.title
            tv_desc_simple_stuff.text = item.description
            tv_price_simple_stuff.text = item.price

        }
    }
}