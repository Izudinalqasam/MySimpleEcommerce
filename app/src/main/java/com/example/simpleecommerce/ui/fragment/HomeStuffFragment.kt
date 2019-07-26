package com.example.simpleecommerce.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.simpleecommerce.R
import com.example.simpleecommerce.adapter.ProductPromoAdapter
import com.example.simpleecommerce.adapter.StuffCategoryAdapter
import com.example.simpleecommerce.model.network.Category
import com.example.simpleecommerce.model.network.ProductPromo
import com.example.simpleecommerce.ui.activity.SearchActivity
import com.example.simpleecommerce.ui.activity.StuffDetailActivity
import com.example.simpleecommerce.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_stuff.*

class HomeStuffFragment : Fragment(), ProductPromoAdapter.IProductExecute, View.OnClickListener {

    private val homeViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private lateinit var categoryAdapter: StuffCategoryAdapter
    private lateinit var productAdapter: ProductPromoAdapter

    companion object {
        const val EXTRA_PRODUCT_PROMO = "product promo"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_stuff, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        homeViewModel.allListStuff.observe(this, productObserver)
        homeViewModel.allCategoryStuff.observe(this, categoryObserver)

        rv_product_promo.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        rv_category_stuff.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        }

        edt_search_home_stuff.setOnClickListener(this)

        context?.let {
            homeViewModel.getAllListStuff(it)
        }

    }

    override fun onItemClick(item: ProductPromo) {
        val mIntent = Intent(context, StuffDetailActivity::class.java)
        mIntent.putExtra(EXTRA_PRODUCT_PROMO, item)
        startActivity(mIntent)
    }

    private val productObserver = Observer<List<ProductPromo>> { list ->
        if (list != null) {
            Log.d("Hommme", ": ${list.size}")
            productAdapter = ProductPromoAdapter(context!!, list, this@HomeStuffFragment)
            rv_product_promo.adapter = productAdapter
        }
    }

    private val categoryObserver = Observer<List<Category>> { list ->
        if (list != null) {
            categoryAdapter = StuffCategoryAdapter(context!!, list)
            rv_category_stuff.adapter = categoryAdapter
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.edt_search_home_stuff -> {
                val intent = Intent(context, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
