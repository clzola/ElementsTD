package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.WorldControlButton;
import clzola.elements.world.World;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseButton extends WorldControlButton {
	private WorldState previousState;
	
	public PauseButton(ElementsTowerDefence game, World world) {
		super(game, world, 4, 448, 
				game.assets.get("gui/world/pause_up.png", Texture.class),
				game.assets.get("gui/world/pause_down.png", Texture.class),
				game.assets.get("gui/world/pause_over.png", Texture.class));
		
		this.setName("PauseButton");
		this.previousState = WorldState.normalSpeed;
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.app.log("PauseButton", "Trigger enter event.");
			}

			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("PauseButton", "Trigger clicked event.");
				
				if ( PauseButton.this.world.state == WorldState.paused ) {
					Gdx.app.log("PauseButton", "world Unpaused.");
					PauseButton.this.world.state = PauseButton.this.previousState;
				} else {
					Gdx.app.log("PauseButton", "world Paused.");
					PauseButton.this.world.state = WorldState.paused;
				}
			}
		});
	}
}
