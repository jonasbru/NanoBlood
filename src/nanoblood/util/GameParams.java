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
    
    // Scoring
    public static int   ScorePerSecond      = 10;
    public static float ScoreModifierLow    = 0.5f;
    public static float ScoreModifierNormal = 1f;
    public static float ScoreModifierHigh   = 3f;
    
    // Heart beat
    public static int   BeatBoost           = 3;
    public static float BeatDecreaseRate    = 0.01f;
    public static int   MaxBeat             = 30;
    public static float LowBeatThreshold    = 10f;
    public static float HighBeatThreshold   = 20;
    
    // Life
    public static int   MaxLife             = 150;
    
    // Maps
    public static String Maps = "1, 2, 3";
    public static String MapsSpawnRate= "1, 1, 1";
    public static boolean HorizontalFlipping=false;
    
    
    private GameParams() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("data/GameParams.properties"));
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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
}
