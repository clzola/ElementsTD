package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MagicPool extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	private ShapeRenderer renderer;
	
	public float total;
	public float current;
	
	public float replanishRate = 4.5f;
	public float replanishTimer = 0.0f;
	public float replanisRate = 1.0f;
	
	public MagicPool(ElementsTowerDefence game, World world) {
		this.game = game;
		this.world = world;
		this.total = 800;
		this.current = 320;
		this.setBounds(472, 452, 320, 24);
		this.renderer = new ShapeRenderer();
	}
	
	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			this.replanishTimer += delta;
			if( this.replanishTimer >= replanisRate && this.current < this.total ) {
				this.replanishTimer = 0.0f;
				this.current += this.replanishRate;
				if( this.current > this.total )
					this.current = this.total;
			}
		}
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		float percent = current / total;
		batch.end();
		
		// Inner
		this.renderer.begin(ShapeType.Filled);
		this.renderer.setColor(Color.CYAN);
		this.renderer.rect(getX(), getY(), getWidth(), getHeight());
		this.renderer.setColor(Color.BLUE);
		this.renderer.rect(getX(), getY(), getWidth() * percent, getHeight());
		this.renderer.end();
		
		// Border
		this.renderer.begin(ShapeType.Line);
		this.renderer.setColor(Color.WHITE);
		this.renderer.rect(getX(), getY(), getWidth(), getHeight());
		this.renderer.end();
		
		batch.begin();
	}
}
