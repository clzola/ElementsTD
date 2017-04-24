package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ElementsToggleButton extends WorldToggleButton {
	public ElementsPanel panel;
	
	public ElementsToggleButton(ElementsTowerDefence game, World world, ElementsPanel panel) {
		super(game, world, 760, 409, 
				game.assets.get("gui/world/element_on.png", Texture.class),
				game.assets.get("gui/world/element_off.png", Texture.class));
		
		this.setName("ElementsToggleButton");
		this.panel = panel;
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("ElementToggleButton", "Trigger clicked event.");
			}
		});
	}

	@Override public void setChecked(boolean isChecked) {
		super.setChecked(isChecked);
		
		if( isChecked ) {
			panel.slideIn();
		} else panel.slideOut();
	}

	@Override
	public void onWorldClick(float x, float y) {
		// TODO Auto-generated method stub
		
	}	
	
	
}
