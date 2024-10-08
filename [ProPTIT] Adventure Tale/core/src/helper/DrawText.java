package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Main;

public class DrawText {
    BitmapFont font;
    public DrawText() {

    }
    public void drText(String path, Color color, String text, float x, float y, float size) {
        font = new BitmapFont(Gdx.files.internal(path));
        font.setColor(color);
        font.getData().setScale(size);
        font.draw(Main.batch, text, x, y);
    }
}