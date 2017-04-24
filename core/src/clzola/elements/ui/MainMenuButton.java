package clzola.elements.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class MainMenuButton extends ImageButton {
	public MainMenuButton(Texture up, Texture down, Texture over, float x, float y) {
		super(new ImageButtonStyle());
		this.getStyle().up = new SpriteDrawable(new Sprite(up));
		this.getStyle().down = new SpriteDrawable(new Sprite(down));
		this.getStyle().over = new SpriteDrawable(new Sprite(over));
		this.setBounds(x, y, up.getWidth(), up.getHeight());
		this.setName("MainMenuButton");
	}
}
