package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class TestPacket extends Packet {
	
	public static byte EXCEPTION_TEST = 0;
	
	protected final byte test;

	public TestPacket(AbstractConnection source, final byte test) {
		super(source);
		this.test = test;
	}
	
	public TestPacket(AbstractConnection source, final byte[] bytes) {
		super(source);
		this.test = bytes[1];
	}
	
	public byte getTest() {
		return test;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[2];
		bytes[0] = getPacketType().getTypeByte();
		bytes[1] = test;
		return bytes;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.TEST_PACKET;
	}

}
