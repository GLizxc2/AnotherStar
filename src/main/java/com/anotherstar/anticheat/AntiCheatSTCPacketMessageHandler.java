package com.anotherstar.anticheat;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AntiCheatSTCPacketMessageHandler
		implements IMessageHandler<AntiCheatSTCPacketMessage, AntiCheatCTSPacketMessage> {

	@Override
	public AntiCheatCTSPacketMessage onMessage(AntiCheatSTCPacketMessage message, MessageContext ctx) {
		// ClassLoader cl = Thread.currentThread().getContextClassLoader();
		// if (cl instanceof LaunchClassLoader) {
		// @SuppressWarnings("resource")
		// LaunchClassLoader lcl = (LaunchClassLoader) cl;
		// List<URL> sources = lcl.getSources();
		// byte[][] md5s = new byte[sources.size()][];
		// try {
		// if (message.salt.length > 0) {
		// for (int i = 0; i < sources.size(); i++) {
		// URL url = sources.get(i);
		// md5s[i] = DigestUtils.md5Hex(DigestUtils.md5Hex(url.openStream()) + new
		// String(message.salt))
		// .getBytes();
		// }
		// } else {
		// for (int i = 0; i < sources.size(); i++) {
		// URL url = sources.get(i);
		// InputStream is = null;
		// try {
		// is = url.openStream();
		// } catch (IOException e) {
		// e.printStackTrace();
		// System.out.println(url.getPath());
		// }
		// md5s[i] = DigestUtils.md5Hex(is).getBytes();
		// }
		// }
		// return new AntiCheatCTSPacketMessage(md5s);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// try {
		// Class clazz = lcl.getClass();
		// Field ccf = clazz.getDeclaredField("cachedClasses");
		// ccf.setAccessible(true);
		// Map<String, Class<?>> cachedClasses = (Map<String, Class<?>>)
		// ccf.get(lcl);
		// Set<String> packs = new HashSet<>();
		// for (String str : cachedClasses.keySet()) {
		// int index = str.lastIndexOf('.');
		// String p = str.substring(0, index);
		// if(!packs.contains(p)) {
		// packs.add(p);
		// }
		//// String[] strs = str.split("\\.");
		//// if(!packs.contains(strs[0])) {
		//// packs.add(strs[0]);
		//// }
		// }
		// for (String string : packs) {
		// System.out.println(string);
		// }
		// } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
		// | IllegalAccessException e) {
		// e.printStackTrace();
		// }
		// }
		return messageHandler(message, ctx);
	}

	private native AntiCheatCTSPacketMessage messageHandler(AntiCheatSTCPacketMessage message, MessageContext ctx);

}
