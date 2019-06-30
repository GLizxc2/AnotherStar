package com.anotherstar.anticheat;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.relauncher.Side;

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
		FMLCommonHandler.instance().bus().register(new PlayerTickEvent());
	}

	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new ReloadCommand());
	}

}
