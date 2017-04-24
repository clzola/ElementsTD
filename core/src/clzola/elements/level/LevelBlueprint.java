package clzola.elements.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class LevelBlueprint {
	public boolean open;
	public boolean completed;
	public String tiledMap;
	
	public TowerBlueprint[] towers;
	public TrapBlueprint[] traps;
	public WallBlueprint[] walls;
	public SpawnerBlueprint[] spawners;
	public WaveBlueprint[] waves;
	public int[][] path;
	
	private FileHandle fileHandle;
	
	public static LevelBlueprint fromFile(String filename) {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		
		FileHandle fileHandle = Gdx.files.internal(filename);
		LevelBlueprint level = json.fromJson(LevelBlueprint.class, fileHandle);
		level.fileHandle = fileHandle;
		return level;
	}
	
	public void save() {
		Json json = new Json();
		json.setOutputType(OutputType.json);
		json.toJson(this, LevelBlueprint.class, fileHandle);
	}
}
