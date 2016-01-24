package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class EventPacket extends Packet {
	public static final int LOGIN_SUCCESS = 1;
	protected final int eventCode;

	public EventPacket(final AbstractConnection source, final int eventCode) {
		super(source);
		this.eventCode = eventCode;
	}

	public EventPacket(final byte[] bytes, final AbstractConnection source) {
		super(source);
		this.eventCode = (bytes[1] << 24) | (bytes[2] << 16) | (bytes[3] << 8) | bytes[4];
	}

	public int getEventCode() {
		return eventCode;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.EVENT_PACKET;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[5]; // 1 for type id, 4 for event code
		bytes[0] = getPacketType().getTypeByte();
		bytes[1] = (byte) (eventCode >> 24);
		bytes[2] = (byte) ((eventCode >> 16) & 0xFF);
		bytes[3] = (byte) ((eventCode >> 8) & 0xFF);
		bytes[4] = (byte) (eventCode & 0xFF);
		return bytes;
	}

}
