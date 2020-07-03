package son.ysy.lib.base.ext

import android.net.Uri
import com.blankj.utilcode.util.Utils
import java.io.File
import java.io.IOException

@Throws(IOException::class)
fun Uri.copyToFile(target: File) {
    com.blankj.utilcode.util.FileUtils.createOrExistsDir(target.parentFile)
    Utils.getApp()
        .contentResolver
        .openInputStream(this)
        ?.use { inputStream ->
            target.outputStream()
                .use { outputStream ->
                    val buffer = ByteArray(1024 * 4)
                    do {
                        val readCount = inputStream.read(buffer)
                        if (readCount != -1) {
                            outputStream.write(buffer, 0, readCount)
                        }
                    } while (readCount != -1)
                }
        }
}