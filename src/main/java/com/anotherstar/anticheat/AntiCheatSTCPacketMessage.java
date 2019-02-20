package com.anotherstar.anticheat;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class AntiCheatSTCPacketMessage implements IMessage {

	public byte[] salt;

	public AntiCheatSTCPacketMessage() {
	}

	public AntiCheatSTCPacketMessage(byte[] salt) {
		this.salt = salt;
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
