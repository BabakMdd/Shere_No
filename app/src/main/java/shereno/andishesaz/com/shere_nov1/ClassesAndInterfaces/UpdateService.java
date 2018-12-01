package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import org.apache.http.HttpClientConnection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import shereno.andishesaz.com.shere_nov1.MainActivity;
import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 26/04/2017.
 */

public class UpdateService extends Service {

    String Version="1.0";
    String HostAddress="";
    String FileUrl="";
    boolean permission=false;
    ProgressDialog prgDialog;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Connection(getApplicationContext()).execute();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         super.onStartCommand(intent, flags, startId);
         return Service.START_NOT_STICKY;
    }

    private void AlertMaker(){
        AlertDialog.Builder alt=new AlertDialog.Builder(UpdateService.this);

        alt.setTitle(R.string.update_title);
        alt.setMessage(getString(R.string.update_body));
       // alt.setIcon(R.drawable.off);

        alt.setPositiveButton(getString(R.string.update_yes),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
                new Download(getApplicationContext()).execute(FileUrl);
            }
        });

        alt.setNegativeButton(getString(R.string.update_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        alt.show();
    }

    //----------------------------------------------------Class For Check Connection---------------------------
    private class Connection extends AsyncTask<String,String,Boolean>{
        ProgressDialog nDialog;
        Context con;

        public Connection(Context con){
            this.con=con;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog=new ProgressDialog(con);
            nDialog.setTitle(getString(R.string.progress_title));
            nDialog.setMessage(getString(R.string.progress_message));
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            ConnectivityManager cm=(ConnectivityManager)getSystemService(UpdateService.CONNECTIVITY_SERVICE);
            NetworkInfo netinfo= cm.getActiveNetworkInfo();
            if (netinfo != null && netinfo.isConnected()) {
                try{
                    URL url=new URL("http://www.google.com");
                    HttpURLConnection con=(HttpURLConnection)url.openConnection();
                    con.setConnectTimeout(3000);
                    con.connect();
                    if (con.getResponseCode()==200) {
                        return true;
                    }
                }catch(MalformedURLException e)
                {
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if (aBoolean==true) {
                nDialog.dismiss();
                new AppVersion(con).execute(HostAddress);
            }
            else{
                Setting setting=new Setting(con);
                setting.Message(getString(R.string.connection_faild));
            }
        }
    }

    //---------------------------------------------Class To Update If Exists-------------------------------

    private class AppVersion extends AsyncTask<String,Void,String>{

        ProgressDialog mDialog;
        Context con;

        public AppVersion(Context con){
            this.con=con;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog=new ProgressDialog(con);
            mDialog.setMessage(getString(R.string.connect_server));
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try{
                String url=strings[0];
                HttpClient client=new DefaultHttpClient();
                HttpContext context=new BasicHttpContext();
                HttpGet get=new HttpGet(url);
                HttpResponse response=client.execute(get,context);
                BufferedReader reader=new BufferedReader
                        (new InputStreamReader(response.getEntity().getContent()));

                char[] lines;
                lines=reader.readLine().toCharArray();
                String a=new StringBuilder().append(lines[0]).append(lines[1]).append(lines[2]).toString();

                if(Version.equals(a)){
                    permission=false;
                }else{
                    permission=true;
                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(permission==false){
                Setting setting=new Setting(con);
                setting.Message(getString(R.string.is_update));

            }else{
                AlertMaker();
            }
        }
    }

    //-------------------------------------------------Download New APK File-------------------------------------

    private class Download extends AsyncTask<String,String,Void>{

        Context con;

        public Download(Context con){
            this.con=con;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prgDialog=new ProgressDialog(con);
            prgDialog.setMessage(getString(R.string.updating));
            prgDialog.setMax(100);
            prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            prgDialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {

            try{
                URL url=new URL(strings[0]);
                HttpURLConnection c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                String path="/mnt/sdcard/download/";
                File file=new File(path);
                file.mkdir();

                String appName=getString(R.string.app_name)+".apk";
                File output=new File(file,appName);
                if(output.exists()){
                    output.delete();
                }
                FileOutputStream fos=new FileOutputStream(output);
                InputStream is=c.getInputStream();
                int lenght=c.getContentLength();
                byte[] buffer=new byte[1024];
                int len=0;
                int total=0;
                while((len=is.read(buffer))!= -1){
                    fos.write(buffer,0,len);
                    total++;
                    publishProgress(""+(int)((total*100)/lenght));
                }
                fos.flush();
                fos.close();
                is.close();

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile
                        (new File(Environment.getExternalStorageDirectory()+"/download/"+appName)),"application/vnd/android/package-archive");
                startActivity(intent);

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            prgDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            prgDialog.setProgress(Integer.parseInt(values[0]));
        }
    }
}
