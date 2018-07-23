package com.serenegiant.myhome.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.serenegiant.myhome.ItemClickListener
import com.serenegiant.myhome.R
import com.serenegiant.myhome.Util.StatusBarUtils
import com.serenegiant.myhome.request.HttpMethods
import com.serenegiant.myhome.request.RequestUrls
import com.serenegiant.myhome.request.UrlConnector
import com.serenegiant.myhome.ui.foods.FoodsActivity
import com.serenegiant.myhome.ui.main.adapter.MainListAdapter
import com.serenegiant.myhome.ui.main.vo.MainItemBean
import com.serenegiant.myhome.vo.DishParam
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),ItemClickListener {
    override fun OnItemLongClicked(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun OnItemClicked(position:Int) {
        if(position == 0){
            var intent = Intent()
            intent.setClass(this,FoodsActivity::class.java)
            startActivity(intent)
        }
    }

    lateinit var adapter:MainListAdapter
    var list = ArrayList<MainItemBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        StatusBarUtils.compat(this,R.color.colorPrimary)

        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recycler.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        createData()
        adapter = MainListAdapter(this,this,list)
        recycler.adapter = adapter

        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.title = "主页"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
        var test = UrlConnector()
        UrlConnector.test()


    }

    fun createData(){
        var bean1 = MainItemBean("菜肴",R.drawable.ic_local_restaurant_black_24dp)
        var bean2 = MainItemBean("穿着",R.drawable.ic_clothes_fill)

        list.add(bean1)
        list.add(bean2)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
