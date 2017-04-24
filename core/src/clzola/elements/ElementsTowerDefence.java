package clzola.elements;

import clzola.elements.screens.MainMenuScreen;
import clzola.elements.screens.MazeScreen;
import clzola.elements.screens.SkillsScreen;
import clzola.elements.screens.SplashScreen;
import clzola.elements.screens.WorldScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class ElementsTowerDefence extends Game {
	public AssetManager assets;
	public ScreenManager screens;
	
	public void init() {
		this.screens.register(new MainMenuScreen(this));
		this.screens.register(new MazeScreen(this));
		this.screens.register(new SkillsScreen(this));
		this.screens.register(new WorldScreen(this));
		this.setScreen(this.screens.get(MainMenuScreen.class));
	}
	
	@Override public void create () {
		this.assets = new AssetManager();
		this.screens = ScreenManager.getInstance();
		this.setScreen(new SplashScreen(this));
	}

	@Override public void render () {
		Gdx.gl.glClearColor(0.1f, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override public void dispose() {
		Gdx.app.log("ElementsTowerDefence", "Disposing resources.");
		super.dispose();
		this.assets.dispose();
		this.screens.dispose();
	}
}
