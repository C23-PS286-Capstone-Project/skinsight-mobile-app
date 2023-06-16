package com.dayatmuhammad.skinsight.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dayatmuhammad.skinsight.data.*
import com.dayatmuhammad.skinsight.di.Dispatcher
import com.dayatmuhammad.skinsight.di.DispatcherEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: Repository,
    @Dispatcher(DispatcherEnum.Default) private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    private val _uiStateProfile = MutableStateFlow<Result<RegisterResponse>>(Result.Loading)
    val uiStateProfile: StateFlow<Result<RegisterResponse>> = _uiStateProfile

    fun register(
        registerRequest: RegisterRequest
    ) = viewModelScope.launch(dispatcher) {
        try {
            repository.register(registerRequest)
                .collect{
                    _uiStateProfile.value = it
                }
        }catch (t: HttpException) {
            _uiStateProfile.value = Result.Error(true, t.code(), t.response()?.errorBody())
        }
    }
}
