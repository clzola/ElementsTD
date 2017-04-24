package clzola.elements.desktop;

import clzola.elements.ElementsTowerDefence;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
		config.resizable = false;
		config.addIcon("icon_128.png", FileType.Internal);
		config.addIcon("icon.png", FileType.Internal);
		config.addIcon("icon_32.png", FileType.Internal);
		config.addIcon("icon_16.png", FileType.Internal);
		new LwjglApplication(new ElementsTowerDefence(), config);
	}
}
