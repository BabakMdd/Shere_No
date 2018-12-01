package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomFavoriteDelete;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.FavoriteShowDetail;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Setting setting=null;
    ActionBar mAction;
    TabHost tabs;
    List<Integer> list,dek;
    ListView deklame;
    ListView sher;
    List<String> deklameList=new ArrayList<String>();
    List<String> bedooneDeklameList=new ArrayList<String>();
    int IDgotten;
    TextView empty;
    List<Integer> favoriteShow,soundID;
    public static boolean redirectFromFavorite=false;
    public static int poemid,sID;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(FavoriteActivity.this);
        setting.SetLocale("fa");
        setContentView(R.layout.activity_favorite);
        customAction(FavoriteActivity.this);
        TabsInit();
        Fill();

        //------------------------------------Set Text Color For ListView in Tab Hosts-----------------------------
        setTheme(R.style.AppTheme_list);
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
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP );
        mAction.setTitle(R.string.favorite);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    private void TabsInit(){
        tabs=(TabHost)findViewById(R.id.tabHost);
        tabs.setup();
        TabHost.TabSpec specs=tabs.newTabSpec("Tab1");
        specs.setIndicator(getString(R.string.fav_deklame));

        SQLclass sql=new SQLclass(FavoriteActivity.this);
        list=sql.getPoemID("Favorite");

        if(list.size()==0){
            specs.setContent(R.id.emptydata);
        }
        else{
            specs.setContent(R.id.tab1);
        }

        TabHost.TabSpec specs2=tabs.newTabSpec("Tab2");
        specs2.setIndicator(getString(R.string.fav_bedoonedeklame));

        if(list.size()==0){
            specs2.setContent(R.id.emptydata);
        }
        else{
            specs2.setContent(R.id.tab2);
        }


        tabs.addTab(specs);
        tabs.addTab(specs2);
        ChangetabColor();

    }

    private void ChangetabColor(){
        TabWidget tw = (TabWidget)tabs.findViewById(android.R.id.tabs);
        for(int i=0;i<tabs.getTabWidget().getChildCount();i++){
            View tabView = tw.getChildTabViewAt(i);
            TextView tv = (TextView)tabView.findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.toast));
        }
    }

    //---------------------------------------Fill Content To Tabs IF Exists-------------------------

    private void Fill(){
        deklame=(ListView)findViewById(R.id.with_sound);
        sher=(ListView)findViewById(R.id.without_sound);
        SQLclass sql=new SQLclass(FavoriteActivity.this);
        try {
            //sql.createDataBase();
            list=sql.getPoemID("Favorite");
            for(int item: list){
               IDgotten=item;
                List<String> temp=sql.deklame("Poems",IDgotten);
                for(String content: temp){
                    deklameList.add(content.toString());
                }
                if(deklameList.size()>0){
                   // CustomFavoriteDelete adapter=new CustomFavoriteDelete(FavoriteActivity.this,deklameList);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(FavoriteActivity.this,android.R.layout.simple_list_item_1,deklameList);
                    deklame.setAdapter(adapter);
                    deklame.setOnItemClickListener(this);
                    empty=(TextView)findViewById(R.id.emptyText);
                    empty.setVisibility(View.INVISIBLE);
                }
                else{
                    empty=(TextView)findViewById(R.id.list_empty);
                    empty.setText(getString(R.string.empty_area));
                    deklame.setEmptyView(empty);
                }
            }
            dek=sql.getPoemID("Favorite");
            for(int item: dek){
                IDgotten=item;
                List<String> temp=sql.NoDeklame("Poems",IDgotten);
                for(String content: temp){
                    bedooneDeklameList.add(content.toString());
                }
                if(bedooneDeklameList.size()>0){
                    //CustomFavoriteDelete adapter=new CustomFavoriteDelete(FavoriteActivity.this,bedooneDeklameList);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(FavoriteActivity.this,android.R.layout.simple_list_item_1,bedooneDeklameList);
                    sher.setAdapter(adapter);
                    sher.setOnItemClickListener(this);
                    empty=(TextView)findViewById(R.id.emptyText);
                    empty.setVisibility(View.INVISIBLE);
                }
                else{
                    empty=(TextView)findViewById(R.id.list_empty);
                    empty.setText(getString(R.string.empty_area));
                    sher.setEmptyView(empty);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //--------------------------------Event To handle Favorite Select Item-------------------------------------
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
          int id=adapterView.getId();

        if(id==R.id.without_sound){
            String result=adapterView.getItemAtPosition(i).toString();
            SQLclass sql=new SQLclass(FavoriteActivity.this);
            try{
                //sql.createDataBase();
                favoriteShow=sql.FavoriteShow("Poems",result);
                for(int item: favoriteShow){
                     poemid=item;
                }
                redirectFromFavorite=true;
                setting=new Setting(FavoriteActivity.this);
                setting.OpenActivity(ShowPoems.class);
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            String soundResult=adapterView.getItemAtPosition(i).toString();
            SQLclass sql=new SQLclass(FavoriteActivity.this);

            try{
               // sql.createDataBase();
                soundID=sql.FavoriteShow("Poems",soundResult);
                for(int item: soundID){
                    sID=item;
                }
                redirectFromFavorite=true;
                setting=new Setting(FavoriteActivity.this);
                setting.OpenActivity(PlaySoundWithPoems.class);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //---------------------------------------------Set Redirect false after go to poems from main ----------------------------

    public static void setRedirectFromFavorite(boolean redirectFromFavorite) {
        FavoriteActivity.redirectFromFavorite = redirectFromFavorite;
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
