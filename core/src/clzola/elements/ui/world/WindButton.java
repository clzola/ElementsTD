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

public class WindButton extends ElementButton {
	public WindButton(ElementsTowerDefence game, World world, ElementsPanel panel, ElementsInventory inventory) {
		super(game, world, 8, 48, game.assets.get("gui/world/wind_button.png", Texture.class), panel, inventory);
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("WindButton", "Triggered Clicked Event");
				WindButton.this.inventory.stockElement(WindButton.this.builder.build(ElementType.wind));
			}
		});
	}
}
