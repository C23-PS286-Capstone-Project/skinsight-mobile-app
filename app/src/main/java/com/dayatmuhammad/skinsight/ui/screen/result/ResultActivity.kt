package com.dayatmuhammad.skinsight.ui.screen.result

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dayatmuhammad.skinsight.R
import com.dayatmuhammad.skinsight.component.CheckInternetConnection
import com.dayatmuhammad.skinsight.ui.screen.camera.CameraActivity
import com.dayatmuhammad.skinsight.databinding.ActivityResultBinding
import com.dayatmuhammad.skinsight.preference.SharedPreference
import com.dayatmuhammad.skinsight.utils.reduceFileImage
import com.dayatmuhammad.skinsight.utils.rotateBitmap
import com.dayatmuhammad.skinsight.utils.showMessage
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates
import com.dayatmuhammad.skinsight.data.Result
import com.dayatmuhammad.skinsight.utils.LoadingDialog
import kotlin.math.round

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var isBackCamera by Delegates.notNull<Boolean>()

    private val viewModel: ResultViewModel by viewModels()
    private lateinit var loadingDialog : LoadingDialog

    @Inject
    lateinit var preference: SharedPreference


    companion object {
        const val CAMERA_X_RESULT = 200
        val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                showMessage("Tidak mendapatkan permission.", this)
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this@ResultActivity)

        supportActionBar?.hide()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.saveResult.setOnClickListener {
            onBackPressed()
        }


        startCameraX()
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private var getFile: File? = null
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {

            val myFile = it.data?.getSerializableExtra("picture") as File
            isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            Glide.with(this)
                .load(result)
                .transition(DrawableTransitionOptions.withCrossFade())
                .transform(CenterCrop())
                .into(binding.ivPhotoInside)

            getFile = reduceFileImage(myFile, isBackCamera)

            whenGetPhoto()
        }
    }

    private fun whenGetPhoto() {
        if (getFile != null) {
            val file = getFile as File

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val token = viewModel.getToken()

            if (CheckInternetConnection(this).value){
                viewModel.predict("Bearer $token", imageMultipart).observe(this){
                    when (it){
                        is Result.Loading -> {
                            loadingDialog.startLoading()
                        }
                        is Result.Success -> {
                            loadingDialog.stopLoading()
                            val score = it.data.data?.prediction_score.times(100)
                            binding.predictResult.text = it.data.data?.prediction_result
                            binding.predictScore.text = score.toString() + " %"
                            binding.predictAge.text = it.data.data?.prediction_age
                        }
                        is Result.Error ->{
                            Toast.makeText(this, "Ooops, Something when Wrong", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            loadingDialog.stopLoading()
                            Toast.makeText(this, "Ooops, Something when Wrong", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show()
            }


        } else showMessage("Error When get Photot", this)
    }

    override fun onResume() {
        super.onResume()
    }
}

operator fun Any?.times(i: Int): Any {
    return when (this) {
        is Int -> this * i
        is Long -> this * i
        is Float -> round(this * i)
        is Double -> round(this * i)
        is Byte -> this * i
        is Short -> this * i
        is Char -> this * i
        else -> throw UnsupportedOperationException("Multiplication is not supported for this type.")
    }
}
