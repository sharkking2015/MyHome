package com.serenegiant.myhome.ui.foods

import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.serenegiant.myhome.ItemClickListener
import com.serenegiant.myhome.MyApplication
import com.serenegiant.myhome.R
import com.serenegiant.myhome.Util.*
import com.serenegiant.myhome.bean.FoodBean
import com.serenegiant.myhome.ui.fooddetail.FoodDetailActivity
import com.serenegiant.myhome.ui.foods.adapter.DishListAdapter
import com.serenegiant.myhome.ui.foods.adapter.FoodAdapter
import com.serenegiant.myhome.ui.foods.widget.MyBottomSheetDialog
import kotlinx.android.synthetic.main.activity_foods.*
import kotlinx.android.synthetic.main.dialog_create_dish.view.*
import kotlinx.android.synthetic.main.dialog_dish_list.view.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class FoodsActivity : AppCompatActivity(),ItemClickListener {
    override fun OnItemClicked(position: Int) {
        var intent = Intent()
        intent.setClass(this,FoodDetailActivity::class.java)
        intent.putExtra("id",list.get(position).id)
        startActivity(intent)
    }

    override fun OnItemLongClicked(position: Int) {
        var choices = arrayOf("删除","修改")
        AlertDialog.Builder(this).setItems(
                choices,
                DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                    if(i==0){
                        var bean = list.get(position)
                        MyApplication.getInstance().daoSession.foodBeanDao.delete(bean)
                        list.removeAt(position)
                        adapter.notifyDataSetChanged()
                    }else if(i==1){
                        showDialog(list.get(position))
                    }
                }
        ).create().show()
    }

    var choosenImage:ImageView? = null
    var picPath = ""
    var REQUEST_SYSTEM_PIC = 101
    var list = ArrayList<FoodBean>()
    lateinit var adapter:FoodAdapter
    lateinit var bottomSheetDialog: MyBottomSheetDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foods)

        fab.setOnClickListener(View.OnClickListener {
            bottomSheetDialog = MyBottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager,"Dialog")
        })

        initRecycler()

        toolbar.setNavigationIcon(R.drawable.back_icon)
        toolbar.title = "食谱"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun initRecycler(){
        var templist = MyApplication.getInstance().daoSession.foodBeanDao.queryBuilder().build().list()
        list.addAll(templist)
        adapter  = FoodAdapter(this,list,this)
        recycler.adapter = adapter
//        recycler.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }


    fun showDialog(oldBean:FoodBean){
        picPath = oldBean.getPicUrl()
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_create_dish,null)
        var dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()

        choosenImage = view.image
        if(oldBean.picUrl.equals("")){
            view.image.setImageResource(R.drawable.no_pic)
        }else{
            view.image.setImageBitmap(BitmapFactory.decodeFile(oldBean.getPicUrl()))
        }
        view.name_edit.setText(oldBean.name)
        view.introduce_edit.setText(oldBean.introduce)

        view.choose_pic_tv.setOnClickListener(View.OnClickListener {
            openAlbum()
        })


        view.ensure_btn.setOnClickListener(View.OnClickListener {
            var dir = Environment.getExternalStorageDirectory().toString()+File.separator+"MyHome"+File.separator
            var name = System.currentTimeMillis().toString()+".png"
            SDCardUtil.getimage(picPath,dir,name)
            picPath = dir+name

            var bean = FoodBean(oldBean.id,picPath,view.name_edit.text.toString(),view.introduce_edit.text.toString())
            MyApplication.getInstance().daoSession.foodBeanDao.insertOrReplace(bean)
            dialog.dismiss()
            list.clear()
            var templist = MyApplication.getInstance().daoSession.foodBeanDao.queryBuilder().build().list()
            list.addAll(templist)
            adapter.notifyDataSetChanged()
        })
    }

    fun showDialog(){
        if(bottomSheetDialog != null){
            bottomSheetDialog.dismiss()
        }
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_create_dish,null)
        var dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()

        choosenImage = view.image


        view.choose_pic_tv.setOnClickListener(View.OnClickListener {
            openAlbum()
        })


        view.ensure_btn.setOnClickListener(View.OnClickListener {
            var dir = Environment.getExternalStorageDirectory().toString()+File.separator+"MyHome"+File.separator
            var name = System.currentTimeMillis().toString()+".png"
            SDCardUtil.getimage(picPath,dir,name)
            picPath = dir+name

            var bean = FoodBean(null,picPath,view.name_edit.text.toString(),view.introduce_edit.text.toString())
            MyApplication.getInstance().daoSession.foodBeanDao.insertOrReplace(bean)
            dialog.dismiss()
            list.clear()
            var templist = MyApplication.getInstance().daoSession.foodBeanDao.queryBuilder().build().list()
            list.addAll(templist)
            adapter.notifyDataSetChanged()
        })
    }

    fun showDialog2(){
        if(bottomSheetDialog != null){
            bottomSheetDialog.dismiss()
        }
        var view = LayoutInflater.from(this).inflate(R.layout.dialog_dish_list,null)
        var dialog = AlertDialog.Builder(this).setView(view).create()
        dialog.show()

        var tempList = ArrayList<FoodBean>()
        tempList.addAll(list)
        Collections.shuffle(tempList)
        for(i in tempList.size downTo view.number_edit.text.toString().toInt()+1){
            tempList.removeAt(i-1)
        }
        var dishAdapter = DishListAdapter(this,tempList)
        view.dish_recycler.adapter = dishAdapter
        view.dish_recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        view.dish_recycler.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))


        view.re_create_tv.setOnClickListener(View.OnClickListener {
            tempList.clear()
            tempList.addAll(list)
            Collections.shuffle(tempList)
            for(i in tempList.size downTo view.number_edit.text.toString().toInt()+1){
                tempList.removeAt(i-1)
            }
            dishAdapter.notifyDataSetChanged()
        })

        view.ensur_list_btn.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            var dir = File(Urls.dishesUrl)
            if(!dir.exists()){
                dir.mkdir()
            }
            var file = File(Urls.dishesUrl+TimeUtils.getStrTime(System.currentTimeMillis().toString())+".txt")
            var fos = FileOutputStream(file)

            var str = ""
            for(i in tempList.indices){
                if(i!=0){
                    str+=","
                }
                str += tempList.get(i).name
            }

            fos.write(str.toByteArray())
            fos.close()
        })
    }

    private fun openAlbum() {
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_SYSTEM_PIC)//打开系统相册

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SYSTEM_PIC && resultCode == RESULT_OK && null != data) {
            var util = GalleryUtil(this,choosenImage)
            if (Build.VERSION.SDK_INT >= 19) {
                util.handleImageOnKitkat(data)
            } else {
                util.handleImageBeforeKitkat(data)
            }
            picPath = util.imagePath


        }
    }
}
