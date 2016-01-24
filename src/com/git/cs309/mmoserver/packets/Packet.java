package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;
import com.git.cs309.mmoserver.util.ByteFormatted;

public abstract class Packet implements ByteFormatted {
	protected final AbstractConnection source;
	/*
	 * Packet "toByte" convention: first byte: packetType final byte: '\n' In
	 * between: Adequate representation of needed data.
	 */

	public Packet(final AbstractConnection source) {
		this.source = source;
	}

	public AbstractConnection getConnection() {
		return source;
	}

	public abstract PacketType getPacketType();
}
