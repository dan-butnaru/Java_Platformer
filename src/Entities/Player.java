package Entities;

import GameStates.Playing;
import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.Directions.*;
import static utils.Constants.Directions.DOWN;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

import main.Game;



public class Player extends Entity
{
    private BufferedImage [][]animations;
    private int aniTick,aniIndex,aniSpeed = 10;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean up,left,down,right,jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] lvlData;
    private float xDrawOffset = 5 * Game.SCALE;
    private float yDrawOffset = 2 * Game.SCALE;
    // Gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    //Status Bar
    private BufferedImage statusBarImg;
    private BufferedImage healthBarImg;


    private int statusBarWidth = (int) (128 * Game.SCALE);
    private int statusBarHeight = (int) (16 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (128 * Game.SCALE);
    private int healthBarHeight = (int) (16 * Game.SCALE);
    private int healthBarXStart = (int) (10 * Game.SCALE);
    private int healthBarYStart = (int) (10 * Game.SCALE);

    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    //AttackBox

    private Rectangle2D.Float attackBox;

    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        loadAnimations();
        initHitbox(x,y,(int)(20*Game.SCALE),(int)(29*Game.SCALE));
        initAttackBox();
    }
    public void setSpawn(Point spawn){
          this.x = spawn.x;
          this.y = spawn.y;
          hitBox.x = x;
          hitBox.y = y;
    }
    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x,y,(int)(20 *Game.SCALE),(int)(20 *Game.SCALE));
    }

    public void update()
    {
        updateHealthBar();
        if(currentHealth <= 0) {
            playing.setGameOver(true);
            return;
        }
        if(moving)
        {
            checkSpikesTouched();
        }
        updateAttackBox();
        updatePos();
        if(attacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkAttack() {
        if(attackChecked || aniIndex != 1)
            return;
        attackChecked = true;
        playing.checkEnemyHit(attackBox);

    }

    private void updateAttackBox() {
        if(right)
        {
            attackBox.x = hitBox.x + hitBox.width + (int)(Game.SCALE * 10);
        }
        else if (left)
        {
            attackBox.x = hitBox.x - hitBox.width - (int)(Game.SCALE * 10);
        }
        attackBox.y = hitBox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int) ((currentHealth / (float)maxHealth) * healthBarWidth);
    }
    public void changeHealth(int value)
    {
        currentHealth += value;
        if(currentHealth <= 0)
        {
            currentHealth = 0;
            //gameofer
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    public void render(Graphics g, int lvlOffset)
    {
        int widt = animations[playerAction][aniIndex].getWidth();
        int heigh = animations[playerAction][aniIndex].getHeight();

        g.drawImage(animations[playerAction][aniIndex],
                (int) (hitBox.x - xDrawOffset) - lvlOffset + flipX,
                (int) (hitBox.y - yDrawOffset),
                (int)(widt * Game.SCALE) * flipW,
                (int)(heigh * Game.SCALE),null);
        //drawHitbox(g, lvlOffset);
        //drawAttackBox(g,lvlOffset);
        drawUI(g);
    }

    private void drawAttackBox(Graphics g, int lvlOffsetX) {
        g.setColor(Color.RED);
        g.drawRect((int)attackBox.x - lvlOffsetX,(int)attackBox.y,(int)attackBox.width,(int)attackBox.height);
    }

    private void drawUI(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.drawImage(healthBarImg, healthBarXStart, healthBarYStart, healthWidth, healthBarHeight, null);
    }

    private void updateAnimationTick() {
        aniTick++;
        if(aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if(aniIndex >= GetSpriteAmmount(playerAction)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = playerAction;
        if(moving)
            playerAction=RUNNING;
        else
            playerAction=IDLE;

        if(inAir)
            playerAction=JUMPING;

        if(attacking) {
            playerAction = ATTACK1;
            if(startAni != ATTACK1)
            {
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }
        if(startAni != playerAction)
        {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick=0;
        aniIndex=0;
    }

    private void updatePos() {
        moving = false;

        if (jump)
            jump();

        if(!inAir)
            if((!left && !right) || (left && right))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (!inAir)
            if (!IsEntityOnFloor(hitBox, lvlData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlData)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityYPosUnderRoofOrAboveFloor(hitBox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }

    }

    private void loadAnimations(){
        BufferedImage []img = new BufferedImage[6];
        animations = new BufferedImage[6][12];
        img[0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_IDLE_ATLAS);
        img[1] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_RUN_ATLAS);
        for(int j=0;j<8;j++)
        {
            animations[0][j] = img[0].getSubimage(j*32,0,32,32);
            animations[1][j] = img[1].getSubimage(j*32,0,32,32);
        }

        img[2] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_JUMP_ATLAS);
        for(int j=0;j<11;j++)
        {
            animations[2][j] = img[2].getSubimage(j*32,0,32,32);
        }

        img[3] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATTACK1_ATLAS);
        for(int j=0;j<9;j++)
        {
            animations[3][j] = img[3].getSubimage(j*64,0,64,32);
        }

        img[4] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATTACK2_ATLAS);
        for(int j=0;j<12;j++)
        {
            animations[4][j] = img[4].getSubimage(j*80,0,80,32);
        }

        img[5] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_DEATH_ATLAS);
        for(int j=0;j<6;j++)
        {
            animations[5][j] = img[5].getSubimage(j*32,0,32,32);
        }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH_BAR);
        healthBarImg = LoadSave.GetSpriteAtlas(LoadSave.HEALTH);
    }
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if(IsEntityOnFloor(hitBox,lvlData))
            inAir = true;
    }
    public void resetDirBooleans()
    {
        left=false;
        right=false;
        up=false;
        down=false;
    }

    public void setAttacking(boolean attacking)
    {
        this.attacking=attacking;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
    public void setJump(boolean jump)
    {
       this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        playerAction = IDLE;
        currentHealth = maxHealth;
        hitBox.x = x;
        hitBox.y = y;
        if(IsEntityOnFloor(hitBox,lvlData))
            inAir = true;
    }

    public void kill() {
        currentHealth = 0;
    }
}


