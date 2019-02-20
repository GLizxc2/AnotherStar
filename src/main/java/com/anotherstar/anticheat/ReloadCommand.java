package com.anotherstar.anticheat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;

public class ReloadCommand extends CommandBase {

	@Override
	public String getName() {
		return "anotherstaranticheatreload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			throw new WrongUsageException("commands.position.usage");
		} else {
			ConfigLoader.reload();
		}
	}

	@Override
	public List<String> getAliases() {
		ArrayList<String> list = new ArrayList<>();
		list.add("asacr");
		list.add("asacrl");
		list.add("asacreload");
		list.add("asanticheatreload");
		list.add("anotherstaracreload");
		return list;
	}

}
