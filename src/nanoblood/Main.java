/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package nanoblood;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import java.lang.reflect.Field;

/**
 *
 * @author jonas
 */
public class Main extends StateBasedGame{

    public static final int height = 720;
    public static final int width = 1280;
    public static final boolean fullscreen = false;
    public static final boolean showFPS = true;
    public static final String title = "NanoBlood";
    public static final int fpslimit = 60;
    public static final int MAINMENU = 0;
    public static final int GAMEPLAY = 1;
    public static final int GAMEEND = 2;
    public static final int WAIT = 3;

    public static void main(String[] args) throws SlickException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {

        //Hack for set the library path
        System.setProperty( "java.library.path", "./slick/lib" );
        Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
        fieldSysPath.setAccessible( true );
        fieldSysPath.set( null, null );

        AppGameContainer app = new AppGameContainer(new Main());
        app.setDisplayMode(Main.width, Main.height, Main.fullscreen);
        app.setSmoothDeltas(true);
        app.setTargetFrameRate(Main.fpslimit);
        app.setShowFPS(showFPS);
        app.start();
    }



    public Main() {
        super(title);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new MainMenu(MAINMENU));
		this.addState(new GamePlay(GAMEPLAY));
    }

}
