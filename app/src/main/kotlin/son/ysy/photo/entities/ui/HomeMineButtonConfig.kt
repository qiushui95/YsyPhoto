package son.ysy.photo.entities.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class HomeMineButtonConfig(
    @DrawableRes val iconRes: Int,
    @StringRes val tipRes: Int,
    val marginTopDp: Int
)