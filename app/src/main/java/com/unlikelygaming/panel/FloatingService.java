package com.unlikelygaming.panel;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;

public class FloatingService extends Service {

    private WindowManager windowManager;
    private View floatingView;
    private WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(18, 15, 18, 15);
        panel.setBackgroundColor(Color.rgb(20, 20, 20));

        TextView title = new TextView(this);
        title.setText("🔥 UNLIKELY GAMING");
        title.setTextColor(Color.RED);
        title.setTextSize(17);
        panel.addView(title);

        TextView info = new TextView(this);
        info.setText("Vivo Y51 Pro\nHeadshot Practice Panel");
        info.setTextColor(Color.WHITE);
        info.setTextSize(13);
        panel.addView(info);

        Button sensitivity = new Button(this);
        sensitivity.setText("Sensitivity Guide");
        sensitivity.setOnClickListener(v ->
            info.setText("Try General 90-100\nRed Dot 85-95\nAdjust to your comfort")
        );
        panel.addView(sensitivity);

        Button hide = new Button(this);
        hide.setText("CLOSE PANEL");
        hide.setOnClickListener(v -> stopSelf());
        panel.addView(hide);

        floatingView = panel;

        params = new WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 30;
        params.y = 150;

        panel.setOnTouchListener(new View.OnTouchListener() {
            private int initialX, initialY;
            private float touchX, touchY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    initialX = params.x;
                    initialY = params.y;
                    touchX = event.getRawX();
                    touchY = event.getRawY();
                    return true;
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    params.x = initialX + (int)(event.getRawX() - touchX);
                    params.y = initialY + (int)(event.getRawY() - touchY);
                    windowManager.updateViewLayout(floatingView, params);
                    return true;
                }

                return false;
            }
        });

        windowManager.addView(floatingView, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null) {
            windowManager.removeView(floatingView);
            floatingView = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
