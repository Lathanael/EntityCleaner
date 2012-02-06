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

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import be.Balor.Tools.MaterialContainer;
import be.Balor.Tools.Utils;
import de.Lathanael.EC.Main.EntityCleaner;
import de.Lathanael.EC.Tasks.AnimalTask;
import de.Lathanael.EC.Tasks.ArrowTask;
import de.Lathanael.EC.Tasks.BoatTask;
import de.Lathanael.EC.Tasks.CartTask;
import de.Lathanael.EC.Tasks.CompleteTask;
import de.Lathanael.EC.Tasks.FloatingItemsTask;
import de.Lathanael.EC.Tasks.MonsterTask;
import de.Lathanael.EC.Tasks.OrbTask;
import de.Lathanael.EC.Tasks.VehicleTask;
import de.Lathanael.EC.Tasks.VillagerTask;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Scheduler {

	public static HashMap<String, Integer> taskIDs = new HashMap<String, Integer>();
	public static HashMap<String, TaskContainer> tasks = new HashMap<String, TaskContainer>();
	public Server server;
	public List<World> worlds = new ArrayList<World>();
	public List<Material> items = new ArrayList<Material>();
	private EntityCleaner instance;

	public Scheduler(Server server, EntityCleaner instance) {
		this.server = server;
		this.instance = instance;
		for (String world : ECConfig.WORLDS.getStringList()) {
			if (EntityCleaner.debug)
				Tools.debugMsg("Adding world: " + server.getWorld(world).getName());
			worlds.add(server.getWorld(world));
		}
		for (String item : ECConfig.ITEMS.getStringList()) {
			MaterialContainer m = Utils.checkMaterial(item);
			Material mat = m.getMaterial();
			if (mat != null)
				items.add(mat);
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

	private void initTaskList() {
		tasks.put("cart", new TaskContainer((Runnable) new CartTask(worlds), ECConfig.C_INIT_TIME.getLong(0L),
					ECConfig.C_TIME.getLong(600L), ECConfig.C_ENABLE.getBoolean()));
		tasks.put("boat", new TaskContainer((Runnable) new BoatTask(worlds), ECConfig.B_INIT_TIME.getLong(0L),
				ECConfig.B_TIME.getLong(600L), ECConfig.B_ENABLE.getBoolean()));
		tasks.put("arrow", new TaskContainer((Runnable) new ArrowTask(worlds), ECConfig.AR_INIT_TIME.getLong(0L),
				ECConfig.AR_TIME.getLong(600L), ECConfig.AR_ENABLE.getBoolean()));
		tasks.put("animal", new TaskContainer((Runnable) new AnimalTask(worlds), ECConfig.AN_INIT_TIME.getLong(0L),
				ECConfig.AN_TIME.getLong(600L), ECConfig.AN_ENABLE.getBoolean()));
		tasks.put("orb", new TaskContainer((Runnable) new OrbTask(worlds), ECConfig.O_INIT_TIME.getLong(0L),
				ECConfig.O_TIME.getLong(600L), ECConfig.O_ENABLE.getBoolean()));
		tasks.put("monster", new TaskContainer((Runnable) new MonsterTask(worlds), ECConfig.M_INIT_TIME.getLong(0L),
				ECConfig.M_TIME.getLong(600L),	ECConfig.M_ENABLE.getBoolean()));
		tasks.put("villager", new TaskContainer((Runnable) new VillagerTask(worlds), ECConfig.VIL_INIT_TIME.getLong(0L),
				ECConfig.VIL_TIME.getLong(600L), ECConfig.VIL_ENABLE.getBoolean()));
		tasks.put("items", new TaskContainer((Runnable) new FloatingItemsTask(worlds, items), ECConfig.FI_INIT_TIME.getLong(0L),
				ECConfig.FI_TIME.getLong(600L), ECConfig.FI_ENABLE.getBoolean()));
		tasks.put("vehicle", new TaskContainer((Runnable) new VehicleTask(worlds), ECConfig.VEH_INIT_TIME.getLong(0L),
				ECConfig.VEH_TIME.getLong(600L), ECConfig.VEH_ENABLE.getBoolean()));
		tasks.put("all", new TaskContainer((Runnable) new CompleteTask(worlds), ECConfig.ALL_INIT_TIME.getLong(0L),
				ECConfig.ALL_TIME.getLong(600L), ECConfig.ALL_ENABLE.getBoolean()));
	}
}
