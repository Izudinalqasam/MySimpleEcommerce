package com.example.simpleecommerce.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.simpleecommerce.R
import com.example.simpleecommerce.adapter.SimpleStuffListAdapter
import com.example.simpleecommerce.model.database.PurchaseHistory
import com.example.simpleecommerce.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_purchase_history.*

class PurchaseHistoryFragment : Fragment() {

    private val mainViewModel by lazy {
        ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    private lateinit var simpleStuffListAdapter: SimpleStuffListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_purchase_history, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        rv_purchase_history.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }

        mainViewModel.purchaseHistoryStuff.observe(this, historyObserver)
        mainViewModel.getPurchaseHistory()
    }

    private val historyObserver = Observer<List<PurchaseHistory>> { listHistories ->
        context?.let {
            simpleStuffListAdapter = SimpleStuffListAdapter(it, mainViewModel.convertHistoryToProduct(listHistories ?: emptyList()))
            rv_purchase_history.adapter = simpleStuffListAdapter
        }
    }

}
