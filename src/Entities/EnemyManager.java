package Entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import GameStates.Playing;
import levels.Level;
import levels.LevelManager;
import utils.HelpMethods;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;

public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] destroyerArr;
    private ArrayList<Destroyer> destroyers = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        destroyers = level.getDestroyers();
    }

    public void update(int [][]lvlData, Player player) {
        boolean isAnyActive = false;
        for (Destroyer d : destroyers)
            if(d.isActive()) {
                d.update(lvlData, player);
                isAnyActive = true;
            }
        if(!isAnyActive)
            playing.setLevelCompleted(true);
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawDestroyers(g, xLvlOffset);
    }

    private void drawDestroyers(Graphics g, int xLvlOffset) {
        for (Destroyer d : destroyers)
            if(d.isActive())
                {
                    g.drawImage(destroyerArr[d.getEnemyState()][d.getAniIndex()],
                            (int) d.getHitBox().x - DESTROYER_DRAWOFFSET_X - xLvlOffset + d.flipX(),
                            (int) d.getHitBox().y - DESTROYER_DRAWOFFSET_Y,
                            DESTROYER_WIDTH * d.flipW(), DESTROYER_HEIGHT, null);
                    //g.setColor(Color.PINK);
                    //g.drawRect((int) d.getHitBox().x - xLvlOffset, (int) d.getHitBox().y, (int) d.getHitBox().width, (int) d.getHitBox().height);
                    //d.drawAttackBox(g,xLvlOffset);
                }

    }
    public void checkEnemyHit(Rectangle2D.Float attackBox)
    {
        for (Destroyer d : destroyers)
        {
            if(attackBox.intersects(d.getHitBox())){
                d.hurt(10);
                return;
            }
        }
    }
    private void loadEnemyImgs() {
        destroyerArr = new BufferedImage[6][8];
        BufferedImage []temp = new BufferedImage[6];

        temp[0] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_IDLE);
        for(int j=0;j<5;j++)
        {
            destroyerArr[0][j] = temp[0].getSubimage(j*128,0,128,128);
        }
        temp[1] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_WALK);
        for(int j=0;j<8;j++)
        {
            destroyerArr[1][j] = temp[1].getSubimage(j*128,0,128,128);

        }
        temp[2] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_ATTACK);
        for(int j=0;j<4;j++)
        {
            destroyerArr[2][j] = temp[2].getSubimage(j*128,0,128,128);
        }
        temp[3] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_ATTACK2);
        temp[4] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_HIT);
        for(int j=0;j<3;j++)
        {
            destroyerArr[3][j] = temp[3].getSubimage(j*128,0,128,128);
            destroyerArr[4][j] = temp[4].getSubimage(j*128,0,128,128);


        }
        temp[5] = LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DESTROYER_DEAD);
        for(int j=0;j<7;j++)
        {
            destroyerArr[5][j] = temp[5].getSubimage(j*128,0,128,128);
        }

    }

    public void resetAllEnemies() {
        for(Destroyer d : destroyers)
            d.resetEnemy();
    }
}