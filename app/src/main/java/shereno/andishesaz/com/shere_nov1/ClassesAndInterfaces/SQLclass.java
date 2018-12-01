package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by babak on 10/02/2017.
 */

public class SQLclass extends SQLiteOpenHelper {

    public static String DB_PATH ;

    public static String DB_NAME = "Shere-No.sqlite";

    public static final int DB_VERSION = 3;

    public static final String TB_Books = "Books";

    public static final String TB_BooksDetails="BooksDetail";

    public static final String TB_Favorite="Favorite";

    public static final String TB_PoemDetails="PoemDetail";

    public static final String TB_Poems="Poems";

    public static final String TB_Setting="Settings";

    public static final String TB_Songs="SongsDetail";

    private SQLiteDatabase myDB;
    private Context context;

    public SQLclass(Context con){
        super(con,DB_NAME,null,DB_VERSION);
        this.context=con;
        DB_PATH="/data/data/" + context.getPackageName() + "/" + "databases/";
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("TAG","Database is Upgrading....");
        if(i1>i) {
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //-----------------------------------------Close Database---------------------------
    @Override
    public synchronized void close() {

        if (myDB!=null){
            myDB.close();
        }
        super.close();
    }

    //------------------------------------------Open Database----------------------------
    public void openDataBase() throws SQLException
    {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
    //------------------------------------------Show Details----------------------------
    public List<String> Books(){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TB_Books , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("BookName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }



    public List<String> Chapters(String TBL , int ID){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where BookID="+ID , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("ChapterName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    public List<String> PoemsList(String TBL , int ID){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where ChapterID="+ID , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    public List<String> DisplayPoems(String TBL , int ID){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where PoemID="+ID , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("LineOfPoem");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    public List<String> WithSound(String TBL){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where HasSong not in ('0')" , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }



    public void LoadSound(String TBL, int ID){
        byte[] AudioFile=null;
        byte[] buffer=new byte[1024];
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor c;
        FileOutputStream fos=null;
        try{
            String query="SELECT * FROM "+TBL+" where ID="+ID+" and IsLock=0";
            //c=db.query(TBL,new String[]{"ID","IsLock","Name"},"ID="+ID,new String[]{String.valueOf(ID)},null,null,null);
            c=db.rawQuery(query,null);
            if(c!=null){
                c.moveToFirst();
                AudioFile=c.getBlob(c.getColumnIndex("Name"));
                InputStream myInput = new ByteArrayInputStream (AudioFile);
                //String filePath=DB_PATH+"mus"+ID;
                //---------------------------------------Select System Music Folder To save Sound From Database---------------------
                File outputFilepath=android.os.Environment.getExternalStorageDirectory();
                File f=new File(outputFilepath.getAbsolutePath()+"/ShereNo_Music");
                f.mkdirs();
                File sound=new File(f,"music"+ID);
                String filePath=sound.getPath();
                fos = new FileOutputStream(filePath);
                int length;
                while ((length = myInput.read (buffer)) > 0)
                {
                    fos.write ( buffer, 0, length );
                }
                fos.flush();
                fos.close();
                myInput.close();
                c.close();
            }
            db.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//----------------------------------------------Search Query From Database-------------------------------------
    public List<String> SearchResult(String TBL,String item){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            //--------------------LIKE 'item%' is query that show Items that start with item----------------------
            c = db.rawQuery("SELECT DISTINCT * FROM " + TBL +" where LineOfPoem LIKE '%"+item+"%'" , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("LineOfPoem");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    //-----------------------------------Copy Database From Asset folder To Device--------------------

    public void copyDataBase() throws IOException
    {
        try
        {
            InputStream myInput = context.getAssets().open(DB_NAME);
            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while((length = myInput.read(buffer))>0)
            {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (Exception e)
        {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

    }

    //------------------------------------If Database doesent Exists on device Create New One------------------

    public boolean createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if (dbExist){
            getReadableDatabase();
            close();

            return true;
        }
        else
        {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                Log.e("tle99 - create", e.getMessage());
            }
            return false;
        }
    }
    //---------------------------------------Check Database----------------------------------------
    private boolean checkDataBase()
    {
        SQLiteDatabase tempDB = null;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch (SQLiteException e)
        {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
        {
            tempDB.close();
        }

        return tempDB != null ? true : false;
    }
    //---------------------------------------------Insert Into Database-----------------------------

    public long Insert_Favorite(int PoemID){
        ContentValues cv=new ContentValues();
        String poemid="PoemID";
        String song="HasSong";
        cv.put(poemid,PoemID);
        cv.put(song,0);
        return myDB.insert(TB_Favorite,null,cv);
    }

    public long Insert_Favorite_song(int PoemID){
        ContentValues cv=new ContentValues();
        String poemid="PoemID";
        String song="HasSong";
        cv.put(poemid,PoemID);
        cv.put(song,1);
        return myDB.insert(TB_Favorite,null,cv);
    }

    //---------------------------------------------Delete From Favorite Table-----------------------

    public int Delete_Favorite(Long id){
        String ID="PoemID";
        return myDB.delete(TB_Favorite,ID+"="+id,null);
    }

    //---------------------------------------------Read Favorite Table------------------------------

    public List<Integer> getPoemID(String TBL){
        List<Integer> listBooks = new ArrayList<Integer>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL , null);
            if(c == null){
                return null;
            }
            int name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemID");
            do
            {
                name = c.getInt(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    //------------------------------------------Favorite Show-----------------------------------------


    public List<String> deklame(String TBL , int num){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where HasSong not in ('0') and ID="+num, null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    public List<Integer> FavoriteShow(String TBL ,String Name){
        List<Integer> listBooks = new ArrayList<Integer>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM "+ TBL +" where PoemName="+"'"+Name+"'", null);
            if(c == null){
                return null;
            }
            int name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("ID");
            do
            {
                name = c.getInt(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    public List<String> NoDeklame(String TBL, int num ){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM " + TBL +" where HasSong=0 and ID="+num , null);
            if(c == null){
                return null;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemName");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());
            c.close();
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return listBooks;
    }

    //-------------------------------------Check ID If Exists then return false----------------------------

    public boolean isExists(int id){
        List<String> listBooks = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c;
        try
        {
            c = db.rawQuery("SELECT * FROM Favorite where PoemID="+id  , null);
            if(c == null){
                return false;
            }
            String name;
            c.moveToFirst();
            int Colindex=c.getColumnIndex("PoemID");
            do
            {
                name = c.getString(Colindex);
                listBooks.add(name);
            } while (c.moveToNext());

            if(listBooks.size()==0){
                return false;
            }else{
                c.close();
                return true;
            }
        }
        catch (Exception e)
        {
            Log.e("tle99", e.getMessage());
        }
        db.close();
        return false;
    }


}
