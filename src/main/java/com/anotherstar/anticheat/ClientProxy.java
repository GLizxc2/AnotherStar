package com.anotherstar.anticheat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

import org.apache.commons.codec.digest.DigestUtils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		boolean is64 = ManagementFactory.getRuntimeMXBean().getVmName().contains("64");
		String dllName = "mods/AnotherStar/ANOTHERSTARANTICHEAT" + (is64 ? 64 : 32) + ".dll";
		File dllFile = new File(Minecraft.getMinecraft().mcDataDir, dllName);
		if (dllFile.exists()) {
			String dllmd5 = "";
			try (InputStream is = new FileInputStream(dllFile)) {
				dllmd5 = DigestUtils.md5Hex(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String dllsmd5 = is64 ? "bd120a0f1a93dc5916dd82c30cf0d9aa" : "471d4659c3ab5b0b252075c7d04e5f34";
			if (!dllmd5.equals(dllsmd5)) {
				dllFile.delete();
			}
		}
		if (!dllFile.exists()) {
			dllFile.getParentFile().mkdirs();
			try (InputStream is = getClass().getResourceAsStream("/ANOTHERSTARANTICHEAT" + (is64 ? 64 : 32) + ".dll");
					FileOutputStream fos = new FileOutputStream(dllFile);) {
				byte[] buf = new byte[8192];
				int len;
				while ((len = is.read(buf)) != -1) {
					fos.write(buf, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		System.load(dllFile.getAbsolutePath());

		/*
		 * ClassLoader cl = Thread.currentThread().getContextClassLoader(); if (cl
		 * instanceof LaunchClassLoader) {
		 * 
		 * @SuppressWarnings("resource") LaunchClassLoader lcl = (LaunchClassLoader) cl;
		 * List<URL> sources = lcl.getSources(); Iterator<URL> it = sources.iterator();
		 * while (it.hasNext()) { URL url = it.next(); if
		 * (url.getProtocol().equals("asmgen")) { it.remove(); break; } } }
		 */

		/*
		 * File forges = new File(Minecraft.getMinecraft().mcDataDir,
		 * "libraries/net/minecraftforge/forge"); File[] forgeDirs = forges.listFiles();
		 * for (File forgeDir : forgeDirs) { File[] forgefiles = forgeDir.listFiles();
		 * for (File forgefile : forgefiles) { if (forgefile.isFile()) { try {
		 * ((LaunchClassLoader) Thread.currentThread().getContextClassLoader())
		 * .addURL(forgefile.toURI().toURL()); } catch (MalformedURLException e) { //
		 * TODO 自动生成的 catch 块 e.printStackTrace(); } } } }
		 */

		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatSTCPacketMessageHandler(),
				AntiCheatSTCPacketMessage.class, 0, Side.CLIENT);
		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatCTSPacketMessageHandler(),
				AntiCheatCTSPacketMessage.class, 1, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

}
