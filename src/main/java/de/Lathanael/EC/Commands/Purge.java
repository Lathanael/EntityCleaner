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
		if (args.hasFlag('s')) {
			// Start/Stop a task
			if (Scheduler.taskIDs.containsKey(args.getString(0).toLowerCase())) {
				EntityCleaner.scheduler.stopTask(args.getString(0).toLowerCase());
				replace.put("name", args.getString(0));
				sender.sendMessage(Utils.I18n("TaskStop", replace));
			} else {
				EntityCleaner.scheduler.startTask(args.getString(0).toLowerCase());
				replace.put("name", args.getString(0));
				sender.sendMessage(Utils.I18n("TaskStart", replace));
			}
		} else if (args.hasFlag('t')) {
			// Set time between two executions of the task
			long time = (long) (args.getDouble(1)*20*60);
			ECConfig.getConfig().set(args.getString(0) + ".time", time);
			TaskContainer container = Scheduler.tasks.get(args.getString(0));
			container.setTime(time);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", args.getString(0));
			replace.put("list", "Time - " + time);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('i')) {
			// Set the initial waiting time of the task
			long time = (long) (args.getDouble(1)*20*60);
			ECConfig.getConfig().set(args.getString(0) + ".inittime", time);
			TaskContainer container = Scheduler.tasks.get(args.getString(0));
			container.setInitTime(time);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", args.getString(0));
			replace.put("list", "Initial waiting Time - " + time);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('o')) {
			// Turn a task on/off
			boolean on = Boolean.parseBoolean(args.getString(1));
			ECConfig.getConfig().set(args.getString(0) + ".enable", on);
			TaskContainer container = Scheduler.tasks.get(args.getString(0));
			container.setEnabled(on);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", args.getString(0));
			replace.put("list", "Enable - " + on);
			sender.sendMessage(Utils.I18n("TaskChange", replace));
		} else if (args.hasFlag('r')) {
			// Restart a task
			EntityCleaner.scheduler.restartTask(args.getString(0));
			replace.put("name", args.getString(0));
			sender.sendMessage(Utils.I18n("TaskRestart", replace));
		} else if (args.hasFlag('a')) {
			// Set all values of a task (excluding the check value)
			long time = (long) (args.getDouble(1)*20*60);
			long initTime = (long) (args.getDouble(2)*20*60);
			boolean on = Boolean.parseBoolean(args.getString(3));
			String task = args.getString(0);
			ECConfig.getConfig().set(task + ".enable", on);
			ECConfig.getConfig().set(task + ".time", time);
			ECConfig.getConfig().set(task + ".inittime", initTime);
			TaskContainer container = Scheduler.tasks.get(args.getString(0));
			container.setEnabled(on);
			container.setTime(time);
			container.setInitTime(initTime);
			EntityCleaner.reloadConf();
			EntityCleaner.scheduler.reInitTaskList();
			replace.put("name", args.getString(0));
			String list = "Enable - " + on + "//n" + "Initial waiting Time - " + initTime+ "//n"
					+ "Time - " + time;
			replace.put("list", list);
			sender.sendMessage(Utils.I18n("TaskChanged", replace));
		} else if (args.hasFlag('c')) {
			// Check values of a task.
			String task = args.getString(0);
			TaskContainer container = Scheduler.tasks.get(task);
			if (Utils.isPlayer(sender, false)) {
				ChatColor col1 = ChatColor.AQUA;
				ChatColor col2 = ChatColor.GOLD;
				sender.sendMessage(col1 + "Enabled: " + col2 + container.isEnabled());
				sender.sendMessage(col1 + "Initial Start Time: " + col2 + container.getInitTIme());
				sender.sendMessage(col1 + "Waiting Time: " + col2 + container.getTime());
				if (task.equalsIgnoreCase("cart") || task.equalsIgnoreCase("boat") || task.equalsIgnoreCase("vehicle")
						|| task.equalsIgnoreCase("all")) {
					sender.sendMessage(col1 + "Protected: " + col2 + container.isProtected());
				}
			} else {
				sender.sendMessage("Enabled: " + container.isEnabled());
				sender.sendMessage("Initial Start Time: " + container.getInitTIme());
				sender.sendMessage("Waiting Time: " + container.getTime());
				if (task.equalsIgnoreCase("cart") || task.equalsIgnoreCase("boat") || task.equalsIgnoreCase("vehicle")
						|| task.equalsIgnoreCase("all")) {
					sender.sendMessage("Protected: " + container.isProtected());
				}
			}
		} else if (args.hasFlag('p')) {
			String task = args.getString(0);
			if (task.equalsIgnoreCase("cart") || task.equalsIgnoreCase("boat") || task.equalsIgnoreCase("vehicle")
					|| task.equalsIgnoreCase("all")) {
				boolean on = Boolean.parseBoolean(args.getString(1));
				ECConfig.getConfig().set(args.getString(0) + ".protected", on);
				TaskContainer container = Scheduler.tasks.get(args.getString(0));
				container.setProtected(on);
				EntityCleaner.reloadConf();
				EntityCleaner.scheduler.reInitTaskList();
				replace.put("name", args.getString(0));
				replace.put("list", "Protected - " + on);
				sender.sendMessage(Utils.I18n("TaskChanged", replace));
			}
		}
	}

	@Override
	public boolean argsCheck(String... args) {
		return args != null && args.length >= 2;
	}
}
