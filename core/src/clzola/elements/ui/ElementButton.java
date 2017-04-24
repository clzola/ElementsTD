package clzola.elements.ui;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.builders.ElementBuilder;
import clzola.elements.ui.world.ElementsInventory;
import clzola.elements.ui.world.ElementsPanel;
import clzola.elements.world.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class ElementButton extends ImageButton {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected ElementsPanel parent;
	protected ElementsInventory inventory;
	protected float relativeX;
	protected float relativeY;
	
	protected ElementBuilder builder;
	
	public ElementButton(ElementsTowerDefence game, World world, float x, float y, Texture texture, ElementsPanel parent, ElementsInventory inventory) {
		super(new SpriteDrawable(new Sprite(texture)));
		this.sprite = new Sprite(texture);
		this.parent = parent;
		this.game = game;
		this.world = world;
		this.inventory = inventory;
		
		this.relativeX = x;
		this.relativeY = y;
		
		this.setBounds(parent.getX() + relativeX, parent.getY() + relativeY, 32, 32);
		this.setName("ElementButton");
		this.sprite.setPosition(parent.getX() + relativeX, parent.getY() + relativeY);
		
		this.builder = new ElementBuilder(game, world);
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		this.sprite.draw(batch);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		this.setX(parent.getX() + relativeX);
		this.setY(parent.getY() + relativeY);
		this.sprite.setPosition(parent.getX() + relativeX, parent.getY() + relativeY);
	}
}
