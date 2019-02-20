package com.anotherstar.anticheat;

import java.util.HashSet;

import org.apache.commons.codec.digest.DigestUtils;

import com.anotherstar.anticheat.PlayerJoinEvent.PlayerSalt;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AntiCheatCTSPacketMessageHandler implements IMessageHandler<AntiCheatCTSPacketMessage, IMessage> {

	/*
	 * private static String modulusString =
	 * "110765265706288445432931740098429930486184776709780238438557625017629729661573053311960037088088056476891441153774532896215697933861615265976216025080531157954939381061122847093245480153835410088489980899310444547515616362801564379991216339336084947840837937083577860481298666622413144703510357744423856873247";
	 * private static String privateExponentString =
	 * "46811199235043884723986609175064677734346396089701745030024727102450381043328026268845951862745851965156510759358732282931568208403881136178696846768321267356928789780189985031058525539943424151785807761491334305713351706700232920994479762308513198807509163912459260953727448797718901389753582140608347129153";
	 * private static Cipher cipher;
	 * 
	 * static { BigInteger modulus = new BigInteger(modulusString); BigInteger
	 * privateExponent = new BigInteger(privateExponentString); RSAPrivateKeySpec
	 * privateKeySpec = new RSAPrivateKeySpec(modulus, privateExponent); try {
	 * KeyFactory keyFactory = KeyFactory.getInstance("RSA"); PrivateKey privateKey
	 * = keyFactory.generatePrivate(privateKeySpec); cipher =
	 * Cipher.getInstance("RSA"); cipher.init(Cipher.DECRYPT_MODE, privateKey); }
	 * catch (Exception e) { e.printStackTrace(); } }
	 */

	@Override
	public IMessage onMessage(AntiCheatCTSPacketMessage message, MessageContext ctx) {
		EntityPlayerMP player = ctx.getServerHandler().player;
		player.mcServer.addScheduledTask(() -> {
			chick(message, ctx);
		});
		return null;
	}

	private static void chick(AntiCheatCTSPacketMessage message, MessageContext ctx) {
		PlayerSalt playerSalt = PlayerJoinEvent.timers.remove(ctx.getServerHandler().player.getDisplayNameString());
		playerSalt.timer.cancel();
		if (ConfigLoader.receive) {
			ConfigLoader.md5s = new String[message.md5s.length];
			// ConfigLoader.necessaryMd5s = new String[message.md5s.length];
			for (int i = 0; i < message.md5s.length; i++) {
				ConfigLoader.md5s[i] = new String(message.md5s[i]);
				// ConfigLoader.necessaryMd5s[i] = new String(message.md5s[i]);
			}
			ConfigLoader.save();
		} /*
			 * else if (ConfigLoader.extension) { ConfigLoader.md5s = new
			 * String[message.md5s.length]; for (int i = 0; i < message.md5s.length; i++) {
			 * ConfigLoader.md5s[i] = new String(message.md5s[i]); } ConfigLoader.save(); }
			 */else {
			/*
			 * if (message.md5s.length == 0) { kickPlayer(ctx.getServerHandler().player,
			 * ConfigLoader.antiCheatMessage); }
			 */
			HashSet<String> md5s = new HashSet<>();
			// HashSet<String> necessaryMd5s = new HashSet<>();
			for (String md5 : ConfigLoader.md5s) {
				md5s.add(DigestUtils.md5Hex(md5 + playerSalt.salt));
			}
			// for (String md5 : ConfigLoader.necessaryMd5s) {
			// necessaryMd5s.add(DigestUtils.md5Hex(md5 + playerSalt.salt));
			// }
			HashSet<String> clientMd5s = new HashSet<>();
			for (byte[] md5data : message.md5s) {
				try {
					String md5 = new String(md5data, "UTF-8");
					if (!md5s.contains(md5)) {
						kickPlayer(ctx.getServerHandler().player, ConfigLoader.antiCheatMessage);
						break;
					}
					clientMd5s.add(md5);
				} catch (Exception e) {
					kickPlayer(ctx.getServerHandler().player, ConfigLoader.antiCheatMessage);
				}
			}
			for (String md5 : md5s) {
				if (!clientMd5s.contains(md5)) {
					kickPlayer(ctx.getServerHandler().player, ConfigLoader.antiCheatExtensionMessage);
					break;
				}
			}
		}
	}

	public static void addKickPlayerTask(EntityPlayerMP player, String message) {
		player.world.getMinecraftServer()
				.addScheduledTask(() -> player.connection.disconnect(new TextComponentString(message)));
	}

	public static void kickPlayer(EntityPlayerMP player, String message) {
		player.connection.disconnect(new TextComponentString(message));
	}

}
