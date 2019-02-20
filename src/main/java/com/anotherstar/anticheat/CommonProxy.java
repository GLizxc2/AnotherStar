package com.anotherstar.anticheat;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatSTCPacketMessageHandler(),
				AntiCheatSTCPacketMessage.class, 0, Side.CLIENT);
		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatCTSPacketMessageHandler(),
				AntiCheatCTSPacketMessage.class, 1, Side.SERVER);
		ConfigLoader.load(event);
	}

	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new PlayerJoinEvent());
	}

	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new ReloadCommand());
	}

}
