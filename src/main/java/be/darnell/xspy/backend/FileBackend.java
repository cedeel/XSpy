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
package be.darnell.xspy.backend;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileBackend extends Backend {

  private static BackendListener listener;

  public FileBackend(XSpy plugin) {
    super(plugin);
    listener = new BackendListener(plugin, this);
  }

  @Override
  public XrayPlayer getInfo(String suspect) {
    if (!playerMap.containsKey(suspect)) {
      YamlConfiguration userConfig = loadUserConfig(suspect);
      playerMap.put(suspect,
              new XrayPlayer(Bukkit.getOfflinePlayer(suspect),
                             userConfig.getInt(XrayPlayer.Ore.STONE.toString()),
                             userConfig.getInt(XrayPlayer.Ore.IRON.toString()),
                             userConfig.getInt(XrayPlayer.Ore.GOLD.toString()),
                             userConfig.getInt(XrayPlayer.Ore.DIAMOND.toString()),
                             userConfig.getInt(XrayPlayer.Ore.EMERALD.toString()),
                             userConfig.getInt(XrayPlayer.Ore.LAPIS.toString())
              )
      );
    }
    return playerMap.get(suspect);
  }

  @Override
  public void persist() {
    for(String suspect : playerMap.keySet()) {
      File configFile = new File(plugin.getDataFolder(), suspect + ".yml");
      YamlConfiguration userConfig = YamlConfiguration.loadConfiguration(configFile);
      
      XrayPlayer xp = playerMap.get(suspect);
      
      userConfig.set(XrayPlayer.Ore.STONE.toString(),
              xp.getOre(XrayPlayer.Ore.STONE));
      
      userConfig.set(XrayPlayer.Ore.IRON.toString(),
              xp.getOre(XrayPlayer.Ore.IRON));
      
      userConfig.set(XrayPlayer.Ore.GOLD.toString(),
              xp.getOre(XrayPlayer.Ore.GOLD));
      
      userConfig.set(XrayPlayer.Ore.DIAMOND.toString(),
              xp.getOre(XrayPlayer.Ore.DIAMOND));
      
      userConfig.set(XrayPlayer.Ore.EMERALD.toString(),
              xp.getOre(XrayPlayer.Ore.EMERALD));
      
      try {
        userConfig.save(configFile);
      } catch(IOException e) {}
    }
  }

  private YamlConfiguration loadUserConfig(String user) {
    File configFile = new File(plugin.getDataFolder(), user + ".yml");
    return YamlConfiguration.loadConfiguration(configFile);
  }
}
