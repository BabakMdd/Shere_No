package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.FavoriteActivity;
import shereno.andishesaz.com.shere_nov1.MainActivity;
import shereno.andishesaz.com.shere_nov1.R;
import shereno.andishesaz.com.shere_nov1.ShowPoems;
import shereno.andishesaz.com.shere_nov1.WithSoundPoems;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by babak on 30/04/2017.
 */

public class FavoriteShowDetail extends AppCompatActivity {

    Setting setting=null;
    ActionBar mAction;
    List<String> poems;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(FavoriteShowDetail.this);
        setting.SetLocale("fa");
        setContentView(R.layout.favorite_show);
        customAction(FavoriteShowDetail.this);

        ListView lv=(ListView)findViewById(R.id.favoriteShow);
        SQLclass sql=new SQLclass(FavoriteShowDetail.this);

        try {
            sql.createDataBase();
            int id=FavoriteActivity.poemid;
            poems = sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(), id);
            CustomFontSizeForPoems adapter = new CustomFontSizeForPoems(FavoriteShowDetail.this, poems);
            lv.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setting=new Setting(FavoriteShowDetail.this);
        setting.OpenActivity(FavoriteActivity.class);
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
        mAction.setTitle(R.string.no_deklame_poems);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(FavoriteShowDetail.this);
        switch(id){
            case R.id.action_home:
                setting.OpenActivity(MainActivity.class);
                break;
            case android.R.id.home:
                setting.OpenActivity(FavoriteActivity.class);
                break;
        }
        return true;
    }
}
