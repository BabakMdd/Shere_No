package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.app.Activity;
import android.graphics.Point;

import java.util.Locale;

/**
 * Created by babak on 10/02/2017.
 */

public interface ISetting {

    //---------------------------Open Activity---------------------------
    public void OpenActivity(Class<? extends Activity> activity);

    //---------------------------Make Message----------------------------
    public void Message(String text);

    //---------------------------Set Localization------------------------

    public void SetLocale(String locale);

    public void SetLocale(Locale locale);

    //--------------------------Get Screen Resolution--------------------

    public Point getScreenSize();
}
