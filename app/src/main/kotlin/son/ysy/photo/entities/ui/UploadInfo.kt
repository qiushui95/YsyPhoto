package son.ysy.photo.entities.ui

import android.os.Parcelable
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.StringUtils
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import son.ysy.photo.R
import java.io.File

@Parcelize
data class UploadInfo(
    val imageInfo: ImageInfo,
    val isDealing: Boolean = false,
    val localFile: File? = null,
    val md5: String? = null,
    val canUpload: Boolean? = null,
    val namePrefix: String? = null,
    val qiNiuProgress: Float? = null,
    val hasUploadToServer: Boolean = false,
    val hasError: Boolean = false
) : Parcelable {

    @IgnoredOnParcel
    private val fileExtension: String = FileUtils.getFileExtension(imageInfo.fileName)

    @IgnoredOnParcel
    val localFileName: String = "${System.identityHashCode(this)}.${fileExtension}"

    @IgnoredOnParcel
    val qiNiuName: String = "$namePrefix$md5.${fileExtension}"

    @IgnoredOnParcel
    val itemTip: String = when {
        hasError -> StringUtils.getString(R.string.string_upload_apply_tip_error)
        hasUploadToServer -> StringUtils.getString(son.ysy.photo.R.string.string_upload_apply_tip_success)
        !isDealing -> StringUtils.getString(R.string.string_upload_apply_tip_wait)
        localFile == null -> StringUtils.getString(R.string.string_upload_apply_tip_copy)
        md5 == null -> StringUtils.getString(R.string.string_upload_apply_tip_md5)
        canUpload == null -> StringUtils.getString(R.string.string_upload_apply_tip_check)
        !canUpload -> StringUtils.getString(R.string.string_upload_apply_tip_can_not_upload)
        qiNiuProgress == null -> StringUtils.getString(
            R.string.string_upload_apply_tip_wait
        )
        qiNiuProgress < 100f -> StringUtils.getString(
            R.string.string_upload_apply_tip_progress,
            qiNiuProgress
        )
        !hasUploadToServer -> StringUtils.getString(R.string.string_upload_apply_tip_server)
        else -> ""
    }
}