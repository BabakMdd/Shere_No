package shereno.andishesaz.com.shere_nov1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.Calendar;

import permission.auron.com.marshmallowpermissionhelper.ActivityManagePermission;
import permission.auron.com.marshmallowpermissionhelper.PermissionResult;
import permission.auron.com.marshmallowpermissionhelper.PermissionUtils;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.UpdateService;
import shereno.andishesaz.com.shere_nov1.ReciverClass.ApplicationReciver;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends ActivityManagePermission
        implements View.OnClickListener {

    String[] menu=new String[6];
    Setting setting=null;
    ActionBar mAction;
    Point p;
    private static final int RESULT_SETTING=1;
    public static String selectedBack;
    public static String selectedSize;
    public static Boolean IsActive;
    public static String selectedFont;
    boolean doubleBackToExitPressedOnce = false;
    String btn_update;
    boolean isExternalStorageRead;
    boolean isExternalStorageWrite;
    boolean isWakclock;
    boolean isBazar;
    boolean isNetworkState;
    boolean isInternet;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(MainActivity.this);
        setting.SetLocale("fa");
        p=setting.getScreenSize();

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            customAction(MainActivity.this);
            Initialize();
            //---------------------------Setting Page---------------------------
            showUserSettings();
        //---------------------------------Manage Permission--------------------------------------
        if (Build.VERSION.SDK_INT>=23){
            isExternalStorageRead=isPermissionGranted(MainActivity.this,PermissionUtils.Manifest_READ_EXTERNAL_STORAGE);
            isExternalStorageWrite=isPermissionGranted(MainActivity.this,PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE);
            isWakclock=isPermissionGranted(MainActivity.this,"android.permission.WAKE_LOCK");
            isNetworkState=isPermissionGranted(MainActivity.this,"android.permission.ACCESS_NETWORK_STATE");
            isInternet=isPermissionGranted(MainActivity.this,"android.permission.INTERNET");
            isBazar=isPermissionGranted(MainActivity.this,"com.farsitel.bazaar.permission.PAY_THROUGH_BAZAAR");

            if(isExternalStorageRead==false) {
                askCompactPermission(PermissionUtils.Manifest_READ_EXTERNAL_STORAGE, new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
            if (isExternalStorageWrite==false){
                askCompactPermission(PermissionUtils.Manifest_WRITE_EXTERNAL_STORAGE, new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
            if(isWakclock==false){
                askCompactPermission("android.permission.WAKE_LOCK", new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
            if(isNetworkState==false){
                askCompactPermission("android.permission.ACCESS_NETWORK_STATE", new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
            if(isInternet==false){
                askCompactPermission("android.permission.INTERNET", new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
            if(isBazar==false){
                askCompactPermission("com.farsitel.bazaar.permission.PAY_THROUGH_BAZAAR", new PermissionResult() {
                    @Override
                    public void permissionGranted() {}
                    @Override
                    public void permissionDenied() {}
                });
            }
        }

        //----------------------------------------Call AlarmManager------------------------------
        SetAlarm();
    }


    //-----------------------------------Call Update Service--------------------------

    private void Service(Context con){
        Intent i=new Intent(MainActivity.this, UpdateService.class);
        con.startService(i);
    }

    private void Initialize(){
        Button poems,deklame,bio,favorite,about,config;
        poems=(Button)findViewById(R.id.Poems);
        deklame=(Button)findViewById(R.id.deklame);
        bio=(Button)findViewById(R.id.bio);
        favorite=(Button)findViewById(R.id.favorite);
        about=(Button)findViewById(R.id.about);
        config=(Button)findViewById(R.id.setting);
        poems.setOnClickListener(this);
        deklame.setOnClickListener(this);
        bio.setOnClickListener(this);
        favorite.setOnClickListener(this);
        about.setOnClickListener(this);
        config.setOnClickListener(this);

        SQLclass sql=new SQLclass(MainActivity.this);
        try {
            sql.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------Main Page Button Click Handled-------------------------------
    @Override
    public void onClick(View view) {
        Button btn=(Button)view;
        int id=btn.getId();
        setting=new Setting(MainActivity.this);
        switch(id){
            case R.id.Poems:
                setting.OpenActivity(ListOfPoems.class);
                break;
            case R.id.deklame:
                setting.OpenActivity(WithSoundPoems.class);
                break;
            case R.id.favorite:
                setting.OpenActivity(FavoriteActivity.class);
                break;
            case R.id.setting:
                Intent i=new Intent(MainActivity.this,SettingActivity.class);
                startActivityForResult(i,RESULT_SETTING);
                break;
            case R.id.bio:
                setting.OpenActivity(BioGraphyActivity.class);
                break;
            case R.id.about:
                setting.OpenActivity(AboutUs.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            //-------------------------------------exit app on 2 click back button---------------------
           /* if(doubleBackToExitPressedOnce){
                super.onBackPressed();
                moveTaskToBack(true);
                System.exit(1);
            }
            this.doubleBackToExitPressedOnce = true;
            setting=new Setting(MainActivity.this);
            setting.Message(getString(R.string.quit_msg));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000); */

            //----------------------------------Go to Rating Page on Cafe Bazzar -----------------------------
            AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
            alert.setTitle(R.string.exit);
            alert.setIcon(R.mipmap.ic_launcher);
            alert.setMessage(R.string.exit_dialog);
            alert.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    PackageInfo pm=new PackageInfo();
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setData(Uri.parse("bazaar://details?id=" + pm.packageName));
                    intent.setPackage("com.farsitel.bazaar");
                    startActivity(intent);
                }
            });

            alert.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    moveTaskToBack(true);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
            });

            //--------------------Asign Animation into AlertDialog----------------------------
            AlertDialog dialog;
            dialog=alert.create();
            dialog.getWindow().getAttributes().windowAnimations=R.style.DialogAnimation;

            dialog.show();
        }
    }

    //----------------------------------Customize Action bar

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
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE );
        mAction.setTitle(R.string.app_name);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }
//----------------------------------this is For Setting Page-----------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTING:
                showUserSettings();
                break;
        }
    }

    private void showUserSettings() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        selectedBack=sharedPrefs.getString("prefBackgroundSound","NULL");
        selectedSize=sharedPrefs.getString("prefFontSize","NULL");
        IsActive=sharedPrefs.getBoolean("isActive",false);
        btn_update=sharedPrefs.getString("PrefUpdateBTN","NULL");
        selectedFont=sharedPrefs.getString("prefFontType","NULL");
    }

    //----------------------------------SetAlarm For Use Application---------------------------------

    private void SetAlarm(){
        AlarmManager alarm=(AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.SECOND,300);

        long time=cal.getTimeInMillis();
        Intent callTimer=new Intent(MainActivity.this, ApplicationReciver.class);
        PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this,0,callTimer,0);
        alarm.set(AlarmManager.RTC_WAKEUP,time,pi);
    }

}
