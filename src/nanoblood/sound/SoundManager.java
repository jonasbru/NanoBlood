/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.sound;

import java.io.IOException;
import org.newdawn.slick.Sound;
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
    private Audio audioDie;
    private Audio audioHeartBeat;
    private Audio audioMenu;
    private Audio audioPlay;
    private Audio audioSelect;
    private Audio audioSplash;
    private Audio audioVirusDies;
    private Audio audioLifeUp;
    private Audio audioGameOver;
    // TODO collision
    
    private SoundManager() {     
        isMuted = false;
        
        ss = SoundStore.get();
        ss.init();
        
        // Load all sounds
        try {
            audioBonus = ss.getOgg("sounds/bonus1.ogg");
            audioDie = ss.getOgg("sounds/die.ogg");
            audioHeartBeat = ss.getOgg("sounds/heartbeat1.ogg");
            audioMenu = ss.getOgg("sounds/mainmenu.ogg");
            audioPlay = ss.getOgg("sounds/play.ogg");
            audioSelect = ss.getOgg("sounds/select.ogg");
            audioSplash = ss.getOgg("sounds/splash.ogg");
            audioVirusDies = ss.getOgg("sounds/virusdies.ogg");
            audioLifeUp = ss.getOgg("sounds/lifeup.ogg");
            audioGameOver = ss.getOgg("sounds/gameover.ogg");
            
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
                
            case DIE:
                return audioDie;
                
            case HEARTBEAT:
                return audioHeartBeat;
                
            case MENU:
                return audioMenu;
                
            case PLAY:
                return audioPlay;
                
            case SELECT:
                return audioSelect;
                
            case SPLASH:
                return audioSplash;
                
            case VIRUSDIES:
                return audioVirusDies;
                
            case LIFEUP:
                return audioLifeUp;
                
            case GAMEOVER:
                return audioGameOver;
            
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
