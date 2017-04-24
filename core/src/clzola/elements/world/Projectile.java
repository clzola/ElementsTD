package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.Element.ElementType;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected ElementType type;
	protected Enemy target;
	protected ShapeRenderer renderer;
	protected float speed;
	
	public float damage;
	
	public Projectile(ElementsTowerDefence game, World world, ElementType type) {
		super();
		this.game = game;
		this.world = world;
		this.type = type;
		this.renderer = new ShapeRenderer();
		this.speed = 6.0f;
		
		switch(type) {
		case arcane:
			this.sprite = new Sprite(game.assets.get("textures/world/p_arcane.png", Texture.class));
			break;
		case divine:
			this.sprite = new Sprite(game.assets.get("textures/world/p_divine.png", Texture.class));
			break;
		case earth:
			this.sprite = new Sprite(game.assets.get("textures/world/p_earth.png", Texture.class));
			break;
		case fire:
			this.sprite = new Sprite(game.assets.get("textures/world/p_fire.png", Texture.class));
			break;
		case water:
			this.sprite = new Sprite(game.assets.get("textures/world/p_water.png", Texture.class));
			break;
		case wind:
			this.sprite = new Sprite(game.assets.get("textures/world/p_wind.png", Texture.class));
			break;		
		}
		
		setSize(8, 8);
		setOrigin(4, 4);
	}
	
	public void setTarget(Enemy e) {
		target = e;
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		this.sprite.draw(batch);
		
		if( world.debugOn ) {
			Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
			Vector2 p = target.getPostionVector();
			
			batch.end();
				renderer.begin(ShapeType.Line);
					renderer.setColor(Color.BLUE);
					renderer.line(v, p);
				renderer.end();
			batch.begin();
		}
	}

	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {	
			if( target != null ) {
				Vector2 position = new Vector2(getX()+getOriginX(), getY()+getOriginY());
				Vector2 targetPosition = new Vector2(target.getX()+getOriginX(), target.getY()+target.getOriginY());
				if( position.dst(targetPosition) <= 16.0f ) {
					target.hitBy(this);
					return;
				}
				
				move(delta);
			} else {
				world.removeProjectile(this);
			}
		}
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		this.sprite.setPosition(getX(), getY());
	}
	
	private void move(float delta) {
        Vector2 position = new Vector2(getX()+getOriginX(), getY()+getOriginY());
        Vector2 targetPosition = new Vector2(target.getX()+getOriginX(), target.getY()+target.getOriginY());
        Vector2 velocity = targetPosition.sub(position).nor();
        velocity = velocity.scl(32 * speed * delta);
        moveBy(velocity.x, velocity.y);

    }
	
	
}
