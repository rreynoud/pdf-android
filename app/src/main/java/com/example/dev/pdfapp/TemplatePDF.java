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
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TemplatePDF {


    private String tagLogErr = "openDocument";
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private Font fText = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    private Font fTotalValue = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD, BaseColor.BLACK);

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

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());

        if(!folder.exists())
            folder.mkdirs();


        if(folder.exists())
            pdfFile = new File(folder, "TemplatePDF.pdf");

    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title, String subTitle, String client, String value){

        try {

            paragraph = new Paragraph();
            addChield(new Paragraph(title, fTitle));
            addChield(new Paragraph(subTitle, fSubTitle));
            addChield(new Paragraph(  client, fHighText));
            addChield(new Paragraph(  value, fTotalValue));

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
                clients.add(new String[]{"Manteiga", ""});
                clients.add(new String[]{"R$ 50,00", "x1 R$50,00"});

                clients.add(new String[]{"Produto_1", ""});
                clients.add(new String[]{"R$ 10,00", "x1 R$10,00"});

            }

            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;

            int indexC=0;
//            while (indexC < header.length){
//                pdfPCell = new PdfPCell(new Phrase(header[indexC++]));
//                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                pdfPCell.setBackgroundColor(BaseColor.GREEN);
//                pdfPTable.addCell(pdfPCell);
//            }


            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC=0; indexC<header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfPCell.setFixedHeight(20);
                    pdfPCell.setFixedHeight(0);
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
            paragraph.setAlignment(Element.ALIGN_CENTER);
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


            try {

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TemplatePDF.pdf");

                if(file.exists()){
                    Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);

                    Intent intent = new Intent();
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    intent.setType("application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


                        intent.setAction(Intent.ACTION_SEND);
                        activity.startActivity(Intent.createChooser(intent, null));


//                        intent.setAction(Intent.ACTION_VIEW);
//                        activity.startActivity(intent);


                }


//                Uri uri = Uri.fromFile(outputFile);

//                Intent share = new Intent();
//                share.setAction(Intent.ACTION_SEND);
//                share.setType("application/pdf");
//                share.putExtra(Intent.EXTRA_STREAM, uri);
//                share.setPackage("com.whatsapp");
//
//                activity.startActivity(share);

//                Uri uri= FileProvider.getUriForFile(context,BuildConfig.APPLICATION_ID , pdfFile);
//

//                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
            }catch (Exception e){
                e.printStackTrace();
//                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.adobe.reader")));
                System.out.println("FUDEU");
            }

        }


//        Intent intent = new Intent(context, ViewPDFActivity.class);
//        intent.putExtra("path", pdfFile.getAbsolutePath());
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }

}
