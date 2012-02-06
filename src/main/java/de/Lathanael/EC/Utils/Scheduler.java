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
import java.util.Map;

import org.bukkit.Server;
import org.bukkit.World;

import de.Lathanael.EC.Main.EntityCleaner;
import de.Lathanael.EC.Tasks.CartTask;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Scheduler {

	public static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	public static HashMap<String, TaskContainer> tasks = new HashMap<String, TaskContainer>();
	public Server server;
	public List<World> worlds = new ArrayList<World>();
	private EntityCleaner instance;

	public Scheduler(Server server, EntityCleaner instance) {
		this.server = server;
		this.instance = instance;
		for (String world : ECConfig.WORLDS.getStringList()) {
			if (EntityCleaner.debug)
				Tools.debugMsg("Adding world: " + server.getWorld(world).getName());
			worlds.add(server.getWorld(world));
		}
		initTaskList();

	}

	public void startTasks() {
		for (Map.Entry<String, TaskContainer> map : tasks.entrySet()) {
			TaskContainer container = map.getValue();
			if (container.isEnabled())
				startTask(container.getTask(), map.getKey(), container.getInitTIme(), container.getTime());
		}
	}

	public void startTask(Runnable task, String taskName, long initTime, long time) {
		taskIDs.put(taskName, server.getScheduler().scheduleSyncRepeatingTask(
				instance, task, initTime, time));
	}

	public void startTask(String task) {
		TaskContainer container = tasks.get(task);
		taskIDs.put(task, server.getScheduler().scheduleSyncRepeatingTask(
				instance, container.getTask(), container.getInitTIme(), container.getTime()));
	}

	public void stopTasks() {
		for (Map.Entry<String, Integer> entries : taskIDs.entrySet())
			stopTask(entries.getKey(), entries.getValue());
	}

	public void stopTask(String taskName, int id) {
		server.getScheduler().cancelTask(id);
		taskIDs.remove(taskName);
	}

	public void stopTask(String className) {
		int id = taskIDs.get(className);
		server.getScheduler().cancelTask(id);
		taskIDs.remove(className);
	}

	public void restartTask(String task) {
		int id = taskIDs.get(task);
		server.getScheduler().cancelTask(id);
		taskIDs.remove(task);
	}

	//---------------------------------private functons-----------------------------------------

	// TODO: Add all tasks
	private void initTaskList() {
		tasks.put("cart", new TaskContainer((Runnable) new CartTask(worlds), ECConfig.C_INIT_TIME.getLong(0L),
					ECConfig.C_TIME.getLong(600L), ECConfig.C_ENABLE.getBoolean()));
	}
}
