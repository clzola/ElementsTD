package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class Enemy extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	
	public String name;
	public float hitPoints;
	public float speed;
	public float armor;
	public Animation animation;
	
	private float animationTimer;
	private float originalHitPoints;
	private ShapeRenderer renderer;
	
	public Enemy(ElementsTowerDefence game, World world, String name,
			float hitPoints, float speed, float armor, String textureKey) {
		super();
		this.game = game;
		this.world = world;
		this.name = name;
		this.originalHitPoints = hitPoints;
		this.hitPoints = hitPoints;
		this.speed = speed;
		this.armor = armor;
		
		this.renderer = new ShapeRenderer();
		
		Texture texture = game.assets.get(textureKey, Texture.class);
		TextureRegion[][] sprites = TextureRegion.split(texture, 32, 32);
		this.animation = new Animation(0.15f, sprites[0]);
		this.setBounds(0, 0, 32, 32);
		this.setOrigin(16, 16);
		
		fireMoveEvent();
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		TextureRegion frame = animation.getKeyFrame(animationTimer, true);
		batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		
		if(  hitPoints < originalHitPoints ) {
			batch.end();
				renderer.begin(ShapeType.Filled);
					renderer.setColor(new Color(1.0f, 0.0f, 0.0f, 0.2f));
					renderer.rect(getX()+6, getY()+32+4, 20, 6);
					renderer.setColor(new Color(0.0f, 1.0f, 0.0f, 0.2f));
					renderer.rect(getX()+6, getY()+32+4, 20 * (hitPoints/originalHitPoints), 6);
				renderer.end();
			batch.begin();
		}
	}

	@Override public void act(float delta) {
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			super.act(delta);
			animationTimer += delta; 
			
			if( getActions().size == 0 ) {
				fireMoveEvent();
			}
		}
	}

	private void fireMoveEvent() {
		Vector2 dst = findDestination();
		if( dst == null ) {
			return;
		}
		
		MoveToAction moveToAction = new MoveToAction();
		moveToAction.setPosition(dst.x, dst.y);
		moveToAction.setDuration(1.0f / speed);
		addAction(moveToAction);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
	}
	
	public Vector2 findDestination() {
		Vector2 postionVector = getPostionVector();
		int currentX = (int) (postionVector.x / 32);
		int currentY = (int) (postionVector.y / 32);
		int realX = currentX + 1;
		int realY = currentY + 1;
		int realNextX = 0, realNextY = 0;
		boolean found = false;
		
		int[] directionX = { -1, 0, +1, 0 };
        int[] directionY = { 0, -1, 0, +1 };
        int currentValue = world.path.matrix[realY][realX];
        
        int width = world.path.matrix[0].length;
        int height = world.path.matrix.length;
        
        for(int i=0; i<directionX.length; i++) {
        	int nextX = realX + directionX[i];
        	int nextY = realY + directionY[i];
        	
        	if( 0 <= nextX && nextX < width && 0 <= nextY && nextY < height ) {
        		int value = world.path.matrix[nextY][nextX];
        		if( currentValue > value && value > 0) {
        			realNextX = nextX;
        			realNextY = nextY;
        			found = true;
        			break;
        		}
        	}
        }
        
        realNextX--;
        realNextY--;
        	
        if( found == false )
        	return null;
        return new Vector2(realNextX*32, realNextY*32);
		
	}
	
	public Vector2 getPostionVector() {
		return new Vector2(getX() + getOriginX(), getY() + getOriginY());
	}

	public void hitBy(Projectile projectile) {
		float damageTaken = projectile.damage * ( 1 - this.armor / 15.0f );
		this.hitPoints -= damageTaken;
		world.removeProjectile(projectile);
		
		if( this.hitPoints <= 0 ) {
			die();
		}
	}
	
	public void die() {
		float magicPoints = originalHitPoints / 3 * (armor / 2);
		world.getUser().magicPool.current += magicPoints;
		world.removeEnemy(this);
	}

	public void hurtBy(Element element) {
		float damageTaken = element.damage * ( 1 - this.armor / 15.0f );
		this.hitPoints -= damageTaken;
		
		if( this.hitPoints <= 0 ) {
			die();
		}
	}
}
