package utils;

import main.Game;

public class Constants {
    public static class ObjectConstants{
        public static final int SPIKE = 50;
        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int) ( SPIKE_WIDTH_DEFAULT * Game.SCALE);
        public static final int SPIKE_HEIGHT = (int) ( SPIKE_HEIGHT_DEFAULT * Game.SCALE);

    }
    public static class EnemyConstants{
        public static final int DESTROYER = 0;
        public static final int INFANTRY = 1;
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int ATTACK = 2;
        public static final int HARD_ATTACK = 3;
        public static final int HIT = 4;
        public static final int DEAD = 5;
        public static final int DESTROYER_WIDTH_DEFAULT =128;
        public static final int DESTROYER_HEIGHT_DEFAULT =128;
        public static final int DESTROYER_WIDTH =(int) ( DESTROYER_WIDTH_DEFAULT * Game.SCALE);
        public static final int DESTROYER_HEIGHT = (int)( DESTROYER_HEIGHT_DEFAULT * Game.SCALE);
        public static final int DESTROYER_DRAWOFFSET_X = (int)(46 * Game.SCALE);
        public static final int DESTROYER_DRAWOFFSET_Y = (int)(51 * Game.SCALE);

        public static int GetSpriteAmmount(int enemy_type, int enemy_state)
        {
            switch (enemy_type)
            {
                case DESTROYER ->
                {
                    switch (enemy_state)
                    {
                        case IDLE:
                            return 5;
                        case WALK:
                            return 8;
                        case ATTACK:
                            return 4;
                        case HARD_ATTACK, HIT:
                            return 3;
                        case DEAD:
                            return 7;
                    }
                }
                case INFANTRY ->
                {
                    switch (enemy_state)
                    {
                        case IDLE, WALK:
                            return 6;
                        case ATTACK:
                            return 17;
                        case HARD_ATTACK, HIT:
                            return 4;
                        case DEAD:
                            return 5;
                    }
                }
            }
            return 0;
        }

        public static int GetMaxHealth(int enemy_type)
        {
            switch (enemy_type){
                case DESTROYER -> {
                    return 10;
                }
                default ->
                {
                    return 1;
                }
            }
        }
        public static int GetEnemyDmg(int enemy_type)
        {
            switch (enemy_type)
            {
                case DESTROYER -> {
                    return 15;
                }
                default ->
                {
                    return 0;
                }
            }
        }

    }
    public static class UI{
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 411;
            public static final int B_HEIGHT_DEFAULT = 165;
            public static final int B_WIDTH = (int)(0.5 * B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(0.5 * B_HEIGHT_DEFAULT * Game.SCALE);

        }
    }
    public static class Directions{
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;

    }

    public static class PlayerConstants{
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int ATTACK1 = 3;
        public static final int ATTACK2 = 4;
        public static final int DEATH = 5;

        public static int GetSpriteAmmount(int playerAction){
            switch (playerAction){
                case IDLE:
                case RUNNING:
                    return 8;
                case JUMPING:
                    return 11;
                case ATTACK1:
                    return 9;
                case ATTACK2:
                    return 12;
                case DEATH:
                    return 6;
                default:
                    return 1;
            }
        }
    }
}
