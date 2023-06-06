package utils;

import Entities.Destroyer;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import static utils.Constants.EnemyConstants.*;

public class LoadSave {

    public static final String PLAYER_IDLE_ATLAS = "cyber_character.png";
    public static final String PLAYER_RUN_ATLAS = "cyber prisoner run cycle-Sheet.png";
    public static final String PLAYER_JUMP_ATLAS = "cyber prisoner jump cycle -Sheet.png";
    public static final String PLAYER_ATTACK1_ATLAS = "cyber prisoner Light attack slash-Sheet.png";
    public static final String PLAYER_ATTACK2_ATLAS = "cyber prisoner Heavy attack laser -Sheet.png";
    public static final String PLAYER_DEATH_ATLAS = "cyber prisoner death-Sheet.png";
    public static final String LEVEL_ATLAS = "level_tileset.png";
    public static final String MENU_BUTTONS = "Buttons_Project.png";
    public static final String MENU_BACKGROUND = "Menu_Background.jpg";
    public static final String PLAYING_BACKGROUND = "level_background.png";
    public static final String PLAYING_BACKGROUND2 = "level2_background.png";
    public static final String ENEMY_DESTROYER_IDLE = "destroyer_idle.png";
    public static final String ENEMY_DESTROYER_WALK = "destroyer_walk.png";
    public static final String ENEMY_DESTROYER_ATTACK = "destroyer_attack.png";
    public static final String ENEMY_DESTROYER_ATTACK2 = "destroyer_attack2.png";
    public static final String ENEMY_DESTROYER_HIT = "destroyer_hit.png";
    public static final String ENEMY_DESTROYER_DEAD = "destroyer_dead.png";
    public static final String HEALTH_BAR = "no_health_bar.png";
    public static final String HEALTH = "health.png";
    public static final String TRAP_ATLAS = "spike.png";








    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png"))
                    filesSorted[i] = files[j];

            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }

}
