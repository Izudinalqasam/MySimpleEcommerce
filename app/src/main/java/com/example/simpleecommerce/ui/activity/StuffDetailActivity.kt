package com.example.simpleecommerce.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.simpleecommerce.R
import com.example.simpleecommerce.model.network.ProductPromo
import com.example.simpleecommerce.ui.fragment.HomeStuffFragment
import com.example.simpleecommerce.viewmodel.DetailStuffViewModel
import kotlinx.android.synthetic.main.activity_stuff_detail.*

class StuffDetailActivity : AppCompatActivity(), View.OnClickListener {

    private val detailViewModel by lazy {
        ViewModelProviders.of(this)[DetailStuffViewModel::class.java]
    }

    private lateinit var item: ProductPromo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stuff_detail)

        initView()
    }

    private fun initView() {
        item = intent.getParcelableExtra<ProductPromo>(HomeStuffFragment.EXTRA_PRODUCT_PROMO)

        tv_fav_count_stuff_detail.text = item.loved.toString()
        tv_title_stuff_detail.text = item.title
        tv_price_stuff_detail.text = item.price
        tv_desc_stuff_detail.text = item.description

        Glide.with(baseContext)
            .load(item.imageUrl)
            .into(img_stuff_detail)

        btn_buy_stuff_detail.setOnClickListener(this)

        img_back_stuff_detail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btn_buy_stuff_detail){
            item?.let {
                detailViewModel.insertToHistory(it)
            }
        }else if (v?.id == R.id.img_back_stuff_detail){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
