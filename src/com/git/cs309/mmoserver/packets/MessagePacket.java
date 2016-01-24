package com.git.cs309.mmoserver.packets;

import com.git.cs309.mmoserver.connection.AbstractConnection;

public class MessagePacket extends Packet {
	public static final byte UNKNOWN = 0;
	public static final byte PARTY_CHAT = 4;
	public static final byte LOCAL_CHAT = 1;
	public static final byte GLOBAL_CHAT = 2;
	public static final byte PRIVATE_CHAT = 3;
	private final byte messageCode;
	private final String message;

	public MessagePacket(final AbstractConnection destination, final byte messageCode, final String message) {
		super(destination);
		this.message = message;
		this.messageCode = messageCode;
	}

	public MessagePacket(final byte[] buffer, final AbstractConnection source) {
		super(source);
		messageCode = buffer[1];
		char[] chars = new char[buffer.length - 2];
		for (int i = 2, j = 0; i < buffer.length; i++, j++) {
			chars[j] = (char) buffer[i];
		}
		message = String.valueOf(chars);
	}

	public String getMessage() {
		return message;
	}

	public byte getMessageCode() {
		return messageCode;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.MESSAGE_PACKET;
	}

	@Override
	public byte[] toBytes() {
		byte[] bytes = new byte[message.length() + 2];
		int index = 0;
		bytes[index++] = getPacketType().getTypeByte();
		bytes[index++] = messageCode;
		for (char c : message.toCharArray()) {
			bytes[index++] = (byte) c;
		}
		return bytes;
	}
}
