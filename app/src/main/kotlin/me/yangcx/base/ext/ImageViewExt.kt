package me.yangcx.base.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import java.io.File

val ImageView.glide: RequestManager
    get() = Glide.with(this)

private fun RequestBuilder<Drawable>.loadInto(
    block: RequestBuilder<Drawable>.() -> Unit,
    imageView: ImageView
) {
    apply(block)
        .into(imageView)
}

fun ImageView.load(
    source: Uri,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: File,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: String,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: Bitmap,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: Drawable,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    @RawRes @DrawableRes source: Int,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: ByteArray,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}

fun ImageView.load(
    source: Any,
    block: RequestBuilder<Drawable>.() -> Unit = { }
) {
    glide.load(source)
        .loadInto(block, this)
}
