package com.anotherstar.anticheat;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Timer;

import org.apache.commons.codec.digest.DigestUtils;

import com.anotherstar.anticheat.PlayerJoinEvent.PlayerSalt;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import scala.util.Random;

public class PlayerTickEvent {

	private Random rand = new Random();

	public static HashMap<EntityPlayer, Integer> players = new HashMap<>();

	@SubscribeEvent
	public void onPlayerUpdate(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;
		if (players.containsKey(player)) {
			int tick = players.get(player);
			if (++tick >= 20 * ConfigLoader.circleDetection) {
				tick = 0;
				if (!PlayerJoinEvent.timers.containsKey(player.getDisplayName())) {
					String salt = DigestUtils.md5Hex(rand.nextString(16));
					byte[] asalt = null;
					try {
						asalt = salt.getBytes("UTF-8");
						AntiCheat.antiCheatNetwork.sendTo(new AntiCheatSTCPacketMessage(asalt, true),
								(EntityPlayerMP) player);
						Timer timer = new Timer();
						timer.schedule(new PlayerJoinEvent.PlayerTask((EntityPlayerMP) player),
								ConfigLoader.timeOut * 1000);
						PlayerJoinEvent.timers.put(player.getDisplayName(), new PlayerSalt(salt, timer));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			players.put(player, tick);
		} else {
			players.put(player, 0);
		}
	}

}
