package clzola.elements.world;

import clzola.elements.ElementsTowerDefence;
import clzola.elements.ui.world.ElementsInventory;
import clzola.elements.world.World.WorldState;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class Element extends Actor {
	protected ElementsTowerDefence game;
	protected World world;
	public Sprite sprite;
	protected float fireTimer;
	public ElementsInventory inventory;
	
	public Tower tower;
	public Trap trap;
	public ElementType type;
	public ElementState state;
	public String name;
	public float damage;
	public float fireRange;
	public float fireSpeed;

	private Tower oldTower;
	private Trap oldTrap;
	
	protected Enemy target;
	
	public Element(ElementsTowerDefence game, World world, ElementType type) {
		super();
		this.game = game;
		this.world = world;
		this.type = type;
		this.setOrigin(16.0f, 16.0f);
		this.setWidth(32);
		this.setHeight(32);
		
		switch(type) {
			case arcane:
				this.sprite = new Sprite(game.assets.get("textures/world/arcane_32.png", Texture.class));
				this.name = "Arcane Element";
				this.damage = 10.0f;
				this.fireSpeed = 1.0f;
				this.fireRange = 80.0f;
				break;
			case divine:
				this.sprite = new Sprite(game.assets.get("textures/world/divine_32.png", Texture.class));
				this.name = "Divine Element";
				this.damage = 10.0f;
				this.fireSpeed = 1.0f;
				this.fireRange = 80.0f;
				break;
			case earth:
				this.sprite = new Sprite(game.assets.get("textures/world/earth_32.png", Texture.class));
				this.name = "Earth Element";
				this.damage = 12.0f;
				this.fireSpeed = 0.8f;
				this.fireRange = 60.0f;
				break;
			case fire:
				this.sprite = new Sprite(game.assets.get("textures/world/fire_32.png", Texture.class));
				this.name = "Fire Element";
				this.damage = 9.0f;
				this.fireSpeed = 1.1f;
				this.fireRange = 75.0f;
				break;
			case water:
				this.sprite = new Sprite(game.assets.get("textures/world/water_32.png", Texture.class));
				this.name = "Water Element";
				this.damage = 7.0f;
				this.fireSpeed = 1.0f;
				this.fireRange = 80.0f;
				break;
			case wind:
				this.sprite = new Sprite(game.assets.get("textures/world/wind_32.png", Texture.class));
				this.name = "Wind Element";
				this.damage = 11.0f;
				this.fireSpeed = 1.3f;
				this.fireRange = 90.0f;
				break;		
		}
		
		addListener(new ClickListener(Input.Buttons.LEFT) {
			@Override public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				if ( getTapCount() == 2 ) {
					
					if( Element.this.tower != null ) {
						Element.this.tower.element = null;
						Element.this.tower = null;
					}
					
					if( Element.this.trap != null ) {
						Element.this.trap.element = null;
						Element.this.trap = null;
					}
					
					Element.this.inventory.stockElement(Element.this);
				}
			}
		});
		
		addListener(new DragListener() {

			@Override
			public void dragStart(InputEvent event, float x, float y,
					int pointer) {
				// TODO Auto-generated method stub
				super.dragStart(event, x, y, pointer);
				Element.this.oldTower = Element.this.tower;
				Element.this.oldTrap = Element.this.trap;
				
				if( Element.this.tower != null ) {
					Element.this.tower.element = null;
					Element.this.tower = null;
				}
				
				if( Element.this.trap != null ) {
					Element.this.trap.element = null;
					Element.this.trap = null;
				}
			}

			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				// TODO Auto-generated method stub
				super.drag(event, x, y, pointer);
				Element.this.moveBy(x-16, y-16);
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer) {
				// TODO Auto-generated method stub
				super.dragStop(event, x, y, pointer);
				
				Vector2 pos = new Vector2(Element.this.getX() + 16, Element.this.getY() + 16);
				for( Tower tower : Element.this.world.towers ) {
					if( tower.isEmpty() && tower.containsPoint(pos) ) {
						Element.this.deploy(tower);
						return;
					}
				}
				
				for( Trap trap : Element.this.world.traps ) {
					if( trap.isEmpty() && trap.containsPoint(pos) ) {
						Element.this.deploy(trap);
						return;
					}
				}
				
				if( Element.this.state == ElementState.deployed )
					Element.this.deploy(Element.this.oldTower);
				else Element.this.deploy(Element.this.oldTrap);
			}
		});
	}
	
	

	@Override public void act(float delta) {
		super.act(delta);
		
		if( world.state == WorldState.normalSpeed || world.state == WorldState.speed2x ) {
			if( state == ElementState.deployed ) {
				fireTimer -= delta;
				if( fireTimer <= 0.0f ) {
					if( target != null && targetIsInRange(target) ) {
						Projectile projectile = this.createProjectile();
						world.addProjectile(projectile);
						fireTimer = this.fireSpeed;
						return;
					}
					
				}
				
				target = findNewTarget();
			} else if ( state == ElementState.trap ) {
				fireTimer -= delta;
				if( fireTimer <= 0.0f ) {
					target = findNewTarget();
					if( targetIsInTrapRange(target) ) {
						target.hurtBy(this);
						fireTimer = this.fireSpeed;
						return;
					}
				}
			}
		}
	}

	private boolean targetIsInTrapRange(Enemy target2) {
		if ( target2 == null )
			return false;
		
		Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
		return (v.dst(target2.getPostionVector()) <= 30.0f);
	}



	private Enemy findNewTarget() {
		for(Enemy e : world.enemies) 
			if( targetIsInRange(e) )
				return e;
		return null;
	}

	private Projectile createProjectile() {
		Projectile projectile = new Projectile(game, world, type);
		projectile.damage = this.damage;
		projectile.setTarget(target);
		projectile.setPosition(getX()+getOriginX()-4, getY()+getOriginY()-4);
		return projectile;
	}

	private boolean targetIsInRange(Enemy target2) {
		Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
		return (v.dst(target2.getPostionVector()) <= this.fireRange);
	}

	protected ShapeRenderer renderer = new ShapeRenderer();
	@Override public void draw(Batch batch, float parentAlpha) {
		if( state == ElementState.deployed || ( trap == null && state==ElementState.trap) ) {
			this.sprite.draw(batch);
		}
		
		if( world.debugOn ) {
			batch.end();
			
			renderer.setProjectionMatrix(world.camera.combined);
			
			renderer.begin(ShapeType.Line);
				renderer.setColor(Color.WHITE);
				renderer.circle(getX()+getOriginY(), getY()+getOriginY(), fireRange);
			renderer.end();
			
			if( target != null ) {
					Vector2 v = new Vector2(getX()+getOriginX(), getY()+getOriginY());
					Vector2 p = target.getPostionVector();
					renderer.begin(ShapeType.Line);
						renderer.setColor(Color.RED);
						renderer.line(v, p);
					renderer.end();
				
			}
			batch.begin();
		}
	}
	

	@Override protected void positionChanged() {
		super.positionChanged();
		this.sprite.setPosition(getX(), getY());
	}
	
	public void deploy(Tower tower) {
		fireTimer = 2.0f;
		state = ElementState.deployed;
		tower.element = this;
		if (world.elements.contains(this) == false)
			world.addElement(this);
		setPosition(tower.getX(), tower.getY());
		this.tower = tower;
	}
	
	public void deploy(Trap trap) {
		fireTimer = 2.0f;
		state = ElementState.trap;
		if (world.elements.contains(this) == false)
			world.addElement(this);
		setPosition(trap.getX(), trap.getY());
		this.trap = trap;
		this.trap.element = this;
		
		
	}
	
	public void stock() {
		state = ElementState.inventory;
	}

	public enum ElementType {
		earth,
		water,
		fire,
		wind,
		arcane,
		divine
	}
	
	public enum ElementState {
		deployed,
		trap,
		inventory
	}
}
