package clzola.elements.ui.menu;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.MainMenuButton;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsButton extends MainMenuButton {
	private ElementsTowerDefence game;
	
	public OptionsButton(ElementsTowerDefence game) {
		super(game.assets.get("gui/menu/options_up.png", Texture.class), 
				game.assets.get("gui/menu/options_down.png", Texture.class), 
				game.assets.get("gui/menu/options_over.png", Texture.class), 
				282.0f, 254.0f);
		
		this.game = game;
		this.setName("OptionsButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				Sound clickEffect = OptionsButton.this.game.assets.get("sfx/button_click.wav", Sound.class);
				clickEffect.play();
			}
		});
	}
}
