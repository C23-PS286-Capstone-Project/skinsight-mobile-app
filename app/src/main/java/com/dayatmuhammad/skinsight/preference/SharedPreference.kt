package com.dayatmuhammad.skinsight.preference

interface SharedPreference {
    fun putAccessToken(accessToken: String)
    fun deleteToken()
    fun getAccessToken() : String

    fun isLogin() : Boolean

    fun putIsLogin(isLogin:Boolean)
}