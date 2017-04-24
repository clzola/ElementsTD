package clzola.elements.screens;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.menu.CampaignButton;
import clzola.elements.ui.menu.ExitButton;
import clzola.elements.ui.menu.HelpButton;
import clzola.elements.ui.menu.OptionsButton;
import clzola.elements.ui.menu.RankModeButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends ScreenAdapter {
	private ElementsTowerDefence game;
	private Sprite background;
	private SpriteBatch batch;
	private Stage stage;
	
	public MainMenuScreen(ElementsTowerDefence game) {
		this.game = game;
		this.background = new Sprite(this.game.assets.get("textures/bg/menu.jpg", Texture.class));
		this.batch = new SpriteBatch();
		this.stage = new Stage(new ScreenViewport(), this.batch);
		
		this.createGuiStage();
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		this.batch.begin();
		this.background.draw(batch);
		this.batch.end();
		
		this.stage.act(delta);
		this.stage.draw();
	}

	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(this.stage);
	}
	
	private void createGuiStage() {
		this.stage.addActor(new CampaignButton(this.game));
		this.stage.addActor(new RankModeButton(this.game));
		this.stage.addActor(new HelpButton(this.game));
		this.stage.addActor(new OptionsButton(this.game));
		this.stage.addActor(new ExitButton(this.game));
	}
}
