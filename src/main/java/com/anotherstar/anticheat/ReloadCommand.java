package com.anotherstar.anticheat;

import java.util.Arrays;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class ReloadCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "anotherstaranticheatreload";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length > 0) {
			throw new WrongUsageException("commands.position.usage");
		} else {
			ConfigLoader.reload();
		}
	}

	@Override
	public List getCommandAliases() {
		return Arrays.asList(new String[] { "aacreload" });
	}

}
