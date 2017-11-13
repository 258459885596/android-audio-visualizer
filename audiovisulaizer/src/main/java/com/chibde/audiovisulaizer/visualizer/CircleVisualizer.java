/*
* Copyright (C) 2017 Gautam Chibde
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.chibde.audiovisulaizer.visualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.chibde.audiovisulaizer.BaseVisualizer;

/**
 * Custom view that creates a circle visualizer effect for
 * the android {@link android.media.MediaPlayer}
 *
 * Created by gautam on 13/11/17.
 */
public class CircleVisualizer extends BaseVisualizer {
    private float[] points;
    private float radiusMultiplier = 1;

    public CircleVisualizer(Context context) {
        super(context);
    }

    public CircleVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleVisualizer(Context context,
                            @Nullable AttributeSet attrs,
                            int defStyleAttr,
                            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {

    }

    /**
     * This method sets the multiplier to the circle, by default the
     * multiplier is set to 1. you can provide value more than 1 to
     * increase size of the circle visualizer.
     *
     * @param radiusMultiplier multiplies to the radius of the circle.
     */
    public void setRadiusMultiplier(float radiusMultiplier) {
        this.radiusMultiplier = radiusMultiplier;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bytes != null) {
            if (points == null || points.length < bytes.length * 4) {
                points = new float[bytes.length * 4];
            }
            double angle = 0;

            for (int i = 0; i < 360; i++, angle++) {
                points[i * 4] = (float) (getWidth() / 2
                        + Math.abs(bytes[i * 2])
                        * radiusMultiplier
                        * Math.cos(Math.toRadians(angle)));
                points[i * 4 + 1] = (float) (getHeight() / 2
                        + Math.abs(bytes[i * 2])
                        * radiusMultiplier
                        * Math.sin(Math.toRadians(angle)));

                points[i * 4 + 2] = (float) (getWidth() / 2
                        + Math.abs(bytes[i * 2 + 1])
                        * radiusMultiplier
                        * Math.cos(Math.toRadians(angle + 1)));

                points[i * 4 + 3] = (float) (getHeight() / 2
                        + Math.abs(bytes[i * 2 + 1])
                        * radiusMultiplier
                        * Math.sin(Math.toRadians(angle + 1)));
            }
            canvas.drawLines(points, paint);
        }
        super.onDraw(canvas);
    }
}
