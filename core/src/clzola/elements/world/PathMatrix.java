package clzola.elements.world;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class PathMatrix {
	public int[][] matrix;
	public WorldEndPoint end;
	public ArrayList<WorldEndPoint> start;
	public World world;

	public PathMatrix(int[][] matrix, World world) {		
		this.matrix = new int [matrix.length][matrix[0].length];
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				this.matrix[y][x] = matrix[matrix.length - y - 1][x];
			}
		}
		
		this.world = world;
		
		findStart();
		findEnd();
		pathifize();
	}
	
	public void findEnd() {
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				if ( matrix[y][x] == 2 ) {
					end = new WorldEndPoint(x, y);
					return;
				}
			}
		}
	}
	
	public void findStart() {
		start = new ArrayList<WorldEndPoint>();
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				if ( matrix[y][x] == 3 ) {
					start.add(new WorldEndPoint(x, y));
				}
			}
		}
	}
	
	public void pathifize() {
		Queue<WorldEndPoint> points = new LinkedList<WorldEndPoint>();
		boolean[][] visited = new boolean[matrix.length][matrix[0].length];
		points.add(end);
		matrix[end.y][end.x] = 1;
		
		int[] directionX = { -1, 0, +1, 0 };
        int[] directionY = { 0, -1, 0, +1 };
		
		while( points.isEmpty() == false ) {
			WorldEndPoint node = points.poll();
			visited[node.y][node.x] = true;
			
			for (int i = 0; i < 4; i++) {
                int nextX = node.x + directionX[i];
                int nextY = node.y + directionY[i];

                if( 0 <= nextX && nextX < matrix[0].length && 0 <= nextY && nextY < matrix.length ) {
                    if( visited[nextY][nextX] == false && matrix[nextY][nextX] > 0 ) {
                        WorldEndPoint newNode = new WorldEndPoint(nextX, nextY);
                        matrix[nextY][nextX] = matrix[node.y][node.x] + 1;
                        points.add(newNode);
                    }
                }
            }
		}
	}
	
	public boolean canBuildTower(float dx, float dy) {
		int x = (int)(dx / 32)+1;
		int y = (int)(dy / 32)+1;
		
		return matrix[y][x] == 0;
	}
	
	public boolean canBuildWall(float dx, float dy) {
		Vector2 pos = new Vector2(dx, dy);
		
		// Is There a Tower ?
		for(Tower tower : world.towers) {
			if( tower.containsPoint(pos) )
				// TODO: Message cannot paste wall
				Gdx.app.log("PathMatrix", "There is a tower in a way.");
				return false;
		}
		
		// Is There a Trap ??
		for(Trap trap : world.traps) {
			if( trap.containsPoint(pos) )
				// TODO: Message cannot paste wall
				Gdx.app.log("PathMatrix", "There is a trap in a way.");
				return false;
		}
		
		// Is There an Enemy ???
		
		// Ok let's construct new path and see can all spawners reach end point.
		int[][] matrixB = new int [matrix.length][matrix[0].length];
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				matrixB[y][x] = matrix[y][x];
			}
		}
		
		Queue<WorldEndPoint> points = new LinkedList<WorldEndPoint>();
		boolean[][] visited = new boolean[matrixB.length][matrixB[0].length];
		
		matrixB[end.y][end.x] = 1;
		
		int[] directionX = { -1, 0, +1, 0 };
        int[] directionY = { 0, -1, 0, +1 };
        
        int x = (int)(dx / 32)+1;
		int y = (int)(dy / 32)+1;
		
		if( matrixB[y][x] == 0 )
			return true;
		
		matrixB[y][x] = 0;
		if( y != end.y && end.x != x )
			points.add(end);
        
        ArrayList<WorldEndPoint> foundStart = new ArrayList<WorldEndPoint>();
        
        while( points.isEmpty() == false ) {	
			WorldEndPoint node = points.poll();
			if( visited[node.y][node.x] )
				continue;
			
			visited[node.y][node.x] = true;
			
			for (int i = 0; i < 4; i++) {
                int nextX = node.x + directionX[i];
                int nextY = node.y + directionY[i];

                if( 0 <= nextX && nextX < matrixB[0].length && 0 <= nextY && nextY < matrixB.length ) {
                    if( visited[nextY][nextX] == false && matrixB[nextY][nextX] > 0 ) {
                        WorldEndPoint newNode = new WorldEndPoint(nextX, nextY);
                        matrixB[nextY][nextX] = matrixB[node.y][node.x] + 1;
                        points.add(newNode);
                    }
                }
            }
			
			for(WorldEndPoint s : start) {
				if ( s.x == node.x && s.y == node.y ) {
					foundStart.add(node);
					break;
				}
			}
		}
        
        return foundStart.size() == start.size();
	}
	
	public void buildWall(float dx, float dy) {
		int x = (int)(dx / 32)+1;
		int y = (int)(dy / 32)+1;
		matrix[y][x] = -1;
		pathifize();
	}
	
	public boolean canBuildTrap(float dx, float dy) {
		int x = (int)(dx / 32)+1;
		int y = (int)(dy / 32)+1;
		
		return (matrix[y][x] > 1);
	}
	
	public void draw(Batch batch, Texture tex) {
		batch.begin();
		for(int y = 0; y < matrix.length; y++) {
			for(int x = 0; x < matrix[y].length; x++) {
				if( matrix[y][x] > 0 )
					batch.draw(tex, (x-1)*32, (y-1)*32);
			}
		}
		batch.end();
	}
}
