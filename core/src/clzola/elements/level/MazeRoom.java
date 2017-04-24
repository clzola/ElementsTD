package clzola.elements.level;

import java.util.ArrayList;

import clzola.elements.ElementsTowerDefence;

public class MazeRoom {
	public ElementsTowerDefence game;
	public LevelBlueprint level;
	public ArrayList<MazeRoom> successors;
	
	public MazeRoom(ElementsTowerDefence game, String levelFilename) {
		this.game = game;
		this.level = LevelBlueprint.fromFile(levelFilename);
		this.successors = new ArrayList<MazeRoom>();
	}
	
	public void addSuccessorRoom(MazeRoom room) {
		this.successors.add(room);
	}
}
