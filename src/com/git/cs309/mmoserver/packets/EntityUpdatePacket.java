package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class EntityUpdatePacket extends Packet {
	private final int positionX, positionY, destX, destY, uniqueId, entityId, anim, health;

	public EntityUpdatePacket(AbstractConnection source, final int entityId, final int positionX, final int positionY,
			final int destX, final int destY, final int uniqueId, final int anim, final int health) {
		super(source);
		this.positionX = positionX;
		this.positionY = positionY;
		this.destX = destX;
		this.destY = destY;
		this.uniqueId = uniqueId;
		this.entityId = entityId;
		this.anim = anim;
		this.health = health;
	}

	public EntityUpdatePacket(final byte[] bytes, AbstractConnection source) {
		super(source);
		int index = 1;
		this.positionX = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.positionY = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.destX = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.destY = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.uniqueId = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.anim = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.health = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
		this.entityId = (bytes[index++] << 24) | (bytes[index++] << 16) | (bytes[index++] << 8) | bytes[index++];
	}

	public int getAnim() {
		return anim;
	}

	public int getDestX() {
		return destX;
	}

	public int getDestY() {
		return destY;
	}

	public int getEntityID() {
		return entityId;
	}

	public int getHealth() {
		return health;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.ENTITY_UPDATE_PACKET;
	}

	public int getPositionX() {
		return positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public int getUniqueID() {
		return uniqueId;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[29];
		int index = 0;
		int[] ints = { positionX, positionY, destX, destY, uniqueId, anim, health, entityId };
		bytes[index++] = getPacketType().getTypeByte();
		for (int i = 0; i < ints.length; i++) {
			bytes[index++] = (byte) (ints[i] >> 24);
			bytes[index++] = (byte) ((ints[i] >> 16) & 0xFF);
			bytes[index++] = (byte) ((ints[i] >> 8) & 0xFF);
			bytes[index++] = (byte) (ints[i] & 0xFF);
		}
		return bytes;
	}

}
