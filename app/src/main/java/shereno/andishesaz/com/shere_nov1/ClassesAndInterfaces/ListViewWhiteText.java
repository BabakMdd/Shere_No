package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 30/03/2017.
 */

public class ListViewWhiteText extends ArrayAdapter<String> {

    Context con;
    List<String> list;
    LayoutInflater inflate=null;

    public ListViewWhiteText(Context con, List<String> list){
        super(con, R.layout.customize_books,list);
        this.con=con;
        this.list=list;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //--------------------------------------------Holder Class--------------------------------------
    private class Holder{
        TextView text;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder view=new Holder();
        convertView=inflate.inflate(R.layout.custom_listview_white_text,parent,false);
        view.text=(TextView)convertView.findViewById(R.id.list_content);
        view.text.setText(list.get(position));
        return convertView;
    }
}
