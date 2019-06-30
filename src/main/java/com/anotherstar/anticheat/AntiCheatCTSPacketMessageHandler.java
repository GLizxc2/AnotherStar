package com.anotherstar.anticheat;

import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;

import com.anotherstar.anticheat.PlayerJoinEvent.PlayerSalt;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class AntiCheatCTSPacketMessageHandler implements IMessageHandler<AntiCheatCTSPacketMessage, IMessage> {

	@Override
	public IMessage onMessage(AntiCheatCTSPacketMessage message, MessageContext ctx) {
		PlayerSalt playerSalt = PlayerJoinEvent.timers.remove(ctx.getServerHandler().playerEntity.getDisplayName());
		playerSalt.timer.cancel();
		if (message.inGame) {
			HashSet<String> md5s = new HashSet<>();
			HashSet<String> necessaryMd5s = new HashSet<>();
			for (String md5 : ConfigLoader.md5s) {
				md5s.add(DigestUtils.md5Hex(
						DigestUtils.md5Hex(md5 + ctx.getServerHandler().playerEntity.getFoodStats().getFoodLevel())
								+ playerSalt.salt));
			}
			for (String md5 : ConfigLoader.necessaryMd5s) {
				necessaryMd5s.add(DigestUtils.md5Hex(
						DigestUtils.md5Hex(md5 + ctx.getServerHandler().playerEntity.getFoodStats().getFoodLevel())
								+ playerSalt.salt));
			}
			HashSet<String> clientMd5s = new HashSet<>();
			for (byte[] md5data : message.md5s) {
				try {
					String md5 = new String(md5data, "UTF-8");
					if (!md5s.contains(md5)) {
						ctx.getServerHandler().playerEntity.playerNetServerHandler
								.kickPlayerFromServer(ConfigLoader.antiCheatMessage);
						break;
					}
					clientMd5s.add(md5);
				} catch (Exception e) {
					ctx.getServerHandler().playerEntity.playerNetServerHandler
							.kickPlayerFromServer(ConfigLoader.antiCheatMessage);
				}
			}
			for (String md5 : necessaryMd5s) {
				if (!clientMd5s.contains(md5)) {
					ctx.getServerHandler().playerEntity.playerNetServerHandler
							.kickPlayerFromServer(ConfigLoader.antiCheatExtensionMessage);
					break;
				}
			}
		} else {
			if (message.md5s.length == 2 && message.md5s[0].length == 1 && message.md5s[1].length == 2) {
				ctx.getServerHandler().playerEntity.playerNetServerHandler
						.kickPlayerFromServer(ConfigLoader.antiVapeMessage);
			}
			if (ConfigLoader.receive) {
				ConfigLoader.md5s = new String[message.md5s.length];
				ConfigLoader.necessaryMd5s = new String[message.md5s.length];
				for (int i = 0; i < message.md5s.length; i++) {
					ConfigLoader.md5s[i] = new String(message.md5s[i]);
					ConfigLoader.necessaryMd5s[i] = new String(message.md5s[i]);
				}
				ConfigLoader.save();
			} else if (ConfigLoader.extension) {
				ConfigLoader.md5s = new String[message.md5s.length];
				for (int i = 0; i < message.md5s.length; i++) {
					ConfigLoader.md5s[i] = new String(message.md5s[i]);
				}
				ConfigLoader.save();
			} else {
				HashSet<String> md5s = new HashSet<>();
				HashSet<String> necessaryMd5s = new HashSet<>();
				for (String md5 : ConfigLoader.md5s) {
					md5s.add(DigestUtils.md5Hex(md5 + playerSalt.salt));
				}
				for (String md5 : ConfigLoader.necessaryMd5s) {
					necessaryMd5s.add(DigestUtils.md5Hex(md5 + playerSalt.salt));
				}
				HashSet<String> clientMd5s = new HashSet<>();
				for (byte[] md5data : message.md5s) {
					try {
						String md5 = new String(md5data, "UTF-8");
						if (!md5s.contains(md5)) {
							ctx.getServerHandler().playerEntity.playerNetServerHandler
									.kickPlayerFromServer(ConfigLoader.antiCheatMessage);
							break;
						}
						clientMd5s.add(md5);
					} catch (Exception e) {
						ctx.getServerHandler().playerEntity.playerNetServerHandler
								.kickPlayerFromServer(ConfigLoader.antiCheatMessage);
					}
				}
				for (String md5 : necessaryMd5s) {
					if (!clientMd5s.contains(md5)) {
						ctx.getServerHandler().playerEntity.playerNetServerHandler
								.kickPlayerFromServer(ConfigLoader.antiCheatExtensionMessage);
						break;
					}
				}
			}
		}
		return null;
	}

}
