package son.ysy.photo.apis

import retrofit2.http.GET
import son.ysy.photo.entities.response.ResponseUserInfo

interface UserInfoApi {

    //region 获取用户信息
    @GET("user")
    suspend fun getUserInfo(): ResponseUserInfo
    //endregion
}