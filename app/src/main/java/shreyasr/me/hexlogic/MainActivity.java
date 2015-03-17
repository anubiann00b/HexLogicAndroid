package shreyasr.me.hexlogic;

import android.app.Activity;
import android.os.Bundle;

import shreyasr.me.hexlogic.drawing.DrawingPanelView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawingPanelView(this));
    }
}
