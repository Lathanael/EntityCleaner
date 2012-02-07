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
		startTask(task);
	}

	public void reInitTaskList() {
		tasks.clear();
		initTaskList();
	}

	//---------------------------------private functons-----------------------------------------

	private void initTaskList() {
		tasks.put("cart", new TaskContainer((Runnable) new CartTask(worlds), (long) (ECConfig.C_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.C_TIME.getDouble(5D)*20*60), ECConfig.C_ENABLE.getBoolean(), ECConfig.C_DERAILED.getBoolean()));
		tasks.put("boat", new TaskContainer((Runnable) new BoatTask(worlds), (long) (ECConfig.B_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.B_TIME.getDouble(5D)*20*60), ECConfig.B_ENABLE.getBoolean(), ECConfig.B_WATER.getBoolean()));
		tasks.put("arrow", new TaskContainer((Runnable) new ArrowTask(worlds), (long) (ECConfig.AR_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.AR_TIME.getDouble(5D)*20*60), ECConfig.AR_ENABLE.getBoolean(), false));
		tasks.put("animal", new TaskContainer((Runnable) new AnimalTask(worlds), (long) (ECConfig.AN_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.AN_TIME.getDouble(5D)*20*60), ECConfig.AN_ENABLE.getBoolean(), false));
		tasks.put("orb", new TaskContainer((Runnable) new OrbTask(worlds), (long) (ECConfig.O_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.O_TIME.getDouble(5D)*20*60), ECConfig.O_ENABLE.getBoolean(), false));
		tasks.put("monster", new TaskContainer((Runnable) new MonsterTask(worlds), (long) (ECConfig.M_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.M_TIME.getDouble(5D)*20*60), ECConfig.M_ENABLE.getBoolean(), false));
		tasks.put("villager", new TaskContainer((Runnable) new VillagerTask(worlds), (long) (ECConfig.VIL_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.VIL_TIME.getDouble(5D)*20*60), ECConfig.VIL_ENABLE.getBoolean(), false));
		tasks.put("item", new TaskContainer((Runnable) new FloatingItemsTask(worlds, items), (long) (ECConfig.FI_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.FI_TIME.getDouble(5D)*20*60), ECConfig.FI_ENABLE.getBoolean(), false));
		tasks.put("vehicle", new TaskContainer((Runnable) new VehicleTask(worlds), (long) (ECConfig.VEH_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.VEH_TIME.getDouble(5D)*20*60), ECConfig.VEH_ENABLE.getBoolean(), ECConfig.VEH_PROTECT.getBoolean()));
		tasks.put("all", new TaskContainer((Runnable) new CompleteTask(worlds), (long) (ECConfig.ALL_INIT_TIME.getDouble(0.5D)*20*60),
				(long) (ECConfig.ALL_TIME.getDouble(5D)*20*60), ECConfig.ALL_ENABLE.getBoolean(), ECConfig.ALL_PROTECT.getBoolean()));
	}
}
