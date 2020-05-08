package com.example.gettyimagesviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var loader : GettyImagesLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loader = GettyImagesLoader(this, this.main_list, this.main_img)
        loader.execute()
        this.main_next.setOnClickListener {
            val (id, bitmaps) = loader.get()
            this.main_img.animation = AnimationUtils.loadAnimation(this, R.anim.to_left)

            id[0] = kotlin.math.min(id[0] + 1, bitmaps.size - 1)
            this.main_img.setImageBitmap(bitmaps[id[0]])
        }
        this.main_previous.setOnClickListener {
            val (id, bitmaps) = loader.get()
            this.main_img.animation = AnimationUtils.loadAnimation(this, R.anim.to_right)

            id[0] = kotlin.math.max(id[0] - 1, 0)
            this.main_img.setImageBitmap(bitmaps[id[0]])
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
