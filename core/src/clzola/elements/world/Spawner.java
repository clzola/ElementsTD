package clzola.elements.world;

import java.util.Iterator;
import java.util.LinkedList;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Spawner extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected LinkedList<SpawnerQueue> spawnerQueues;
	
	public Spawner(ElementsTowerDefence game, World world, float x, float y, String texture) {
		super();
		this.game = game;
		this.world = world;
		this.spawnerQueues = new LinkedList<SpawnerQueue>();
		
		if( texture != null ) {
			this.sprite = new Sprite(game.assets.get(texture, Texture.class));
			setBounds(x - sprite.getOriginX() + 16, y - sprite.getOriginY() + 16, sprite.getWidth(), sprite.getHeight());
			setOrigin(sprite.getOriginX(), sprite.getOriginY());
		} else {
			setBounds(x, y, 32, 32);
			setOrigin(16, 16);
		}
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		if( sprite != null )
			this.sprite.draw(batch);
	}

	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			Iterator<SpawnerQueue> itQueue = this.spawnerQueues.iterator();
			while( itQueue.hasNext() ) {
				SpawnerQueue queue = itQueue.next();
				if( queue.isReady(delta) ) {
					Enemy e = queue.spawn();
					if( e != null ) {
						e.setPosition(getX() + getOriginX() - 16, getY() + getOriginY() - 16);
						world.addEnemy(e);
					} else {
						itQueue.remove();
					}
				}
			}
		}
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		if( sprite != null )
			this.sprite.setPosition(getX(), getY());
	}
	
	public SpawnerQueue openQueue() {
		SpawnerQueue queue = new SpawnerQueue(game, world, this);
		this.spawnerQueues.add(queue);
		return queue;
	}
}
