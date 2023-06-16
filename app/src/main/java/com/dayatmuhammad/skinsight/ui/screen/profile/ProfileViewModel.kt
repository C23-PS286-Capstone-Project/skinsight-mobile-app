package com.dayatmuhammad.skinsight.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayatmuhammad.skinsight.data.*
import com.dayatmuhammad.skinsight.di.Dispatcher
import com.dayatmuhammad.skinsight.di.DispatcherEnum
import com.dayatmuhammad.skinsight.preference.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val preference: SharedPreference,
    @Dispatcher(DispatcherEnum.Default) private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _uiStateProfile = MutableStateFlow<Result<ProfileResponse>>(Result.Loading)
    val uiStateProfile: StateFlow<Result<ProfileResponse>> = _uiStateProfile

    fun getProfile(
        token: String
    ) = viewModelScope.launch(dispatcher) {
        try {
            repository.getProfile(token)
                .collect{
                    _uiStateProfile.value = it
                }
        }catch (t: HttpException) {
            _uiStateProfile.value = Result.Error(true, t.code(), t.response()?.errorBody())
        }
    }
    fun getToken() : String = preference.getAccessToken()

    fun clearToken() {
        preference.deleteToken()
    }

    fun setIsLogin(isLogin: Boolean) {
        preference.putIsLogin(isLogin)
    }
}
