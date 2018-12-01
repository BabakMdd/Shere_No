package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.R;
import shereno.andishesaz.com.shere_nov1.ShowPoems;

/**
 * Created by babak on 29/06/2017.
 */

public class CustomFavoriteDelete extends ArrayAdapter<String> {
    Context con;
    private List<String> list;
    LayoutInflater inflate=null;

    public CustomFavoriteDelete(Context con, List<String> list){
        super(con, R.layout.custom_font_size,list);
        this.con=con;
        this.list=list;
        inflate=(LayoutInflater)con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Control crl=new Control();
        convertView=inflate.inflate(R.layout.listwith_delete,parent,false);
        crl.title=(TextView)convertView.findViewById(R.id.textTitle);
        crl.title.setText(list.get(position));
        crl.delete=(ImageButton)convertView.findViewById(R.id.delete_btn);
        crl.delete.setImageResource(R.drawable.favdelete);
        crl.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    SQLclass sql=new SQLclass(con);
                    sql.openDataBase();
                    //sql.Delete_Favorite((long) ShowPoems.counter);
                    sql.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }

    private class Control {
        TextView title;
        ImageButton delete;
    }
}
