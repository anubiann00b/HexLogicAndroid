package shreyasr.me.hexlogic.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class DrawableLibrary {

    public static final int SIZE = 60;

    public final static Paint STROKE;
    public final static Paint STROKE_THICK;
    public final static Paint FILL_OFF;
    public final static Paint FILL_ON;

    public final static Path HEXAGON;
    public final static Path XOR;
    public final static Path ON;
    public final static Path OR;

    static {
        STROKE = new Paint();
        STROKE.setColor(Color.parseColor("#000000"));
        STROKE.setStyle(Paint.Style.STROKE);

        STROKE_THICK = new Paint();
        STROKE_THICK.setColor(Color.parseColor("#000000"));
        STROKE_THICK.setStrokeWidth(2);
        STROKE_THICK.setStyle(Paint.Style.STROKE);

        FILL_OFF = new Paint();
        FILL_OFF.setColor(Color.parseColor("#AAAAAA"));
        FILL_OFF.setStyle(Paint.Style.FILL);

        FILL_ON = new Paint();
        FILL_ON.setColor(Color.parseColor("#FFFF00"));
        FILL_ON.setStyle(Paint.Style.FILL);

        HEXAGON = new Path();
        for (int i=0;i<=6;i++) {
            double angle = 2 * Math.PI / 6 * i;
            double px = SIZE * Math.cos(angle);
            double py = SIZE * Math.sin(angle);
            if (i == 0)
                HEXAGON.moveTo((float) px, (float) py);
            else
                HEXAGON.lineTo((float) px, (float) py);
        }

        XOR = new Path();
        XOR.moveTo(-SIZE / 4, -SIZE / 4);
        XOR.lineTo(SIZE / 4, SIZE / 4);
        XOR.moveTo(-SIZE / 4, SIZE / 4);
        XOR.lineTo(SIZE / 4, -SIZE / 4);

        ON = new Path();
        ON.moveTo(-SIZE/4, 0);
        ON.lineTo(SIZE/4, 0);
        ON.moveTo(0, SIZE/4);
        ON.lineTo(0, -SIZE/4);

        OR = new Path();
        OR.addCircle(0, 0, SIZE/4, Path.Direction.CW);
    }
}
