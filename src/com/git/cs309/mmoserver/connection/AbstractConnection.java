package com.git.cs309.mmoserver.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.git.cs309.mmoserver.packets.Packet;
import com.git.cs309.mmoserver.util.StreamUtils;

public abstract class AbstractConnection extends Thread {
	protected final OutputStream output;
	protected final InputStream input;
	protected final Socket socket;
	protected final String ip;
	protected volatile boolean disconnected = false;
	protected volatile Packet packet; // Making this volatile should allow for other threads to access it properly, as well as be changed by this thread properly.
	protected volatile List<Packet> outgoingPackets = new ArrayList<>(10);

	protected volatile Thread outgoingThread = new Thread() {
		@Override
		public void run() {
			while (!disconnected && !socket.isClosed()) {
				while (outgoingPackets.size() > 0) {
					try {
						StreamUtils.writeBlockToStream(output, outgoingPackets.remove(0).toBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					synchronized (outgoingPackets) {
						outgoingPackets.wait();
					}
				} catch (InterruptedException e) {
					//Don't care if it gets interrupted.
				}
			}
		}
	};

	//Throwing IOException because if the exception occurs here, there's not much we can really do.
	//It's better to let the ConnectionAcceptor try and handle this issue.
	public AbstractConnection(final Socket socket) throws IOException {
		this.socket = socket;
		output = socket.getOutputStream();
		input = socket.getInputStream();
		ip = socket.getInetAddress().getHostAddress();
		setName(toString());
		start();
		outgoingThread.start();
	}

	public void addOutgoingPacket(Packet packet) {
		synchronized (outgoingPackets) {
			outgoingPackets.add(packet);
			outgoingPackets.notifyAll();
		}
	}

	public synchronized void close() {
		try {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		disconnected = true;
		outgoingPackets.notifyAll(); // Make sure thread kills itself.
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof AbstractConnection && ((AbstractConnection) other).ip.equals(ip);
	}

	public void forceOutgoingPacket(final Packet packet) throws IOException {
		StreamUtils.writeBlockToStream(output, packet.toBytes());
	}

	public String getIP() {
		return ip;
	}

	//Since packet is volatile, shouldn't need synchronized method block.
	public Packet getPacket() {
		return packet;
	}

	public boolean isDisconnected() {
		return disconnected;
	}

	@Override
	public abstract void run(); // Force run implementation.

	@Override
	public String toString() {
		return "Connection: " + ip;
	}
}
