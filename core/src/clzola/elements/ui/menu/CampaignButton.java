package clzola.elements.ui.menu;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ScreenManager;
import clzola.elements.screens.MazeScreen;
import clzola.elements.ui.MainMenuButton;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CampaignButton extends MainMenuButton {
	private ElementsTowerDefence game;
	
	public CampaignButton(ElementsTowerDefence game) {
		super(game.assets.get("gui/menu/campaign_up.png", Texture.class), 
				game.assets.get("gui/menu/campaign_down.png", Texture.class), 
				game.assets.get("gui/menu/campaign_over.png", Texture.class), 
				96.0f, 354.0f);
		
		this.game = game;
		this.setName("CampaignButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				Sound clickEffect = CampaignButton.this.game.assets.get("sfx/button_click.wav", Sound.class);
				clickEffect.play();
				
				MazeScreen next = (MazeScreen) ScreenManager.getInstance().get(MazeScreen.class);
				CampaignButton.this.game.setScreen(next);
			}
		});
	}
}
