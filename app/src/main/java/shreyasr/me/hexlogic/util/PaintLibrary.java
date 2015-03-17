package shreyasr.me.hexlogic.util;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintLibrary {

    public final static Paint RED;
    public final static Paint WHITE;

    static {
        RED = new Paint();
        RED.setColor(Color.RED);

        WHITE = new Paint();
        WHITE.setColor(Color.WHITE);
    }
}
