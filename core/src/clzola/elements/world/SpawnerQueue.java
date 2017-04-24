package clzola.elements.world;

import java.util.LinkedList;
import java.util.Queue;

import clzola.elements.ElementsTowerDefence;

public class SpawnerQueue {
	protected ElementsTowerDefence game;
	protected World world;
	protected Queue<Enemy> queue;
	protected float spawnTimer;
	protected Spawner parent;
	
	public SpawnerQueue(ElementsTowerDefence game, World world, Spawner parent) {
		super();
		this.game = game;
		this.world = world;
		this.parent = parent;
		this.queue = new LinkedList<Enemy>();
	}
	
	public void enque(Enemy e) {
		this.queue.add(e);
	}
	
	public boolean isReady(float delta) {
		spawnTimer += delta;
		return ( spawnTimer >= 1.3f ); 
	}
	
	public Enemy spawn() {
		spawnTimer = 0.0f;
		return queue.poll();
	}
}
