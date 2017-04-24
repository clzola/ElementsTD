package clzola.elements.world;

import java.util.ArrayList;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.level.LevelBlueprint;
import clzola.elements.level.MazeRoom;
import clzola.elements.level.SpawnerBlueprint;
import clzola.elements.ui.WorldToggleButton;
import clzola.elements.ui.world.ElementStockButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.net.httpserver.Authenticator.Success;

public class World {
	protected ElementsTowerDefence game;
	protected SpriteBatch batch;
	protected Stage stage;
	protected boolean debugOn = false;
	protected MazeRoom room;
	
	private LevelBlueprint level;
	private GameUser user;
	
	protected OrthographicCamera camera;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer tiledMapRenderer;
	private ShapeRenderer grid;
	private WaveManager waves;
	
	private Group towerGroup;
	private Group wallGroup;
	private Group trapGroup;
	private Group elementGroup;
	private Group spawnerGroup;
	private Group enemyGroup;
	private Group projectileGroup;
	public ArrayList<Tower> towers;
	public ArrayList<Wall> walls;
	public ArrayList<Trap> traps;
	public ArrayList<Element> elements;
	public ArrayList<Spawner> spawners;
	public ArrayList<Enemy> enemies;
	public ArrayList<Projectile> projectiles;
	
	public ButtonGroup<WorldToggleButton> toggleButtons;
	public ButtonGroup<ElementStockButton> inventoryToggleButtons;
	public PathMatrix path;
	public WorldState state;
	
	public World(ElementsTowerDefence game, LevelBlueprint levelBlueprint) {
		this.game = game;
		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera(800, 480);
		this.stage = new Stage(new ScreenViewport(camera), batch);
		this.level = levelBlueprint;
		this.user = new GameUser(game, this);
		this.path = new PathMatrix(levelBlueprint.path, this);
		this.grid = new ShapeRenderer();
		this.state = WorldState.paused;
		
		// Load TiledMap
		this.tiledMap = new TmxMapLoader().load(level.tiledMap);
		this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1.0f);
		this.camera.translate(new Vector2(800/2, 480/2));
		
		this.towers = new ArrayList<Tower>();
		this.walls = new ArrayList<Wall>();
		this.traps = new ArrayList<Trap>();
		this.elements = new ArrayList<Element>();
		this.spawners = new ArrayList<Spawner>();
		this.enemies = new ArrayList<Enemy>();
		this.projectiles = new ArrayList<Projectile>();
		
		this.towerGroup = new Group();
		this.wallGroup = new Group();
		this.trapGroup = new Group();
		this.elementGroup = new Group();
		this.spawnerGroup = new Group();
		this.enemyGroup = new Group();
		this.projectileGroup = new Group();
		
		this.stage.addActor(trapGroup);
		this.stage.addActor(towerGroup);
		this.stage.addActor(enemyGroup);
		this.stage.addActor(wallGroup);
		this.stage.addActor(elementGroup);
		this.stage.addActor(projectileGroup);
		this.stage.addActor(spawnerGroup);
		
		//Enemy enemy = new Enemy(game, this, "Hello", 10.0f, 1.0f, 1.0f, "textures/enemies/blue_slime.png");
		//enemy.setPosition(3*32, 1*32);
		//addEnemy(enemy);
		
		waves = new WaveManager(game, this);
		waves.addWaves(level);
		spawnerGroup.addActor(waves);

		this.trapGroup.addActor(new LifeOrb(game, this, path.end.x*32-32, path.end.y*32-32));
		
		for(SpawnerBlueprint spawnerBlueprint : levelBlueprint.spawners) {
			Spawner spawner = new Spawner(game, this, spawnerBlueprint.x*32, spawnerBlueprint.y*32, spawnerBlueprint.texture);
			addSpawner(spawner);
		}
		
		this.stage.addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Vector2 vec = World.this.stage.screenToStageCoordinates(new Vector2(x, y));
				System.out.println("W: " + vec);
				
				WorldToggleButton toggleButton = toggleButtons.getChecked();
				if(toggleButton != null) toggleButton.onWorldClick(vec.x, vec.y);
				
				ElementStockButton stockButton = World.this.inventoryToggleButtons.getChecked();
				if ( stockButton != null )
					stockButton.setChecked(false);
			}

			@Override
			public boolean keyTyped(InputEvent event, char character) {
				// TODO Auto-generated method stub
				if( character == 'f' )
					debugOn = !debugOn;
				return super.keyTyped(event, character);
			}
			
			
		});
	}

	public void act(float delta) {
		this.handleInput();
		this.camera.update();
		this.batch.setProjectionMatrix(this.camera.combined);
		this.tiledMapRenderer.setView(camera);
		this.stage.act();
	}
	
	public void draw() {
		this.tiledMapRenderer.render();
		this.stage.draw();
		this.debugDraw();
	}
	
	protected void debugDraw() {
		if( debugOn == false )
			return;
		
		/*this.path.draw(batch, game.assets.get("textures/world/path.png", Texture.class));
		
		grid.begin(ShapeType.Line);
			grid.setColor(Color.WHITE);
			for(int i=0; i<480; i+=32) {
				grid.line(0, i, 800, i, );
			}
			
			for(int i=0; i<800; i+=32) {
				grid.line(i, 0, i, 480);
			}
		
		grid.end();*/
	}
	
	private void handleInput() {
		
	  //////////////////////////////////////////////////////////////////////////
	  // Camera Input
	  //
		
		if( Gdx.input.isKeyPressed(Input.Keys.Z) ) {
			this.camera.zoom += 0.02f;
			if( this.camera.zoom >= 1.0f )
				this.camera.zoom = 1.0f;
		} else if ( Gdx.input.isKeyPressed(Input.Keys.X) ) {
			this.camera.zoom -= 0.02f;
		}
		
		if ( Gdx.input.isKeyPressed(Input.Keys.A) ) {
			this.camera.translate(-3f, 0.0f);
		} else if ( Gdx.input.isKeyPressed(Input.Keys.D) ) {
			this.camera.translate(+3f, 0.0f);
		} else if ( Gdx.input.isKeyPressed(Input.Keys.S) ) {
			this.camera.translate(0.0f, -3f);
		} else if ( Gdx.input.isKeyPressed(Input.Keys.W) ) {
			this.camera.translate(0.0f, 3f);
		} 
		
		this.camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, camera.viewportWidth);
		
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 800 - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 480 - effectiveViewportHeight / 2f);
	}
	
	public Stage getStage() {
		return this.stage;
	}
	
	public GameUser getUser() {
		return this.user;
	}
	
	public void dispose() {
		this.tiledMap.dispose();
	}
	
	public void addTower(Tower tower) {
		this.towers.add(tower);
		this.towerGroup.addActor(tower);
	}

	public void addWall(Wall wall) {
		this.walls.add(wall);
		this.wallGroup.addActor(wall);
		this.path.buildWall(wall.getX(), wall.getY());
		
	}

	public void addTrap(Trap trap) {
		this.traps.add(trap);
		this.trapGroup.addActor(trap);
		
	}

	public void addElement(Element element) {
		this.elements.add(element);
		this.elementGroup.addActor(element);
		
	}
	
	public void addSpawner(Spawner spawner) {
		this.spawners.add(spawner);
		this.spawnerGroup.addActor(spawner);
	}
	
	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
		this.enemyGroup.addActor(enemy);
	}
	
	public void removeEnemy(Enemy enemy) {
		this.enemies.remove(enemy);
		//this.enemyGroup.removeActor(enemy);
		enemy.remove();
		
		if( this.enemies.size() == 0 && waves.waves.size() == 0 && user.magicPool.current > 0 ) {
			// Win
			level.completed = true;
			for(MazeRoom r : room.successors) {
				r.level.open = true;
			}
			
		}
	}

	public void removeElement(Element element) {
		this.elementGroup.removeActor(element, true);
		this.elements.remove(element);
	}
	
	public void addProjectile(Projectile projectile) {
		this.projectiles.add(projectile);
		this.projectileGroup.addActor(projectile);
	}
	
	public void removeProjectile(Projectile projectile) {
		this.projectiles.remove(projectile);
		projectile.remove();
	}
	
	public void setMazeRoom(MazeRoom room) {
		this.room = room;
	}
	
	public enum WorldState {
		paused,
		normalSpeed,
		speed2x
	}
}
