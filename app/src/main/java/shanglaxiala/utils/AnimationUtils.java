package shanglaxiala.utils;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import shanglaxiala.BaseRecyclerView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class AnimationUtils {
    public static void showAndHide(final TextView textView, String message) {
        textView.setVisibility(View.VISIBLE);
        textView.setText(message);
        textView.setAnimation(moveToViewLocation());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
                textView.setAnimation(moveToViewBottom());
                BaseRecyclerView.isLoading = false;
            }
        }, 2000);
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
        TranslateAnimation hiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        hiddenAction.setDuration(500);
        return hiddenAction;
    }

    /**
     * 从控件所在位置移动到控件所在的位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
        TranslateAnimation showAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAction.setDuration(500);
        return showAction;
    }
}
