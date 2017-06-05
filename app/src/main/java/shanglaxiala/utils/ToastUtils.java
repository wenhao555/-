package shanglaxiala.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ToastUtils {
    public static void longToast(Context context, String message){
        if(context!=null&&message!=null){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    }

    public static void shortToast(Context context,String message){
        if(context!=null&&message!=null){
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }
}
