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

package de.Lathanael.EC.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Lathanael.EC.Main.EntityCleaner;
import de.Lathanael.EC.Utils.ECConfig;
import de.Lathanael.EC.Utils.Scheduler;
import de.Lathanael.EC.Utils.TaskContainer;

import be.Balor.Manager.Commands.CommandArgs;
import be.Balor.Manager.Commands.CoreCommand;
import be.Balor.Tools.Utils;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Purge extends CoreCommand {

	public Purge() {
		super("ec_purge", "admincmd.entitycleaner.purge", "EntityCleaner");
	}

	@Override
	public void execute(CommandSender sender, CommandArgs args) {
		HashMap<String, String> replace = new HashMap<String, String>();
		String world = "";
		boolean noWorld = true;
		if (Utils.isPlayer(sender, false)) {
			if (args.length > 2)
				world = args.getString(0);
			else {
				world = ((Player) sender).getWorld().getName();
				noWorld = false;
			}
		} else if (args.length <= 2 && !args.hasFlag('c')) {
			sender.sendMessage("Invalid amount of arguments!");
			return;
		} else
			world = args.getString(0);
		String taskName;
		if (noWorld)
			taskName = world + "." + args.getString(0).toLowerCase();
		else
			taskName = world + "." + args.getString(1).toLowerCase();
		if (args.hasFlag('s')) {
			// Start/Stop a task
			if (Scheduler.taskIDs.containsKey(taskName)) {
				EntityCleaner.scheduler.stopTask(taskName);
				replace.put("name", taskName);
				sender.sendMessage(Utils.I18n("TaskStop", replace));
			} else {
				EntityCleaner.scheduler.startTask(taskName);
				replace.put("name", taskName);
				sender.sendMessage(Utils.I18n("TaskStart", replace));
			}
		} else if (args.hasFlag('t')) {
			// Set time between two executions of the task
			long time;
			if (noWorld)
				time = (long) (args.getDouble(1)*20*60);
			else
				time = (long) (args.getDouble(2)*20*60);
			ECConfig.getConfig().set(taskName + ".time", time/(20*60));
			TaskContainer container = Scheduler.tasks.get(taskName);
			container.setTime(time);
			Scheduler.tasks.put(taskName, container);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", taskName);
			replace.put("list", "Time - " + time);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('i')) {
			// Set the initial waiting time of the task
			long time;
			if (noWorld)
				time = (long) (args.getDouble(1)*20*60);
			else
				time = (long) (args.getDouble(2)*20*60);
			ECConfig.getConfig().set(taskName + ".inittime", time/(20*60));
			TaskContainer container = Scheduler.tasks.get(taskName);
			container.setInitTime(time);
			Scheduler.tasks.put(taskName, container);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", taskName);
			replace.put("list", "Initial waiting Time - " + time);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('o')) {
			// Turn a task on/off
			boolean on;
			if (noWorld)
				on = Boolean.parseBoolean(args.getString(1));
			else
				on = Boolean.parseBoolean(args.getString(2));
			ECConfig.getConfig().set(taskName + ".enable", on);
			TaskContainer container = Scheduler.tasks.get(taskName);
			container.setEnabled(on);
			Scheduler.tasks.put(taskName, container);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", taskName);
			replace.put("list", "Enable - " + on);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('r')) {
			// Restart a task
			EntityCleaner.scheduler.restartTask(taskName);
			replace.put("name", taskName);
			sender.sendMessage(Utils.I18n("TaskRestart", replace));
		} else if (args.hasFlag('a')) {
			// Set all values of a task (excluding the check value)
			long initTime;
			boolean on;
			long time;
			if (noWorld) {
				time = (long) (args.getDouble(1)*20*60);
				initTime = (long) (args.getDouble(2)*20*60);
				on = Boolean.parseBoolean(args.getString(3));
			} else {
				time = (long) (args.getDouble(2)*20*60);
				initTime = (long) (args.getDouble(3)*20*60);
				on = Boolean.parseBoolean(args.getString(4));
			}
			ECConfig.getConfig().set(taskName + ".enable", on);
			ECConfig.getConfig().set(taskName + ".time", time);
			ECConfig.getConfig().set(taskName + ".inittime", initTime);
			TaskContainer container = Scheduler.tasks.get(taskName);
			container.setEnabled(on);
			container.setTime(time);
			container.setInitTime(initTime);
			Scheduler.tasks.put(taskName, container);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", taskName);
			String list = "Enable - " + on + "//n" + "Initial waiting Time - " + initTime + "//n"
					+ "Time - " + time;
			replace.put("list", list);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('c')) {
			// Check values of a task.
			TaskContainer container = Scheduler.tasks.get(taskName);
			if (Utils.isPlayer(sender, false)) {
				ChatColor col1 = ChatColor.AQUA;
				ChatColor col2 = ChatColor.GOLD;
				sender.sendMessage(col2 + taskName + ":");
				sender.sendMessage(col1 + "Enabled: " + col2 + container.isEnabled());
				sender.sendMessage(col1 + "Initial Start Time: " + col2 + container.getInitTIme());
				sender.sendMessage(col1 + "Waiting Time: " + col2 + container.getTime());
				if (taskName.contains("cart") || taskName.contains("boat") || taskName.contains("vehicle")) {
					sender.sendMessage(col1 + "Protected: " + col2 + container.isProtected());
					sender.sendMessage(col1 + "Passenger: " + col2 + container.isPassanger());
				}
			} else {
				sender.sendMessage(taskName + ":");
				sender.sendMessage("Enabled: " + container.isEnabled());
				sender.sendMessage("Initial Start Time: " + container.getInitTIme());
				sender.sendMessage("Waiting Time: " + container.getTime());
				if (taskName.contains("cart") || taskName.contains("boat") || taskName.contains("vehicle")) {
					sender.sendMessage("Protected: " + container.isProtected());
					sender.sendMessage("Passenger: " + container.isPassanger());
				}
			}
		} else if (args.hasFlag('p')) {
			if (taskName.contains("cart") || taskName.contains("boat") || taskName.contains("vehicle")) {
				boolean on;
				if (noWorld)
					on = Boolean.parseBoolean(args.getString(1));
				else
					on = Boolean.parseBoolean(args.getString(2));
				ECConfig.getConfig().set(taskName + ".protect", on);
				TaskContainer container = Scheduler.tasks.get(taskName);
				container.setProtected(on);
				Scheduler.tasks.put(taskName, container);
				EntityCleaner.reloadConf();
				EntityCleaner.scheduler.reInitTaskList();
				replace.put("name", taskName);
				replace.put("list", "Protected - " + on);
				sender.sendMessage(Utils.I18n("TaskChange", replace));
			} else {
				if (Utils.isPlayer(sender, false)) {
					sender.sendMessage(ChatColor.RED + "Given task is not among the list of taks which have a portect value!");
				} else
					sender.sendMessage("Given task is not among the list of taks which have a portect value!");
			}
		} else if (args.hasFlag('e')) {
			if (taskName.contains("cart") || taskName.contains("boat") || taskName.contains("vehicle")) {
				boolean on;
				if (noWorld)
					on = Boolean.parseBoolean(args.getString(1));
				else
					on = Boolean.parseBoolean(args.getString(2));
				ECConfig.getConfig().set(taskName + ".protect", on);
				TaskContainer container = Scheduler.tasks.get(taskName);
				container.setPassanger(on);
				Scheduler.tasks.put(taskName, container);
				EntityCleaner.reloadConf();
				EntityCleaner.scheduler.reInitTaskList();
				replace.put("name", taskName);
				replace.put("list", "Protected - " + on);
				sender.sendMessage(Utils.I18n("TaskChange", replace));
			} else {
				if (Utils.isPlayer(sender, false)) {
					sender.sendMessage(ChatColor.RED + "Given task is not among the list of taks which have a passenger value!");
				} else
					sender.sendMessage("Given task is not among the list of taks which have a passenger value!");
			}
		}
	}

	@Override
	public boolean argsCheck(String... args) {
		return args != null && args.length >= 2;
	}
}
