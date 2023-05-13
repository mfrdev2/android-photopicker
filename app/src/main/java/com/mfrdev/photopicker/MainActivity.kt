package com.mfrdev.photopicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val wrapper = NewPhotoPickerWrapper(this, 2)
    private val button: Button by lazy {
        findViewById(R.id.button)
    }

    private val image1: ImageView by lazy {
        findViewById(R.id.imageView1)
    }
    private val image2: ImageView by lazy {
        findViewById(R.id.imageView2)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener {
            resetImageView()
            //show button sheet
            PhotoPickerSelectionFragment(
                onSingle = { sheet ->
                    sheet.dismiss()
                    wrapper.singleImage(
                        onSingle = {
                            resetImageView()
                            image1.setImageURI(it)
                        },
                        onEmpty = {
                            Toast.makeText(this,"Select nothing",Toast.LENGTH_LONG).show()
                        }
                    )
                }, onMultiple = { sheet ->
                    sheet.dismiss()
                    wrapper.multipleImage(
                        onMultiple = {
                            resetImageView()
                            if(it.size == 1){
                                image1.setImageURI(it[0])
                            }

                            if(it.size == 2){
                                image1.setImageURI(it[0])
                                image2.setImageURI(it[1])
                            }

                        },
                        onEmpty = {
                            Toast.makeText(this,"Select nothing",Toast.LENGTH_LONG).show()
                        }
                    )
                }
            ).show(supportFragmentManager,"PhotoPickerSelectorFragment")
        }
    }

    private fun resetImageView() {
        image1.setImageURI(null)
        image2.setImageURI(null)
    }
}