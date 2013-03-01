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

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import be.darnell.xspy.XSpy;
import be.darnell.xspy.XrayPlayer;

/**
 * A dummy backend implementation that keeps all info in memory
 * @author cedeel
 *
 */
public final class DummyBackend extends Backend {
	
	private static BackendListener _Listener;
	
	public DummyBackend(XSpy plugin) {
		super(plugin);
		_Listener = new BackendListener(plugin, this);
		
		plugin.getServer().getPluginManager().registerEvents(
				_Listener, plugin);
	}
	
	@Override
	public XrayPlayer getInfo(String suspect) {
		if (!playerMap.containsKey(suspect)) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(suspect);
			playerMap.put(suspect, new XrayPlayer(op));
		}
		return playerMap.get(suspect);
	}
	
	@Override
	public String toString() {
		return "DummyBackend";
	}

  @Override
  public void persist() { /*  This is a dummy backend. Don't expect persisting!  */ }
}
