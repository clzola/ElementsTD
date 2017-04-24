package clzola.elements.level;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;

public class MazeGraph {
	public VertexNode startRoom;
	public VertexNode endRoom;
	public ArrayList<MazeGraphNode> rooms;
	
	public MazeGraph() {
		rooms = new ArrayList<MazeGraphNode>();
	}
	
	public static MazeGraph load() {
		try {
			Json json = new Json();
			json.setOutputType(OutputType.json);
			return json.fromJson(MazeGraph.class, Gdx.files.internal("maze.json"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
}
