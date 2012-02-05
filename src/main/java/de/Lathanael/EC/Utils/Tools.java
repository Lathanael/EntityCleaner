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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Minecart;
import org.bukkit.material.Rails;

import de.Lathanael.EC.Main.EntityCleaner;

import be.Balor.bukkit.AdminCmd.ACPluginManager;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class Tools {

	/**
	 * @author bergerkiller
	 * http://forums.bukkit.org/members/bergerkiller.96957/
	 */
	public static boolean isDerailed(Minecart m) {
		return getRails(m) == null;
	}

	/**
	 * @author bergerkiller
	 * http://forums.bukkit.org/members/bergerkiller.96957/
	 */
	public static Rails getRails(Minecart m) {
		Block b = m.getLocation().getBlock();
		Rails rails = null;
		if (b.getState().getData() instanceof Rails) {
			rails = (Rails) b.getState().getData();
			if (EntityCleaner.debug)
				debugMsg("Cart is railed!");
		} else {
			b = b.getRelative(BlockFace.DOWN);
			if (b.getState().getData() instanceof Rails) {
				rails = (Rails) b.getState().getData();
				if (EntityCleaner.debug)
					debugMsg("Cart is railed!");
			}
		}
		return rails;
	}

	/**
	 * Checks if a boat is in water.
	 *
	 * @param boat - The boat to be checked.
	 * @return
	 */
	public static boolean isBoatInWater(Boat boat) {
		Block b = boat.getLocation().getBlock();
		if (b.equals(Material.WATER))
			return true;
		return false;
	}

	/**
	 * Logs a message to the console.
	 *
	 * @param message - The message to be displayed
	 */
	public static void debugMsg(String message) {
		ACPluginManager.getPluginInstance("EntityCleaner").getLogger().info(message);
	}
}