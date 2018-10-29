package com.example.dev.pdfapp

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import org.jetbrains.anko.share
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var header = arrayOf("Id", "Nombre", "Apellido")
    var  shortText = "Hola"
    var  longText = "Nunca consideres el estudio como una obligación, sino como una oportunidad para penetrar en el bello y maravilloso mundo del saber"
    lateinit var templatePDF:TemplatePDF

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }




    fun pdfView(view: View){


        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
            )
        }


        if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
            )
        }

        templatePDF = TemplatePDF(applicationContext)
        templatePDF.openDocument()
        templatePDF.addMetaData("clientes", "Ventas", "Marines")
        templatePDF.addTitles("Tienda CodigoFacilito", "Clientes", "06/12/2018")

        templatePDF.addParagraph(shortText)
        templatePDF.addParagraph(longText)
        templatePDF.createTable(header, null)

        templatePDF.closeDocument()



        if(isExternalStorageWritable()){
            templatePDF.viewPDF(this)

            share("asdfasdf")


        }else{
            println("asdfasdfasdf")
        }



    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

}
