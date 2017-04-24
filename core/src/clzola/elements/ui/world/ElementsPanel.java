package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class ElementsPanel extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	protected Sprite background;
	
	public ElementsToggleButton toggleButton;
	public ElementsInventory inventory;
	public EarthButton earthButton;
	public WindButton windButton;
	public FireButton fireButton;
	public WaterButton waterButton;
	public ArcaneButton arcaneButton;
	public DivineButton divineButton;
	
	public ElementsPanel(ElementsTowerDefence game, World world, ElementsInventory inventory) {
		super();
		this.game = game;
		this.world = world;
		this.background = new Sprite(game.assets.get("gui/world/elements_panel.png", Texture.class));
		
		this.inventory = inventory;
		earthButton = new EarthButton(game, world, this, inventory);
		windButton = new WindButton(game, world, this, inventory);
		fireButton = new FireButton(game, world, this, inventory);
		waterButton = new WaterButton(game, world, this, inventory);
		arcaneButton = new ArcaneButton(game, world, this, inventory);
		divineButton = new DivineButton(game, world, this, inventory);
		setBounds(704, 80, background.getWidth(), background.getHeight());
	}
	
	@Override public void act(float delta) {
		super.act(delta);
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		this.background.draw(batch);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		this.background.setPosition(getX(), getY());
		
		earthButton.setPosition(getX(), getY());
		windButton.setPosition(getX(), getY());
		fireButton.setPosition(getX(), getY());
		waterButton.setPosition(getX(), getY());
		arcaneButton.setPosition(getX(), getY());
		divineButton.setPosition(getX(), getY());
	}

	public void slideIn() {
		if( this.getActions().size == 0 ) {
			inventory.setTouchable(Touchable.disabled);
			MoveByAction moveByAction = new MoveByAction();
			moveByAction.setDuration(0.3f);
			moveByAction.setAmount(-96.0f, 0.0f);
			addAction(moveByAction);
		}
	}
	
	public void slideOut() {
		if( this.getActions().size == 0 ) {
			inventory.setTouchable(Touchable.enabled);
			MoveByAction moveByAction = new MoveByAction();
			moveByAction.setDuration(0.3f);
			moveByAction.setAmount(96.0f, 0.0f);
			addAction(moveByAction);
		}
	}
}
