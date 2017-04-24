package clzola.elements.screens;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.level.LevelBlueprint;
import clzola.elements.level.MazeRoom;
import clzola.elements.ui.WorldGuiStage;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.ui.world.ElementsInventory;
import clzola.elements.ui.world.ElementsPanel;
import clzola.elements.ui.world.ElementsToggleButton;
import clzola.elements.ui.world.ExitButton;
import clzola.elements.ui.world.PauseButton;
import clzola.elements.ui.world.Play2XButton;
import clzola.elements.ui.world.PlayButton;
import clzola.elements.ui.world.RestartButton;
import clzola.elements.ui.world.TowerToggleButton;
import clzola.elements.ui.world.TrapToggleButton;
import clzola.elements.ui.world.WallToggleButton;
import clzola.elements.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;

public class WorldScreen extends ScreenAdapter {
	private ElementsTowerDefence game;
	private LevelBlueprint level;
	private World world;
	private WorldGuiStage gui;
	private Sprite background;
	private MazeRoom room;
	
	public WorldScreen(ElementsTowerDefence game) {
		this.game = game;		
	}
	
	public void act(float delta) {
		this.world.act(delta);
		this.gui.stage.act(delta);
	}
	
	@Override public void render(float delta) {
		super.render(delta);
		
		// Update
		this.act(delta);
		
		// Draw World
		this.world.draw();
		
		// Draw UI
		Batch batch = this.gui.stage.getBatch();
		batch.begin();
		background.draw(batch);
		batch.end();
		this.gui.stage.draw();
	}

	@Override public void show() {
		super.show();
		
		// Create world
		this.world = new World(this.game, this.level);
		this.world.setMazeRoom(room);
		
		
		// Prepare stage
		this.gui = new WorldGuiStage();
		this.gui.controls.addActor(this.world.getUser().magicPool);
		this.createGuiStage();
		
		// Set Input Processor
		InputMultiplexer inputMultiplexer = new InputMultiplexer(this.gui.stage, this.world.getStage());
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override public void dispose() {
		super.dispose();
		if( this.world != null )
			this.world.dispose();
	}

	public void setLevel(MazeRoom room) {
		this.level = room.level;
		this.room = room;
	}
	
	public void createGuiStage() {
		this.background = new Sprite(game.assets.get("gui/world/gui_background.png", Texture.class));
		this.gui.controls.addActor(new PauseButton(game, world));
		this.gui.controls.addActor(new PlayButton(game, world));
		this.gui.controls.addActor(new Play2XButton(game, world));
		this.gui.controls.addActor(new RestartButton(game, world));
		this.gui.controls.addActor(new ExitButton(game, world));
		
		ElementsInventory inventory = new ElementsInventory(game, world);
		ElementsPanel panel = new ElementsPanel(game, world, inventory);
		
		ButtonGroup<WorldToggleButton> toggleButtons = new ButtonGroup<WorldToggleButton>();
		TowerToggleButton towerButton = new TowerToggleButton(game, world);
		ElementsToggleButton elementsButton = new ElementsToggleButton(game, world, panel);
		TrapToggleButton trapButton = new TrapToggleButton(game, world);
		WallToggleButton wallButton = new WallToggleButton(game, world);
		
		toggleButtons.setMinCheckCount(0);
		toggleButtons.add(towerButton, elementsButton, trapButton, wallButton);	
		
		inventory.setStage(gui);
		panel.toggleButton = elementsButton;
		inventory.elementsToggleButton = elementsButton;
		world.toggleButtons = toggleButtons;
		world.inventoryToggleButtons = inventory.elements;
		
		this.gui.controls.addActor(towerButton);
		this.gui.controls.addActor(elementsButton);
		this.gui.controls.addActor(trapButton);
		this.gui.controls.addActor(wallButton);
		this.gui.controls.addActor(panel.inventory);
		this.gui.elementsPanel.addActor(panel);
		this.gui.elementsPanel.addActor(panel.earthButton);
		this.gui.elementsPanel.addActor(panel.windButton);
		this.gui.elementsPanel.addActor(panel.fireButton);
		this.gui.elementsPanel.addActor(panel.waterButton);
		this.gui.elementsPanel.addActor(panel.arcaneButton);
		this.gui.elementsPanel.addActor(panel.divineButton);
	}
}
