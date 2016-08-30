package com.milkriver.helloworld;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.ImageView;

/**
 * Created by xukaitang on 8/29/16.
 */
public class Rose extends ImageView {
    int direction = 0;

    public Rose(Context context) {
        super(context);
        this.setImageResource(R.drawable.ic_action_name);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int height = this.getHeight();
        int width = this.getWidth();
        canvas.rotate(direction, width / 2, height / 2);
        super.onDraw(canvas);
    }

    public void setDirection(int direction) {
        this.direction = direction;
        this.invalidate();
    }
}
