package clzola.elements.level;

import java.util.ArrayList;

public class MazeGraphNode {
	public int x;
	public int y;
	public boolean opened;
	public boolean completed;
	public ArrayList<VertexNode> vertices;
	
	public MazeGraphNode() {
		vertices = new ArrayList<VertexNode>();
	}
}
