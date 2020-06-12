package son.ysy.photo.apis

import me.yangcx.base.parcelables.ParcelableBoolean
import retrofit2.http.GET

interface LoginApi {

    //region 检查token是否可用
    @GET("login")
    suspend fun checkLogin(): ParcelableBoolean
    //endregion
}