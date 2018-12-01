package shereno.andishesaz.com.shere_nov1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.ActionBar;
import android.widget.Button;
import android.widget.LinearLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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
import java.net.URL;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.UpdateService;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SettingActivity extends PreferenceActivity {
    Setting setting=null;
    ActionBar mAction;
    private AppCompatDelegate mDelegate;


    //-----------------------------For Update---------------------------
    ProgressDialog prgDialog;
    String Version;
    String HostAddress="http://www.pishgam-app.ir/MoshiriVersion.txt";
    String FileUrl="http://www.pishgam-app.ir/ShereNo.apk";
    boolean permission=false;

    public SettingActivity() throws PackageManager.NameNotFoundException {
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          //--------------------------Config Action--------------------------
        try{
            getDelegate().installViewFactory();
            getDelegate().onCreate(savedInstanceState);
            super.onCreate(savedInstanceState);
            setting=new Setting(SettingActivity.this);
            setting.SetLocale("fa");
            addPreferencesFromResource(R.xml.settings);
            customAction(SettingActivity.this);
            //---------------------------------Set BackGround Color-----------------------------
            if(Build.VERSION.SDK_INT>=16) {
                //this.getListView().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.settingback));
                this.getWindow().setBackgroundDrawable(this.getResources().getDrawable(R.drawable.settingback));
            }
            setTheme(R.style.AppTheme_WhiteBack);

            //-------------------------------- Handle Update button----------------------------
            Preference button=(Preference)getPreferenceManager().findPreference("PrefUpdateBTN");
            if(button!=null){
                button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        new Connection().execute();
                        return true;
                    }
                });
            }
            //---------------------------------Read Application Version -------------------------

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(),0);
            Version= String.valueOf(pInfo.versionName);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //-------------------------------These Parts is OverRide To Handled ActionBar In Setting Activity------------
   @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
       //----------------------------------Handle Home as Up Onclick-----------------------------------------
       getDelegate().onPostCreate(savedInstanceState);
      /* LinearLayout root = (LinearLayout)findViewById(android.R.id.list).getParent().getParent().getParent();
       Toolbar bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.setting_toolbar, root, false);
       root.addView(bar,0); // insert at top
       bar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       }); */
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        getDelegate().setTitle(getString(R.string.setting));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }
        return mDelegate;
    }

    //--------------------------------Back Click--------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        switch(id){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(SettingActivity.this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void customAction(Context con){
        mAction=getDelegate().getSupportActionBar();
        mAction.setDisplayShowHomeEnabled(false);
        mAction.setDisplayShowTitleEnabled(false);
        LayoutInflater inflate=LayoutInflater.from(con);
        ViewGroup view=(ViewGroup)inflate.inflate(R.layout.action_bar,null);
        mAction.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        mAction.setHomeButtonEnabled(true);
        mAction.setDisplayHomeAsUpEnabled(true);
        // mAction.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));
        mAction.setDisplayShowCustomEnabled(true);
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP);
        mAction.setTitle(R.string.setting);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //----------------------------------------Alert For Update------------------------------------------------

    private void AlertMaker(){
        AlertDialog.Builder alt=new AlertDialog.Builder(SettingActivity.this,R.style.MyDialogTheme);

        alt.setTitle(R.string.update_title);
        alt.setIcon(R.mipmap.ic_launcher);
        alt.setMessage(getString(R.string.update_body));

        alt.setPositiveButton(getString(R.string.update_yes),new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
                new Download().execute(FileUrl);
            }
        });

        alt.setNegativeButton(getString(R.string.update_no), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        AlertDialog dialog = alt.create();
        dialog.show();
    }

    //----------------------------------------------------Class For Check Connection---------------------------
    private class Connection extends AsyncTask<String,String,Boolean> {
        ProgressDialog nDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nDialog=new ProgressDialog(SettingActivity.this);
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
                new AppVersion().execute(HostAddress);
            }
            else{
                Setting setting=new Setting(SettingActivity.this);
                setting.Message(getString(R.string.connection_faild));
                nDialog.dismiss();
            }
        }
    }

    //---------------------------------------------Class To Update If Exists-------------------------------

    private class AppVersion extends AsyncTask<String,Void,String>{

        ProgressDialog mDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog=new ProgressDialog(SettingActivity.this);
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
                String a=new StringBuilder().append(lines[0]).append(lines[1]).append(lines[2]).append(lines[3]).append(lines[4]).toString();

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
                Setting setting=new Setting(SettingActivity.this);
                setting.Message(getString(R.string.is_update));
                mDialog.dismiss();

            }else{
                mDialog.dismiss();
                AlertMaker();
            }
        }
    }

    //-------------------------------------------------Download New APK File-------------------------------------

    private class Download extends AsyncTask<String,String,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(0);
        }

        @Override
        protected Void doInBackground(String... strings) {

            try{
                URL url=new URL(strings[0]);
                HttpURLConnection c=(HttpURLConnection)url.openConnection();
                c.setRequestMethod("GET");
                c.connect();

                String path=android.os.Environment.getExternalStorageDirectory()+"/ShereNo_UPDATE";
                File file=new File(path);
                file.mkdir();

                //String appName=getString(R.string.app_name)+".apk";
                String appName="ShereNo.apk";
                File output=new File(file,appName);
                if(output.exists()){
                    output.delete();
                }
                FileOutputStream fos=new FileOutputStream(output);
                InputStream is=c.getInputStream();
                int lenght=c.getContentLength();
                //int lenght=100;
                byte[] buffer=new byte[1024];
                int len=0;
                int total=0;
                while((len=is.read(buffer))!= -1){
                    total+=len;
                    fos.write(buffer,0,len);
                    publishProgress(""+(int)((total*100)/lenght));

                }
                fos.flush();
                fos.close();
                is.close();

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile
                        (new File(Environment.getExternalStorageDirectory()+"/ShereNo_UPDATE/"+appName)),"application/vnd.android.package-archive");
                startActivity(intent);

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissDialog(0);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            prgDialog.setProgress(Integer.parseInt(values[0]));
        }
    }

    //------------------------------------------Show Dialog-----------------------------------------
    @Override
    protected Dialog onCreateDialog(int id) {
        prgDialog=new ProgressDialog(SettingActivity.this);
        prgDialog.setMessage(getString(R.string.updating));
        prgDialog.setMax(100);
        prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        prgDialog.show();

        return prgDialog;
    }
}
