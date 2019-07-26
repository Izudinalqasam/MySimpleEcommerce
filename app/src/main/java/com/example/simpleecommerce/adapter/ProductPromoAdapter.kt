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
import kotlinx.android.synthetic.main.item_product_promo.*

class ProductPromoAdapter (
    private val context: Context,
    private val items: List<ProductPromo>,
    private val listener: IProductExecute
) : RecyclerView.Adapter<ProductPromoAdapter.ProductPromoHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        ProductPromoHolder(LayoutInflater.from(p0.context).inflate(R.layout.item_product_promo, p0, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ProductPromoHolder, p1: Int) {
       p0.bindItem(context, items[p1], listener)
    }


    class ProductPromoHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private var isClicked = false

        fun bindItem(context: Context, item: ProductPromo, listener: IProductExecute){
            Glide.with(context)
                .load(item.imageUrl)
                .into(img_item_product)

            tv_item_name_product.text = item.title
            tv_item_desc_product.text = item.description
            tv_item_price_product.text = item.price

            img_item_fav_product.setOnClickListener {
                isClicked = if (isClicked) {
                    Glide.with(context)
                        .load(R.drawable.ic_favorite_black)
                        .into(img_item_fav_product)
                    false
                } else {
                    Glide.with(context)
                        .load(R.drawable.ic_favorite_red)
                        .into(img_item_fav_product)
                    true
                }
            }

            parent_item_product_promo.setOnClickListener {
                listener.onItemClick(item)
            }
        }
    }

    interface IProductExecute {
        fun onItemClick(item: ProductPromo)
    }
}