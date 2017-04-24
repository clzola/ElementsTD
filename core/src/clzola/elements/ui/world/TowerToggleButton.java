package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.builders.TowerBuilder;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TowerToggleButton extends WorldToggleButton {
	private TowerBuilder builder;
	
	public TowerToggleButton(ElementsTowerDefence game, World world) {
		super(game, world, 712, 409, 
				game.assets.get("gui/world/tower_on.png", Texture.class),
				game.assets.get("gui/world/tower_off.png", Texture.class));
		
		this.setName("TowerToggleButton");
		this.builder = new TowerBuilder(game, world);
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("TowerToggleButton", "TowerToggleButton trigger clicked event.");
			}
		});
	}

	@Override public void onWorldClick(float x, float y) {
		if (builder.build(x, y) )
			this.setChecked(false);
	}
}
