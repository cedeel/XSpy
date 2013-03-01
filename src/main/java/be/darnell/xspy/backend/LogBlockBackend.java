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

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;
import be.darnell.xspy.XrayPlayer.Ore;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;

public class LogBlockBackend extends Backend {
  
  private final LogBlock lb;
  
  public LogBlockBackend(XSpy plugin) {
    super(plugin);
    final PluginManager pm = plugin.getServer().getPluginManager();
    lb = (LogBlock) pm.getPlugin("LogBlock");
  }
  
  @Override
  public XrayPlayer getInfo(String suspect) {
    OfflinePlayer suspectPlayer = plugin.getServer().getOfflinePlayer(suspect);
    
    if (!playerMap.containsKey(suspect)) {
      XrayPlayer result = new XrayPlayer(suspectPlayer);
      int stones = 0, diamonds = 0, gold = 0, lapis = 0, iron = 0, emerald = 0;      
      
      QueryParams params = new QueryParams(lb);
      params.setPlayer(suspect);
      params.bct = BlockChangeType.DESTROYED;
      params.limit = -1;
      params.since = -1;
      params.needId = true;
      
      try {
        for (BlockChange bc : lb.getBlockChanges(params)) {
          switch (bc.replaced) {
            case 1:
              stones++;
              break;
            case 56:
              diamonds++;
              break;
            case 14:
              gold++;
              break;
            case 21:
              lapis++;
              break;
            case 15:
              iron++;
              break;
            case 129:
              emerald++;
              break;
            default:
              break;
          }
        }
      } catch (Exception e) {
      }
      
      params = new QueryParams(lb);
      params.setPlayer(suspect);
      params.bct = BlockChangeType.CREATED;
      params.limit = -1;
      params.since = -1;
      params.needId = true;
      
      try {
        for (BlockChange bc : lb.getBlockChanges(params)) {
          switch (bc.replaced) {
            case 56:
              diamonds--;
              break;
            case 14:
              gold--;
              break;
            case 21:
              lapis--;
              break;
            case 15:
              iron--;
              break;
            case 129:
              emerald--;
              break;
            default:
              break;
          }
        }
      } catch (Exception e) {
      }
      
      for (int i = 0; i < stones; i++) {
        result.addOre(Ore.STONE);
      }
      if(plugin.config.isActive(Ore.DIAMOND)) {
        for(int i = 0; i < diamonds; i++) {
          result.addOre(Ore.DIAMOND);
        }
      }
      if(plugin.config.isActive(Ore.GOLD)) {
        for(int i = 0; i < gold; i++) {
          result.addOre(Ore.GOLD);
        }
      }
      if(plugin.config.isActive(Ore.LAPIS)) {
        for(int i = 0; i < lapis; i++) {
          result.addOre(Ore.LAPIS);
        }
      }
      if(plugin.config.isActive(Ore.IRON)) {
        for(int i = 0; i < iron; i++) {
          result.addOre(Ore.IRON);
        }
      }
      if(plugin.config.isActive(Ore.EMERALD)) {
        for(int i = 0; i < emerald; i++) {
          result.addOre(Ore.EMERALD);
        }
      }
      playerMap.put(suspect, result);
    }
    return playerMap.get(suspect);
  }

  @Override
  public void persist() {
    // We let LogBlock do the persisting, so nothing to do here.
  }
}
