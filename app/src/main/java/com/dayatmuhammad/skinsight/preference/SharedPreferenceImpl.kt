package com.dayatmuhammad.skinsight.preference

import android.content.SharedPreferences
import com.dayatmuhammad.skinsight.utils.Constant
import javax.inject.Inject

class SharedPreferenceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreference {

    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(sharedPreferences.edit()) {
            block()
            commit()
        }
    }

    override fun putAccessToken(accessToken: String) {
        edit { putString(Constant.TOKEN, accessToken) }
    }

    override fun deleteToken() {
        edit {
            remove(Constant.TOKEN)
        }
    }

    override fun getAccessToken(): String {
        return sharedPreferences.getString(Constant.TOKEN, "").orEmpty()
    }

    override fun isLogin(): Boolean = sharedPreferences.getBoolean(Constant.IS_LOGIN,false)

    override fun putIsLogin(isLogin: Boolean) {
        edit { putBoolean(Constant.IS_LOGIN, isLogin) }
    }

}