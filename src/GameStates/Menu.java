package GameStates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
        menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
        menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
        menuX = Game.GAME_WIDTH/2 - menuWidth/2;
        menuY = (int)(0 * Game.SCALE);

    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2,(int)(100* Game.SCALE), 0, GameState.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2,(int)(195* Game.SCALE), 2, GameState.LEADERBOARD);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2,(int)(290* Game.SCALE), 3, GameState.QUIT);

    }

    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg,menuX,menuY,menuWidth,menuHeight,null);
        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void MouseClicked(MouseEvent e) {

    }

    @Override
    public void MousePressed(MouseEvent e) {
        for (MenuButton mb : buttons)
            if(isIn(e,mb)){
                mb.setMousePressed(true);
                break;
            }
    }

    @Override
    public void MouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons)
            if(isIn(e,mb)){
                if(mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons)
        {
            mb.resetBools();
        }
    }

    @Override
    public void MouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons)
        {
            mb.setMouseOver(false);
        }
        for(MenuButton mb : buttons)
        {
            if(isIn(e,mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            GameState.state = GameState.PLAYING;
    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
