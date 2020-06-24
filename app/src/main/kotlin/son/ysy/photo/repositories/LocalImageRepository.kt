package son.ysy.photo.repositories

import android.net.Uri
import android.provider.MediaStore
import com.blankj.utilcode.util.Utils
import kotlinx.coroutines.flow.flow
import me.yangcx.base.ext.parcelable
import me.yangcx.base.ext.toArray
import me.yangcx.base.parcelables.ParcelableList
import me.yangcx.base.repositories.BaseRepository
import son.ysy.photo.entities.ui.ImageInfo

class LocalImageRepository : BaseRepository() {

    private object MediaStoreConstants {
        sealed class MimeType(val mimeTypeName: String, val extensions: Set<String>) {
            object Jpeg : MimeType("image/jpeg", setOf("jpg", "jpeg"))
            object Png : MimeType("image/png", setOf("png"))
            object Bmp : MimeType("image/x-ms-bmp", setOf("bmp", "x-ms-bmp"))
            object Webp : MimeType("image/webp", setOf("webp"))
        }

        object Columns {
            const val MIME_TYPE = MediaStore.Files.FileColumns.MIME_TYPE
            const val ID = MediaStore.Files.FileColumns._ID
            const val DISPLAY_NAME = MediaStore.Files.FileColumns.DISPLAY_NAME
            const val WIDTH = MediaStore.Files.FileColumns.WIDTH
            const val HEIGHT = MediaStore.Files.FileColumns.HEIGHT
            const val SIZE = MediaStore.Files.FileColumns.SIZE
            const val DATE_MODIFIED = MediaStore.Files.FileColumns.DATE_MODIFIED
        }

        val URI: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val selectColumns = arrayOf(
            Columns.ID,
            Columns.DISPLAY_NAME,
            Columns.WIDTH,
            Columns.HEIGHT,
            Columns.SIZE,
            Columns.DATE_MODIFIED
        )

        const val sortOrder = Columns.DATE_MODIFIED + " DESC"

        val selectionsArgs = listOf(
            MimeType.Jpeg,
            MimeType.Bmp,
            MimeType.Png,
            MimeType.Webp
        ).flatMap { mimeType ->
            mimeType.extensions
                .map {
                    "image/$it"
                }
        }.toArray()

        val mimeSelections = selectionsArgs.joinToString(" or ") {
            "${MediaStore.Files.FileColumns.MIME_TYPE} =?"
        }
        val selections = " ${MediaStore.MediaColumns.SIZE} >=${1024 * 100}" +
                " and " +
                "($mimeSelections)"
    }

    fun loadLocalImage() = flow<ParcelableList<ImageInfo>> {
        val imageList = mutableListOf<ImageInfo>()
        Utils.getApp().contentResolver
            .query(
                MediaStoreConstants.URI,
                MediaStoreConstants.selectColumns,
                MediaStoreConstants.selections,
                MediaStoreConstants.selectionsArgs,
                MediaStoreConstants.sortOrder
            )?.use { cursor ->

                while (cursor.moveToNext()) {
                    val id = cursor.getString(
                        cursor.getColumnIndex(MediaStoreConstants.Columns.ID)
                    )
                    val fileName = cursor.getString(
                        cursor.getColumnIndex(MediaStoreConstants.Columns.DISPLAY_NAME)
                    )
                    val width = cursor.getInt(
                        cursor.getColumnIndex(MediaStoreConstants.Columns.WIDTH)
                    )
                    val height = cursor.getInt(
                        cursor.getColumnIndex(MediaStoreConstants.Columns.HEIGHT)
                    )
                    val size = cursor.getInt(
                        cursor.getColumnIndex(MediaStoreConstants.Columns.SIZE)
                    )
                    imageList.add(ImageInfo(id, fileName, width, height, size))
                }
            }
        emit(imageList.parcelable())
    }
}