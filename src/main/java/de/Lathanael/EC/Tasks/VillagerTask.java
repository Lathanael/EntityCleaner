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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class VillagerTask implements Runnable {

	private World world;
	public VillagerTask(World world) {
		this.world = world;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		List<Entity> entites;
		entites = world.getEntities();
		for (Entity e : entites) {
			if (e instanceof Villager) {
				e.remove();
			}
		}
	}
}
