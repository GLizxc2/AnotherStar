package com.anotherstar.anticheat;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class AntiCheatCTSPacketMessage implements IMessage {

	public byte[][] md5s;

	public AntiCheatCTSPacketMessage() {
	}

	public AntiCheatCTSPacketMessage(byte[][] md5s) {
		this.md5s = md5s;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		NBTTagList md5List = nbt.getTagList("md5s", 7);
		md5s = new byte[md5List.tagCount()][];
		for (int i = md5s.length - 1; i >= 0; i--) {
			md5s[i] = ((NBTTagByteArray) md5List.removeTag(i)).getByteArray();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList strList = new NBTTagList();
		for (byte[] md5 : md5s) {
			strList.appendTag(new NBTTagByteArray(md5));
		}
		nbt.setTag("md5s", strList);
		ByteBufUtils.writeTag(buf, nbt);
	}

}
