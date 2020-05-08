package com.example.gettyimagesviewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import java.net.URL

class ImageViewCreator : AsyncTask<Void, Void, Pair<ImageView, Bitmap>?> {
    private var context : Context
    private var url : String
    private lateinit var view: ImageView

    constructor(context: Context, url : String) : super(){
        this.context = context
        this.url = url
    }

    override fun onPreExecute() {
        super.onPreExecute()
        this.view = ImageView(this.context)
    }

    override fun doInBackground(vararg params: Void?): Pair<ImageView, Bitmap>? {
        var retval : Pair<ImageView, Bitmap>? = null
        try{
            retval = Pair<ImageView, Bitmap>(
                this.view,
                BitmapFactory.decodeStream(URL(this.url).openStream()))
        }
        catch (t: Throwable){
        }
        finally {
            return retval
        }
    }
}