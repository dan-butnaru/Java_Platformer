package GameStates;

import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelCompletedUI {
    private Playing playing;
    public LevelCompletedUI(Playing playing)
    {
        this.playing = playing;
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.setColor(Color.white);
        g.drawString("Level Completed!", Game.GAME_WIDTH / 2, 150);

        g.drawString("Press Enter to continue or ESC to go to main menu!", Game.GAME_WIDTH / 2, 300);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            GameState.state = GameState.MENU;
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            playing.loadNextLevel();
        }
    }
}
