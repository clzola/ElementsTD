package clzola.elements.ui;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ScreenManager;
import clzola.elements.level.MazeRoom;
import clzola.elements.screens.WorldScreen;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MazeRoomButton extends Actor {
	private ElementsTowerDefence game;
	private Sprite faceUp;
	private Sprite completedCarpet;
	private Sprite border;
	private MazeRoom room;
	private boolean isMouseOver;
	
	public MazeRoomButton(ElementsTowerDefence game, float x, float y, Sprite faceUp, MazeRoom room) {
		this.game = game;
		this.faceUp = faceUp;
		this.room = room;
		this.isMouseOver = false;
		
		this.completedCarpet = new Sprite(this.game.assets.get("textures/maze/carpet.png", Texture.class));
		this.border = new Sprite(this.game.assets.get("textures/maze/border.png", Texture.class));
		
		this.setTouchable(Touchable.enabled);
		this.setBounds(x, y, faceUp.getWidth(), faceUp.getHeight());
		this.faceUp.setPosition(x, y);
		this.completedCarpet.setPosition(x, y);
		this.border.setPosition(x, y);
		
		this.addListener(new InputListener(){
			@Override 
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				if( MazeRoomButton.this.room.level.open ) {
					super.enter(event, x, y, pointer, fromActor);
					MazeRoomButton.this.isMouseOver = true;
					
					Sound enterSound = MazeRoomButton.this.game.assets.get("sfx/maze_room_button_over_2.mp3", Sound.class);
					enterSound.play(0.3f);
				}
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if( MazeRoomButton.this.room.level.open ) {
					super.exit(event, x, y, pointer, toActor);
					MazeRoomButton.this.isMouseOver = false;
				}
			}
		});
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if( MazeRoomButton.this.room.level.open ) {
					WorldScreen screen = (WorldScreen) ScreenManager.getInstance().get(WorldScreen.class);
					screen.setLevel(MazeRoomButton.this.room);
					MazeRoomButton.this.game.setScreen(screen);
				}
			}
		});
	}

	@Override public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if( room.level.open ) {
			if( room.level.completed ) 
				this.completedCarpet.draw(batch);	
			this.faceUp.draw(batch);
			
			if( isMouseOver ) 
				this.border.draw(batch);
		}
	}

	@Override public void act(float delta) {
		super.act(delta);
	}
}
