package com.anotherstar.anticheat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = AntiCheat.MODID, name = AntiCheat.NAME, version = AntiCheat.VERSION, acceptedMinecraftVersions = "1.7.10")
public class AntiCheat {

	public static final String MODID = "anotheranticheat";
	public static final String NAME = "AnotherAntiCheat Mod";
	public static final String VERSION = "2.0.0";

	@SidedProxy(clientSide = "com.anotherstar.anticheat.ClientProxy", serverSide = "com.anotherstar.anticheat.CommonProxy")
	public static CommonProxy proxy;

	@Instance(AntiCheat.MODID)
	public static AntiCheat instance;

	public static SimpleNetworkWrapper antiCheatNetwork;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		antiCheatNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

}