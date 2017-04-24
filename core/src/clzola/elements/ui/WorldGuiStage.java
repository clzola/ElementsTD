package clzola.elements.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WorldGuiStage {
	public Stage stage;
	public Group background;
	public Group controls;
	public Group elements;
	public Group elementsPanel;
	
	public WorldGuiStage() {
		this.stage = new Stage(new ScreenViewport());
		this.background = new Group();
		this.controls = new Group();
		this.elements = new Group();
		this.elementsPanel = new Group();
		
		this.stage.addActor(background);
		this.stage.addActor(controls);
		this.stage.addActor(elements);
		this.stage.addActor(elementsPanel);
	}

	public void clear() {
		this.background = new Group();
		this.controls = new Group();
		this.elements = new Group();
		this.elementsPanel = new Group();
		this.stage.clear();
	}
}
