package son.ysy.photo.apis

import retrofit2.http.GET
import retrofit2.http.Query
import son.ysy.photo.entities.enums.ImageType
import son.ysy.photo.entities.response.ResponseTokenInfo

interface UploadApi {

    @GET("upload/token")
    suspend fun getUploadToken(@ImageType @Query("imageType") imageType: Int): ResponseTokenInfo
}