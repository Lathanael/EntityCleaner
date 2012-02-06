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

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class TaskContainer {

	private Runnable task;
	private long initTime;
	private long time;
	private boolean enabled;

	public TaskContainer(Runnable task, long initTime, long time, boolean enabled) {
		this.task = task;
		this.initTime = initTime;
		this.time = time;
		this.enabled = enabled;
	}

	public Runnable getTask() {
		return task;
	}

	public long getInitTIme() {
		return initTime;
	}

	public long getTime() {
		return time;
	}

	public boolean isEnabled() {
		return enabled;
	}
}
