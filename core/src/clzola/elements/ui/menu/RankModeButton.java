package clzola.elements.ui.menu;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.MainMenuButton;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RankModeButton extends MainMenuButton {
	private ElementsTowerDefence game;
	
	public RankModeButton(ElementsTowerDefence game) {
		super(game.assets.get("gui/menu/rank_up.png", Texture.class), 
				game.assets.get("gui/menu/rank_down.png", Texture.class), 
				game.assets.get("gui/menu/rank_over.png", Texture.class), 
				282.0f, 354.0f);
		
		this.game = game;
		this.setName("RankModeButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				Sound clickEffect = RankModeButton.this.game.assets.get("sfx/button_click.wav", Sound.class);
				clickEffect.play();
			}
		});
	}
}
