package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public final class PacketFactory {

	public static Packet buildPacket(final byte[] bytes, final AbstractConnection source) {
		switch (bytes[0]) { // First byte should ALWAYS be the type byte.
		case 0: // Null packet
			return null;
		case 1: // Message packet
			return new MessagePacket(bytes, source);
		case 2: // Login packet
			return new LoginPacket(bytes, source);
		case 3: // Error packet
			return new ErrorPacket(bytes, source);
		case 4:
			return new EventPacket(bytes, source);
		case 5:
			return new EntityUpdatePacket(bytes, source);
		case 6:
			return new TestPacket(source, bytes);
		default:
			System.out.println("No case for type: " + bytes[0]);
			return null;
		}
	}

	private PacketFactory() {
		// To prevent instantiation.
	}
}
