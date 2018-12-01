package shereno.andishesaz.com.shere_nov1;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;

public class WelcomeActivity extends Activity {

    Setting setting=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(WelcomeActivity.this);
        setting.SetLocale("fa");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        //--------------------------------------------------Set Splash FullScreen-------------------------------
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(3000);
                    setting.OpenActivity(MainActivity.class);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
