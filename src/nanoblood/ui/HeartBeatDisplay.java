/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.ui;

import nanoblood.GamePlay;
import nanoblood.Main;
import nanoblood.Sprite;
import nanoblood.util.IObservable;
import nanoblood.util.IObserver;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Anthony
 */
public class HeartBeatDisplay implements IObserver {

    private final static int X_OFFSET = 20;
    private final static int Y_OFFSET = 20;
    public static final int CONTENT_WIDTH = 209;
    public static final int CONTENT_HEIGHT = 21;
    public static final int CONTENT_X_OFFSET = 83;
    public static final int CONTENT_Y_OFFSET = 11;
    public static final int MIN_HB = 0; // get values from properties (or Gameplay)
    public static final int MAX_HB = 200;
    private float heartBeat;
    private Image heartBeatBackground;

    public HeartBeatDisplay() throws SlickException {
        this.heartBeat = 0f;
        heartBeatBackground = Sprite.getImage("sprites/ui/HeartBar.png");
    }

    @Override
    public void update(IObservable obs, Object obj) {
        if (obs instanceof GamePlay) {
            heartBeat = ((GamePlay) obs).getCurrentHeartBeat();
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics gr) {

        // bar BG
        heartBeatBackground.draw(X_OFFSET, Main.height - 40 - Y_OFFSET);

        if (heartBeat >= 0 && heartBeat < MAX_HB) {
            int hbCursorPos = (int) (heartBeat * CONTENT_WIDTH / MAX_HB);

            gr.setColor(Color.green);
            gr.drawLine(X_OFFSET + CONTENT_X_OFFSET + hbCursorPos,
                    Main.height - 40 - Y_OFFSET + CONTENT_Y_OFFSET,
                    X_OFFSET + CONTENT_X_OFFSET + hbCursorPos,
                    Main.height - 40 - Y_OFFSET + CONTENT_Y_OFFSET + 21);
        }
    }
}
