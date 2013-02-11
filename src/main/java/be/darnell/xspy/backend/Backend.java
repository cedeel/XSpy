package be.darnell.xspy.backend;

import java.util.HashSet;
import java.util.Set;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;

public abstract class Backend {

	protected final XSpy plugin;
	protected Set<XrayPlayer> playerMap = new HashSet<XrayPlayer>();
	
	public abstract XrayPlayer getInfo(String suspect);
	
	public Backend(XSpy plugin) {
		this.plugin = plugin;
	}

}
