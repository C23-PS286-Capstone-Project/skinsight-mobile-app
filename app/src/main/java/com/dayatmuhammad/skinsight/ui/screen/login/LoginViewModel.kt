package com.dayatmuhammad.skinsight.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayatmuhammad.skinsight.data.LoginRequest
import com.dayatmuhammad.skinsight.data.LoginResponse
import com.dayatmuhammad.skinsight.data.Repository
import com.dayatmuhammad.skinsight.data.Result
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
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val preference: SharedPreference,
    @Dispatcher(DispatcherEnum.Default) private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _uiStateProfile = MutableStateFlow<Result<LoginResponse>>(Result.Loading)
    val uiStateProfile: StateFlow<Result<LoginResponse>> = _uiStateProfile

    fun login(
        loginRequest: LoginRequest
    ) = viewModelScope.launch(dispatcher) {
        try {
            repository.login(loginRequest)
                .collect{
                    _uiStateProfile.value = it
                }
        }catch (t: HttpException) {
            _uiStateProfile.value = Result.Error(true, t.code(), t.response()?.errorBody())
        }
    }

    fun setToken(token: String) {
        preference.putAccessToken(token)
    }

    fun setIsLogin(isLogin: Boolean) {
        preference.putIsLogin(isLogin)
    }
}
