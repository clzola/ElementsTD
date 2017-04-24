package clzola.elements.builders;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.Wall;
import clzola.elements.world.World;

public class WallBuilder {
	protected ElementsTowerDefence game;
	protected World world;
	protected float price;
	protected float priceModifier;
	
	public WallBuilder(ElementsTowerDefence game, World world) {
		super();
		this.game = game;
		this.world = world;
		this.price = 60.0f;
	}

	// TODO: ApplySkills
	public void applySkills() {
		
	}
	
	public boolean build(float x, float y) {
		if( world.getUser().magicPool.current < price - price*priceModifier ) {
			return false;
		}
		
		x = (int)(x / 32) * 32.0f; // Normalize
		y = 448 - (int)(y / 32) * 32.0f;
		
		if( 0 <= x && x <= 800 && 0 <= y && y <= 480 ) {
			if( world.path.canBuildWall(x, y) == false ) {
				// TODO: Message cannot build tower
				return false;
			}
			
			world.getUser().magicPool.current -= ( price - price*priceModifier );
			Wall wall = new Wall(game,world);
			wall.setPosition(x, y);
			world.addWall(wall);
			return true;
		}
		
		return false;
	}
}
