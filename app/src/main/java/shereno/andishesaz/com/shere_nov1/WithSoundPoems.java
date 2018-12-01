package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.io.IOException;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomAdapterForWithSoundPoem;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomFontSizeForPoems;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WithSoundPoems extends AppCompatActivity {

    ActionBar mAction;
    Setting setting=null;
    List<String> sound;
    public static String withSound;
    public static int pID,location;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(WithSoundPoems.this);
        setting.SetLocale("fa");
            setContentView(R.layout.activity_with_sound_poems);
            customAction(WithSoundPoems.this);
            Initialize();
    }


    //---------------------------------------Customize Action--------------------------------------
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
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP );
        mAction.setTitle(R.string.withSound);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //---------------------------------------Initialize--------------------------------------------
    private void Initialize(){
        SQLclass sql=new SQLclass(WithSoundPoems.this);
        try{
            //sql.createDataBase();
            sound=sql.WithSound(SQLclass.TB_Poems);
            ListView list=(ListView)findViewById(R.id.soundPoems);
            //CustomFontSizeForPoems adapter=new CustomFontSizeForPoems(WithSoundPoems.this,sound);
            CustomAdapterForWithSoundPoem adapter=new CustomAdapterForWithSoundPoem(WithSoundPoems.this,sound);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String result=adapterView.getItemAtPosition(i).toString();
                    setting=new Setting(WithSoundPoems.this);
                    switch(result){
                        case "اگر جام بشکست":
                            pID=21;
                            location=0;
                          withSound="اگر جام بشکست";
                            FavoriteActivity.setRedirectFromFavorite(false);
                          setting.OpenActivity(PlaySoundWithPoems.class);
                            break;
                        case "جادوی بی اثر":
                            pID=50;
                            location=1;
                            withSound="جادوی بی اثر";
                            FavoriteActivity.setRedirectFromFavorite(false);
                            setting.OpenActivity(PlaySoundWithPoems.class);
                            break;
                        case "دیوار":
                            pID=53;
                            location=2;
                            withSound="دیوار";
                            FavoriteActivity.setRedirectFromFavorite(false);
                            setting.OpenActivity(PlaySoundWithPoems.class);
                            break;
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
    //--------------------------------------------Return To Home Page Handle-------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(WithSoundPoems.this);
        switch(id){
            case R.id.action_home:
                setting.OpenActivity(MainActivity.class);
                break;
            case android.R.id.home:
                setting.OpenActivity(MainActivity.class);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }
}
