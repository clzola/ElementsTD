package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Trap extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected Element element;
	
	public Trap(ElementsTowerDefence game, World world, float x, float y) {
		super();
		this.game = game;
		this.world = world;
		this.sprite = new Sprite(game.assets.get("textures/world/trap.png", Texture.class));
		setBounds(x, y, 32, 32);
	}
	
	

	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			// Act here
		}
	}



	@Override public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if( this.element != null )
			this.element.sprite.draw(batch, parentAlpha);
		this.sprite.draw(batch);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		this.sprite.setPosition(getX(), getY());
	}
	
	public void putElement(Element element) {
		this.element = element;
	}

	public Element pullElement() {
		return element;
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return element == null;
	}

	public boolean containsPoint(Vector2 pos) {
		if( getX() <= pos.x && pos.x <= getX()+getWidth() && getY() <= pos.y && pos.y <= getY()+getHeight() )
			return true;
		return false;
	}
}
