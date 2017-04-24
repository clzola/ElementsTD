package clzola.elements.world;

import java.util.LinkedList;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.level.LevelBlueprint;
import clzola.elements.level.WaveBlueprint;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class WaveManager extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected LinkedList<Wave> waves;
	
	protected float dispenseTimer;

	public WaveManager(ElementsTowerDefence game, World world) {
		super();
		this.game = game;
		this.world = world;
		this.waves = new LinkedList<Wave>();
		this.dispenseTimer = 1000.0f;
	}

	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			dispenseTimer += delta;
			
			if( dispenseTimer > 30.0f ) {
				dispenseTimer = 0.0f;
				
				Wave wave = waves.poll();
				if( wave == null ) {
					world.stage.getActors().removeValue(this, true);
					return;
				}
				
				pushNextWave(wave);
			}
		}
	}

	public void addWaves(LevelBlueprint level) {
		for (WaveBlueprint wave : level.waves) {
			this.waves.add(new Wave(game, world, wave));
		}
	}
	
	public void pushNextWave(Wave w) {
		LinkedList<SpawnerQueue> spawnQueues = new LinkedList<SpawnerQueue>();
		for(Spawner spawner : world.spawners) {
			spawnQueues.add(spawner.openQueue());
		}
		
		while( w.enemies.isEmpty() == false ) {
			SpawnerQueue spawnerQueue = spawnQueues.poll();
			spawnerQueue.enque(w.enemies.poll());
			spawnQueues.addLast(spawnerQueue);
		}
	}
}
