package levels;

import Entities.Destroyer;
import main.Game;
import objects.Spike;
import utils.HelpMethods;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level {
    private int[][] lvlData;
    private BufferedImage img;
    private ArrayList<Destroyer> destroyers;
    private ArrayList<Spike> spikes;

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;
    public Level(BufferedImage img)
    {
        this.img = img;
        createLevelData();
        createEnemies();
        createSpikes();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void calcPlayerSpawn(){
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        destroyers = GetDestroyers(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y){
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }
    public int getLvlOffset()
    {
        return maxLvlOffsetX;
    }

    public ArrayList<Destroyer> getDestroyers()
    {
        return destroyers;
    }
    public Point getPlayerSpawn()
    {
        return playerSpawn;
    }
    public ArrayList<Spike> getSpikes()
    {
        return spikes;
    }
}
