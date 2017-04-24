package clzola.elements.ui;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

public abstract class WorldToggleButton extends Button {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite on;
	protected Sprite off;
	
	public WorldToggleButton(ElementsTowerDefence game, World world, float x, float y, Texture on, Texture off) {
		this.game = game;
		this.world = world;
		this.on = new Sprite(on);
		this.off = new Sprite(off);
		this.setBounds(x, y, on.getWidth(), on.getHeight());
		this.on.setPosition(x, y);
		this.off.setPosition(x, y);
		this.setName("WorldToggleButton");
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if( this.isChecked() ) {
			this.on.draw(batch);
		} else this.off.draw(batch);
	}
	
	public abstract void onWorldClick(float x, float y);
}
