package be.darnell.xspy;

import org.bukkit.configuration.file.FileConfiguration;

import be.darnell.xspy.XrayPlayer.Ore;

public final class Config {
	
	private XSpy _Plugin;
	private FileConfiguration _FileConfig;
	
	public Config(XSpy plugin) {
		_Plugin = plugin;
	}

	public void load() {
		_Plugin.reloadConfig();
		_FileConfig = _Plugin.getConfig();
		
		// The backend to use. Valid options are File, SQL, and LogBlock
		_FileConfig.addDefault("Storage Backend", "File");
		
		// The default world to search
		_FileConfig.addDefault("Default world", "world");
		
		// What ores to check
		_FileConfig.addDefault("Diamond", true);
		_FileConfig.addDefault("Gold", true);
		_FileConfig.addDefault("Lapis", true);
		_FileConfig.addDefault("Iron", true);
		_FileConfig.addDefault("Emerald", true);
		
		// At what level do we warn?
		_FileConfig.addDefault("Diamond Warning Level", 3.0D);
		_FileConfig.addDefault("Diamond Severe Level", 4.0D);
		
		_FileConfig.addDefault("Gold Warning Level", 8.0D);
		_FileConfig.addDefault("Gold Severe Level", 15.0D);
		
		_FileConfig.addDefault("Lapis Warning Level", 4.0D);
		_FileConfig.addDefault("Lapis Severe Level", 5.2D);
		
		_FileConfig.addDefault("Iron Warning Level", 20.0D);
		_FileConfig.addDefault("Iron Severe Level", 45.0D);
		
		_FileConfig.addDefault("Emerald Warning Level", 1.5D);
		_FileConfig.addDefault("Emerald Severe Level", 3.0D);
		
		_FileConfig.options().copyDefaults(true);
		_Plugin.saveConfig();
	}
	
	public boolean isActive(Ore ore) {
		return _FileConfig.getBoolean(ore.toString());
	}
	
	public double getRate(XLevel level, Ore ore) {
		return _FileConfig.getDouble(ore.toString() + " " + level.toString());
	}
	
	public String defaultWorld() {
		return _FileConfig.getString("Default world");
	}
	
	public Storage getStorage() {
		String storage = _FileConfig.getString("Storage Backend");
		
		if (storage.equalsIgnoreCase("File")) return Storage.FILE;
		if (storage.equalsIgnoreCase("SQL")) return Storage.SQL;
		if (storage.equalsIgnoreCase("LogBlock")) return Storage.LOGBLOCK;
		return null;
	}
	
	public static enum Storage {
		FILE,
		SQL,
		LOGBLOCK
	}
	
	public static enum XLevel {
		WARNING {
			public String toString() {
				return "Warning Level";
			}
		},
		SEVERE {
			public String toString() {
				return "Severe Level";
			}
		}
	}

}
