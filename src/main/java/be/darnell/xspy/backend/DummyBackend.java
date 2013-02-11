package be.darnell.xspy.backend;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;

/**
 * A dummy backend implementation that keeps all info in memory
 * @author cedeel
 *
 */
public final class DummyBackend extends Backend {
	
	private static BackendListener _Listener;
	
	public DummyBackend(XSpy plugin) {
		super(plugin);
		_Listener = new BackendListener(plugin, this);
		
		plugin.getServer().getPluginManager().registerEvents(
				_Listener, plugin);
	}
	
	@Override
	public XrayPlayer getInfo(String suspect) {
		if (!playerMap.containsKey(suspect)) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(suspect);
			playerMap.put(suspect, new XrayPlayer(op));
		}
		return playerMap.get(suspect);
	}
	
	@Override
	public String toString() {
		return "DummyBackend";
	}

}
