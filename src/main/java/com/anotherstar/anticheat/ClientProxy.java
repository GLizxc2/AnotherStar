package com.anotherstar.anticheat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;

import org.apache.commons.codec.digest.DigestUtils;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		boolean is64 = ManagementFactory.getRuntimeMXBean().getVmName().contains("64");
		String dllName = "mods/AnotherStar/ANOTHERSTARANTICHEAT" + (is64 ? 64 : 32) + ".dll";
		String attachName = "mods/AnotherStar/attach" + (is64 ? 64 : 32) + ".dll";
		File dllFile = new File(Minecraft.getMinecraft().mcDataDir, dllName);
		File attachFile = new File(Minecraft.getMinecraft().mcDataDir, attachName);
		if (dllFile.exists()) {
			String dllmd5 = "";
			try (InputStream is = new FileInputStream(dllFile)) {
				dllmd5 = DigestUtils.md5Hex(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String dllsmd5 = is64 ? "c03a8880ce7b169de1afbff70c31b4fc" : "39d901a2bed09b908b044c93edf38676";
			if (!dllmd5.equals(dllsmd5)) {
				dllFile.delete();
			}

		}
		if (attachFile.exists()) {
			String attachmd5 = "";
			try (InputStream is = new FileInputStream(attachFile)) {
				attachmd5 = DigestUtils.md5Hex(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String attachsmd5 = is64 ? "43e0668e9da12fd20922bdc56d58decf" : "27d0d1ac1e6798c1ba41815c2f8a2b72";
			if (!attachmd5.equals(attachsmd5)) {
				attachFile.delete();
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
		if (!attachFile.exists()) {
			attachFile.getParentFile().mkdirs();
			try (InputStream is = getClass().getResourceAsStream("/attach" + (is64 ? 64 : 32) + ".dll");
					FileOutputStream fos = new FileOutputStream(attachFile);) {
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
		System.load(attachFile.getAbsolutePath());
		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatSTCPacketMessageHandler(),
				AntiCheatSTCPacketMessage.class, 0, Side.CLIENT);
		AntiCheat.antiCheatNetwork.registerMessage(new AntiCheatCTSPacketMessageHandler(),
				AntiCheatCTSPacketMessage.class, 1, Side.SERVER);
	}

	@Override
	public void init(FMLInitializationEvent event) {
	}

}
