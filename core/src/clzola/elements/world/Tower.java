package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.WorldToggleButton;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Tower extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected Element element;
	
	public Tower(ElementsTowerDefence game, World world, float x, float y) {
		super();
		this.game = game;
		this.world = world;
		this.sprite = new Sprite(game.assets.get("textures/world/tower.png", Texture.class));
		
		setBounds(x, y, 32, 32);
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				event.stop();
				
				WorldToggleButton button = Tower.this.world.toggleButtons.getChecked();
				if ( button != null ) {
					button.setChecked(false);
				}
			}
			
		});
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.sprite.draw(batch);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		sprite.setPosition(getX(), getY());
	}

	public boolean containsPoint(Vector2 pos) {
		if( getX() <= pos.x && pos.x <= getX()+getWidth() && getY() <= pos.y && pos.y <= getY()+getHeight() )
			return true;
		return false;
	}

	public boolean isEmpty() {
		return this.element == null;
	}
}
