package com.git.cs309.mmoserver.packets;

public enum PacketType {
	ENTITY_UPDATE_PACKET((byte) 5), EVENT_PACKET((byte) 4), ERROR_PACKET((byte) 3), LOGIN_PACKET((byte) 2), MESSAGE_PACKET((byte) 1), NULL_PACKET((byte) 0);

	private final byte typeByte;

	private PacketType(final byte typeByte) {
		this.typeByte = typeByte;
	}

	public byte getTypeByte() {
		return typeByte;
	}
}
