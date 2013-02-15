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
                            result.addOre(Ore.STONE);
                            break;
                        case 56:
                            if (plugin.config.isActive(Ore.DIAMOND))
                                result.addOre(Ore.DIAMOND);
                            break;
                        case 14:
                            if (plugin.config.isActive(Ore.GOLD))
                                result.addOre(Ore.GOLD);
                            break;
                        case 21:
                            if (plugin.config.isActive(Ore.LAPIS))
                                result.addOre(Ore.LAPIS);
                            break;
                        case 15:
                            if (plugin.config.isActive(Ore.IRON))
                                result.addOre(Ore.IRON);
                            break;
                        case 129:
                            if (plugin.config.isActive(Ore.EMERALD))
                                result.addOre(Ore.EMERALD);
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
            }
            playerMap.put(suspect, result);
        }
        return playerMap.get(suspect);
    }
}
