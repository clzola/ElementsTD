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

public class PlayButton extends WorldControlButton {
	public PlayButton(ElementsTowerDefence game, World world) {
		super(game, world, 36, 448, 
				game.assets.get("gui/world/play_up.png", Texture.class),
				game.assets.get("gui/world/play_down.png", Texture.class),
				game.assets.get("gui/world/play_over.png", Texture.class));
		
		this.setName("PlayButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.app.log("PlayButton", "Trigger enter event.");
			}

			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("PlayButton", "Trigger clicked event.");
				PlayButton.this.world.state = WorldState.normalSpeed;
			}
		});
	}
}
