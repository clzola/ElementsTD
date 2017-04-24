package clzola.elements.ui;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class WorldControlButton extends ImageButton {
	protected ElementsTowerDefence game;
	protected World world;
	
	public WorldControlButton(ElementsTowerDefence game, World world, float x, float y, Texture up, Texture down, Texture over) {
		super(new ImageButtonStyle());
		this.getStyle().up = new SpriteDrawable(new Sprite(up));
		this.getStyle().down = new SpriteDrawable(new Sprite(down));
		this.getStyle().over = new SpriteDrawable(new Sprite(over));
		this.setBounds(x, y, up.getWidth(), up.getHeight());
		this.setName("WorldControlButton");
		this.setTouchable(Touchable.enabled);

		this.game = game;
		this.world = world;
	}	
}
