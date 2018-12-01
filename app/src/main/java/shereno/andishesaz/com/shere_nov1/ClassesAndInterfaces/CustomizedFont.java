package shereno.andishesaz.com.shere_nov1.ClassesAndInterfaces;

import android.app.Application;
import android.support.v7.widget.AppCompatTextView;

import shereno.andishesaz.com.shere_nov1.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by babak on 13/02/2017.
 */

public class CustomizedFont extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Fonts/A.Armita.Black.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
