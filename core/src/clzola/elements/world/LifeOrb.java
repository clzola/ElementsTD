package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LifeOrb extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	
	private ShapeRenderer renderer;
	
	public LifeOrb(ElementsTowerDefence game, World world, float x, float y) {
		this.game = game;
		this.world = world;
		this.sprite = new Sprite(game.assets.get("textures/world/life_orb.png", Texture.class));
		setBounds(x, y, 32, 32);
		setOrigin(16, 16);
		
		this.renderer = new ShapeRenderer();
	}

	@Override
	protected void positionChanged() {
		// TODO Auto-generated method stub
		super.positionChanged();
		this.sprite.setPosition(getX(), getY());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
		this.sprite.draw(batch);
		
		debugDraw(batch);
	}
	
	private void debugDraw(Batch batch) {
		if( world.debugOn ) {
			batch.end();
				renderer.setProjectionMatrix(world.camera.combined);
			
				renderer.begin(ShapeType.Line);
					renderer.setColor(new Color(0x00FF08FF));
					
					Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
					for(Enemy enemy : world.enemies) {
						Vector2 p = enemy.getPostionVector();
						renderer.line(v, p);
					}
					
				renderer.end();
			batch.begin();
		}

	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
		for(Enemy enemy : world.enemies) {
			Vector2 p = enemy.getPostionVector();
			if( Math.abs(p.dst(v)) < 24.0f ) {
				this.world.getUser().hit(enemy);
				this.world.removeEnemy(enemy);
				break;
			}
		}
	}
	
	
	
}
