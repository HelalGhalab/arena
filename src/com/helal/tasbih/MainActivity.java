package com.helal.tasbih;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int count = 0;
    private TextView numView;
    private TextView subView;
    private SharedPreferences prefs;
    private static final int TARGET = 33;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        prefs = getSharedPreferences("tasbih", MODE_PRIVATE);
        count = prefs.getInt("count", 0);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFF0B2B22, 0xFF071A15});
        root.setBackground(bg);
        root.setPadding(dp(24), dp(40), dp(24), dp(40));

        TextView title = new TextView(this);
        title.setText("مسبحة");
        title.setTextColor(0x99FFFFFF);
        title.setTextSize(16);
        title.setGravity(Gravity.CENTER);
        root.addView(title, lp(-2, -2, 0, 0, 0, dp(6)));

        TextView dhikr = new TextView(this);
        dhikr.setText("سُبْحَانَ اللّٰه");
        dhikr.setTextColor(0xFFB9F6CA);
        dhikr.setTextSize(30);
        dhikr.setTypeface(Typeface.DEFAULT_BOLD);
        dhikr.setGravity(Gravity.CENTER);
        root.addView(dhikr, lp(-1, -2, 0, dp(24), 0, dp(8)));

        numView = new TextView(this);
        numView.setTextColor(Color.WHITE);
        numView.setTextSize(110);
        numView.setTypeface(Typeface.DEFAULT_BOLD);
        numView.setGravity(Gravity.CENTER);
        numView.setShadowLayer(24f, 0f, 0f, 0xFF33D17A);
        root.addView(numView, lp(-1, 0, 0, 0, 0, 0));

        subView = new TextView(this);
        subView.setTextColor(0x88FFFFFF);
        subView.setTextSize(15);
        subView.setGravity(Gravity.CENTER);
        root.addView(subView, lp(-1, -2, 0, dp(4), 0, dp(18)));

        TextView hint = new TextView(this);
        hint.setText("اضغط في أي مكان للتسبيح");
        hint.setTextColor(0x77FFFFFF);
        hint.setTextSize(14);
        hint.setGravity(Gravity.CENTER);
        root.addView(hint, lp(-1, -2, 0, 0, 0, dp(24)));

        TextView reset = new TextView(this);
        reset.setText("تصفير");
        reset.setTextColor(0xFF0B2B22);
        reset.setTextSize(16);
        reset.setTypeface(Typeface.DEFAULT_BOLD);
        reset.setGravity(Gravity.CENTER);
        GradientDrawable rg = new GradientDrawable();
        rg.setColor(0xFFB9F6CA);
        rg.setCornerRadius(dp(26));
        reset.setBackground(rg);
        reset.setPadding(dp(40), dp(12), dp(40), dp(12));
        LinearLayout.LayoutParams rlp = new LinearLayout.LayoutParams(-2, -2);
        rlp.gravity = Gravity.CENTER;
        root.addView(reset, rlp);

        reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count = 0;
                save();
                render();
                v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            }
        });

        View.OnClickListener inc = new View.OnClickListener() {
            public void onClick(View v) {
                count++;
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                save();
                render();
            }
        };
        root.setOnClickListener(inc);

        setContentView(root);
        render();
    }

    private LinearLayout.LayoutParams lp(int w, int h, int l, int t, int r, int bo) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(w, h);
        p.setMargins(l, t, r, bo);
        return p;
    }

    private int dp(int v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void render() {
        numView.setText(String.valueOf(count));
        int inCycle = count % TARGET;
        subView.setText("الدورة " + (count / TARGET + 1) + "  •  " + inCycle + "/" + TARGET);
    }

    private void save() {
        prefs.edit().putInt("count", count).apply();
    }
}
