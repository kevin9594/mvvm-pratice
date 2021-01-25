package com.sample.mvvm_data_binding_koin.repository

import androidx.annotation.WorkerThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

//private val userInfoDao: UserInfoDao
class UserInfoRepository() {

//    val userInfo: Flow<UserInfo?>
//        get() = userInfoDao.getUserInfo().map {
//            if (it.isNotEmpty()) {
//                return@map it[0]
//            }
//            return@map null
//        }
//
//    suspend fun getUserInfo() {
//        val userInfoResponse = OneBoSportApi.userService.getUserInfo()
//
//        if (userInfoResponse.isSuccessful) {
//            userInfoResponse.body()?.let {
//                updateUserInfo(it.userInfoData)
//            }
//        }
//    }
//
//    @WorkerThread
//    suspend fun updateUserInfo(userInfoData: UserInfoData?) {
//        userInfoData?.let {
//            val userInfo = transform(it)
//
//            withContext(Dispatchers.IO) {
//                userInfoDao.upsert(userInfo)
//            }
//        }
//    }
//
//    private fun transform(userInfoData: UserInfoData) =
//        UserInfo(
//            userInfoData.userId,
//            fullName = userInfoData.fullName,
//            iconUrl = userInfoData.iconUrl,
//            lastLoginIp = userInfoData.lastLoginIp,
//            loginIp = userInfoData.loginIp,
//            nickName = userInfoData.nickName,
//            platformId = userInfoData.platformId,
//            testFlag = userInfoData.testFlag,
//            userName = userInfoData.userName,
//            userType = userInfoData.userType,
//            email = userInfoData.email,
//            qq = userInfoData.qq,
//            phone = userInfoData.phone,
//            wechat = userInfoData.wechat,
//            updatePayPw = userInfoData.updatePayPw,
//            setted = userInfoData.setted,
//            userRebateList = userInfoData.userRebateList
//        )
}