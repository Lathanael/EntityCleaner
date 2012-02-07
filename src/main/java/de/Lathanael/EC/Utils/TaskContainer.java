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
	private boolean protect;

	public TaskContainer(Runnable task, long initTime, long time, boolean enabled, boolean protect) {
		this.task = task;
		this.initTime = initTime;
		this.time = time;
		this.enabled = enabled;
		this.protect = protect;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}

	public long getInitTIme() {
		return initTime;
	}

	public void setInitTime(long time) {
		initTime = time;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isProtected() {
		return protect;
	}

	public void setProtected(boolean protect) {
		this.protect = protect;
	}
}
