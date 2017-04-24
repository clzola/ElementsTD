package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;

public class GameUser {
	protected ElementsTowerDefence game;
	protected World world;
	public MagicPool magicPool;
	
	public GameUser(ElementsTowerDefence game, World world) {
		this.game = game;
		this.world = world;
		this.magicPool = new MagicPool(game, world);
	}

	public void hit(Enemy enemy) {
		this.magicPool.current -= (enemy.hitPoints * 1.7f + this.magicPool.current * 0.05);
		if( magicPool.current < 0 ) {
			magicPool.current = 0;
		}
	}
}
