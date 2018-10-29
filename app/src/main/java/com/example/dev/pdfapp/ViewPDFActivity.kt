package com.example.dev.pdfapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class ViewPDFActivity : AppCompatActivity() {


    lateinit var file:File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pdf)

        var bundle = intent.extras

        if(bundle != null)
            file = File(bundle.getString("path", ""))



    }
}
