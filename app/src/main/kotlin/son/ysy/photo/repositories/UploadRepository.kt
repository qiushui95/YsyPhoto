package son.ysy.photo.repositories

import kotlinx.coroutines.flow.flow
import me.yangcx.base.repositories.BaseRepository
import org.koin.core.inject
import son.ysy.photo.apis.UploadApi
import son.ysy.photo.entities.enums.ImageType
import son.ysy.photo.entities.response.ResponseTokenInfo

class UploadRepository : BaseRepository() {

    private val uploadApi by inject<UploadApi>()

    fun getUploadToken(@ImageType imageType: Int) = flow<ResponseTokenInfo> {
//        emit(uploadApi.getUploadToken(imageType))
        emit(ResponseTokenInfo("", "photo/"))
    }
}