package com.example.gettyimagesviewer

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.MainThread
import java.net.URL

class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>> {
    //lateinit var img_urls : Array<String>
    private lateinit var layout: LinearLayout
    //private var requestLayouts : ArrayList<LayoutCreator>
    private var main_list : LinearLayout
    private var main_img : ImageView
    private var activity : Activity


    constructor(activity: Activity, main_list : LinearLayout, main_img : ImageView) : super() {
        this.main_list = main_list
        this.main_img = main_img
        this.activity = activity
    }

    @MainThread
    private fun makeLayout(): LinearLayout{
        val layout = LinearLayout(this.activity)
        layout.orientation = LinearLayout.HORIZONTAL
        this.main_list.addView(layout)
        layout.layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT
        layout.layoutParams.height = (this.activity.resources.displayMetrics.density * 95).toInt() // 95dp
        return layout
    }

    @MainThread
    private fun makeImageView(layout: LinearLayout, outID : Array<Int>, bitmaps : ArrayList<Bitmap>) : ImageView{
        val view = ImageView(this.activity)
        val id = bitmaps.size - 1
        view.setImageBitmap(bitmaps[id])
        view.setOnClickListener(View.OnClickListener { _ ->
            this.main_img.setImageBitmap(bitmaps[id])
            outID[0] = id
        })
        view.animation = AnimationUtils.loadAnimation(this.activity, R.anim.completed_loading)

        layout.addView(view)
        view.layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        view.layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        (view.layoutParams as LinearLayout.LayoutParams).weight = 1.0f
        return view
    }

    override fun doInBackground(vararg params: Void?): Pair<Array<Int>, ArrayList<Bitmap>> {
        val retval = Pair<Array<Int>, ArrayList<Bitmap>>(Array<Int>(1){0}, ArrayList<Bitmap>())
        val (id, bitmaps) = retval

        this.activity.runOnUiThread{
            this.main_list.removeAllViews()
            this.layout = makeLayout()
        }
        val urls = get_urls("https://www.gettyimages.com/photos/collaboration")

        for (url in urls){
            val bitmap = BitmapFactory.decodeStream(URL(url).openStream())
            retval.second.add(bitmap)

            this.activity.runOnUiThread{
                if(this.layout.childCount > 2){
                    this.layout = makeLayout()
                }
                val view = makeImageView(this.layout, id, bitmaps)

            }
        }
        return retval
    }

    // native-lib.cpp에 정의
    external fun get_urls(url : String) : Array<String>
}