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

public class WaterButton extends ElementButton {
	public WaterButton(ElementsTowerDefence game, World world, ElementsPanel panel, ElementsInventory inventory) {
		super(game, world, 8, 128, game.assets.get("gui/world/water_button.png", Texture.class), panel, inventory);
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("WaterButton", "Triggered Clicked Event");
				WaterButton.this.inventory.stockElement(WaterButton.this.builder.build(ElementType.water));
			}
		});
	}
}
