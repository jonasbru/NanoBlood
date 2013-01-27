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
    public static final int PLAYER_X = Main.width / 10;
    public static final int fpslimit = 60;
    public static final int LOGO = 0;
    public static final int MAINMENU = 1;
    public static final int GAMEPLAY = 2;
    public static final int GAMEOVER = 3;
    public static final int TUTORIAL = 4;

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
        this.addState(new LogoScreen(LOGO));
        this.addState(new MainMenu(MAINMENU));
        this.addState(new GamePlay(GAMEPLAY));
        this.addState(new GameOver(GAMEOVER));
        this.addState(new Tutorial(TUTORIAL));
    }

}
