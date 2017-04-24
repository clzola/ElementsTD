package clzola.elements.ui.menu;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.MainMenuButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ExitButton extends MainMenuButton {
	private ElementsTowerDefence game;
	
	public ExitButton(ElementsTowerDefence game) {
		super(game.assets.get("gui/menu/exit_up.png", Texture.class), 
				game.assets.get("gui/menu/exit_down.png", Texture.class), 
				game.assets.get("gui/menu/exit_over.png", Texture.class), 
				699.0f, 1.0f);
		
		this.game = game;
		this.setName("ExitButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				Sound clickEffect = ExitButton.this.game.assets.get("sfx/button_click.wav", Sound.class);
				clickEffect.play();
				Gdx.app.exit();
			}
		});
	}
}
