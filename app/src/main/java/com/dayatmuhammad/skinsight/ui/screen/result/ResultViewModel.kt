package com.dayatmuhammad.skinsight.ui.screen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dayatmuhammad.skinsight.data.*
import com.dayatmuhammad.skinsight.di.Dispatcher
import com.dayatmuhammad.skinsight.di.DispatcherEnum
import com.dayatmuhammad.skinsight.preference.SharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val repository: Repository,
    private val preference: SharedPreference,
    @Dispatcher(DispatcherEnum.Default) private val dispatcher: CoroutineDispatcher,
): ViewModel() {

    fun predict(
        token: String,
        image : MultipartBody.Part
    ) = repository
        .predict(token, image)
        .asLiveData()

    fun getToken() : String = preference.getAccessToken()


}