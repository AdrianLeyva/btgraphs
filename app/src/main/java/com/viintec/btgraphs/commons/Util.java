package com.viintec.btgraphs.commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by adrianaldairleyvasanchez on 12/1/17.
 */

public class Util {
    //Opens a new activity and close the previous one
    public static void sendAndFinish(Activity activity, Class clase) {
        Intent i = new Intent(activity, clase);
        activity.startActivity(i);
        activity.finish();
    }

    //Just opens a new activity
    public static void sendTo(Activity activity, Class clase) {
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
    }

    public static void sentToWithBundle(Activity activity, Class clase, Bundle bundle){
        Intent mainIntent = new Intent().setClass(activity, clase);
        mainIntent.putExtras(bundle);
        activity.startActivity(mainIntent);
    }

    //Show a short Toast
    public static void showToastShort(Context activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    //Show a long Toast
    public static void showToastLong(Context activity, String text) {
        Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
    }

}
