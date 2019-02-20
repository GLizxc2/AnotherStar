package com.anotherstar.anticheat;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.codec.digest.DigestUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import scala.util.Random;

public class PlayerJoinEvent {

	/*
	 * private static String modulusString =
	 * "127165929499203230494093636558638013965252017663799535492473366241186172657381802456786953683177089298103209968185180374096740166047543803456852621212768600619629127825926162262624471403179175000577485553838478368190967564483813134073944752700839742123715548482599351441718070230200126591331603170595424433351";
	 * private static String publicExponentString = "65537"; private static Cipher
	 * cipher;
	 * 
	 * static { BigInteger modulus = new BigInteger(modulusString); BigInteger
	 * publicExponent = new BigInteger(publicExponentString); RSAPublicKeySpec
	 * publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent); try {
	 * KeyFactory keyFactory = KeyFactory.getInstance("RSA"); PublicKey publicKey =
	 * keyFactory.generatePublic(publicKeySpec); cipher = Cipher.getInstance("RSA");
	 * cipher.init(Cipher.ENCRYPT_MODE, publicKey); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	private Random rand = new Random();

	public static HashMap<String, PlayerSalt> timers = new HashMap<>();

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		String salt = DigestUtils.md5Hex(rand.nextString(16));
		byte[] esalt = new byte[0];
		try {
			if (!ConfigLoader.receive) {
				esalt = salt.getBytes("UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AntiCheat.antiCheatNetwork.sendTo(new AntiCheatSTCPacketMessage(esalt), (EntityPlayerMP) event.player);
		Timer timer = new Timer();
		timer.schedule(new PlayerTask((EntityPlayerMP) event.player), ConfigLoader.timeOut * 1000);
		timers.put(event.player.getDisplayNameString(), new PlayerSalt(salt, timer));
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

	private static class PlayerTask extends TimerTask {

		private EntityPlayerMP player;

		public PlayerTask(EntityPlayerMP player) {
			this.player = player;
		}

		@Override
		public void run() {
			AntiCheatCTSPacketMessageHandler.addKickPlayerTask(player, ConfigLoader.timeOutMessage);
		}

	}

	/*
	 * public static void test(Object o) { System.out.println(o); if (o instanceof
	 * byte[]) { String str = ""; try { str = new String((byte[]) o, "UTF-8"); }
	 * catch (UnsupportedEncodingException e) { // TODO 自动生成的 catch 块
	 * e.printStackTrace(); } System.out.println(str); } }
	 */

}
