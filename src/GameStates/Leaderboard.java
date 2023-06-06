package GameStates;

import main.Game;
import ui.MenuButton;
import utils.Database;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.Buttons.*;

public class Leaderboard extends State implements StateMethods{

    private Database db;
    private MenuButton home;
    private BufferedImage background;

    public Leaderboard(Game game) {
        super(game);
        db=game.getDb();
        home=new MenuButton(Game.GAME_WIDTH/2,12*Game.TILES_SIZE,3, GameState.MENU);
        background= LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
    }

    @Override
    public void update() {
        home.update();
    }


    private boolean isIn(MenuButton b,MouseEvent e){
        return b.getBounds().contains(e.getX(),e.getY());
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(background,0,0,Game.GAME_WIDTH,Game.GAME_HEIGHT,null);
        home.draw(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,50));
        g.drawString("LEADERBOARD",Game.GAME_WIDTH/2 -200,200);
        String champ[]=db.bestScore();
        g.setColor(Color.RED);
        g.drawString(champ[0],Game.GAME_WIDTH/2-champ[0].length()*11-15,Game.GAME_HEIGHT/2-100);
        g.setColor(Color.PINK);
        g.drawString(champ[1],Game.GAME_WIDTH/2-champ[1].length()*11-15,Game.GAME_HEIGHT/2);
        g.drawString(champ[2],Game.GAME_WIDTH/2-champ[2].length()*11-15,Game.GAME_HEIGHT/2+100);
    }

    @Override
    public void MouseClicked(MouseEvent e) {

    }

    @Override
    public void MousePressed(MouseEvent e) {
        if(isIn(home,e))
            home.setMousePressed(true);
    }

    @Override
    public void MouseReleased(MouseEvent e) {
        if(isIn(home,e)){
            if(home.isMousePressed()) {
                GameState.state=GameState.MENU;
            }
        }
        home.resetBools();
    }

    @Override
    public void MouseMoved(MouseEvent e) {
        home.setMouseOver(false);
        if(isIn(home,e))
            home.setMouseOver(true);
    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
            GameState.state=GameState.MENU;
    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
}
