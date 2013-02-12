package be.darnell.xspy.backend;

import java.util.HashMap;
import java.util.Map;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;

public abstract class Backend {

	protected final XSpy plugin;
	protected Map<String, XrayPlayer> playerMap = new HashMap<String, XrayPlayer>();
	
	public abstract XrayPlayer getInfo(String suspect);
	
	public Backend(XSpy plugin) {
		this.plugin = plugin;
	}

}
