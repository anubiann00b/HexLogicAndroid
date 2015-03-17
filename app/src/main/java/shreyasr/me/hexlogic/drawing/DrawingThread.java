package shreyasr.me.hexlogic.drawing;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import shreyasr.me.hexlogic.util.PaintLibrary;

public class DrawingThread extends Thread {

    boolean run = true;
    private SurfaceHolder surfaceHolder;
    private DrawingPanelView panel;

    public DrawingThread(SurfaceHolder surfaceHolder, DrawingPanelView panel) {
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (run) {
            canvas = surfaceHolder.lockCanvas();
            if (canvas == null)
                continue;
            canvas.drawPaint(PaintLibrary.WHITE);
            canvas.drawRect(50f, 50f, 150f, 175f, PaintLibrary.RED);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
