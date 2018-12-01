package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.ListOfPoems;
import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 12/02/2017.
 */

public class CustomizeChapter extends ArrayAdapter<String> {

    private Context con;
    private List<String> chapters;
    LayoutInflater inflate=null;

    public CustomizeChapter(Context context, List<String> chapters) {
        super(context, R.layout.custom_chapters,chapters);

        this.chapters=chapters;
        this.con=context;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //---------------------------------customize view------------------------------------
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=new ViewHolder();
        convertView=inflate.inflate(R.layout.custom_chapters,parent,false);
        holder.chapterTitle=(TextView)convertView.findViewById(R.id.chapterTitle);
        holder.chapterDetail=(TextView)convertView.findViewById(R.id.chapterDetail);

        holder.chapterTitle.setText(chapters.get(position));
        if(ListOfPoems.result.toString().equals("پرواز با خورشید")){
            holder.chapterDetail.setText(R.string.bookdetail);
        }
        else if(ListOfPoems.result.toString().equals("آه باران")){
            holder.chapterDetail.setText(R.string.bookdetail_rain);
        }


        return convertView;

    }

    public class ViewHolder{
        TextView chapterTitle;
        TextView chapterDetail;
    }
}
