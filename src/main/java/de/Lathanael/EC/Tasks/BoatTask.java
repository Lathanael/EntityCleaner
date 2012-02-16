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

import de.Lathanael.EC.Utils.ECConfig;
import de.Lathanael.EC.Utils.Tools;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class BoatTask implements Runnable {

	private World world;
	public BoatTask(World world) {
		this.world = world;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		boolean protect = ECConfig.getBoolean(world.getName() + ".boat.protect");
		boolean passenger = ECConfig.getBoolean(world.getName() + ".boat.passenger");
		List<Entity> entites;
		entites = world.getEntities();
		for (Entity e : entites) {
			if (e instanceof Boat) {
				Boat boat = (Boat) e;
				Entity ep = boat.getPassenger();
				if (protect && !Tools.isBoatInWater(boat)) {
					if (!passenger)
						boat.remove();
					else if (ep == null)
						boat.remove();
				} else if (passenger) {
					if (!protect) {
						if (ep == null)
							boat.remove();
					} else if (ep == null && protect && !Tools.isBoatInWater(boat))
						boat.remove();
				} else
					boat.remove();
			}
		}
	}
}
