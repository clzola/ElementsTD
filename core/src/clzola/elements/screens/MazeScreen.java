package clzola.elements.screens;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ScreenManager;
import clzola.elements.level.MazeGraph;
import clzola.elements.level.MazeGraphNode;
import clzola.elements.level.MazeRoom;
import clzola.elements.level.VertexNode;
import clzola.elements.ui.MainMenuButton;
import clzola.elements.ui.MazeRoomButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MazeScreen extends ScreenAdapter {
	private ElementsTowerDefence game;
	private SpriteBatch batch;
	private Stage stage;
	private MazeGraph graph;
	
	private Sprite background;
	
	public MazeScreen(ElementsTowerDefence game) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.stage = new Stage(new ScreenViewport(), this.batch);
		
		this.background = new Sprite(game.assets.get("textures/bg/bgd.jpg", Texture.class));
		graph = MazeGraph.load();
		this.createGuiStage();
	}

	@Override public void render(float delta) {
		super.render(delta);
		
		this.batch.begin();
		this.background.draw(batch);
		this.batch.end();
		
		this.stage.act();
		this.stage.draw();
	}

	@Override public void show() {
		super.show();
		Gdx.input.setInputProcessor(this.stage);
	}
	
	private void createGuiStage() {
		Texture labyrinth = this.game.assets.get("textures/maze/map.png", Texture.class);
		TextureRegion[][] roomRegions = TextureRegion.split(labyrinth, 48, 48);
		
		float offsetX = 100;
		float offsetY = 55;
		
		MazeRoom placeholder[][] = new MazeRoom[8][8];
		
		for(int y = 7; y >= 0; y --) {
			for(int x = 0; x < 8; x++) {
				MazeRoom room = new MazeRoom(this.game, "levels/prototype.json");
				placeholder[y][x] = room;
				//room.level.open = true;
				Sprite faceUp = new Sprite(roomRegions[y][x]);
				this.stage.addActor(new MazeRoomButton(this.game, offsetX + x*48, offsetY + (7-y)*48, faceUp, room));
				
				// Prototype Opened Level
				if( x == 2 && y == 7 ) {
					room.level.open = true;
					room.level.completed = true;
				}
			}
		}
		
		for(MazeGraphNode node : graph.rooms) {
			for( VertexNode v : node.vertices ) {
				placeholder[node.y][node.x].addSuccessorRoom(placeholder[v.y][v.x]);
			}
		}
		
		for(MazeRoom RR : placeholder[7][2].successors)
			RR.level.open = true;
		
		MainMenuButton backButton = new MainMenuButton(
				game.assets.get("gui/maze/back_up.png", Texture.class), 
				game.assets.get("gui/maze/back_down.png", Texture.class), 
				game.assets.get("gui/maze/back_over.png", Texture.class), 
				600, 55);
		
		backButton.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				game.setScreen(ScreenManager.getInstance().get(MainMenuScreen.class));
			}
		});
		
		stage.addActor(backButton);
	}
}
