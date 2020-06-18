package son.ysy.photo.repositories

import kotlinx.coroutines.flow.flow
import me.yangcx.base.repositories.BaseRepository
import org.koin.core.inject
import son.ysy.photo.apis.UserInfoApi
import son.ysy.photo.entities.response.ResponseUserInfo

class UserInfoRepository : BaseRepository() {
    private val userInfoApi by inject<UserInfoApi>()

    fun getUserInfo() = flow<ResponseUserInfo> {
        emit(userInfoApi.getUserInfo())
    }.catchHttpException()
}