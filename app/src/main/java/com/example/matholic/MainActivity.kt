package com.example.matholic

import android.Manifest
import android.app.Application
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.desmond.squarecamera.CameraActivity
import com.example.matholic.Utility.Constants.Companion.QUESTION_DIR
import com.example.matholic.Utility.Constants.Companion.REQUEST_CAMERA
import com.example.matholic.adapter.QuestionsAdapter
import com.example.matholic.databinding.ActivityMainBinding
import com.example.matholic.model.Question
import com.example.matholic.model.QuestionResponse
import com.example.matholic.repo.MainRepository
import com.example.matholic.viewmodel.MainActivityViewModel
import com.example.matholic.viewmodel.MainActivityViewModelFactory
import com.himanshurawat.imageworker.Extension
import com.himanshurawat.imageworker.ImageWorker
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter: QuestionsAdapter
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var viewModelFactory: MainActivityViewModelFactory
    private val repository by lazy {  MainRepository() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        adapter = QuestionsAdapter()
        binding.itemList.layoutManager = LinearLayoutManager(this)
        binding.itemList.adapter = adapter

        viewModelFactory = MainActivityViewModelFactory(repository, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        binding.iconCamera.setOnClickListener{
            Dexter.withContext(this@MainActivity)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener{
                    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                        Toast.makeText(it.context, "You need to give permission to take picture", Toast.LENGTH_LONG)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: PermissionRequest?,
                        p1: PermissionToken?
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                        val intent = Intent(this@MainActivity, CameraActivity::class.java)
                        startActivityForResult(intent, REQUEST_CAMERA)
                    }
                })
                .check()
        }

        updateData()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK) {
            return
        }
        when(requestCode) {
            REQUEST_CAMERA -> {
                val uri = data!!.data
                val file = File(uri?.path)
                val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                val uuid = UUID.randomUUID().toString();

                //save image
                ImageWorker.to(this)
                    .directory(QUESTION_DIR)
                    .setFileName(uuid)
                    .withExtension(Extension.PNG)
                    .save(bitmap, 80)

                val path : String = filesDir.absolutePath.toString() + "/questions" + "/" + uuid + ".png"
                val question = Question(uuid = uuid, "", path)
                viewModel.saveQuestion(question)

                //post question
                viewModel.postQuestion(file, uuid)
                onQuestionPosted(uuid)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun updateData(){
        viewModel.getAllQuestions()
        viewModel.questions.observe(this, Observer {
            questions -> adapter.setAllData(questions)
        })
    }

    fun onQuestionPosted(uuid : String) {
        viewModel.response.observe(this, Observer {
            response ->
                if(!response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "file could not posted", Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this@MainActivity, "Request is successfull, data will be updated", Toast.LENGTH_LONG)
                    val expression = response.body()
                    expression?.let{
                        viewModel.updateQuestion(uuid, it.expression)
                        viewModel.response = MutableLiveData()
                        updateData()
                    }
                }

        })

    }
}