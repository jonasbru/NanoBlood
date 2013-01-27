/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nanoblood.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Game Parameters singleton
 * 
 * @author Anthony
 */
public enum GameParams {
    
    INSTANCE;
    
    private Properties prop;   
    
    private GameParams() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("data/GameParams.properties"));
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    // Scoring
    public int ScorePerSecond() {
        return Integer.parseInt(prop.getProperty("ScorePerSecond"));
    }
    
    public float ScoreModifier1() {
        return Float.parseFloat(prop.getProperty("ScoreModifier1"));
    }
    
    public float ScoreModifier2() {
        return Float.parseFloat(prop.getProperty("ScoreModifier2"));
    }
    
    public float ScoreModifier3() {
        return Float.parseFloat(prop.getProperty("ScoreModifier3"));
    }
    
    public float ScoreModifier4() {
        return Float.parseFloat(prop.getProperty("ScoreModifier4"));
    }
    
    public float ScoreModifier5() {
        return Float.parseFloat(prop.getProperty("ScoreModifier5"));
    }
    
    // Heart beat
    public int BeatBoost() {
        return Integer.parseInt(prop.getProperty("BeatBoost"));
    }
    
    public float BeatDecreaseRate() {
        return Float.parseFloat(prop.getProperty("BeatDecreaseRate"));
    }
    
    public float MaxBeat() {
        return Float.parseFloat(prop.getProperty("MaxBeat"));
    }
    
    public float BeatThreshold1() {
        return Float.parseFloat(prop.getProperty("BeatThreshold1"));
    }
    
    public float BeatThreshold2() {
        return Float.parseFloat(prop.getProperty("BeatThreshold2"));
    }
    
    public float BeatThreshold3() {
        return Float.parseFloat(prop.getProperty("BeatThreshold3"));
    }
    
    public float BeatThreshold4() {
        return Float.parseFloat(prop.getProperty("BeatThreshold4"));
    }
    
    // Life
    public int MaxLife() {
        return Integer.parseInt(prop.getProperty("MaxLife"));
    }
    
    public int DamageLowBeat() {
        return Integer.parseInt(prop.getProperty("DamageLowBeat"));
    }
    
    public int DamageHighBeat() {
        return Integer.parseInt(prop.getProperty("DamageHighBeat"));
    }
    
    // TODO damages
    
    // TODO Maps
    
    
}
