package org.hxzon.netprotocol.packet;

import java.util.ArrayList;
import java.util.List;

import org.hxzon.netprotocol.common.GeneralPacket;
import org.hxzon.netprotocol.common.GeneralPacketPayload;
import org.hxzon.netprotocol.parse.ProtocolBindingList;
import org.hxzon.netprotocol.parse.ProtocolField;
import org.hxzon.util.BytesUtil;

public class Packet implements GeneralPacket {
	private byte[] srcData;
	private int offset;
	private int headerLength;
	private GeneralPacket srcPacket;
	private GeneralPacketPayload payload;
	private GeneralPacket lastPacket;
	private List<ProtocolField> headerFields;

	public Packet() {
	}

	public Packet(byte[] data) {
		setSrcData(data);
	}

	public Packet(Packet srcPacket) {
		setSrcPacket(srcPacket);
	}

	public byte[] getSrcData() {
		return srcData;
	}

	public void setSrcData(byte[] data) {
		this.srcData = data;
	}

	public byte[] getData() {
		return BytesUtil.copyBytes(srcData, offset, getTotalLength());
	}

	public int getLength() {
		return getTotalLength();
	}

	public int getTotalLength() {
		return srcData.length - offset;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPayloadOffset() {
		return this.offset + this.headerLength;
	}

	public int getPayloadLength() {
		return this.srcData.length - getPayloadOffset();
	}

	protected int expectHeaderLength() {
		return 0;
	}

	public int getHeaderLength() {
		return headerLength;
	}

	public void setHeaderLength(int length) {
		this.headerLength = length;
	}

	public List<ProtocolField> getHeaderFields() {
		if (headerFields == null) {
			headerFields = new ArrayList<ProtocolField>();
			for (ProtocolField field : expectHeaderFields()) {
				headerFields.add(field);
			}
		}
		return headerFields;
	}

	protected ProtocolField[] expectHeaderFields() {
		return new ProtocolField[0];
	}

	public GeneralPacketPayload getPayload() {
		if (srcData.length <= this.offset + this.headerLength) {
			payload = new NullPayload();
			payload.setSrcPacket(this);
		}
		if (payload == null) {
			payload = parsePayload();
		}
		if (payload == null) {
			payload = new UnknownPayload();
			payload.setSrcPacket(this);
		}
		return payload;
	}

	public GeneralPacket getLastPacket() {
		if (lastPacket == null) {
			GeneralPacketPayload packet = getPayload();
			while (packet instanceof GeneralPacket) {
				packet = ((GeneralPacket) packet).getPayload();
			}
			lastPacket = packet.getSrcPacket();
		}
		return lastPacket;
	}

	public void setPayload(Packet payload) {
		this.payload = payload;
	}

	public GeneralPacket getSrcPacket() {
		return srcPacket;
	}

	public void setSrcPacket(GeneralPacket srcPacket) {
		this.srcPacket = srcPacket;
		this.srcData = srcPacket.getSrcData();
		this.offset = srcPacket.getOffset() + srcPacket.getHeaderLength();
		this.headerLength = this.srcData.length;//for some field fetch before expectHeaderLength
		this.headerLength = expectHeaderLength();
		if (this.offset + this.headerLength > srcData.length) {
			this.headerLength = srcData.length - this.offset;
		}
	}

	public Packet parsePayload() {
		Packet packet = ProtocolBindingList.findBinding(this);
		return packet;
	}

	public byte[] getByteArray(int offset, int len) {
		return BytesUtil.copyBytes(srcData, offset, len);
	}

	public String getHexString(int offset, int len) {
		return BytesUtil.toHexString(srcData, offset, len);
	}

	public int getInt(int offset, int len) {
//		return Integer.valueOf(getHexString(offset, len), 16);
		return BytesUtil.toInt(srcData, offset, len);
	}

	public long getLong(int offset, int len) {
//		return Long.valueOf(getHexString(offset, len), 16);
		return BytesUtil.toULong(srcData, offset, len);
	}

	public int getIntByBit(int offset, int len, int bitOffset, int bitLen) {
		try {
			if (len == 1) {
				return BytesUtil.toInt(srcData[offset], bitOffset, bitLen);
			}
			return BytesUtil.toInt(srcData, offset, len, bitOffset);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
