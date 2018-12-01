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
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomizeChapter;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.ExpandableCustomAdapter;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Poems extends AppCompatActivity {

    ActionBar mAction;
    Setting setting=null;
    List<String> chapters;
    public static String result;
    Point p;
    ExpandableListView exp;
    HashMap<String,List<String>> dataChildList;
    List<String> dataHeader;
    int previousGroup=-1;
    public static int IDs ;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(Poems.this);
        setting.SetLocale("fa");
        p=setting.getScreenSize();
        setContentView(R.layout.expandable_list);
            customAction(Poems.this);
            expListInitilize(1);
            expListInitilize(2);
            expListInitilize(3);
            expListInitilize(4);
            expListInitilize(5);
            expListInitilize(6);
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
        mAction.setTitle(R.string.no_deklame_poems);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //---------------------------------------Initialize Expandable Listview in this activity------------------------

    private void expListInitilize(int ID){
        exp=(ExpandableListView)findViewById(R.id.expList);
        SQLclass sql=new SQLclass(Poems.this);
        // Adding child data
        try{
            //sql.createDataBase();
            if(ListOfPoems.result.toString().equals("گناه دریا") && ID==1) {
                dataHeader = sql.Chapters(SQLclass.TB_BooksDetails.toString(), ID);
                dataChildList = new HashMap<String, List<String>>();
                List<String> child_content1 = new ArrayList<String>();
                child_content1 = sql.PoemsList(SQLclass.TB_Poems.toString(), 1);
                TextView tv = (TextView) findViewById(R.id.chapterHeader);
                String text = " فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                dataChildList.put(dataHeader.get(0), child_content1);
                final ExpandableCustomAdapter adapter = new ExpandableCustomAdapter(Poems.this, dataHeader, dataChildList);
                exp.setAdapter(adapter);

                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup = -1;
                        } else {
                            exp.expandGroup(i, true);
                            if (previousGroup != -1) {
                                exp.expandGroup(previousGroup, true);
                            }
                            previousGroup = i;
                        }
                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=1;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=2;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=3;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=4;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=5;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=6;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=7;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=8;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=9;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=10;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                            }
                        }
                        return true;
                    }
                });


            }else if(ListOfPoems.result.toString().equals("تشنه طوفان") && ID==2){
                dataHeader=sql.Chapters(SQLclass.TB_BooksDetails.toString(),ID);
                dataChildList=new HashMap<String,List<String>>();
                List<String> child_content=new ArrayList<String>();
                child_content=sql.PoemsList(SQLclass.TB_Poems.toString(), 2);
                TextView tv=(TextView)findViewById(R.id.chapterHeader);
                String text=" فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                for(int i=0;i<dataHeader.size();i++){
                    dataChildList.put(dataHeader.get(i),child_content);
                }
                final ExpandableCustomAdapter adapter=new ExpandableCustomAdapter(Poems.this,dataHeader,dataChildList);
                exp.setAdapter(adapter);
                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup=-1;
                        } else {
                            exp.expandGroup(i,true);
                            if(previousGroup!=-1){
                                exp.expandGroup(previousGroup,true);
                            }
                            previousGroup=i;
                        }

                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=11;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=12;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=13;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=14;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=15;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=16;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=17;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=18;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=19;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=20;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                            }
                        }
                        return true;
                    }
                });
            }else if(ListOfPoems.result.toString().equals("ابرو کوچه") && ID==3){
                dataHeader=sql.Chapters(SQLclass.TB_BooksDetails.toString(),ID);
                dataChildList=new HashMap<String,List<String>>();
                List<String> child_content=new ArrayList<String>();
                child_content=sql.PoemsList(SQLclass.TB_Poems.toString(), 3);
                TextView tv=(TextView)findViewById(R.id.chapterHeader);
                String text=" فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                for(int i=0;i<dataHeader.size();i++){
                    dataChildList.put(dataHeader.get(i),child_content);
                }
                final ExpandableCustomAdapter adapter=new ExpandableCustomAdapter(Poems.this,dataHeader,dataChildList);
                exp.setAdapter(adapter);
                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup=-1;
                        } else {
                            exp.expandGroup(i,true);
                            if(previousGroup!=-1){
                                exp.expandGroup(previousGroup,true);
                            }
                            previousGroup=i;
                        }

                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=21;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=22;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=23;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=24;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=25;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=26;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=27;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=28;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=29;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=30;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 10:
                                    IDs=31;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 11:
                                    IDs=32;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 12:
                                    IDs=33;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 13:
                                    IDs=34;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 14:
                                    IDs=35;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 15:
                                    IDs=36;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 16:
                                    IDs=37;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 17:
                                    IDs=38;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 18:
                                    IDs=39;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 19:
                                    IDs=40;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                            }
                        }
                        return true;
                    }
                });
            }
            else if(ListOfPoems.result.toString().equals("بهار را باور کن") && ID==4){
                dataHeader=sql.Chapters(SQLclass.TB_BooksDetails.toString(),ID);
                dataChildList=new HashMap<String,List<String>>();
                List<String> child_content=new ArrayList<String>();
                child_content=sql.PoemsList(SQLclass.TB_Poems.toString(), 4);
                TextView tv=(TextView)findViewById(R.id.chapterHeader);
                String text=" فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                for(int i=0;i<dataHeader.size();i++){
                    dataChildList.put(dataHeader.get(i),child_content);
                }
                final ExpandableCustomAdapter adapter=new ExpandableCustomAdapter(Poems.this,dataHeader,dataChildList);
                exp.setAdapter(adapter);
                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup=-1;
                        } else {
                            exp.expandGroup(i,true);
                            if(previousGroup!=-1){
                                exp.expandGroup(previousGroup,true);
                            }
                            previousGroup=i;
                        }

                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=41;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=42;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=43;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=44;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=45;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=46;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=47;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=48;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=49;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=50;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 10:
                                    IDs=51;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 11:
                                    IDs=52;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 12:
                                    IDs=53;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 13:
                                    IDs=54;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 14:
                                    IDs=55;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 15:
                                    IDs=56;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 16:
                                    IDs=57;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 17:
                                    IDs=58;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 18:
                                    IDs=59;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 19:
                                    IDs=60;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 20:
                                    IDs=61;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                            }
                        }
                        return true;
                    }
                });
            }
            else if(ListOfPoems.result.toString().equals("آه باران") && ID==5){
                dataHeader=sql.Chapters(SQLclass.TB_BooksDetails.toString(),ID);
                dataChildList=new HashMap<String,List<String>>();
                List<String> child_content=new ArrayList<String>();
                child_content=sql.PoemsList(SQLclass.TB_Poems.toString(), 5);
                TextView tv=(TextView)findViewById(R.id.chapterHeader);
                String text=" فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                for(int i=0;i<dataHeader.size();i++){
                    dataChildList.put(dataHeader.get(i),child_content);
                }
                final ExpandableCustomAdapter adapter=new ExpandableCustomAdapter(Poems.this,dataHeader,dataChildList);
                exp.setAdapter(adapter);
                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup=-1;
                        } else {
                            exp.expandGroup(i,true);
                            if(previousGroup!=-1){
                                exp.expandGroup(previousGroup,true);
                            }
                            previousGroup=i;
                        }

                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=62;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=63;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=64;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=65;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=66;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=67;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=68;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=69;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=70;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=71;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 10:
                                    IDs=72;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 11:
                                    IDs=73;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 12:
                                    IDs=74;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 13:
                                    IDs=75;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 14:
                                    IDs=76;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 15:
                                    IDs=77;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 16:
                                    IDs=78;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 17:
                                    IDs=79;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 18:
                                    IDs=80;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 19:
                                    IDs=81;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 20:
                                    IDs=82;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 21:
                                    IDs=83;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 22:
                                    IDs=84;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 23:
                                    IDs=85;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 24:
                                    IDs=86;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 25:
                                    IDs=87;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 26:
                                    IDs=88;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 27:
                                    IDs=89;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 28:
                                    IDs=90;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 29:
                                    IDs=91;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 30:
                                    IDs=92;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 31:
                                    IDs=93;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 32:
                                    IDs=94;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 33:
                                    IDs=95;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 34:
                                    IDs=96;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 35:
                                    IDs=97;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 36:
                                    IDs=98;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 37:
                                    IDs=99;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 38:
                                    IDs=100;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 39:
                                    IDs=101;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 40:
                                    IDs=102;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 41:
                                    IDs=103;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                            }
                        }
                        return true;
                    }
                });
            }
            else if(ListOfPoems.result.toString().equals("از خاموشی") && ID==6){
                dataHeader=sql.Chapters(SQLclass.TB_BooksDetails.toString(),ID);
                dataChildList=new HashMap<String,List<String>>();
                List<String> child_content=new ArrayList<String>();
                child_content=sql.PoemsList(SQLclass.TB_Poems.toString(), 6);
                TextView tv=(TextView)findViewById(R.id.chapterHeader);
                String text=" فصل های کتاب " + ListOfPoems.result.toString();
                tv.setText(text);
                for(int i=0;i<dataHeader.size();i++){
                    dataChildList.put(dataHeader.get(i),child_content);
                }
                final ExpandableCustomAdapter adapter=new ExpandableCustomAdapter(Poems.this,dataHeader,dataChildList);
                exp.setAdapter(adapter);
                //-------------------------------set Previous group-----------------------------
                exp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                        // We call collapseGroupWithAnimation(int) and
                        // expandGroupWithAnimation(int) to animate group
                        // expansion/collapse.
                        if (exp.isGroupExpanded(i)) {
                            exp.collapseGroup(i);
                            previousGroup=-1;
                        } else {
                            exp.expandGroup(i,true);
                            if(previousGroup!=-1){
                                exp.expandGroup(previousGroup,true);
                            }
                            previousGroup=i;
                        }

                        return true;
                    }
                });

                exp.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                        int child_ID=(int)adapter.getChildId(i,i1);
                        setting=new Setting(Poems.this);
                        if(i==0){
                            switch(child_ID){
                                case 0:
                                    IDs=104;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 1:
                                    IDs=105;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 2:
                                    IDs=106;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 3:
                                    IDs=107;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 4:
                                    IDs=108;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 5:
                                    IDs=109;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 6:
                                    IDs=110;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 7:
                                    IDs=111;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 8:
                                    IDs=112;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 9:
                                    IDs=113;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 10:
                                    IDs=114;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 11:
                                    IDs=115;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 12:
                                    IDs=116;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 13:
                                    IDs=117;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 14:
                                    IDs=118;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 15:
                                    IDs=119;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 16:
                                    IDs=120;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 17:
                                    IDs=121;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 18:
                                    IDs=122;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 19:
                                    IDs=123;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 20:
                                    IDs=124;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 21:
                                    IDs=125;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 22:
                                    IDs=126;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 23:
                                    IDs=127;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 24:
                                    IDs=128;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 25:
                                    IDs=129;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 26:
                                    IDs=130;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 27:
                                    IDs=131;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 28:
                                    IDs=132;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 29:
                                    IDs=133;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 30:
                                    IDs=134;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 31:
                                    IDs=135;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 32:
                                    IDs=136;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 33:
                                    IDs=137;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);
                                    break;
                                case 34:
                                    IDs=138;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                                case 35:
                                    IDs=139;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                                case 36:
                                    IDs=140;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                                case 37:
                                    IDs=141;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                                case 38:
                                    IDs=142;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                                case 39:
                                    IDs=143;
                                    FavoriteActivity.setRedirectFromFavorite(false);
                                    setting.OpenActivity(ShowPoems.class);

                                    break;
                            }
                        }
                        return true;
                    }
                });
            }
        }catch(Exception e){
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
        setting=new Setting(Poems.this);
        switch(id){
            case R.id.action_home:
                setting.OpenActivity(MainActivity.class);
                break;
            case android.R.id.home:
                setting.OpenActivity(ListOfPoems.class);
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
