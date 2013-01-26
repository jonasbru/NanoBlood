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
    
    public float ScoreModifierLow() {
        return Float.parseFloat(prop.getProperty("ScoreModifierLow"));
    }
    
    public float ScoreModifierNormal() {
        return Float.parseFloat(prop.getProperty("ScoreModifierNormal"));
    }
    
    public float ScoreModifierHigh() {
        return Float.parseFloat(prop.getProperty("ScoreModifierHigh"));
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
    
    public float LowBeatThreshold() {
        return Float.parseFloat(prop.getProperty("LowBeatThreshold"));
    }
    
    public float HighBeatThreshold() {
        return Float.parseFloat(prop.getProperty("HighBeatThreshold"));
    }
    
    // Life
    public int MaxLife() {
        return Integer.parseInt(prop.getProperty("MaxLife"));
    }
    
    // TODO damages
    
    // TODO Maps
    
    
}
