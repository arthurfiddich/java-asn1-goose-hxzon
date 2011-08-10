package org.hxzon.asn1.osipresentation;

import org.hxzon.asn1.core.parse.BerInputStream;
import org.hxzon.asn1.core.type.BerSequence;
import org.hxzon.asn1.core.type.base.BerNode;
import org.hxzon.netprotocol.common.IPacket;
import org.hxzon.netprotocol.common.IPacketPayload;
import org.hxzon.util.BytesUtil;

public class OsiPresentation extends BerSequence implements UserDataContainer, IPacketPayload {
	public OsiPresentation() {
		setName("iso 8823 osi presentation");
		setDisplayString("iso 8823 osi presentation");
	}

	@Override
	public BerNode create(int tag, BerInputStream stream) {
		switch (tag) {
//		case Tag.APPLICATION | 1:
//			return new OsiPresentation(tag, this, stream);
		default:
//			return Asn1Utils.createUnknownTagBerNode(tag, stream);
			return new OsiUserData().init(tag, stream, false);
		}
	}

	SingleAsn1Type userData;

	public BerNode[] getUserData() {
		for (BerNode child : getChildren()) {
			if (child instanceof UserDataContainer) {
				return ((UserDataContainer) child).getUserData();
			}
		}
		return new BerNode[0];
	}

	private IPacket srcPacket;

	@Override
	public byte[] getData() {
		return BytesUtil.copyBytes(getSrcData(), getOffset(), getLength());
	}

	@Override
	public int getLength() {
		return super.getTotalLen();
	}

	@Override
	public int getOffset() {
		return super.getTagOffset();
	}

	@Override
	public byte[] getSrcData() {
		return this.srcPacket.getSrcData();
	}

	@Override
	public IPacket getSrcPacket() {
		return this.srcPacket;
	}

	@Override
	public void setSrcPacket(IPacket srcPacket) {
		this.srcPacket = srcPacket;
	}

	public String getProtocolTypeDesc() {
		return "osi presentation";
	}

}
