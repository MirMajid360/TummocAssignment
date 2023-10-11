package com.majid.tummocassignment.utils

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import com.majid.tummocassignment.R


@GlideModule
class AppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // You can change this to make Glide more verbose
        builder.setLogLevel(Log.ERROR)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}

object Glide {
    private val width = Resources.getSystem().displayMetrics.widthPixels
    private val height = Resources.getSystem().displayMetrics.heightPixels
    private var glideOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)

    private fun fromHtml(source: String): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            @Suppress("DEPRECATION")
            return Html.fromHtml(source).toString()
        }
    }

    // Please ignore, this is for our testing
    private fun assetFetch(urlString: String, imageView: ImageView) {

    }

    fun glideFetch(urlString: String, imageView: ImageView) {

        GlideApp.with(imageView.context)
            .asBitmap() // Try to display animated Gifs and video still
            .load(fromHtml(urlString))
            // .placeholder(R.drawable.icon_no_image)
            .apply(glideOptions)
            //.error(R.drawable.icon_no_image)


            .override(width, height)
            .into(imageView)

    }

    fun loadImage(uri: String, imageView: ImageView) {

        GlideApp.with(imageView.context)
            .load(uri)// Try to display animated Gifs and video still
            .placeholder(R.color.white)
            .apply(glideOptions)
            .into(imageView)

    }


}