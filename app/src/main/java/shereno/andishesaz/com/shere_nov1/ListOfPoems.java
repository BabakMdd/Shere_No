package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomBooks;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListOfPoems extends AppCompatActivity {

    ActionBar mAction;
    Setting setting=null;
    List<String> books;
    String[] DatabaseDetail;
    int[] ImgID;
    public static String result;
    Point p;
    ViewFlipper flipper;
    int mFlipping=0;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(ListOfPoems.this);
        setting.SetLocale("fa");
        p=setting.getScreenSize();
            setContentView(R.layout.activity_list_of_poems);
            customAction(ListOfPoems.this);

    }
    //---------------------------------------Set OnClick For XML layout--------------------------

    public void AhBaran(View view){
        result="آه باران";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void azdiarAshti(View view){
        result="از دیار آشتی";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void abrokoocheh(View view){
        result="ابرو کوچه";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void Parvazbakhorshid(View view){
        result="پرواز با خورشید";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void bapanjsokhansara(View view){
        result="با پنج سخن سرا";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void gonahDaray(View view){
        result="گناه دریا";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void baharrabavarkon(View view){
        result="بهار را باور کن";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void lahzehavaehsas(View view){
        result="لحظه ها و احساس";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void gozideashaar(View view){
        result="گزیده اشعار";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void morvaridmehr(View view){
        result="مروارید مهر";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void yekasemanparande(View view){
        result="یک آسمان پرنده";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void teshnetoofan(View view){
        result="تشنه طوفان";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void azkhamooshi(View view){
        result="از خاموشی";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void avazanparandeghamgin(View view){
        result="آواز آن پرنده غمگین";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void zibayejavdane(View view){
        result="زیبای جاودانه";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void delavviztarin(View view){
        result="دلاویزترین";
        setting=new Setting(ListOfPoems.this);
        setting.OpenActivity(Poems.class);
    }

    public void SecondPage(View view){
        setContentView(R.layout.second_list);
    }
    public void ThirdPage(View view){
        setContentView(R.layout.third_list);
    }
    public void BackToSecond(View view){
        setContentView(R.layout.second_list);
    }
    public void BackToFirst(View view){
        setContentView(R.layout.activity_list_of_poems);
    }
    public void ForthPage(View view){ setContentView(R.layout.forthlist);}
    public void BackToPage(View view){setContentView(R.layout.third_list);}

    //---------------------------------------Customize ActionBar---------------------------------

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
        mAction.setTitle(R.string.listOfPoems);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        setting=new Setting(ListOfPoems.this);
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
