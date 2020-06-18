package son.ysy.photo.apis

import me.yangcx.base.parcelables.ParcelableBoolean
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import son.ysy.photo.entities.response.ResponseLoginResult

interface LoginApi {

    //region 检查token是否可用
    @GET("login/check")
    suspend fun checkLogin(): ParcelableBoolean
    //endregion

    //region 登录
    @POST("login")
    suspend fun login(@Query("phone") phone: String): ResponseLoginResult
    //endregion
}