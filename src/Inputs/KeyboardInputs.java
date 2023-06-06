package Inputs;

import main.GamePanel;
import GameStates.GameState;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static utils.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().KeyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().KeyPressed(e);
                break;
            case LEADERBOARD:
                gamePanel.getGame().getLeaderboard().KeyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
            switch (GameState.state) {
                case MENU:
                    gamePanel.getGame().getMenu().KeyPressed(e);
                    break;
                case PLAYING:
                    gamePanel.getGame().getPlaying().KeyReleased(e);
                    break;
                default:
                    break;
            }
    }
}
