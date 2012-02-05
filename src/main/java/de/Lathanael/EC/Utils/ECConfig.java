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
	DEBUG("debugMsg", true, "When set to true a lot of things will get logged!"),
	ITEMS("Items", Collections.list(new Enumeration<String>() {
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
	}), "Items which the Cleaners should remove when tehy are floating around, if you put all in the list, all floating items are removed."),
	ALL_ENABLE("All.enable", false,
			"When set to true all entities listed below will be cleared every xx minutes."),
	ALL_INIT_TIME("All.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	ALL_TIME("All.time", 5,
			"Time in minutes to wait between deleteing entities."),
	C_ENABLE("Carts.enable", false,
	"When set to true minecarts will be cleared every xx minutes."),
	C_DERAILED("Carts.derailed", true,
			"When set to true only derailed minecarts will be removed."),
	C_INIT_TIME("Carts.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	C_TIME("Carts.time", 5,
			"Time in minutes to wait between deleteing unused orbs."),
	O_ENABLE("Orbs.enable", false,
			"When set to true experience orbs will be cleared every xx minutes."),
	O_INIT_TIME("Orbs.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	O_TIME("Orbs.time", 5,
			"Time in minutes to wait between deleteing orbs."),
	B_ENABLE("Boats.enable", false,
			"When set to true boats will be cleared every xx minutes."),
	B_WATER("Boats.OutOfWater", true,
			"When set to true only boats outside of water will be removed."),
	B_INIT_TIME("Boats.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	B_TIME("Boats.time", 5,
			"Time in minutes to wait between deleteing unused boats."),
	AR_ENABLE("Arrows.enable", false,
			"When set to true arrows will be cleared every xx minutes."),
	AR_INIT_TIME("Arrows.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	AR_TIME("Arrows.time", 5,
			"Time in minutes to wait between deleteing arrows."),
	AN_ENABLE("Animals.enable", false,
			"When set to true animals will be cleared every xx minutes."),
	AN_INIT_TIME("Animals.inittime", 5,
			"Time in minutes before the first deletion occurs."),
	AN_TIME("Animals.time", 5,
			"Time in minutes to wait between deleteing animals.");

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
}