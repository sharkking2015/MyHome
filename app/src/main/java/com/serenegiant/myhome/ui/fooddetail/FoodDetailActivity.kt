package com.serenegiant.myhome.ui.fooddetail

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.serenegiant.myhome.MyApplication
import com.serenegiant.myhome.R
import com.serenegiant.myhome.Util.StatusBarUtils
import com.serenegiant.myhome.Util.Urls
import com.serenegiant.myhome.bean.FoodBean
import com.serenegiant.myhome.bean.FoodBeanDao
import com.serenegiant.myhome.ui.fooddetail.adapter.FoodDetailAdapter
import com.serenegiant.myhome.ui.fooddetail.vo.FoodDetailBean
import kotlinx.android.synthetic.main.activity_food_detail.*
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream

class FoodDetailActivity : AppCompatActivity() {
    var list = ArrayList<FoodDetailBean>()
    lateinit var adapter: FoodDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        StatusBarUtils.compat(this,android.R.color.transparent)


        var id = intent.getLongExtra("id",0)
        var tempList = MyApplication.getInstance().daoSession.foodBeanDao.queryBuilder().where(FoodBeanDao.Properties.Id.eq(id)).build().list()
        var bean:FoodBean? = null
        if(tempList.size>0){
            bean = tempList.get(0)
        }

        if(bean != null){
            if("".equals(bean.getPicUrl())){
                top_image.setImageResource(R.drawable.no_pic)
            }else{
                top_image.setImageBitmap(BitmapFactory.decodeFile(bean.getPicUrl()))
            }
            collapaing_layout.title = bean.getName()
            var file = File(Urls.jsonUrl+bean.name+".txt")
            Log.i("messagess",Urls.jsonUrl+bean.name+".txt")
            if(file.exists()){
                var message = ""
                var bytes = ByteArray(1024)
                var fis = FileInputStream(file)
                while (fis.read(bytes)!=-1){
                    message += String(bytes)
                }
                if(message != ""){
                    var jsonObject = JSONObject(message)
                    Log.i("messagess",jsonObject.toString())
                    var compactTitleBean = FoodDetailBean("配料","",0)
                    list.add(compactTitleBean)
                    var compactArray = jsonObject.getJSONObject("data").getJSONArray("burden")
                    for(i in 0 until compactArray.length()){
                        var compact = compactArray.getJSONObject(i)
                        var bean = FoodDetailBean(compact.getString("name"),compact.getString("content"),1)
                        list.add(bean)
                    }
                    var stepTitleBean = FoodDetailBean("步骤","",0)
                    list.add(stepTitleBean)
                    var stepArray = jsonObject.getJSONObject("data").getJSONArray("makes")
                    for(i in 0 until stepArray.length()){
                        var step = stepArray.getJSONObject(i)
                        var bean = FoodDetailBean(step.getString("info"),"",2)
                        list.add(bean)
                    }


                    adapter = FoodDetailAdapter(this,list)
                    recycler_view.adapter = adapter
                    recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

                }


            }
        }

        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })

    }



}
