package Entities;
import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public class Destroyer extends Enemy{
    //AttackBox
    private Rectangle2D.Float attackBox;
    private int attackBoxOffsetX;
    public Destroyer(float x, float y) {
        super(x, y, DESTROYER_WIDTH, DESTROYER_HEIGHT, DESTROYER);
        initHitbox(x,y,(int)(37 * Game.SCALE),(int)(78 * Game.SCALE));
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(40 * Game.SCALE),(int)(75 * Game.SCALE));
        attackBoxOffsetX = (int) (Game.SCALE * 30);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if(walkDir == RIGHT)
        {
            attackBox.x = hitBox.x + hitBox.width;// + (int)(Game.SCALE * 10);
        }
        else if (walkDir == LEFT)
        {
            attackBox.x = hitBox.x - hitBox.width;// - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitBox.y;// + (Game.SCALE * 10);
    }

    public void drawAttackBox(Graphics g, int xLvlOffset)
    {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x - xLvlOffset, (int)attackBox.y, (int)attackBox.width, (int)attackBox.height);
    }

    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (enemyState) {
                case IDLE:
                    enemyState = WALK;
                    break;
                case WALK:
                    if(canSeePlayer(lvlData,player))
                    {
                        turnTowardsPlayer(player);
                        if(isPlayerCloseForAttack(player))
                            newState(ATTACK);
                    }
                    move(lvlData);
                    break;
                case ATTACK:
                    if(aniIndex == 0) attackChecked = false;
                    if(aniIndex == 2 && !attackChecked)
                        checkEnemyHit(attackBox,player);
                    break;
                case HIT:
                    break;
            }
        }

    }

    public int flipX(){
        if(walkDir == LEFT)
            return width;
        else
            return 0;
    }

    public int flipW(){
        if(walkDir == LEFT)
            return -1;
        else
            return 1;
    }
}
