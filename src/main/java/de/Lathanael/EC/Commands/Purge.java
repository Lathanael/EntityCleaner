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

import org.bukkit.command.CommandSender;

import be.Balor.Manager.Commands.CommandArgs;
import be.Balor.Manager.Commands.CoreCommand;

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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean argsCheck(String... args) {
		return args != null && args.length >= 1;
	}

	/* (non-Javadoc)
	 * @see be.Balor.Manager.Commands.CoreCommand#registerBukkitPerm()
	 */
	@Override
	public void registerBukkitPerm() {
		plugin.getPermissionLinker().addPermChild("admincmd.entitycleaner.purge.cart");
		plugin.getPermissionLinker().addPermChild("admincmd.entitycleaner.purge.orbs");
		super.registerBukkitPerm();
	}
}
