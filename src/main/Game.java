package main;

import Entities.Player;
import GameStates.GameState;
import GameStates.Leaderboard;
import GameStates.Playing;
import GameStates.Menu;
import levels.LevelManager;
import utils.Database;

import javax.swing.*;
import java.awt.*;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 150;
    private Playing playing;
    private Menu menu;
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 1.8f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private Leaderboard leaderboard;
    private Database db;
    private String name;
    private int score=0;


    public Game()
    {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        gamePanel.requestFocus();

        StartGameLoop();
    }
    private void getName(){
        name= JOptionPane.showInputDialog("NUMELE JUCATORULUI:");
    }
    private void initClasses() {
        db = new Database();
        menu = new Menu(this);
        playing = new Playing(this);
        leaderboard = new Leaderboard(this);
    }
    private void closeDB() {
        db.close();
    }
    private void StartGameLoop()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){

        switch (GameState.state){

            case PLAYING -> {
                playing.update();
                if(name==null)
                    getName();

            }
            case MENU -> {
                menu.update();
                if(name!=null)
                    db.saveScoreToDatabase(name ,score);
            }
            case LEADERBOARD ->
            {
                leaderboard.update();
            }
            default ->
            {
                closeDB();
                System.exit(0);
            }

        }
    }

    public void render(Graphics g)
    {

        switch (GameState.state){

            case PLAYING -> {
                playing.draw(g);
            }
            case MENU -> {
                menu.draw(g);
            }
            case LEADERBOARD -> {
                leaderboard.draw(g);
            }
        }

    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true)
        {
            long currentTime = System.nanoTime();

            deltaU += (currentTime-previousTime) / timePerUpdate;
            deltaF += (currentTime-previousTime) / timePerFrame;


            previousTime = currentTime;
            if(deltaU >= 1)
            {
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >=1)
            {
                gamePanel.repaint();
                frames++;
                deltaF--;

            }
//            if(now - lastframe >= timePerFrame)
//            {
//                gamePanel.repaint();
//                lastframe = now;
//                frames++;
//            }

            if(System.currentTimeMillis()-lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: "+frames+" |UPS: "+updates);
                frames=0;
                updates=0;
                score++;
            }

        }
    }
    public void windowFocusLost()
    {
        if(GameState.state == GameState.PLAYING)
        {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu()
    {
        return menu;
    }

    public Playing getPlaying()
    {
        return playing;
    }
    public Database getDb()
    {
        return db;
    }

    public Leaderboard getLeaderboard(){
        return leaderboard;
    }

}
