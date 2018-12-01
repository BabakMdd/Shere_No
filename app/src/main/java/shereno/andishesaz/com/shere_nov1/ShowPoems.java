package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomFontSizeForPoems;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by babak on 01/03/2017.
 */

public class ShowPoems extends AppCompatActivity implements View.OnClickListener{

    Setting setting;
    ActionBar mAction;
    List<String> poems;
    int counter=Poems.IDs;
    int counterFromFavorite=FavoriteActivity.poemid;
    WakeLock wl;
    ListView list;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(ShowPoems.this);
        setting.SetLocale("fa");
            setContentView(R.layout.activity_show_poems);
            customAction(ShowPoems.this);
            Initialize();
            ButtonInitialize();
            TurnOnorOff();
    }


    //------------------------------------------Set WakeLock To Stay Activity Awake-----------------
    private void TurnOnorOff(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        if(MainActivity.IsActive.equals(true)){
            wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
            wl.acquire();
        }
    }

    //-------------------------------------------Custome Action-------------------------------------

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
        //mAction.setTitle(R.string.display_poem);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //-------------------------------------------Initialize-----------------------------------------
    private void Initialize(){
            SQLclass sql=new SQLclass(ShowPoems.this);
        if(FavoriteActivity.redirectFromFavorite==false){
            try {
                poems = sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(), counter);
                CustomFontSizeForPoems adapter = new CustomFontSizeForPoems(ShowPoems.this, poems);
                list = (ListView) findViewById(R.id.PoemsShowList);
                list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                poems = sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(), counterFromFavorite);
                CustomFontSizeForPoems adapter = new CustomFontSizeForPoems(ShowPoems.this, poems);
                list = (ListView) findViewById(R.id.PoemsShowList);
                list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void ButtonInitialize(){
        Button next,prev;
        next=(Button)findViewById(R.id.next);
        prev=(Button)findViewById(R.id.prev);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }
    //----------------------------------------------Check if Liked ---------------------------------
    private Boolean isLiked(String table,int id){
        SQLclass sql=new SQLclass(ShowPoems.this);
        List<Integer> list=sql.getPoemID(table);
        int selectedID;
        for(int item: list){
            selectedID=item;
            if (selectedID==id){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
          Button btn=(Button)view;
          int id=btn.getId();
          SQLclass sql=new SQLclass(ShowPoems.this);
          setting=new Setting(ShowPoems.this);
          switch(id){
              case R.id.next:
                  try{
                     // sql.createDataBase();
                      counter++;
                      if(counter<=143){
                          poems=sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(),counter);
                          CustomFontSizeForPoems adapter=new CustomFontSizeForPoems(ShowPoems.this,poems);
                          list=(ListView)findViewById(R.id.PoemsShowList);
                          list.setAdapter(adapter);
                      }
                      else{
                          setting.Message(getString(R.string.next_faild));
                          counter--;
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
                  break;
              case R.id.prev:
                  try{
                     // sql.createDataBase();
                      counter--;
                      if(counter>0){
                          poems=sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(),counter);
                          CustomFontSizeForPoems adapter=new CustomFontSizeForPoems(ShowPoems.this,poems);
                          list=(ListView)findViewById(R.id.PoemsShowList);
                          list.setAdapter(adapter);
                      }
                      else{
                          setting.Message(getString(R.string.prev_faild));
                          counter++;
                      }
                  }catch(Exception e){
                      e.printStackTrace();
                  }
                  break;
          }
    }

    //------------------------------------- onBack Pressed Configuration------------------------------

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FavoriteActivity.setRedirectFromFavorite(false);
        setting=new Setting(ShowPoems.this);
        setting.OpenActivity(Poems.class);
        finish();
    }


//-----------------------------------------Release WakeLock On Pause----------------------------------

    @Override
    protected void onPause() {
        super.onPause();
        if(MainActivity.IsActive.equals(true)){
            wl.release();
        }
        finish();
    }

    //--------------------------------------------Return To Home Page Handle-------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(ShowPoems.this);
        switch(id){
            case R.id.action_home:
                FavoriteActivity.setRedirectFromFavorite(false);
                setting.OpenActivity(MainActivity.class);
                break;
            case android.R.id.home:
                setting.OpenActivity(Poems.class);
                break;
            case R.id.action_favorite:

                int poemID=0;
                if(FavoriteActivity.redirectFromFavorite){
                    poemID=counterFromFavorite;
                }
                else{
                    poemID=counter;
                }
                   // poemID++;
                setting=new Setting(ShowPoems.this);
                SQLclass sql=new SQLclass(ShowPoems.this);
                try {
                   // sql.createDataBase();
                    sql.openDataBase();
                    if(sql.isExists(poemID)){
                        sql.Delete_Favorite((long)poemID);
                        sql.close();
                        item.setIcon(R.drawable.unselected);
                        setting.Message(getString(R.string.already_favorite));
                    }
                    else{
                        sql.Insert_Favorite(poemID);
                        sql.close();
                        item.setIcon(R.drawable.heart);
                        setting.Message(getString(R.string.fav_added));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //-------------------------------------Share Button-------------------------------------------

            case R.id.btnshare:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String[] my_string = poems.toArray(new String[poems.size()]);
                String result ="";
                for(int i=0; i<poems.size(); i++){
                    result+=my_string[i].toString()+"\n";
                }
                intent.putExtra(Intent.EXTRA_SUBJECT,"اشعار فریدون مشیری");
                intent.putExtra(Intent.EXTRA_TEXT,result);
                this.startActivity(Intent.createChooser(intent, "اشتراک این شعر در :"));
                break;

            case R.id.btnsearch:
                setting=new Setting(ShowPoems.this);
                setting.OpenActivity(SearchActivity.class);
                break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_favorite,menu);
        return true;
    }
//--------------------------------------------Check Favorite if Exist then change favorite icon to red heart------------

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (FavoriteActivity.redirectFromFavorite==false){
            if (isLiked("Favorite",counter)){
                MenuItem item=menu.findItem(R.id.action_favorite);
                item.setIcon(R.drawable.heart);
            }
        }else{
            if (isLiked("Favorite",counterFromFavorite)){
                MenuItem item=menu.findItem(R.id.action_favorite);
                item.setIcon(R.drawable.heart);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }
}
