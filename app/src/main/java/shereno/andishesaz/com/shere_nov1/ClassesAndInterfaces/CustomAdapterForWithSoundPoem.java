package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 09/07/2017.
 */

public class CustomAdapterForWithSoundPoem extends ArrayAdapter<String> {

    Context con;
    private List<String> list;
    LayoutInflater inflate=null;
    public CustomAdapterForWithSoundPoem(Context context, List<String> list) {
        super(context, R.layout.custom_font_size,list);
        this.con=context;
        this.list=list;
        inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        convertView=inflate.inflate(R.layout.custom_font_size,parent,false);
        holder.text=(TextView)convertView.findViewById(R.id.PoemContent);
        holder.text.setTextColor(Color.WHITE);
        holder.text.setTextSize(14);
        holder.text.setText(list.get(position));

        return convertView;
    }

    private class Holder{
        TextView text;
    }
}
