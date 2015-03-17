package shreyasr.me.hexlogic.drawing;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Timer;

import shreyasr.me.hexlogic.Game;

public class DrawingPanelView extends SurfaceView implements SurfaceHolder.Callback {

    Timer drawingTimer;
    Game game = new Game();
    SurfaceHolder surfaceHolder = null;

    public DrawingPanelView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        setWillNotDraw(false);

        this.surfaceHolder = surfaceHolder;

        synchronized (game) {
            Canvas c = surfaceHolder.lockCanvas();
            game.init(c);
            surfaceHolder.unlockCanvasAndPost(c);
        }

        drawingTimer = new Timer();
        drawingTimer.schedule(new DrawingTask(surfaceHolder, game), 0, 200);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawingTimer.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return false;
        float x = event.getX();
        float y = event.getY();

        synchronized (game) {
            game.onClick(x, y, surfaceHolder);
        }

        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }
}
