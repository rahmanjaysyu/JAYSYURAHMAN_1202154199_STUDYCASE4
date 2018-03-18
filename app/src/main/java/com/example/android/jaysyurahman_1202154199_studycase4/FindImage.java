package com.example.android.jaysyurahman_1202154199_studycase4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FindImage extends AppCompatActivity {

    private EditText mUri;
    private Button mButton;
    private ImageView mImage;
    private ProgressDialog proD;
    private static int rotate = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inisialisasi komponen
        setContentView(R.layout.activity_findimage);
        mUri =(EditText)findViewById(R.id.txtUri);
        mButton =(Button)findViewById(R.id.btnLoad);
        mImage = (ImageView)findViewById(R.id.lblImg);
       // mUri.setText("http://images.solopos.com/2013/03/monyet.jpg");

        //aksi saat button di click
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Find().execute(mUri.getText().toString());
            }
        });

        //Pengecekan apakah savedInstanceState sudah berisi sesuatu
        if(savedInstanceState != null){
            if(savedInstanceState.getInt("LOAD")==1){
                new Find().execute(mUri.getText().toString());
                Log.d("Success","Rotate Success"+savedInstanceState.getInt("LOAD"));
            }
        }
    }

    //method yang menyimpan sesuatu pada package

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("LOAD",rotate);
    }

    //class asynctask
    private class Find extends AsyncTask<String,Bitmap,Bitmap>{

        //method sebelum aksi di lakukan
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //inisialisasi progress dialog
            proD = new ProgressDialog(FindImage.this);

           // proD = new ProgressDialog(FindImage.this);
            proD.setTitle("Loading Data");
            proD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proD.setMax(100);
            proD.setProgressStyle(0);
            proD.setMessage("Mohon tunggu sebentar");
            proD.show();

        }

        //method saat prosess berjalan
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try{
                //Melakukan openstream ke link
                InputStream input = new java.net.URL(imageURL).openStream();
                //decode stream data yang di ambil menjadi bitmap
                bitmap = BitmapFactory.decodeStream(input);
                publishProgress(BitmapFactory.decodeStream(input));
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            //Set Image
            mImage.setImageBitmap(result);
            proD.dismiss();
            rotate=1;
        }
    }
}
