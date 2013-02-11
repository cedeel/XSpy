package be.darnell.xspy;

import org.bukkit.OfflinePlayer;

/**
 * Information about a player's xray pattern
 * @author Administrator
 *
 */
public final class XrayPlayer implements Comparable<XrayPlayer> {
	private int xStone, xIron, xGold, xDiamond, xEmerald, xLapis, xLevel, xLevelUpdate;
	private OfflinePlayer player;
	private final int XLEVEL_UPDATE_INTERVAL = 16;
	
	public XrayPlayer(OfflinePlayer player, int xStone, int xIron, int xGold, int xDiamond, int xEmerald, int xLapis) {
		this.xStone   = xStone;     this.xIron    = xIron;
		xGold    = this.xGold;      this.xDiamond = xDiamond;
		xEmerald = this.xEmerald;   this.xLapis   = xLapis;
		
		this.player = player;
		xLevel = 0;
		xLevelUpdate = 0;
		
		if (this.player == null) throw new NullPointerException();
	}
	
	public XrayPlayer(OfflinePlayer player) {
		this(player, 0, 0, 0, 0, 0, 0);
	}
	
	@Override
	public String toString() {
		return player.getName();
	}
	
	/**
	 * Add a broken ore to the counter
	 * @param ore The type of ore to be added
	 */
	public void addOre(Ore ore) {
		switch (ore) {
		case STONE: xStone++; break;
		case IRON: xIron++; break;
		case GOLD: xGold++; break;
		case DIAMOND: xDiamond++; break;
		case EMERALD: xEmerald++; break;
		case LAPIS: xLapis++; break;
		default: break;
		}
		
		if (xLevelUpdate == XLEVEL_UPDATE_INTERVAL) {
			calcXlevel();
			xLevelUpdate = 0;
		} else xLevelUpdate++;
	}
	
	/**
	 * Get a count of a tracked ore
	 * @param ore The type of ore
	 * @return The count of ore breaks
	 */
	public int getOre(Ore ore) {
		switch (ore) {
		case STONE: return xStone;
		case IRON: return xIron;
		case GOLD: return xGold;
		case DIAMOND: return xDiamond;
		case EMERALD: return xEmerald;
		case LAPIS: return xLapis;
		default: return 0;
		}
	}
	
	/**
	 * 
	 * @param ore The type of ore to check
	 * @return The percentage of total blocks mined
	 */
	public double getOrePercentage(Ore ore) {
		switch (ore) {
		case STONE:return xStone / getAll() * 100;
		case IRON: return xIron / getAll() * 100;
		case GOLD: return xGold / getAll() * 100;
		case DIAMOND: return xDiamond / getAll() * 100;
		case EMERALD: return xEmerald / getAll() * 100;
		case LAPIS: return xLapis / getAll() * 100;
		default: return 0.0;
		}
	}
	
	public int getXlevel() {
		return xLevel;
	}
	
	/**
	 * Get a total count of the ores that are tracked
	 * @return The sum of tracked ores, including stone
	 */
	double getAll() {
		return xStone + xIron + xGold + xDiamond + xEmerald + xLapis;
	}
	
	/**
	 * An enumeration of the ores used within this plugin
	 * @author cedeel
	 *
	 */
	public enum Ore {
		STONE {
            @Override
			public String toString() {
				return "Stone";
			}
		},
		IRON {
            @Override
			public String toString() {
				return "Iron";
			}
		},
		GOLD {
            @Override
			public String toString() {
				return "Gold";
			}
		},
		DIAMOND {
            @Override
			public String toString() {
				return "Diamond";
			}
		},
		EMERALD {
            @Override
			public String toString() {
				return "Emerald";
			}
		},
		LAPIS {
            @Override
			public String toString() {
				return "Lapis";
			}
		}
	}
	
	private void calcXlevel() {
		// Let's make sure xLevel is zero before proceeding.
		xLevel = 0;
		
		if (xDiamond > 0) {
			xLevel = xLevel + (int) (getOrePercentage(Ore.DIAMOND) * 10);
		}
		
		if (xGold > 0) {
			xLevel = xLevel + (int) (getOrePercentage(Ore.GOLD) * 3);
		}
		
		if (xLapis > 0) {
			xLevel = xLevel + (int) (getOrePercentage(Ore.LAPIS) * 10);
		}
		
		if (xEmerald > 0) {
			xLevel = xLevel + (int) (getOrePercentage(Ore.EMERALD) * 10);
		}
		
		if (xIron > 0) {
			xLevel = xLevel + (int) getOrePercentage(Ore.IRON);
		}
	}
	
	@Override
	public int compareTo(XrayPlayer x) {
		return (this.getXlevel() < x.getXlevel() ? -1 :
			(this.getXlevel() == x.getXlevel() ? 0 : 1));
	}
	
	@Override
	public int hashCode() {
		return player.getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof XrayPlayer)) return false;
		
		XrayPlayer objXplayer = (XrayPlayer) obj;
		//return (this.name == objTestType.name && this.thought == objTestType.thought && this.rating == objTestType.rating);
		return (this.player.equals(objXplayer.player));
	}
}
