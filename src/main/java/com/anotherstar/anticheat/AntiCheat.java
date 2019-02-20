package com.anotherstar.anticheat;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod(modid = AntiCheat.MODID, name = AntiCheat.NAME, version = AntiCheat.VERSION)
public class AntiCheat {

	public static final String MODID = "anotherstaranticheat";
	public static final String NAME = "AnotherStarAntiCheat Mod";
	public static final String VERSION = "beta-1.0.10";

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