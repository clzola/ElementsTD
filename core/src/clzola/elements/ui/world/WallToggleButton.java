package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.builders.WallBuilder;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WallToggleButton extends WorldToggleButton {
	private WallBuilder builder;
	
	public WallToggleButton(ElementsTowerDefence game, World world) {
		super(game, world, 760, 369, 
				game.assets.get("gui/world/wall_on.png", Texture.class),
				game.assets.get("gui/world/wall_off.png", Texture.class));
		
		this.setName("WallToggleButton");
		this.builder = new WallBuilder(game, world);
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("WallToggleButton", "Trigger clicked event.");
			}
		});
	}

	@Override
	public void onWorldClick(float x, float y) {
		if( builder.build(x, y) )
			setChecked(false);
		
	}
}
