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

package de.Lathanael.EC.Tasks;

import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

import de.Lathanael.EC.Utils.ECConfig;
import de.Lathanael.EC.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class CompleteTask implements Runnable {

	private List<World> worlds;
	public CompleteTask(List<World> worlds) {
		this.worlds = worlds;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		List<Entity> entites;
		for (World world : worlds) {
			 entites = world.getEntities();
			 for (Entity e : entites) {
				 if (ECConfig.ALL_PROTECT.getBoolean()) {
					 if (e instanceof Boat) {
						 Boat boat = (Boat) e;
						 if (ECConfig.ALL_PROTECT.getBoolean() && !Tools.isBoatInWater(boat))
							 boat.remove();
						 else if (!ECConfig.ALL_PROTECT.getBoolean())
							 boat.remove();
					 } else if (e instanceof Minecart) {
						 Minecart cart = (Minecart) e;
							if (Tools.isDerailed(cart)) {
								if (ECConfig.ALL_PROTECT.getBoolean() && Tools.isDerailed(cart)) {
									cart.remove();
								} else if (!ECConfig.ALL_PROTECT.getBoolean())
									cart.remove();
							}
					 }
				 } else
					 e.remove();
			 }
		}
	}
}
