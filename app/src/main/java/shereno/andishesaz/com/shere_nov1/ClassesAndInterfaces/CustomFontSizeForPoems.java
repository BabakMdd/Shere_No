package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.MainActivity;
import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 21/04/2017.
 */

public class CustomFontSizeForPoems extends ArrayAdapter<String> {

    Context con;
    private List<String> list;
    LayoutInflater inflate=null;
    public CustomFontSizeForPoems(Context context, List<String> list) {
        super(context, R.layout.custom_font_size,list);
        this.con=context;
        this.list=list;
        inflate=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();
        convertView=inflate.inflate(R.layout.custom_font_size,parent,false);
        holder.Poems=(TextView)convertView.findViewById(R.id.PoemContent);
        holder.Poems.setText(list.get(position));
        holder.Poems.setTextColor(Color.WHITE);
        String fontResult= MainActivity.selectedSize;
        String fontType=MainActivity.selectedFont;
        Typeface face;
        if(fontResult.equals("کوچک")){
            holder.Poems.setTextSize(12);
            if (fontType.equals("آرمیتا")){
               face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Armita.Black.ttf");
                holder.Poems.setTypeface(face);
            }
            else if (fontType.equals("میترا")){
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Mitra.ttf");
                holder.Poems.setTypeface(face);
            }
            else{
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/Sans.ttf");
                holder.Poems.setTypeface(face);
            }
        }
        else if(fontResult.equals("متوسط")){
            holder.Poems.setTextSize(14);
            if (fontType.equals("آرمیتا")){
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Armita.Black.ttf");
                holder.Poems.setTypeface(face);
            }
            else if (fontType.equals("میترا")){
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Mitra.ttf");
                holder.Poems.setTypeface(face);
            }
            else{
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/Sans.ttf");
                holder.Poems.setTypeface(face);
            }
        }
        else {
            holder.Poems.setTextSize(17);
            if (fontType.equals("آرمیتا")){
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Armita.Black.ttf");
                holder.Poems.setTypeface(face);
            }
            else if (fontType.equals("میترا")){
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/A.Mitra.ttf");
                holder.Poems.setTypeface(face);
            }
            else{
                face=Typeface.createFromAsset(getContext().getAssets(),"Fonts/Sans.ttf");
                holder.Poems.setTypeface(face);
            }
        }
        return convertView;
    }

    //--------------------------------Holder Class-----------------------------------------------
    public class ViewHolder{
        TextView Poems;
    }
}
