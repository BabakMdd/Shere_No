package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import shereno.andishesaz.com.shere_nov1.R;

/**
 * Created by babak on 10/02/2017.
 */

public class Setting implements ISetting {
    Context con;

    public Setting(Context con){
        this.con=con;
    }
    ///------------------------------------------------Activity
    @Override
    public void OpenActivity(Class<? extends Activity> activity) {
        Intent i=new Intent(con,activity);
        con.startActivity(i);
    }

    ///-------------------------------------------------Customized Toast
    @Override
    public void Message(String text) {
        LayoutInflater inflater = (LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View toastRoot = inflater.inflate(R.layout.custom_toast, null);
        TextView title=(TextView)toastRoot.findViewById(R.id.toast_title);
        title.setText(text);
        Toast toast = new Toast(con);
        toast.setView(toastRoot);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    ///-------------------------------------------------Localization
    @Override
    public void SetLocale(String locale) {
        SetLocale(new Locale(locale));
    }

    @Override
    public void SetLocale(Locale locale) {
       locale.setDefault(locale);
        Configuration config=new Configuration();
        config.locale=locale;
        con.getResources().updateConfiguration(config,con.getResources().getDisplayMetrics());
    }
    ///-------------------------------------------------get Display Width and Height
    @Override
    public Point getScreenSize() {
        WindowManager wm=(WindowManager)con.getSystemService(Context.WINDOW_SERVICE);
        int width=wm.getDefaultDisplay().getWidth();
        int height=wm.getDefaultDisplay().getHeight();
        return new Point(width,height);
    }
}
