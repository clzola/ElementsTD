package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.WorldGuiStage;
import clzola.elements.world.Element;
import clzola.elements.world.Element.ElementState;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ElementsInventory extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite sprite;
	protected WorldGuiStage stage;
	
	public ButtonGroup<ElementStockButton> elements;
	public ElementsToggleButton elementsToggleButton;
	
	public ElementsInventory(ElementsTowerDefence game, World world) {
		super();
		this.game = game;
		this.world = world;
		this.sprite = new Sprite(game.assets.get("gui/world/inventory.png", Texture.class));
		this.setBounds(712, 80, sprite.getWidth(), sprite.getHeight());
		this.sprite.setPosition(getX(), getY());
		this.elements = new ButtonGroup<ElementStockButton>();
		this.elements.setMinCheckCount(0);
		
		addListener(new ClickListener() {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("ElementsInventory", "Triggered ClickedEvent");
				elements.uncheckAll();
			}	
		});
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		this.sprite.draw(batch);
	}

	@Override public void act(float delta) {
		super.act(delta);
	}
	
	@Override protected void positionChanged() {
		super.positionChanged();
		this.sprite.setPosition(getX(), getY());
	}
	
	public void stockElement(Element element) {
		if( element == null ) {
			// TODO: Show Message no enough magic points.
			return;
		}
		
		if( element.state == ElementState.deployed || element.state == ElementState.trap )
			world.removeElement(element);
		element.state = ElementState.inventory;
		
		float x = 716 + elements.getButtons().size % 2 * 34;
		float y = 84 + elements.getButtons().size / 2 * 34; 
		element.setPosition(x, y);
		ElementStockButton button = new ElementStockButton(game, world, this, element);
		button.setZIndex(this.getZIndex() + 1);
		button.setPosition(x, y);
		stage.elements.addActor(button);
		elements.add(button);
		elementsToggleButton.setChecked(false);
	}

	public void setStage(WorldGuiStage stage) {
		this.stage = stage;
	}

	public void removeElement(ElementStockButton elementStockButton) {
		this.elements.remove(elementStockButton);
		this.stage.elements.removeActor(elementStockButton, true);
	}
}
