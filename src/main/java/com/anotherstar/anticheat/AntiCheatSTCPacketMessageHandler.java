package com.anotherstar.anticheat;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;

import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class AntiCheatSTCPacketMessageHandler
		implements IMessageHandler<AntiCheatSTCPacketMessage, AntiCheatCTSPacketMessage> {

	@Override
	public AntiCheatCTSPacketMessage onMessage(AntiCheatSTCPacketMessage message, MessageContext ctx) {
		boolean vape = false;
		try {
			LaunchClassLoader lcl = (LaunchClassLoader) Thread.currentThread().getContextClassLoader();
			Field classLoaderExceptionsField = lcl.getClass().getDeclaredField("classLoaderExceptions");
			classLoaderExceptionsField.setAccessible(true);
			Set<String> classLoaderExceptions = (Set<String>) classLoaderExceptionsField.get(lcl);
			classLoaderExceptions.remove("sun.");
			Object virtualmachine = null;
			try {
				Class vmc = Thread.currentThread().getContextClassLoader()
						.loadClass("com.sun.tools.attach.VirtualMachine");
				Method attach = vmc.getMethod("attach", String.class);
				virtualmachine = attach.invoke(null, ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Method gsp = virtualmachine.getClass().getMethod("getSystemProperties");
				gsp.invoke(virtualmachine);
				// System.out.println("一切正常");
			} catch (Exception e) {
				// System.out.println("检测到vape");
				// e.printStackTrace();
				// FMLCommonHandler.instance().exitJava(0, false);
				vape = true;
			}
			classLoaderExceptions.add("sun.");
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		if (vape) {
			byte[][] bs = new byte[2][];
			bs[0] = new byte[1];
			bs[1] = new byte[2];
			return new AntiCheatCTSPacketMessage(bs, false);
		} else {
			return messageHandler(message, ctx);
		}
	}

	private native AntiCheatCTSPacketMessage messageHandler(AntiCheatSTCPacketMessage message, MessageContext ctx);

}
