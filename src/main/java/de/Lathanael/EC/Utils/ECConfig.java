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

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.PluginDescriptionFile;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * Code-Parts taken from AdminCmd ConfigEnum
 */
public enum ECConfig {

	WORLDS("Worlds", Collections.list(new Enumeration<String>() {
		private int count = 0;
		private final String[] val = new String[] { "world", "world_nether", "world_the_end" };

		@Override
		public boolean hasMoreElements() {
			return count < val.length;
		}

		@Override
		public String nextElement() {
			return val[count++];
		}
	}), "Worlds in which the Cleaners should work."),
	DEBUG("debugMsg", false, "When set to true a lot of things will get logged!"),
	ALL_ENABLE("all.enable", false,
			"When set to true all entities listed below will be cleared every xx minutes."),
	ALL_INIT_TIME("all.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	ALL_TIME("all.time", 5,
			"Time in minutes to wait between deleteing entities."),
	ALL_PROTECT("all.protect", true,
			"If set to true only derailed carts and boats outside of water will be removed."),
	C_ENABLE("cart.enable", false,
	"When set to true minecarts will be cleared every xx minutes."),
	C_DERAILED("cart.protect", true,
			"When set to true only derailed minecarts will be removed."),
	C_INIT_TIME("cart.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	C_TIME("cart.time", 5,
			"Time in minutes to wait between deleteing unused orbs."),
	O_ENABLE("orb.enable", false,
			"When set to true experience orbs will be cleared every xx minutes."),
	O_INIT_TIME("orb.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	O_TIME("orb.time", 5,
			"Time in minutes to wait between deleteing orbs."),
	B_ENABLE("boat.enable", false,
			"When set to true boats will be cleared every xx minutes."),
	B_WATER("boat.protect", true,
			"When set to true only boats outside of water will be removed."),
	B_INIT_TIME("boat.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	B_TIME("boat.time", 5,
			"Time in minutes to wait between deleteing unused boats."),
	AR_ENABLE("arrow.enable", false,
			"When set to true arrows will be cleared every xx minutes."),
	AR_INIT_TIME("arrow.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	AR_TIME("arrow.time", 5,
			"Time in minutes to wait between deleteing arrows."),
	AN_ENABLE("animal.enable", false,
			"When set to true animals will be cleared every xx minutes."),
	AN_INIT_TIME("animal.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	AN_TIME("animal.time", 5,
			"Time in minutes to wait between deleteing animals."),
	FI_ENABLE("item.enable", false,
			"When set to true floating Items specified in the itmes List will be cleared every xx minutes."),
	FI_INIT_TIME("item.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	FI_TIME("item.time", 5,
			"Time in minutes to wait between deleteing items."),
	ITEMS("item.List", Collections.list(new Enumeration<String>() {
		private int count = 0;
		private final String[] val = new String[] { "Minecart", "Boat"};

		@Override
		public boolean hasMoreElements() {
			return count < val.length;
		}

		@Override
		public String nextElement() {
			return val[count++];
		}
	}), "Items which the Cleaners should remove when they are floating around, if you put all in the list, all floating items are removed."),
	M_ENABLE("monster.enable", false,
			"When set to true monsters will be removed every xx minutes."),
	M_INIT_TIME("monster.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	M_TIME("monster.time", 5,
			"Time in minutes to wait between removing monsters."),
	VEH_ENABLE("vehicle.enable", false,
			"When set to true vehicles(carts and boats) will be cleared every xx minutes."),
	VEH_INIT_TIME("vehicle.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	VEH_TIME("vehicle.time", 5,
			"Time in minutes to wait between removing vehicles."),
	VEH_PROTECT("vehicle.protect", true,
			"If set to true only derailed carts and boats outside of water will be removed."),
	VIL_ENABLE("villager.enable", false,
			"When set to true villagers will be removed every xx minutes."),
	VIL_INIT_TIME("villager.inittime", 0.5,
			"Time in minutes before the first deletion occurs."),
	VIL_TIME("villager.time", 5,
			"Time in minutes to wait between removing villagers.");

	private final String confVal;
	private final Object defaultVal;
	private final String description;
	private static ConfigurationSection config;
	private static String pluginVersion;
	private static String pluginName;

	/**
	* @param confVal
	* @param defaultVal
	* @param description
	*/
	private ECConfig(String confVal, Object defaultVal, String description) {
		this.confVal = confVal;
		this.defaultVal = defaultVal;
		this.description = description;
	}

	public String getString() {
		return config.getString(confVal);
	}

	public int getInt() {
		return config.getInt(confVal);
	}

	public double getDouble() {
		return config.getDouble(confVal);
	}

	public double getDouble(double defVal) {
		return config.getDouble(confVal, defVal);
	}

	public boolean getBoolean() {
		return config.getBoolean(confVal);
	}

	public long getLong() {
		return config.getLong(confVal);
	}

	public List<String> getStringList() {
		return config.getStringList(confVal);
	}

	/**
	* @return the defaultvalues
	*/
	public static Map<String, Object> getDefaultvalues() {
		Map<String, Object> values = new LinkedHashMap<String, Object>();
		for (ECConfig ce : values())
			values.put(ce.confVal, ce.defaultVal);
		return values;
	}

	public static String getHeader() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Configuration file of ").append(pluginName).append('\n');
		buffer.append("Plugin Version: ").append(pluginVersion).append('\n').append('\n');
		for (ECConfig ce : values())
			buffer.append(ce.confVal).append("\t:\t").append(ce.description).append(" (Default : ")
			.append(ce.defaultVal).append(')').append('\n');
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