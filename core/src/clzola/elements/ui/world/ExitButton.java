package clzola.elements.ui.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ScreenManager;
import clzola.elements.screens.MazeScreen;
import clzola.elements.ui.WorldControlButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ExitButton extends WorldControlButton {
	public ExitButton(ElementsTowerDefence game, World world) {
		super(game, world, 132, 448, 
				game.assets.get("gui/world/exit_up.png", Texture.class),
				game.assets.get("gui/world/exit_down.png", Texture.class),
				game.assets.get("gui/world/exit_over.png", Texture.class));
		
		this.setName("ExitButton");
		
		this.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				Gdx.app.log("ExitButton", "Trigger enter event.");
			}

			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.log("ExitButton", "Trigger clicked event.");
				MazeScreen screen = (MazeScreen) ScreenManager.getInstance().get(MazeScreen.class);
				ExitButton.this.game.setScreen(screen);
			}
		});
	}
}
