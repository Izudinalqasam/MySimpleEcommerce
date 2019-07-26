package com.example.simpleecommerce.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.simpleecommerce.R
import com.example.simpleecommerce.adapter.SimpleStuffListAdapter
import com.example.simpleecommerce.model.network.ProductPromo
import com.example.simpleecommerce.viewmodel.SearchViewModel
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_search.*
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity(), View.OnClickListener {

    private val searchViewModel by lazy {
        ViewModelProviders.of(this)[SearchViewModel::class.java]
    }

    private val compositeDisposable = CompositeDisposable()
    private lateinit var adapter: SimpleStuffListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
    }

    private fun initView() {
        searchViewModel.listStuffSearch.observe(this, searchObserver)

        compositeDisposable.add(edt_search_stuff.textChanges()
            .debounce(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.toString() != "") {
                    searchViewModel.searchProduct(it.toString(), baseContext)
                }else{
                    adapter = SimpleStuffListAdapter(baseContext, emptyList())
                    rv_search_stuff.adapter = adapter
                }
            })

        rv_search_stuff.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(context)
        }

        img_back_search_stuff.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.img_back_search_stuff -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val searchObserver = Observer<List<ProductPromo>> { items ->
        items?.let {
            adapter = SimpleStuffListAdapter(baseContext, it)
            rv_search_stuff.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        searchViewModel.dispatchAll()

        if (!compositeDisposable.isDisposed){
            compositeDisposable.dispose()
        }
    }

}
