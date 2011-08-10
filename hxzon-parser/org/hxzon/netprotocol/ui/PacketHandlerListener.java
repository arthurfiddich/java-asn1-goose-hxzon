package org.hxzon.netprotocol.ui;

import org.hxzon.netprotocol.packet.Packet;
import org.hxzon.pcap.PcapPacket;
import org.hxzon.pcap.PcapPacketListener;

public class PacketHandlerListener implements PcapPacketListener {
	private DisplayFrame2 display;
	private int i = 0;

	public PacketHandlerListener(DisplayFrame2 display_) {
		this.display = display_;
//		System.out.println(JRegistry.toDebugString());
		display.getPacketsTable().clearPackets();
	}

	@Override
	public void addPcapPacket(PcapPacket pcapPacket) {
		i++;
		Packet packet = new Packet();
		packet.setSrcData(pcapPacket.getPacketData());
		display.getPacketsTable().addPacket(packet);
	}

}
