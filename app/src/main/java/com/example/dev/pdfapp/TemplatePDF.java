package com.example.dev.pdfapp;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TemplatePDF {


    private String tagLogErr = "openDocument";
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument(){

        try {
            createFile();
            document = new Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();
        }catch (Exception e){
            Log.e(tagLogErr, e.toString());
        }

    }


    private void createFile(){





        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");

        if(!folder.exists())
            folder.mkdirs();


        if(folder.exists()){
            pdfFile = new File(folder, "TemplatePDF.pdf");
        }

        if(folder.exists())
            System.out.println("somo foda memo!");




//        try {
//            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
//            if (!root.exists()) {
//                root.mkdirs();
//            }
//            File gpxfile = new File(root, "teste");
//            FileWriter writer = new FileWriter(gpxfile);
//            writer.append("sBody");
//            writer.flush();
//            writer.close();
//            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title, String subTitle, String date){

        try {

            paragraph = new Paragraph();
            addChield(new Paragraph(title, fTitle));
            addChield(new Paragraph(subTitle, fSubTitle));
            addChield(new Paragraph( "Generado: " + date, fHighText));

            paragraph.setSpacingAfter(30);
            document.add(paragraph);

        }catch (Exception e){
            Log.e(tagLogErr, e.toString());
        }

    }



    public void createTable(String[] header, ArrayList<String[]> clients){

        try{


            if(clients == null){
                clients = new ArrayList<>();
                clients.add(new String[]{"1", "aa", "bb"});
                clients.add(new String[]{"2", "aa2", "bb"});
                clients.add(new String[]{"4", "aa3", "bb"});
                clients.add(new String[]{"5", "aa5", "bb"});
            }

            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;

            int indexC=0;
            while (indexC < header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.GREEN);
                pdfPTable.addCell(pdfPCell);
            }


            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC=0; indexC<header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);

        }catch (Exception e){
            Log.e(tagLogErr, e.toString());
        }


    }

    public void addParagraph(String text){

        try{
            paragraph = new Paragraph(text, fText);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);

        }catch (Exception e){
            Log.e(tagLogErr, e.toString());
        }

    }

    private void addChield(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }


    public void viewPDF(Activity activity){


        if(pdfFile != null && pdfFile.exists()){
            Uri uri = Uri.fromFile(pdfFile);


            File file = new File(pdfFile.getPath());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(intent);

//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(uri, "application/pdf");

            try {
                //activity.startActivity(intent);
//                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
            }catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));

            }

        }


//        Intent intent = new Intent(context, ViewPDFActivity.class);
//        intent.putExtra("path", pdfFile.getAbsolutePath());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }

}
