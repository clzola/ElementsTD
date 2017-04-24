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

public class Play2XButton extends WorldControlButton {
	public Play2XButton(ElementsTowerDefence game, World world) {
		super(game, world, 68, 448, 
				game.assets.get("gui/world/play2x_up.png", Texture.class),
				game.assets.get("gui/world/play2x_down.png", Texture.class),
				game.assets.get("gui/world/play2x_over.png", Texture.class));
		
		this.setName("Play2XButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.app.log("Play2XButton", "Trigger enter event.");
			}

			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("Play2XButton", "Trigger clicked event.");
			}
		});
	}
}
