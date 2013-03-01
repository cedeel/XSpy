/*
 * Copyright (c) 2013 cedeel.
 * All rights reserved.
 * 
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS ``AS IS''
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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
      @Override
			public String toString() {
				return "Warning Level";
			}
		},
		SEVERE {
      @Override
			public String toString() {
				return "Severe Level";
			}
		}
	}

}
