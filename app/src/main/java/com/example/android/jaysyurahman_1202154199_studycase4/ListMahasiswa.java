package com.example.android.jaysyurahman_1202154199_studycase4;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListMahasiswa extends AppCompatActivity {

    private ListView listView;
    private static int rotate =0;
    private ProgressDialog proD;
    private Button mButton;
    private String[] nama = {"Zeus","Hera","Poseidon","Ares","Hermes","Hefaistos","Afrodit","Athena"
            ,"Apollo","Artemis","Demeter","Hestia"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //deklarasi komponen yang di gunakan
        setContentView(R.layout.activity_listmahasiswa);
        listView =(ListView)findViewById(R.id.listMhs);
        Button mButton = (Button)findViewById(R.id.btnLoad);

        //set adapter
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));

        //aksi saat tombol diklik oleh user
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new listName().execute();
            }
        });

        //pengecakan apakah savedInstanceState sudah berisi sesuatu
        if(savedInstanceState != null){
            if(savedInstanceState.getInt("LOAD")==1){
                new listName().execute();
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
    class listName extends AsyncTask<Void,String,String>{
        ArrayAdapter<String> adapter;
        int i;
        @Override
        //method sebelum aksi di lakukan
        protected void onPreExecute() {

            //adapter untuk mengisi listview
            adapter = (ArrayAdapter<String>) listView.getAdapter();

            //inisialisasi progress dialog
            proD = new ProgressDialog(ListMahasiswa.this);
            proD.setTitle("Loading Data");
            proD.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proD.setMax(100);
            proD.setProgressStyle(0);
            proD.show();
            i=0;
        }
        //method saat prosess berjalan
        @Override
        protected String doInBackground(Void... voids) {

            //memnaggil array
            for(String Name : nama){
                publishProgress(Name);

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Load Success";
        }
        //method yang melakukan update saat publishProgress di kirim dari doInBackground
        @Override
        protected void onProgressUpdate(String... values) {

            //mengisi array pada listview
            adapter.add(values[0]);

            //perhitungan persentase saat progress
            i++;
            int proses = i *100/nama.length;
            proD.setProgress(proses);
            proD.setMessage(proses+"%");
        }

        //method saat proses sudah selesai beroprasi
        @Override
        protected void onPostExecute(String result) {

            //menghilangkan progres dialog
            proD.hide();
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();

            //menambahkan nilai pada rotate
            rotate =1;

        }
    }

}
