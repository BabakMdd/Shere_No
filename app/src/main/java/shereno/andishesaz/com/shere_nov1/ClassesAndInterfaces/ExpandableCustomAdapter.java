package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 25/04/2017.
 */

public class ExpandableCustomAdapter extends BaseExpandableListAdapter{

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableCustomAdapter (Context context, List<String> listDataHeader,HashMap<String, List<String>> listChildData){
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }


    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i))
                .size();
    }

    @Override
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this._listDataChild.get(this._listDataHeader.get(i))
                .get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) view
                .findViewById(R.id.exp_list_title);
        Typeface face=Typeface.createFromAsset(_context.getAssets(),"Fonts/A.Armita.Black.ttf");
        lblListHeader.setTypeface(face,Typeface.BOLD);
        lblListHeader.setTextSize(14);
        lblListHeader.setText(headerTitle);
        lblListHeader.setTextColor(Color.WHITE);

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String) getChild(i, i1);

        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.child_list_group, null);
        }

        TextView txtListChild = (TextView) view
                .findViewById(R.id.lblListItem);
        txtListChild.setTextSize(14);
        txtListChild.setText(childText);
        txtListChild.setTextColor(Color.WHITE);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
