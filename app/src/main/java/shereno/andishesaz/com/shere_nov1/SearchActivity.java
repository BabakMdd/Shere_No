package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.ListViewWhiteText;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    ActionBar mAction;
    Setting setting=null;
    Button search;
    EditText searchItem;
    ListView result;
    List<String> search_Result;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(SearchActivity.this);
        setting.SetLocale("fa");
        setContentView(R.layout.activity_search);
        customAction(SearchActivity.this);
        Initialize();
        //setTheme(R.style.AppTheme_WhiteText);
    }

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
        mAction.setTitle(R.string.search);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //------------------------------------------Initialize Components--------------------------------------

    private void Initialize(){
        search=(Button)findViewById(R.id.search_btn);
        searchItem=(EditText)findViewById(R.id.search_item);
        result=(ListView)findViewById(R.id.search_result);
        search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button btn=(Button)view;
        int id=btn.getId();
        if(id==R.id.search_btn){
            try {
                SQLclass sql = new SQLclass(SearchActivity.this);
                //sql.createDataBase();
                String title=searchItem.getText().toString();
                if(title.equals("")){
                    setting=new Setting(SearchActivity.this);
                    setting.Message(getString(R.string.empty_item));
                }else{
                    search_Result=sql.SearchResult("PoemDetail",title);
                    if(search_Result.size()>0){
                        result.setVisibility(View.VISIBLE);
                        ListViewWhiteText adapter=new ListViewWhiteText(SearchActivity.this,search_Result);
                        result.setAdapter(adapter);
                    }
                    else{
                        setting=new Setting(SearchActivity.this);
                        setting.Message(getString(R.string.empty_area));
                    }
                }

            }catch(Exception e){
                e.printStackTrace();
            }

        }
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
}
