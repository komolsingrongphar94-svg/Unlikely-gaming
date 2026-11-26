package com.unlikelygaming.panel;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(35, 45, 35, 35);
        layout.setGravity(Gravity.CENTER);

        layout.setBackgroundColor(0xFF101010);

        TextView title = new TextView(this);
        title.setText("UNLIKELY GAMING");
        title.setTextSize(27);
        title.setTextColor(0xFFFF3333);
        title.setGravity(Gravity.CENTER);

        layout.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("Vivo Y51 Pro Gaming Panel");
        subtitle.setTextColor(0xFFFFFFFF);
        subtitle.setTextSize(16);
        subtitle.setGravity(Gravity.CENTER);

        layout.addView(subtitle);

        Button permission = new Button(this);
        permission.setText("ENABLE DISPLAY OVER APPS");

        permission.setOnClickListener(v -> {
            Intent intent = new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName())
            );
            startActivity(intent);
        });

        layout.addView(permission);

        Button start = new Button(this);
        start.setText("START FLOATING PANEL");

        start.setOnClickListener(v -> {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, FloatingService.class));
                Toast.makeText(this, "Panel started", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enable overlay permission first", Toast.LENGTH_SHORT).show();
            }
        });

        layout.addView(start);

        setContentView(layout);
    }
}
