package shereno.andishesaz.com.shere_nov1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.SeekBar;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;

import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomDialog;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.CustomFontSizeForPoems;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.SQLclass;
import shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces.Setting;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PlaySoundWithPoems extends AppCompatActivity implements View.OnClickListener {
    Setting setting=null;
    ActionBar mAction;
    List<String> lines;
    SeekBar seek;
    WakeLock wl;
    Boolean bool=true;
    Button play,next,prev;
    int poemIDs=1;
    int soundID;
    Boolean isStart=true;
    //----------------------------Handler For Sync SeekBar ----------------------------------
    android.os.Handler seek_handler=new android.os.Handler();
    MediaPlayer player,back;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setting=new Setting(PlaySoundWithPoems.this);
        setting.SetLocale("fa");
            setContentView(R.layout.activity_play_sound_with_poems);
            customAction(PlaySoundWithPoems.this);
            InitializeButton();
            if(FavoriteActivity.redirectFromFavorite){
                playSound();
            }else {
                if (WithSoundPoems.withSound.equals("اگر جام بشکست")) {

                    Initialize(1, 21);
                    soundID = 1;
                    TurnOnorOff();
                    if (MainActivity.selectedBack.equals("پاییز")) {
                        Background(R.raw.paeez);
                    } else if (MainActivity.selectedBack.equals("زمستان")) {
                        Background(R.raw.zemestan);
                    } else if (MainActivity.selectedBack.equals("بهار")) {
                        Background(R.raw.bahar);
                    } else {
                        Background(R.raw.barg);
                    }
                } else if (WithSoundPoems.withSound.equals("جادوی بی اثر")) {
                    Initialize(2, 50);
                    soundID = 2;
                    TurnOnorOff();
                    if (MainActivity.selectedBack.equals("پاییز")) {
                        Background(R.raw.paeez);
                    } else if (MainActivity.selectedBack.equals("زمستان")) {
                        Background(R.raw.zemestan);
                    } else if (MainActivity.selectedBack.equals("بهار")) {
                        Background(R.raw.bahar);
                    } else {
                        Background(R.raw.barg);
                    }
                } else if (WithSoundPoems.withSound.equals("دیوار")) {
                    Initialize(3, 53);
                    soundID = 3;
                    TurnOnorOff();
                    if (MainActivity.selectedBack.equals("پاییز")) {
                        Background(R.raw.paeez);
                    } else if (MainActivity.selectedBack.equals("زمستان")) {
                        Background(R.raw.zemestan);
                    } else if (MainActivity.selectedBack.equals("بهار")) {
                        Background(R.raw.bahar);
                    } else {
                        Background(R.raw.barg);
                    }
                }
                Seekupdation();
            }
    }

    //----------------------------------------------Check if Liked ---------------------------------
    private Boolean isLiked(String table,int id){
        SQLclass sql=new SQLclass(PlaySoundWithPoems.this);
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
    //------------------------------------------Set WakeLock To Stay Activity Awake-----------------
    private void TurnOnorOff(){
        PowerManager pm=(PowerManager)getSystemService(Context.POWER_SERVICE);
        if(MainActivity.IsActive.equals(true)){
            wl=pm.newWakeLock(PowerManager.FULL_WAKE_LOCK,"Stay Awake");
            wl.acquire();
        }
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
        mAction.setTitle(R.string.app_name);
        android.support.v7.app.ActionBar.LayoutParams params=new android.support.v7.app.ActionBar.LayoutParams(android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT, android.support.v7.app.ActionBar.LayoutParams.FILL_PARENT);
        mAction.setCustomView(view);
    }

    //-----------------------------------------Initiialize Buttons---------------------------------------------

    private void InitializeButton(){
        play=(Button)findViewById(R.id.play);
        next=(Button)findViewById(R.id.nextSound);
        prev=(Button)findViewById(R.id.prevSound);

        play.setTextColor(Color.WHITE);
        play.setText("||");
        next.setTextColor(Color.WHITE);
        next.setText(">>");
        prev.setTextColor(Color.WHITE);
        prev.setText("<<");

        play.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
    }

    //------------------------------------------Play Sound and Show Text --------------------------------------------
    private void Initialize(int ID,int PoemID){
        SQLclass sql=new SQLclass(PlaySoundWithPoems.this);
        try{
            lines=sql.DisplayPoems(SQLclass.TB_PoemDetails.toString(),PoemID);
            ListView list=(ListView)findViewById(R.id.soundlist);
            CustomFontSizeForPoems adapter=new CustomFontSizeForPoems(PlaySoundWithPoems.this,lines);
            list.setAdapter(adapter);
            sql.LoadSound(SQLclass.TB_Songs.toString(),ID);
            File outputFilepath=android.os.Environment.getExternalStorageDirectory();
            File f=new File(outputFilepath.getAbsolutePath()+"/ShereNo_Music");
            File sound=new File(f,"music"+ID);
            String path=sound.getPath();
            player=new MediaPlayer();
            player.setDataSource(path);
            player.prepare();
            player.start();
            sound.delete();
            //music.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //------------------------------Use Runable To manage Seek  (Runnable is like Thread)----------------------------------
    Runnable run=new Runnable() {
        @Override
        public void run() {
            Seekupdation();
        }
    };
    //-------------------------------Handle with Seekbar----------------------------------------
    private void Seekupdation(){
        final TextView start=(TextView)findViewById(R.id.soundtime);
        final TextView end=(TextView)findViewById(R.id.totaltime);
        long totaltime=player.getDuration();
        long currenttime=player.getCurrentPosition();
        end.setText(milliSecondsToTimer(totaltime));
        start.setText(milliSecondsToTimer(currenttime));
        seek=(SeekBar)findViewById(R.id.seek);
        seek.setMax(player.getDuration());
        seek.setProgress(player.getCurrentPosition());
        seek_handler.postDelayed(run,500);
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(b){
                    player.seekTo(i);
                    seek.setProgress(i);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    //-------------------------------------------Background music Handeled-------------------------
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        if (hours > 0) {
            finalTimerString = hours + ":";
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        }   else {
            secondsString = "" + seconds;
        }
        finalTimerString = finalTimerString + minutes + ":" + secondsString;
        return finalTimerString;
    }

    private void Background(int ID){
        back=MediaPlayer.create(getApplicationContext(),ID);
        back.start();
    }
    //-------------------------------------------When BackPressed and onPause Media Should Stopped------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
        back.stop();
        FavoriteActivity.setRedirectFromFavorite(false);
        setting=new Setting(PlaySoundWithPoems.this);
        setting.OpenActivity(WithSoundPoems.class);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        back.stop();
        if(MainActivity.IsActive.equals(true)){
            wl.release();
        }
        finish();
    }

    //--------------------------------------------Return To Home Page Handle-------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        setting=new Setting(PlaySoundWithPoems.this);
        switch(id){
            case R.id.action_home:
                FavoriteActivity.setRedirectFromFavorite(false);
                setting.OpenActivity(MainActivity.class);
                break;
            case android.R.id.home:
                setting.OpenActivity(WithSoundPoems.class);
                break;
            case R.id.action_favorite:
                int poemID=0;
                if (FavoriteActivity.redirectFromFavorite){
                   poemID=FavoriteActivity.sID;
                }
                else{
                    poemID=WithSoundPoems.pID;
                }
                setting=new Setting(PlaySoundWithPoems.this);
                SQLclass sql=new SQLclass(PlaySoundWithPoems.this);
                try {
                    // sql.createDataBase();
                    sql.openDataBase();
                    if(sql.isExists(poemID)){
                        sql.Delete_Favorite((long)poemID);
                        sql.close();
                        item.setIcon(R.drawable.unselected);
                        setting.Message(getString(R.string.already_favorite));
                    }else{
                        sql.Insert_Favorite_song(poemID);
                        sql.close();
                        setting.Message(getString(R.string.fav_added));
                        item.setIcon(R.drawable.heart);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.volume:
                CustomDialog dialog=new CustomDialog(PlaySoundWithPoems.this);
                //-------------------------------Fill get and set For Media----------------------------
                dialog.setMainMedia(player);
                dialog.setBackMedia(back);
                dialog.getMainMedia();
                dialog.getBackMedia();
                dialog.show();
                break;

            //------------------------------------Share Button------------------------------------------
            case R.id.btnshare:
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String[] my_string = lines.toArray(new String[lines.size()]);
                String result ="";
                for(int i=0; i<lines.size(); i++){
                    result+=my_string[i].toString()+"\n";
                }
                intent.putExtra(Intent.EXTRA_SUBJECT,"اشعار فریدون مشیری");
                intent.putExtra(Intent.EXTRA_TEXT,result);
                this.startActivity(Intent.createChooser(intent, "اشتراک این شعر در :"));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_favorite_volume,menu);
        return true;
    }

    //-----------------------------------------------Check Item If Liked change icon to Red----------------
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (FavoriteActivity.redirectFromFavorite==false){
            if(isLiked("Favorite",WithSoundPoems.pID)){
                MenuItem item=menu.findItem(R.id.action_favorite);
                item.setIcon(R.drawable.heart);
            }
        }else{
            if(isLiked("Favorite",FavoriteActivity.sID)){
                MenuItem item=menu.findItem(R.id.action_favorite);
                item.setIcon(R.drawable.heart);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    //-------------------------------------OnClick For Next Play Pause and Prev Sound--------------------
    @Override
    public void onClick(View view) {
       Button btn=(Button)view;
        int id=btn.getId();
        switch (id){
            case R.id.play:
                if(isStart){
                    player.pause();
                    back.pause();
                    play.setText(">");
                    isStart=false;
                }
                else{
                    player.start();
                    back.start();
                    play.setText("||");
                    isStart=true;
                }

                break;
            case R.id.nextSound:
                player.stop();
                back.stop();
                soundID++;
                if(soundID<4){
                    if(soundID==1){
                        Initialize(1,21);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                    else if(soundID==2){
                        Initialize(2,50);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                    else{
                        Initialize(3,53);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                }
                else{
                    setting=new Setting(PlaySoundWithPoems.this);
                    setting.Message(getString(R.string.next_faild));
                }
                break;
            case R.id.prevSound:
                player.stop();
                back.stop();
                soundID--;
                if(soundID>1){
                    if(soundID==1){
                        Initialize(1,21);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                    else if(soundID==2){
                        Initialize(2,50);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                    else{
                        Initialize(3,53);
                        if(MainActivity.selectedBack.equals("پاییز"))
                        {
                            Background(R.raw.paeez);
                        }else if(MainActivity.selectedBack.equals("زمستان")){
                            Background(R.raw.zemestan);
                        }else if(MainActivity.selectedBack.equals("بهار")){
                            Background(R.raw.bahar);
                        }else{
                            Background(R.raw.barg);
                        }
                    }
                }
                else{
                    setting=new Setting(PlaySoundWithPoems.this);
                    setting.Message(getString(R.string.prev_faild));
                }
                break;
        }

    }

    //------------------------------Call This Method when Redirected From Favorite--------------------------

    private void playSound(){
         int soundID=FavoriteActivity.sID;
        switch (soundID){
            case 21:
               Initialize(1,21);
                TurnOnorOff();
                if(MainActivity.selectedBack.equals("پاییز"))
                {
                    Background(R.raw.paeez);
                }else if(MainActivity.selectedBack.equals("زمستان")){
                    Background(R.raw.zemestan);
                }else if(MainActivity.selectedBack.equals("بهار")){
                    Background(R.raw.bahar);
                }else{
                    Background(R.raw.barg);
                }
                Seekupdation();
                break;
            case 50:
                Initialize(2,50);
                TurnOnorOff();
                if(MainActivity.selectedBack.equals("پاییز"))
                {
                    Background(R.raw.paeez);
                }else if(MainActivity.selectedBack.equals("زمستان")){
                    Background(R.raw.zemestan);
                }else if(MainActivity.selectedBack.equals("بهار")){
                    Background(R.raw.bahar);
                }else{
                    Background(R.raw.barg);
                }
                Seekupdation();
                break;
            case 53:
                Initialize(3,53);
                TurnOnorOff();
                if(MainActivity.selectedBack.equals("پاییز"))
                {
                    Background(R.raw.paeez);
                }else if(MainActivity.selectedBack.equals("زمستان")){
                    Background(R.raw.zemestan);
                }else if(MainActivity.selectedBack.equals("بهار")){
                    Background(R.raw.bahar);
                }else{
                    Background(R.raw.barg);
                }
                Seekupdation();
                break;
        }
    }
}
