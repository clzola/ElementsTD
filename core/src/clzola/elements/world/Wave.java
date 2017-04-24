package clzola.elements.world;

import java.util.LinkedList;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.level.WaveBlueprint;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Wave extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	
	public LinkedList<Enemy> enemies;
	
	public Wave(ElementsTowerDefence game, World world, WaveBlueprint waveBlueprint) {
		this.game = game;
		this.world = world;
		this.enemies = new LinkedList<Enemy>();
		
		while( waveBlueprint.size > 0 ) {
			Enemy enemy = new Enemy(game, world, 
					waveBlueprint.enemy.name, 
					waveBlueprint.enemy.hitPoints, 
					waveBlueprint.enemy.speed, 
					waveBlueprint.enemy.armor, 
					waveBlueprint.enemy.spritesheet);
			
			enemies.add(enemy);
			waveBlueprint.size--;
		}
	}
}
