package shreyasr.me.hexlogic.drawing;

import android.view.SurfaceHolder;

import java.util.TimerTask;

import shreyasr.me.hexlogic.Game;

public class DrawingTask extends TimerTask {

    boolean run = true;
    private SurfaceHolder surfaceHolder;
    private Game game;

    public DrawingTask(SurfaceHolder surfaceHolder, Game game) {
        this.surfaceHolder = surfaceHolder;
        this.game = game;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {
        game.update();
        game.repaint(surfaceHolder);
    }
}