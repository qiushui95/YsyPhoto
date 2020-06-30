package son.ysy.photo.entities.enums

import androidx.annotation.IntDef

@IntDef(ImageType.TYPE_PHOTO, ImageType.TYPE_AVATAR, open = false)
annotation class ImageType {
    companion object {
        const val TYPE_PHOTO = 1
        const val TYPE_AVATAR = 2
    }
}