package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class BioGraphyActivity extends AppCompatActivity {

    ActionBar mAction;
    Setting setting=null;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(BioGraphyActivity.this);
        setting.SetLocale("fa");
        setContentView(R.layout.activity_bio_graphy);
        customAction(BioGraphyActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(BioGraphyActivity.this);
        switch (id){
            case android.R.id.home:
                setting.OpenActivity(MainActivity.class);
                break;
        }
        return true;
    } */

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
        mAction.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM | android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME | android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP );
        mAction.setTitle(R.string.biography);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }
}
