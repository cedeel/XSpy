/**
 * XSpy - Your personal spy against xray users
 * 
 * @author cedeel
 */

package be.darnell.xspy;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import be.darnell.xspy.Config.XLevel;
import be.darnell.xspy.XrayPlayer.Ore;
import be.darnell.xspy.backend.Backend;
import be.darnell.xspy.backend.DummyBackend;

/**
 * Main plugin class
 * @author cedeel
 *
 */
public class XSpy extends JavaPlugin {
	
	public final Config config = new Config(this);
	public Backend storage;
	public static final Logger log = Logger.getLogger("Minecraft");
	
	@Override
	public void onEnable() {
		config.load();
		PluginDescriptionFile pdf = this.getDescription();
		
		// TODO: Change this!!!
		storage = new DummyBackend(this);
		log.info("XSpy " + pdf.getVersion() + " enabled.");
	}
	
	@Override
	public void onDisable() {
		log.info("XSpy disabled.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String command = cmd.getName();
		
		if (command.equalsIgnoreCase("xreport")) {
			if(args.length == 1) {
				xReport(sender, args[0]);
				return true;
			}
		}
		return false;
	}

	private void xReport(CommandSender sender, String suspect) {
		XrayPlayer xplayer = storage.getInfo(suspect);
		log.info(xplayer.toString());
		
		ChatColor warnColor = ChatColor.DARK_PURPLE;
		ChatColor severeColor = ChatColor.RED;
		
		sender.sendMessage("XSpy Report for: " + ChatColor.BLUE + xplayer);
		sender.sendMessage("----------------------------------");
		String totalMsg = String.format("Total blocks mined: %d", (int)xplayer.getAll());
		sender.sendMessage(totalMsg);
		
		double pStone = xplayer.getOre(Ore.STONE) / xplayer.getAll() * 100.0;
		String stoneMsg = String.format(ChatColor.WHITE + "Stone: %d - %.2f%%", xplayer.getOre(Ore.STONE), pStone);
		sender.sendMessage(stoneMsg);
		
		Ore[] ores = {Ore.IRON, Ore.GOLD, Ore.LAPIS, Ore.EMERALD, Ore.DIAMOND };
		
		for (Ore ore : ores) {
			if (config.isActive(ore)) {
				ChatColor msgColor = ChatColor.GREEN;
				double orePercentage = xplayer.getOrePercentage(ore);
				if (orePercentage >= config.getRate(XLevel.SEVERE, ore))
					msgColor = severeColor;
				else if (orePercentage >= config.getRate(XLevel.WARNING, ore))
					msgColor = warnColor;
				
				String oreMsg = String.format(msgColor + ore.toString() + ": %d - %.2f%%", xplayer.getOre(ore), orePercentage);
				sender.sendMessage(oreMsg);
			}
		}
	}

}
