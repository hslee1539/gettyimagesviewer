package com.example.gettyimagesviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.get
import androidx.core.view.iterator
import java.net.URL
import kotlin.coroutines.*

class ListLoader() : AsyncTask<String, Void, Array<Pair<LinearLayout, Array<ImageView>>>>(){
    lateinit var main_img : ImageView
    lateinit var row_layout : LinearLayout // 코틀린 문법임. 나중에 초기화 하겠다는 의미.
    override fun doInBackground(vararg params: String?): Array<Pair<LinearLayout, Array<ImageView>>> {
        // 알고리즘
        // 1. row_layout을 비움
        // 2. 배열을 만듬
        // 3. 배열 안에 레이아웃과 이미지 뷰들을 추가함
        // 4. 이미지 뷰들 안에 이미지를 추가하고 레이아웃과 연결함
        // 5. 레이아웃은 row_layout에 연결함

        row_layout.removeAllViews()



        return Array<Pair<LinearLayout, Array<ImageView>>>(params.size / 3) { row ->
            val new_layout = LinearLayout(row_layout.context)
            val new_imgs = Array<ImageView>(3){ col ->
                val img = ImageView(main_img.context)
                val bitmap = BitmapFactory.decodeStream(URL(params[col + row * 3]).openStream())
                img.setOnClickListener(View.OnClickListener { v ->
                    main_img.setImageBitmap(bitmap)
                    for (view in new_layout){
                        view as ImageView
                        (view.layoutParams as LinearLayout.LayoutParams).weight = 1.0f
                    }
                    (v.layoutParams as LinearLayout.LayoutParams).weight = 2.0f
                })
                img.setImageBitmap(bitmap)

                (img.layoutParams as LinearLayout.LayoutParams).weight = 1.0f
                new_layout.addView(img)
                img
            }

            for (new_img in new_imgs){
                new_layout.addView(new_img)
            }

            Pair<LinearLayout, Array<ImageView>>(new_layout, new_imgs)
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }
}