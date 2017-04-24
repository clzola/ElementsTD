package clzola.elements.builders;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.Element;
import clzola.elements.world.World;

public class ElementBuilder {
	protected ElementsTowerDefence game;
	protected World world;
	
	protected float price;
	protected float priceModifier;
	protected float damageModifier;
	protected float rangeModifier;
	protected float fireRateModifier;
	
	public ElementBuilder(ElementsTowerDefence game, World world) {
		super();
		this.game = game;
		this.world = world;
		this.price = 80.0f;
	}
	
	// TODO: applySkills
	public void applySkills() {
		
	}
	
	public Element build(Element.ElementType type) {
		if( world.getUser().magicPool.current < price - price * priceModifier ) 
			return null;
		
		Element element = new Element(game, world, type);
		world.getUser().magicPool.current -= price - price * priceModifier;
		element.damage += element.damage * damageModifier;
		element.fireRange += element.fireRange * rangeModifier;
		element.fireSpeed -= element.fireSpeed * fireRateModifier;
		return element;
	}
	
}
