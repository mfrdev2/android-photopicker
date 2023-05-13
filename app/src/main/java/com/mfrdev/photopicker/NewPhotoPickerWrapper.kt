package com.mfrdev.photopicker

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class NewPhotoPickerWrapper(
    activity: AppCompatActivity,
    multipleMaximum: Int // the count of photos you want to pick
) {
    private val activityResultLauncherForSingle: ActivityResultLauncher<PickVisualMediaRequest>
    private val activityResultLauncherForMultiple: ActivityResultLauncher<PickVisualMediaRequest>
    private var onSingle: ((uri: Uri) -> Unit)? = null
    private var onMultiple: ((uri: List<Uri>) -> Unit)? = null
    private var onEmpty: (() -> Unit)? = null

    init {
        activityResultLauncherForSingle = activity.registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            uri?.let {
                onSingle?.let { onSingle ->
                    onSingle(it)
                }
            } ?: run {
                onEmpty?.let { onEmpty -> onEmpty() }
            }

        }


        activityResultLauncherForMultiple = activity.registerForActivityResult(
            ActivityResultContracts.PickMultipleVisualMedia(multipleMaximum)
        ) { uris ->
            uris?.let {
                if (uris.isNotEmpty()) {
                    onMultiple?.let { onMultiple ->
                        onMultiple(it)
                    }
                } else {
                    onEmpty?.let { onEmpty -> onEmpty() }
                }
            }?:run {
                onEmpty?.let { onEmpty -> onEmpty() }
            }
        }
    }

    fun singleImage(onSingle:(uri:Uri)->Unit,onEmpty:()->Unit){
        if(ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()){
            this.onSingle = onSingle
            this.onEmpty = onEmpty
            activityResultLauncherForSingle.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    fun multipleImage(onMultiple:(uris:List<Uri>)->Unit,onEmpty:()->Unit){
        if(ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()){
            this.onMultiple = onMultiple
            this.onEmpty = onEmpty
            activityResultLauncherForMultiple.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}