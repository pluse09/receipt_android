package com.example.receipt.receipt.presentation.scan

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import com.example.receipt.receipt.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
import com.example.receipt.receipt.presentation.common.RxFragment
import kotlinx.android.synthetic.main.fragment_scan.*
import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


class ScanFragment : RxFragment() {

    val CAMERA_REQUEST_CODE: Int = 0

    val PERMISSION_CODE: Int = 1000

    val IMAGE_CAPUTER_CODE: Int = 1001

    var imageUrl: Uri? = null

    lateinit var contentResolver: ContentResolver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentResolver = context!!.contentResolver

        cameraLaunchButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                    ContextCompat.checkSelfPermission(context!!,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, CAMERA_REQUEST_CODE)
                }
            }
            launchCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchCamera()
                    return
                }
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((resultCode == Activity.RESULT_OK)) {
            imageUrl?.let {
                val intent = Intent(context, ScanResultActivity::class.java)
                intent.putExtra("ImageUrl", imageUrl.toString())
                startActivity(intent)
                return
            }
            Toast.makeText(context, "画像が保存されませんでした。", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        imageUrl = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUrl)
        val fragment: Fragment = this
        fragment.startActivityForResult(intent, IMAGE_CAPUTER_CODE)
    }

}



