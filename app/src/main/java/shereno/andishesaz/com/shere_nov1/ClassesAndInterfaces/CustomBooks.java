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
 * Created by babak on 11/02/2017.
 */

public class CustomBooks extends ArrayAdapter<String> {

    private int[] ImgID;
    private List<String> BooksID;
    Context con;
    LayoutInflater inflate=null;

    public CustomBooks(Context con,int[] ImgID,List<String> BooksID){
        super(con,R.layout.customize_books,BooksID);
        this.con=con;
        this.ImgID=ImgID;
        this.BooksID=BooksID;
        inflate=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //--------------------------------Customize Method ------------------------------------------
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view=new ViewHolder();
        convertView=inflate.inflate(R.layout.customize_books,parent,false);
        view.booksName=(TextView)convertView.findViewById(R.id.bookName);
        view.Img=(ImageView)convertView.findViewById(R.id.img);

        view.booksName.setText(BooksID.get(position));
        view.Img.setImageResource(ImgID[position]);

        return convertView;
    }

    //--------------------------------Holder Class-----------------------------------------------
    public class ViewHolder{
        TextView booksName;
        ImageView Img;
    }
}
