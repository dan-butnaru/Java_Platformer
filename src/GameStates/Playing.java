package GameStates;

import Entities.Enemy;
import Entities.EnemyManager;
import Entities.Player;
import levels.LevelManager;
import main.Game;
import main.GameWindow;
import objects.ObjectManager;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private GameOverUI gameOverUI;
    private LevelCompletedUI levelCompletedUI;
    private int xLvlOffset;
    private int leftBorder = (int)(0.4 * Game.GAME_WIDTH);
    private int rightBorder = (int)(0.6 * Game.GAME_WIDTH);
    private int maxLvlOffsetX;
    private BufferedImage backgroundImg;
    private boolean gameOver;
    private boolean lvlCompleted;

    public Playing(Game game) {
        super(game);
        initClasses();
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BACKGROUND);
        calcLvlOffset();
        loadStartLevel();
    }

    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());

    }

    public void loadNextLevel(){
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        player = new Player(200,200, (int) (32 * game.SCALE), (int) (32 * game.SCALE), this);
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        gameOverUI = new GameOverUI(this);
        levelCompletedUI = new LevelCompletedUI(this);

    }


    @Override
    public void update() {
        if(!gameOver && !lvlCompleted)
        {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int)(player.getHitBox().x);
        int diff = playerX - xLvlOffset;
        if(diff > rightBorder)
        {
            xLvlOffset += diff - rightBorder;
        }
        else if (diff < leftBorder)
        {
            xLvlOffset += diff - leftBorder;
        }
        if(xLvlOffset > maxLvlOffsetX)
        {
            xLvlOffset = maxLvlOffsetX;
        }
        else if (xLvlOffset < 0)
            xLvlOffset = 0;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,0,0, Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        levelManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.draw(g,xLvlOffset);
        if(gameOver)
        {
            gameOverUI.draw(g);
        } else if(lvlCompleted)
        {
            levelCompletedUI.draw(g);
        }
    }

    @Override
    public void MouseClicked(MouseEvent e) {
        if(!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1)
                player.setAttacking(true);
    }

    @Override
    public void MousePressed(MouseEvent e) {

    }

    @Override
    public void MouseReleased(MouseEvent e) {

    }

    @Override
    public void MouseMoved(MouseEvent e) {

    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if(gameOver)
            gameOverUI.keyPressed(e);
        else if(lvlCompleted)
            levelCompletedUI.keyPressed(e);
        switch (e.getKeyCode()) {

            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_BACK_SPACE:
                GameState.state = GameState.MENU;
        }
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        if(!gameOver && !lvlCompleted)
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    player.setLeft(false);
                    break;

                case KeyEvent.VK_D:
                    player.setRight(false);
                    break;
                case KeyEvent.VK_SPACE:
                    player.setJump(false);
                    break;
            }
    }

    public void windowFocusLost()
    {
        player.resetDirBooleans();
    }

    public Player getPlayer(){
        return player;
    }

    public void resetAll() {
        lvlCompleted = false;
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();
    }
    public void setGameOver(boolean GameOver)
    {
        this.gameOver = GameOver;
    }
    public void checkEnemyHit(Rectangle2D.Float attackBox)
    {
        enemyManager.checkEnemyHit(attackBox);
    }
    public EnemyManager getEnemyManager()
    {
        return enemyManager;
    }
    public void setMaxLvlOffset(int lvlOffset)
    {
        this.maxLvlOffsetX = lvlOffset;
    }
    public void setLevelCompleted(boolean levelCompleted)
    {
        this.lvlCompleted = levelCompleted;
    }
    public void checkSpikesTouched(Player player) {
        objectManager.checkSpikesTouched(player);
    }
    public ObjectManager getObjectManager() {
        return objectManager;
    }

}
