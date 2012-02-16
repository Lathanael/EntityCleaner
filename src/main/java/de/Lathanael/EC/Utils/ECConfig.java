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
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class ECConfig {

	private static List<String> header;
	private static ConfigurationSection config;
	private static String pluginVersion;
	private static String pluginName;

	public ECConfig() {
	}

	public static String getString(String path) {
		return config.getString(path);
	}

	public static int getInt(String path) {
		return config.getInt(path);
	}

	public static double getDouble(String path) {
		return config.getDouble(path);
	}

	public static double getDouble(String path, double defVal) {
		return config.getDouble(path, defVal);
	}

	public static boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	public static long getLong(String path) {
		return config.getLong(path);
	}

	public static List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	/**
	* @return the defaultvalues
	*/
	public static Map<String, Object> getDefaultvalues() {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		values.put("debugMsg", false);
		values.put("world.all.enable", false);
		values.put("world.all.inittime", 0.5D);
		values.put("world.all.time", 5);
		values.put("world.cart.enable", false);
		values.put("world.cart.inittime", 0.5D);
		values.put("world.cart.time", 5);
		values.put("world.cart.protect", true);
		values.put("world.cart.passenger", true);
		values.put("world.boat.enable", false);
		values.put("world.boat.inittime", 0.5D);
		values.put("world.boat.time", 5);
		values.put("world.boat.protect", true);
		values.put("world.boat.passenger", true);
		values.put("world.vehicle.enable", false);
		values.put("world.vehicle.inittime", 0.5D);
		values.put("world.vehicle.time", 5);
		values.put("world.vehicle.protect", true);
		values.put("world.vehicle.passenger", true);
		values.put("world.orb.enable", false);
		values.put("world.orb.inittime", 0.5D);
		values.put("world.orb.time", 5);
		values.put("world.arrow.enable", false);
		values.put("world.arrow.inittime", 0.5D);
		values.put("world.arrow.time", 5);
		values.put("world.monster.enable", false);
		values.put("world.monster.inittime", 0.5D);
		values.put("world.monster.time", 5);
		values.put("world.villager.enable", false);
		values.put("world.villager.inittime", 0.5D);
		values.put("world.villager.time", 5);
		values.put("world.animal.enable", false);
		values.put("world.animal.inittime", 0.5D);
		values.put("world.animal.time", 5);
		values.put("world.item.enable", false);
		values.put("world.item.inittime", 0.5D);
		values.put("world.item.time", 5);
		values.put("world.item.list", Arrays.asList("Minecart", "Boat"));
		return values;
	}

	public static String getHeader() {
		header = new ArrayList<String>();
		header.add("xx.xx.enable: When set to true all entities associated with the Task will be cleared every xx minutes.");
		header.add("xx.xx.inittime: Time in minutes before the first deletion occurs.");
		header.add("xx.xx.time: Time in minutes to wait between deleteing");
		header.add("xx.xx.protect: If set to true only derailed carts and boats outside of water will be removed.");
		header.add("xx.xx.passenger: If set to true only carts and boats without passengers will be removed.");
		header.add("xx.item.list: Items which the item task should remove when they are floating around, if you put \"all\" in the list, all floating items are removed.");
		StringBuffer buffer = new StringBuffer();
		buffer.append("Configuration file of ").append(pluginName).append('\n');
		buffer.append("Plugin Version: ").append(pluginVersion).append('\n').append('\n');
		for (String s : header)
			buffer.append(s).append('\n');
		return buffer.toString();
	}

	/**
	* @param config
	* the config to set
	*/
	public static void setConfig(ConfigurationSection config) {
		ECConfig.config = config;
	}

	public static void setPluginInfos(PluginDescriptionFile pdf) {
		ECConfig.pluginVersion = pdf.getVersion();
		ECConfig.pluginName = pdf.getName();
	}

	public static ConfigurationSection getConfig() {
		return config;
	}
}