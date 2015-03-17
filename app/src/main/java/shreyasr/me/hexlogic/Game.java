package shreyasr.me.hexlogic;

import android.graphics.Canvas;
import android.graphics.Path;
import android.view.SurfaceHolder;

import shreyasr.me.hexlogic.util.DrawableLibrary;

import static shreyasr.me.hexlogic.Hex.Type.OFF;
import static shreyasr.me.hexlogic.Hex.Type.OR;
import static shreyasr.me.hexlogic.Hex.Type.XOR;
import static shreyasr.me.hexlogic.Hex.Type.values;

public class Game {

    int[][][] neighbors = {
            {{+1, +1}, {+1, 0}, {0, -1}, {-1, 0}, {-1, +1}, {0, +1}},
            {{+1, 0}, {+1, -1}, {0, -1}, {-1, -1}, {-1, 0}, {0, +1}}
    };

    Hex[][] map;

    final int size = DrawableLibrary.SIZE;
    int width = 0;
    int height = 0;
    boolean firstInit = true;

    public void init(Canvas canvas) {
        if (canvas == null)
            return;
        if (firstInit) {
            width = canvas.getWidth() / size / 3 * 2;
            height = (int)(canvas.getHeight() / size / Math.sqrt(3)) - 1;

            map = new Hex[width][height];
            for (int i=0;i<map.length;i++)
                for (int j=0;j<map[i].length;j++)
                    map[i][j] = new Hex(i,j);

            firstInit = false;
        }
    }

    public void update() {
        for (int i=0;i<map.length;i++) {
            for (int j=0;j<map[i].length;j++) {
                Hex hex = map[i][j];
                switch (hex.type) {
                    case ON:
                        hex.state = true;
                        break;
                    case OFF:
                        hex.state = false;
                        break;
                    case OR:
                        hex.state = getNeighbor(hex, 1).oldstate || getNeighbor(hex, 3).oldstate || getNeighbor(hex, 5).oldstate;
                        break;
                    case XOR:
                        hex.state = ((getNeighbor(hex, 1).oldstate ? 1 : 0) + (getNeighbor(hex, 3).oldstate ? 1 : 0) + (getNeighbor(hex, 5).oldstate ? 1 : 0)) % 2 == 1;
                        break;
                }
                if (hex.state != hex.oldstate)
                    hex.repaint = true;
            }
        }
    }

    public synchronized void repaint(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas == null)
            return;
        for (int i=0;i<map.length;i++) {
            for (int j=0;j<map[i].length;j++) {
                Hex hex = map[i][j];

                double xPos = size + i*size*3/2;
                double yPos = size + j*size*Math.sqrt(3) + (i%2==0?Math.sqrt(3)/2*size:0);

                Path hexPath = new Path(DrawableLibrary.HEXAGON);
                hexPath.offset((float)xPos, (float)yPos);

                if (hex.state)
                    canvas.drawPath(hexPath, DrawableLibrary.FILL_ON);
                else
                    canvas.drawPath(hexPath, DrawableLibrary.FILL_OFF);
                canvas.drawPath(hexPath, DrawableLibrary.STROKE);

                if (hex.type != OFF) {
                    Path path;
                    if (hex.type == XOR)
                        path = new Path(DrawableLibrary.XOR);
                    else if (hex.type == OR)
                        path = new Path(DrawableLibrary.OR);
                    else // on
                        path = new Path(DrawableLibrary.ON);
                    path.offset((float) xPos, (float) yPos);
                    canvas.drawPath(path, DrawableLibrary.STROKE_THICK);
                }

                hex.repaint = false;
                hex.oldstate = hex.state;
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void onClick(float x, float y, SurfaceHolder surfaceHolder) {
        double hx = 2d/3d * x / size - 1;
        double hy = Math.sqrt(3)/3 * y / size + (Math.round(hx)%2)*Math.sqrt(3)/3 - 1;

        Hex hex = map[(int)Math.round(hx)][(int)hy];
        hex.incrementType();

        repaint(surfaceHolder);
    }

    Hex getNeighbor(Hex hex, int d) {
        int[] n = neighbors[(hex.x&1)][d];

        int nx = hex.x+n[0];
        int ny = hex.y+n[1];

        if (nx<0 || nx>=width || ny<0 || ny>=height)
            return new Hex(nx, ny);
        return map[nx][ny];
    }
}

class Hex {

    final int x;
    final int y;
    Type type = OFF;
    boolean state = false;
    boolean oldstate = false;
    boolean repaint = false;

    public Hex(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void incrementType() {
        type = values()[(type.ordinal()+1)% values().length];
        repaint = true;
    }

    enum Type { OFF, XOR, OR, ON}
}
