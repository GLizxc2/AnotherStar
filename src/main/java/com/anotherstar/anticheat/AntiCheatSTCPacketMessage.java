package com.anotherstar.anticheat;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class AntiCheatSTCPacketMessage implements IMessage {

	public byte[] salt;
	public boolean inGame;

	public AntiCheatSTCPacketMessage() {
	}

	public AntiCheatSTCPacketMessage(byte[] salt, boolean inGame) {
		this.salt = salt;
		this.inGame = inGame;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		salt = nbt.getByteArray("salt");
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setByteArray("salt", salt);
		ByteBufUtils.writeTag(buf, nbt);
	}

}
