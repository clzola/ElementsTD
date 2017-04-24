package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.world.Element;
import clzola.elements.world.Tower;
import clzola.elements.world.Trap;
import clzola.elements.world.World;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class ElementStockButton extends Button {
	protected ElementsTowerDefence game;
	protected World world;
	protected ElementsInventory inventory;
	protected Element element;
	protected ShapeRenderer renderer;
	
	protected float oldX;
	protected float oldY;
	
	public ElementStockButton(ElementsTowerDefence game, World world, ElementsInventory inventory, Element element) {
		super();
		this.game = game;
		this.world = world;
		this.inventory = inventory;
		this.element = element;
		this.element.inventory = inventory;
		setWidth(element.getWidth());
		setHeight(element.getHeight());
		renderer = new ShapeRenderer();
		
		/*addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("ElementStockButton", "Triggered Event click");
			}
		});*/
		
		addListener(new DragListener() {
			@Override public void drag(InputEvent event, float x, float y, int pointer) {
				super.drag(event, x, y, pointer);
				ElementStockButton.this.moveBy(x-16, y-16);
			}
		
			@Override public void dragStart(InputEvent event, float x, float y, int pointer) {
				super.dragStart(event, x, y, pointer);
				ElementStockButton.this.oldX = ElementStockButton.this.getX();
				ElementStockButton.this.oldY = ElementStockButton.this.getY();
			}

			@Override public void dragStop(InputEvent event, float x, float y, int pointer) {
				super.dragStop(event, x, y, pointer);
				Vector2 pos = new Vector2(ElementStockButton.this.getX() + 16, ElementStockButton.this.getY() + 16);
				pos.y = 480 - pos.y;
				pos = ElementStockButton.this.world.getStage().screenToStageCoordinates(pos);
				for( Tower tower : ElementStockButton.this.world.towers ) {
					if( tower.isEmpty() && tower.containsPoint(pos) ) {
						ElementStockButton.this.element.deploy(tower);
						ElementStockButton.this.element = null;
						ElementStockButton.this.inventory.removeElement(ElementStockButton.this);
						return;
					}
				}
				
				for( Trap trap : ElementStockButton.this.world.traps ) {
					if( trap.isEmpty() && trap.containsPoint(pos) ) {
						ElementStockButton.this.element.deploy(trap);
						ElementStockButton.this.element = null;
						ElementStockButton.this.inventory.removeElement(ElementStockButton.this);
						return;
					}
				}
				
				ElementStockButton.this.setPosition(oldX, oldY);
			}
			
		});
	}	

	@Override public void draw(Batch batch, float parentAlpha) {
		element.sprite.draw(batch, parentAlpha);
	}

	@Override protected void positionChanged() {
		super.positionChanged();
		this.element.setPosition(getX(), getY());
	}
	
	
}
