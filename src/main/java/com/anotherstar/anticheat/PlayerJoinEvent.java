package com.anotherstar.anticheat;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.codec.digest.DigestUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import scala.util.Random;

public class PlayerJoinEvent {

	private Random rand = new Random();

	public static HashMap<String, PlayerSalt> timers = new HashMap<>();

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		String playerName = event.player.getDisplayName();
		for (String name : ConfigLoader.whiteList) {
			if (playerName.equals(name)) {
				return;
			}
		}
		String salt = DigestUtils.md5Hex(rand.nextString(16));
		byte[] asalt = new byte[0];
		try {
			if (!ConfigLoader.receive && !ConfigLoader.extension) {
				asalt = salt.getBytes("UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		AntiCheat.antiCheatNetwork.sendTo(new AntiCheatSTCPacketMessage(asalt, false), (EntityPlayerMP) event.player);
		Timer timer = new Timer();
		timer.schedule(new PlayerTask((EntityPlayerMP) event.player), ConfigLoader.timeOut * 1000);
		timers.put(event.player.getDisplayName(), new PlayerSalt(salt, timer));
	}

	public static class PlayerSalt {

		public String salt;
		public Timer timer;

		public PlayerSalt(String salt, Timer timer) {
			super();
			this.salt = salt;
			this.timer = timer;
		}
	}

	public static class PlayerTask extends TimerTask {

		private EntityPlayerMP player;

		public PlayerTask(EntityPlayerMP player) {
			this.player = player;
		}

		@Override
		public void run() {
			player.playerNetServerHandler.kickPlayerFromServer(ConfigLoader.timeOutMessage);
		}

	}

}
