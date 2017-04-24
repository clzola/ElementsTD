package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Wall extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	
	public Wall(ElementsTowerDefence game, World world) {
		super();
		this.game = game;
		this.world = world;
		this.sprite = new Sprite(game.assets.get("textures/world/wall2.png", Texture.class));
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		sprite.draw(batch);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		sprite.setPosition(getX(), getY());
	}	
}
