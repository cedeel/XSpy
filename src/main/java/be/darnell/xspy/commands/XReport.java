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
package be.darnell.xspy.commands;

import be.darnell.xspy.Config;
import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author cedeel
 */
public class XReport implements CommandExecutor {
  
  private XSpy instance;
  
  public XReport(XSpy plugin) {
    instance = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
    if (args.length == 1) {
      xReport(sender, args[0]);
      return true;
    }
    return false;
  }
  
  private void xReport(CommandSender sender, String suspect) {
		XrayPlayer xplayer = instance.storage.getInfo(suspect);
		XSpy.log.info(xplayer.toString());
		
		ChatColor warnColor = ChatColor.DARK_PURPLE;
		ChatColor severeColor = ChatColor.RED;
		
		sender.sendMessage("XSpy Report for: " + ChatColor.BLUE + xplayer);
		sender.sendMessage("----------------------------------");
		String totalMsg = String.format("Total blocks mined: %d", (int)xplayer.getAll());
		sender.sendMessage(totalMsg);
		
		double pStone = xplayer.getOre(XrayPlayer.Ore.STONE) / xplayer.getAll() * 100.0;
		String stoneMsg = String.format(ChatColor.WHITE + "Stone: %d - %.2f%%", xplayer.getOre(XrayPlayer.Ore.STONE), pStone);
		sender.sendMessage(stoneMsg);
		
		XrayPlayer.Ore[] ores = {XrayPlayer.Ore.IRON, XrayPlayer.Ore.GOLD, XrayPlayer.Ore.LAPIS, XrayPlayer.Ore.EMERALD, XrayPlayer.Ore.DIAMOND };
		
		for (XrayPlayer.Ore ore : ores) {
			if (instance.config.isActive(ore)) {
				ChatColor msgColor = ChatColor.GREEN;
				double orePercentage = xplayer.getOrePercentage(ore);
				if (orePercentage >= instance.config.getRate(Config.XLevel.SEVERE, ore))
					msgColor = severeColor;
				else if (orePercentage >= instance.config.getRate(Config.XLevel.WARNING, ore))
					msgColor = warnColor;
				
				String oreMsg = String.format(msgColor + ore.toString() + ": %d - %.2f%%", xplayer.getOre(ore), orePercentage);
				sender.sendMessage(oreMsg);
			}
		}
	}
}
