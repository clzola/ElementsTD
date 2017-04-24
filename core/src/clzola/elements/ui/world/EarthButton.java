package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.ElementButton;
import clzola.elements.world.Element.ElementType;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EarthButton extends ElementButton {
	public EarthButton(ElementsTowerDefence game, World world, ElementsPanel panel, ElementsInventory inventory) {
		super(game, world, 8, 8, game.assets.get("gui/world/earth_button.png", Texture.class), panel, inventory);
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("EarthButton", "Triggered Clicked Event");
				EarthButton.this.inventory.stockElement(EarthButton.this.builder.build(ElementType.earth));
			}
		});
	}
}
