package clzola.elements.builders;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.Tower;
import clzola.elements.world.World;

public class TowerBuilder {
	protected ElementsTowerDefence game;
	protected World world;
	protected float price;
	protected float priceModifier;
	
	public TowerBuilder(ElementsTowerDefence game, World world) {
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
		
		System.out.println(x + " " + y);
		
		if( 0 <= x && x <= 800 && 0 <= y && y <= 480 ) {
			// Can build tower?
			if (world.path.canBuildTower(x, y) == false) {
				// TODO: Message cannot build tower
				return false;
			}
			
			// Create tower
			world.getUser().magicPool.current -= ( price - price*priceModifier );
			Tower tower = new Tower(game, world, x, y);
			world.addTower(tower);
			return true;
		}
		
		return false;
	}
	
	public float getPrice() {
		return price;
	}
}
