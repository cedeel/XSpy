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
