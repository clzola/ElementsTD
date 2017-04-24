package clzola.elements.ui.menu;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.MainMenuButton;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HelpButton extends MainMenuButton {
	private ElementsTowerDefence game;
	
	public HelpButton(ElementsTowerDefence game) {
		super(game.assets.get("gui/menu/help_up.png", Texture.class), 
				game.assets.get("gui/menu/help_down.png", Texture.class), 
				game.assets.get("gui/menu/help_over.png", Texture.class), 
				96.0f, 254.0f);
		
		this.game = game;
		this.setName("HelpButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				Sound clickEffect = HelpButton.this.game.assets.get("sfx/button_click.wav", Sound.class);
				clickEffect.play();
			}
		});
	}
}
