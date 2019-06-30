package com.anotherstar.anticheat;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class AntiCheatCTSPacketMessage implements IMessage {

	public byte[][] md5s;
	public boolean inGame;

	public AntiCheatCTSPacketMessage() {
	}

	public AntiCheatCTSPacketMessage(byte[][] md5s, boolean inGame) {
		this.md5s = md5s;
		this.inGame = inGame;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		NBTTagCompound nbt = ByteBufUtils.readTag(buf);
		NBTTagList md5List = nbt.getTagList("md5s", 7);
		md5s = new byte[md5List.tagCount()][];
		for (int i = md5s.length - 1; i >= 0; i--) {
			md5s[i] = ((NBTTagByteArray) md5List.removeTag(i)).func_150292_c();
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
