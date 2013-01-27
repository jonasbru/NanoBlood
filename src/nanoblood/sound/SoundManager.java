/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.sound;

import java.io.IOException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.SoundStore;

/**
 *
 * @author Anthony
 */
public enum SoundManager {

    INSTANCE;
    
    private boolean isMuted;
    
    private SoundStore ss;
    
    private Audio audioBonus;
    private Audio audioHeartBeat;
    private Audio audioMenu;
    private Audio audioPlay;
    private Audio audioSelect;
    private Audio audioSplash1;
    private Audio audioSplash2;
    private Audio audioSplash3;
    private Audio audioLifeUp;
    private Audio audioGameOver;
    private Audio audioLaser1;
    private Audio audioLaser2;
    // TODO collision
    
    private SoundManager() {     
        isMuted = false;
        
        ss = SoundStore.get();
        ss.init();
        
        // Load all sounds
        try {
            audioBonus = ss.getOgg("sounds/bonus1.ogg");
            //audioHeartBeat = ss.getOgg("sounds/heartbeat1.ogg");
            audioHeartBeat = ss.getWAV("sounds/coeur_1.wav");
            audioMenu = ss.getOgg("sounds/mainmenu.ogg");
            audioPlay = ss.getOgg("sounds/play.ogg");
            audioSelect = ss.getOgg("sounds/select.ogg");
            audioSplash1 = ss.getOgg("sounds/splash1.ogg");
            audioSplash2 = ss.getOgg("sounds/splash2.ogg");
            audioSplash3 = ss.getOgg("sounds/splash3.ogg");
            audioLifeUp = ss.getOgg("sounds/lifeup.ogg");
            audioGameOver = ss.getOgg("sounds/gameover.ogg");
            audioLaser1 = ss.getOgg("sounds/lasershot1.ogg");
            audioLaser2 = ss.getOgg("sounds/lasershot2.ogg");
            
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
    
    public SoundStore getSoundStore() {
        return ss;
    }
    
    public void setMuted(boolean isMuted) {
        this.isMuted = isMuted;
        ss.setSoundsOn(!isMuted);
        ss.setMusicOn(!isMuted);
    }
    
    public boolean isMuted() {
        return this.isMuted;
    }
    
    private Audio getAudio(SoundID id) {
        switch(id) {  
            case BONUS:
                return audioBonus;
                
            case HEARTBEAT:
                return audioHeartBeat;
                
            case MENU:
                return audioMenu;
                
            case PLAY:
                return audioPlay;
                
            case SELECT:
                return audioSelect;
                
            case SPLASH1:
                return audioSplash1;
                
            case SPLASH2:
                return audioSplash2;
                
            case SPLASH3:
                return audioSplash3;
                
            case LIFEUP:
                return audioLifeUp;
                
            case GAMEOVER:
                return audioGameOver;
                
            case LASER1:
                return audioLaser1;
                
            case LASER2:
                return audioLaser2;
            
            default:
                return null;
        }
    }
    
    public void playAsMusic(SoundID id, boolean isLooped) {
        getAudio(id).playAsMusic(1f, 1f, isLooped);
    }
    
    public void playAsSoundEffect(SoundID id, float pitch, float gain, boolean isLooped) {
        getAudio(id).playAsSoundEffect(pitch, gain, isLooped);
    }
    
    public void playAsSoundEffect(SoundID id, boolean isLooped) {
        getAudio(id).playAsSoundEffect(1f, 1f, isLooped);
    }
    
    public void stopSound(SoundID id) {
        getAudio(id).stop();
    }
    
    public boolean isPlaying(SoundID id) {
        return getAudio(id).isPlaying();
    }
}
