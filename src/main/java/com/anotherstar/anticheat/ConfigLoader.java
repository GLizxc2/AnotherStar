package com.anotherstar.anticheat;

import java.io.File;
import java.util.HashSet;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigLoader {

	public static Configuration config = null;
	public static String[] md5s = null;
	public static HashSet<String> md5Set = new HashSet<>();
	// public static String[] necessaryMd5s = null;
	// public static HashSet<String> necessaryMd5Set = new HashSet<>();
	public static boolean receive = false;
	// public static boolean extension = false;
	public static String antiCheatMessage = null;
	public static String antiCheatExtensionMessage = null;
	public static String timeOutMessage = null;
	public static int timeOut = 0;

	public static void load(FMLPreInitializationEvent event) {
		if (config == null) {
			File configFile = event.getSuggestedConfigurationFile();
			config = new Configuration(configFile);
			config.load();
			md5s = config.getStringList("md5s", Configuration.CATEGORY_GENERAL, new String[0], "");
			// necessaryMd5s = config.getStringList("necessaryMd5s",
			// Configuration.CATEGORY_GENERAL, new String[0], "");
			receive = config.getBoolean("receive", Configuration.CATEGORY_GENERAL, true, "");
			// extension = config.getBoolean("extension", Configuration.CATEGORY_GENERAL,
			// false, "");
			antiCheatMessage = config.getString("antiCheatMessage", Configuration.CATEGORY_GENERAL, "请勿自行安装mod",
					"踢出玩家显示信息");
			antiCheatExtensionMessage = config.getString("antiCheatExtensionMessage", Configuration.CATEGORY_GENERAL,
					"请勿自行删除mod", "踢出玩家显示信息");
			timeOutMessage = config.getString("timeOutMessage", Configuration.CATEGORY_GENERAL, "验证超时", "验证超时显示信息");
			timeOut = config.getInt("timeOut", Configuration.CATEGORY_GENERAL, 10, 0, 120, "验证超时时间(s)");
			config.save();
			md5Set.clear();
			// necessaryMd5Set.clear();
			for (String md5 : md5s) {
				md5Set.add(md5);
			}
			// for (String md5 : necessaryMd5s) {
			// necessaryMd5Set.add(md5);
			// }
		}
	}

	public static void save() {
		if (config != null) {
			config.getConfigFile().delete();
			config.load();
			config.getStringList("md5s", Configuration.CATEGORY_GENERAL, md5s, "");
			// config.getStringList("necessaryMd5s", Configuration.CATEGORY_GENERAL,
			// necessaryMd5s, "");
			receive = config.getBoolean("receive", Configuration.CATEGORY_GENERAL, false, "");
			// extension = config.getBoolean("extension", Configuration.CATEGORY_GENERAL,
			// false, "");
			antiCheatMessage = config.getString("antiCheatMessage", Configuration.CATEGORY_GENERAL, antiCheatMessage,
					"踢出玩家显示信息");
			antiCheatExtensionMessage = config.getString("antiCheatExtensionMessage", Configuration.CATEGORY_GENERAL,
					"请勿自行删除mod", "踢出玩家显示信息");
			timeOutMessage = config.getString("timeOutMessage", Configuration.CATEGORY_GENERAL, timeOutMessage,
					"验证超时显示信息");
			timeOut = config.getInt("timeOut", Configuration.CATEGORY_GENERAL, timeOut, 0, 120, "验证超时时间(s)");
			config.save();
			md5Set.clear();
			// necessaryMd5Set.clear();
			for (String md5 : md5s) {
				md5Set.add(md5);
			}
			// for (String md5 : necessaryMd5s) {
			// necessaryMd5Set.add(md5);
			// }
		}
	}

	public static void reload() {
		if (config != null) {
			config.load();
			md5s = config.getStringList("md5s", Configuration.CATEGORY_GENERAL, new String[0], "");
			// necessaryMd5s = config.getStringList("necessaryMd5s",
			// Configuration.CATEGORY_GENERAL, new String[0], "");
			receive = config.getBoolean("receive", Configuration.CATEGORY_GENERAL, true, "");
			// extension = config.getBoolean("extension", Configuration.CATEGORY_GENERAL,
			// false, "");
			antiCheatMessage = config.getString("antiCheatMessage", Configuration.CATEGORY_GENERAL, "请勿自行安装mod",
					"踢出玩家显示信息");
			antiCheatExtensionMessage = config.getString("antiCheatExtensionMessage", Configuration.CATEGORY_GENERAL,
					"请勿自行删除mod", "踢出玩家显示信息");
			timeOutMessage = config.getString("timeOutMessage", Configuration.CATEGORY_GENERAL, "验证超时", "验证超时显示信息");
			timeOut = config.getInt("timeOut", Configuration.CATEGORY_GENERAL, 10, 0, 120, "验证超时时间(s)");
			md5Set.clear();
			// necessaryMd5Set.clear();
			for (String md5 : md5s) {
				md5Set.add(md5);
			}
			// for (String md5 : necessaryMd5s) {
			// necessaryMd5Set.add(md5);
			// }
		}
	}

}
