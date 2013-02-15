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

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;
import be.darnell.xspy.XrayPlayer.Ore;

public class BackendListener implements Listener {
	
	private static XSpy _Plugin;
	private static Backend storage;
	
	public BackendListener(XSpy plugin, DummyBackend backend) {
		_Plugin = plugin;
		storage = backend;
	}
	
	@EventHandler
	public void onBlockBreak (BlockBreakEvent event) {
		String player = event.getPlayer().getName();
		
		XrayPlayer xp = storage.getInfo(player);
		Material broken = event.getBlock().getType();
		
		if(broken == Material.STONE)
			xp.addOre(Ore.STONE);
		
		if(_Plugin.config.isActive(Ore.DIAMOND) && broken == Material.DIAMOND_ORE)
			xp.addOre(Ore.DIAMOND);
		
		if(_Plugin.config.isActive(Ore.GOLD) && broken == Material.GOLD_ORE)
			xp.addOre(Ore.GOLD);
		
		if(_Plugin.config.isActive(Ore.LAPIS) && broken == Material.LAPIS_ORE)
			xp.addOre(Ore.LAPIS);
		
		if(_Plugin.config.isActive(Ore.IRON) && broken == Material.IRON_ORE)
			xp.addOre(Ore.IRON);
		
		if(_Plugin.config.isActive(Ore.EMERALD) && broken == Material.EMERALD_ORE)
			xp.addOre(Ore.EMERALD);
		
	}

}
