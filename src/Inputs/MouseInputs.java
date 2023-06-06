package Inputs;

import main.Game;
import main.GamePanel;
import GameStates.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameState.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().MouseClicked(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().MousePressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().MousePressed(e);
                break;
            case LEADERBOARD:
                gamePanel.getGame().getLeaderboard().MousePressed(e);
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().MouseReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().MouseReleased(e);
                break;
            case LEADERBOARD:
                gamePanel.getGame().getLeaderboard().MouseReleased(e);
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGame().getMenu().MouseMoved(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().MouseMoved(e);
                break;
            case LEADERBOARD:
                gamePanel.getGame().getLeaderboard().MouseMoved(e);
                break;
        }
    }
}
