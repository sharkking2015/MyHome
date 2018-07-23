package com.serenegiant.myhome.ui.foods.widget

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.serenegiant.myhome.R
import com.serenegiant.myhome.ui.DialogInterface
import com.serenegiant.myhome.ui.foods.FoodsActivity
import kotlinx.android.synthetic.main.my_bottom_sheet_layout.*

class MyBottomSheetDialog : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.my_bottom_sheet_layout, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        new_dish_layout.setOnClickListener(View.OnClickListener {
            var foodActivity = context as FoodsActivity
            foodActivity.showDialog()
        })

        create_list_layout.setOnClickListener(View.OnClickListener {
            var foodActivity = context as FoodsActivity
            foodActivity.showDialog2()
        })
    }
}