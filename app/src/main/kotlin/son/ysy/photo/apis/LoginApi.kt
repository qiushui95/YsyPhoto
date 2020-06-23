package son.ysy.photo.apis

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import son.ysy.photo.entities.response.ResponseLoginResult
import son.ysy.photo.https.CommonRequestHeaderInterceptor

interface LoginApi {

    //region 检查token是否可用
    @GET("login")
    suspend fun checkLogin(
        @Header(CommonRequestHeaderInterceptor.KEY_USER_ID) userId: String?,
        @Header(CommonRequestHeaderInterceptor.KEY_TOKEN) token: String?
    ): ResponseLoginResult
    //endregion

    //region 登录
    @POST("login")
    suspend fun login(@Query("phone") phone: String): ResponseLoginResult
    //endregion
}