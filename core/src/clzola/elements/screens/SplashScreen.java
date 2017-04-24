package clzola.elements.screens;

import java.io.File;

import clzola.elements.ElementsTowerDefence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen extends ScreenAdapter {
	private ElementsTowerDefence game;
	private SpriteBatch batch;
	private Sprite background;
	private Sprite gameLogo;
	private SplashScreenState state;
	private float waitTimer;
	
	public SplashScreen(ElementsTowerDefence game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.background = new Sprite(new Texture(Gdx.files.internal("splash_background.jpg")));
		this.gameLogo = new Sprite(new Texture(Gdx.files.internal("logo.png")));
		this.state = SplashScreenState.preload;
		this.waitTimer = 0.0f;
		
		this.background.setPosition(0.0f, 0.0f);
		this.gameLogo.setPosition(Gdx.graphics.getWidth() / 2 - this.gameLogo.getWidth() / 2, Gdx.graphics.getHeight() / 2 - this.gameLogo.getHeight() / 2 + 32);
		
		this.game.screens.register(this);
	}


	@Override 
	public void render(float delta) {	
		this.batch.begin();
		this.background.draw(this.batch);
		this.gameLogo.draw(this.batch);
		
		switch(this.state) {
			case preload:
				this.waitTimer += delta;
				if( this.waitTimer >= 2.0f ) {
					this.state = SplashScreenState.loading;
					this.waitTimer = 0.0f;
					this.loadResources();
				}
				break;
				
			case loading:
				if( this.game.assets.update() ) {
					this.state = SplashScreenState.done;
					this.waitTimer = 0.0f;
					break;
				}
				
				System.out.println(this.game.assets.getProgress() * 100);
				break;
				
			case done:
				this.waitTimer += delta;
				if( this.waitTimer >= 2.0f ) {
					this.state = SplashScreenState.finish;
					this.game.init();
				}
					
				break;
				
		case finish:
			break;
			
		default:
			break;
		}
		
		this.batch.end();
	}

	@Override
	public void dispose() {
		Gdx.app.log("SplashScreen", "Disposing resources.");
		
		super.dispose();
		this.background.getTexture().dispose();
		this.gameLogo.getTexture().dispose();
	}
	
	private enum SplashScreenState {
		preload,
		loading,
		done,
		finish
	}
	
	private void loadResources() {
		loadResources(new File("textures"));
		loadResources(new File("gui"));
		loadResources(new File("sfx"));
	}
	
	private void loadResources(File current) {
		for(File file : current.listFiles()) {
			if( file.isDirectory() ) {
				loadResources(file);
				continue;
			}
			
			loadResource(file);
		}
	}
	
	private void loadResource(File file) {
		// Load Images
		if( file.getName().endsWith(".png") || file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg") ) {
			System.out.println("Queued texture: " + file.getPath());
			this.game.assets.load(file.getPath(), Texture.class);
		}
		
		// Load Sounds
		if( file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".ogg") ) {
			System.out.println("Queued sound: " + file.getPath());
			this.game.assets.load(file.getPath(), Sound.class);
		}
	}
}
