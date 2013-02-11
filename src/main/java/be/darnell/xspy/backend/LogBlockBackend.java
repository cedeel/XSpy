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

		if (!playerMap.contains(suspectPlayer)) {
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
			playerMap.add(result);
		}
		return playerMap.get(suspect);
	}

}
