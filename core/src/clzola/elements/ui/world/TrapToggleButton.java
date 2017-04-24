package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.builders.TrapBuilder;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TrapToggleButton extends WorldToggleButton {
	private TrapBuilder builder;
	
	public TrapToggleButton(ElementsTowerDefence game, World world) {
		super(game, world, 712, 369, 
				game.assets.get("gui/world/trap_on.png", Texture.class),
				game.assets.get("gui/world/trap_off.png", Texture.class));
		
		this.setName("TrapToggleButton");
		this.builder = new TrapBuilder(game, world);
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("TrapToggleButton", "Trigger clicked event.");
			}
		});
	}

	@Override
	public void onWorldClick(float x, float y) {
		if (builder.build(x, y) )
			this.setChecked(false);
	}
	
	
}
