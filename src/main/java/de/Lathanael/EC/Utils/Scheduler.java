/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
 *
 * EntityCleaner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityCleaner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityCleaner. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.EC.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Server;
import org.bukkit.World;

import de.Lathanael.EC.Main.EntityCleaner;

import be.Balor.Tools.Debug.ACLogger;
import be.Balor.bukkit.AdminCmd.ACPluginManager;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Scheduler {

	public HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	public Server server;
	public List<World> worlds = new ArrayList<World>();

	public Scheduler(Server server) {
		this.server = server;
		for (String world : ECConfig.WORLDS.getStringList()) {
			if (EntityCleaner.debug)
				Tools.debugMsg("Adding world: " + server.getWorld(world).getName());
			worlds.add(server.getWorld(world));
		}
	}

	public void startTasks() {
		startTask((Runnable) new CartTask(worlds), "CartTask");
	}

	public void startTask(Runnable task, String className) {
		ACLogger.info("Starting Task!");
		taskIDs.put(className, server.getScheduler().scheduleSyncRepeatingTask(
				ACPluginManager.getPluginInstance("EntityCleaner" ), task, 0L, 600L));
	}

	public void stopTasks() {

	}

	public void stopTask(String className) {
		taskIDs.remove(className);
	}
}
