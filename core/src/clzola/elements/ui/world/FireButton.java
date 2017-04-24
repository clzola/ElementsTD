package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.ElementButton;
import clzola.elements.world.World;
import clzola.elements.world.Element.ElementType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FireButton extends ElementButton {
	public FireButton(ElementsTowerDefence game, World world, ElementsPanel panel, ElementsInventory inventory) {
		super(game, world, 8, 88, game.assets.get("gui/world/fire_button.png", Texture.class), panel, inventory);
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("FireButton", "Triggered Clicked Event");
				FireButton.this.inventory.stockElement(FireButton.this.builder.build(ElementType.fire));
			}
		});
	}
}
