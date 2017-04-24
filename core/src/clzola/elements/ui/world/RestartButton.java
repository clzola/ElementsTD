package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.WorldControlButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RestartButton extends WorldControlButton {
	public RestartButton(ElementsTowerDefence game, World world) {
		super(game, world, 100, 448, 
				game.assets.get("gui/world/restart_up.png", Texture.class),
				game.assets.get("gui/world/restart_down.png", Texture.class),
				game.assets.get("gui/world/restart_over.png", Texture.class));
		
		this.setName("RestartButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.app.log("RestartButton", "Trigger enter event.");
			}

			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("RestartButton", "Trigger clicked event.");
			}
		});
	}
}
