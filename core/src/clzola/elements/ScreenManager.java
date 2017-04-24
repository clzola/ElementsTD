package clzola.elements;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.ScreenAdapter;

public class ScreenManager {
	private HashMap<Class<? extends ScreenAdapter>, ScreenAdapter> screens;
	private static ScreenManager instance = null;
	
	private ScreenManager() {
		this.screens = new HashMap<Class<? extends ScreenAdapter>, ScreenAdapter>();
	}
	
	public static ScreenManager getInstance() {
		if( ScreenManager.instance == null ) {
			ScreenManager.instance = new ScreenManager();
		}
		
		return ScreenManager.instance;
	}
	
	public void register(ScreenAdapter screenAdapter) {
		this.screens.put(screenAdapter.getClass(), screenAdapter);
	}
	
	public ScreenAdapter get(Class<? extends ScreenAdapter> clazz) {
		return this.screens.get(clazz);
	}
	
	public void dispose() {
		for(Entry<Class<? extends ScreenAdapter>, ScreenAdapter> entry : this.screens.entrySet()) {
			entry.getValue().dispose();
		}
	}
}