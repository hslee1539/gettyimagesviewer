package com.example.gettyimagesviewer

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.widget.ImageView
import android.widget.LinearLayout

class LayoutCreator : AsyncTask<Void, Void, Pair<LinearLayout, Array<Pair<ImageView, Bitmap>>>?> {
    private lateinit var layout: LinearLayout
    private var requestViews : Array<ImageViewCreator>
    private var context : Context
    constructor(context: Context, vararg urls : String) : super(){
        this.context = context
        this.requestViews = Array<ImageViewCreator>(urls.size) {col ->
            ImageViewCreator(this.context, urls[col])
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
        this.layout = LinearLayout(this.context)
        for (requestView in this.requestViews){
            requestView.execute()
        }
    }

    override fun doInBackground(vararg params: Void?): Pair<LinearLayout, Array<Pair<ImageView, Bitmap>>>? {
        var responseViews = ArrayList<Pair<ImageView, Bitmap>>()
        for (requestView in this.requestViews){
            val retval = requestView.get() // wait
            if (retval != null){
                responseViews.add(retval)
            }
        }
        if(responseViews.size > 0){
            return Pair<LinearLayout, Array<Pair<ImageView, Bitmap>>>(this.layout, responseViews.toTypedArray())
        }
        return null
    }
}