package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import shereno.andishesaz.com.shere_nov1.R;

import static android.R.attr.path;

/**
 * Created by babak on 23/04/2017.
 */

public class CustomDialog extends Dialog implements View.OnClickListener {

    private Context con;
    private Dialog d;
    private Button yes;
    SeekBar deklameSEEK,backSEEK;
    private AudioManager audio=null;
    private MediaPlayer mp1,mp2;
    public static float volume1,volume2;
    File file,myData,firstTime;

    public CustomDialog(Context context) {
        super(context);
        this.con=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //-------------------------------------------------For Handle Audio----------------------------------
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        setContentView(R.layout.custom_dialog);
        Initialize();
        String volumeResult=ReadFirstVolume();
        String secondVolumeResult=ReadSecondVolume();
        if(volumeResult!="null" && secondVolumeResult!="null") {
            volume1 = Float.parseFloat(volumeResult);
            volume2 = Float.parseFloat(secondVolumeResult);
            mp1.setVolume(volume1, volume1);
            mp2.setVolume(volume2, volume2);
        }
    }

    //------------------------------------Create Getter and Setter methods for MediaPlayer------------------
    public void setMainMedia(MediaPlayer mp){
        this.mp1=mp;
    }
    public void setBackMedia(MediaPlayer mp){
        this.mp2=mp;
    }
    public MediaPlayer getMainMedia(){
        return this.mp1;
    }
    public MediaPlayer getBackMedia(){
        return this.mp2;
    }

    //------------------------------------Control Initialize-------------------------------------------------
    private void Initialize(){
        yes=(Button)findViewById(R.id.yes_dialog);
        deklameSEEK=(SeekBar)findViewById(R.id.deklame_volume);
        backSEEK=(SeekBar)findViewById(R.id.background_volume);
        yes.setOnClickListener(this);

        //---------------------------------------Handle Decrease or Increase Volume ---------------------------
        try {
            audio = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
            deklameSEEK.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            deklameSEEK.setProgress(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
            deklameSEEK.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    //audio.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                    volume1 = (float) (1 - (Math.log(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) - i) / Math.log(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC))));
                    mp1.setVolume(volume1, volume1);
                    //mp1.start();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {  //nothing to do
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { // nothing to do
                }
            });

            backSEEK.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            backSEEK.setProgress(audio.getStreamVolume(AudioManager.STREAM_MUSIC));
            backSEEK.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    //audio.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                    volume2 = (float) (1 - (Math.log(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC) - i) / Math.log(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC))));
                    mp2.setVolume(volume2, volume2);
                   // mp2.start();
                }
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { // nothing to do
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { // nothing to do
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Button btn=(Button)view;
        int id=btn.getId();
        switch (id){
            case R.id.yes_dialog:
                dismiss();
                WriteVolume();
                break;
        }
    }

    //------------------------Write Custom Volume into the file ----------------------------------

    private void WriteVolume(){
        File root=android.os.Environment.getExternalStorageDirectory();
        file=new File(root.getAbsolutePath()+"/ShereNo");
        file.mkdirs();
        myData=new File(file,"Volume1.txt");
        firstTime=new File(file,"Volume2.txt");
        if(file.exists()){
            myData.delete();
            FileOutputStream fs= null;
            try {
                fs = new FileOutputStream(myData,true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter writer=new PrintWriter(fs);
            writer.println(volume1);
            writer.flush();
            writer.close();
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } if(file.exists()){
            firstTime.delete();
            FileOutputStream fs= null;
            try {
                fs = new FileOutputStream(firstTime,true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            PrintWriter writer=new PrintWriter(fs);
            writer.println(volume2);
            writer.flush();
            writer.close();
            try {
                fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------------------Read Volume Content----------------------------------

    private String ReadFirstVolume(){
        File sdcard =android.os.Environment.getExternalStorageDirectory();
        //Get the text file
        File file = new File(sdcard.getAbsolutePath()+"/ShereNo");
        File readable=new File(file,"Volume1.txt");
        StringBuilder text=null;
        //Read text from file
        if(readable.exists()) {
            text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(readable));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = String.valueOf(text);
        return result;
    }

    private String ReadSecondVolume(){
        File sdcard =android.os.Environment.getExternalStorageDirectory();
        //Get the text file
        File file = new File(sdcard.getAbsolutePath()+"/ShereNo");
        File readable=new File(file,"Volume2.txt");
        StringBuilder text=null;
        //Read text from file
        if(readable.exists()) {
            text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(readable));
                String line;
                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String result = String.valueOf(text);
        return result;
    }
}
