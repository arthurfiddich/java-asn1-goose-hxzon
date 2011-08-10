package org.hxzon.asn1.mms.sequence;

import org.hxzon.asn1.Asn1Utils;
import org.hxzon.asn1.mms.common.FileName;
import org.hxzon.asn1.mms.common.Identifier;

import com.chaosinmotion.asn1.BerInputStream;
import com.chaosinmotion.asn1.BerNode;
import com.chaosinmotion.asn1.BerSequence;
import com.chaosinmotion.asn1.BerVisibleString;
import com.chaosinmotion.asn1.Tag;

public class RequestDomainDownloadRequest extends BerSequence {
//	RequestDomainDownload-Request ::= SEQUENCE
//	{
//	domainName		[0] IMPLICIT Identifier,
//	listOfCapabilities	[1] IMPLICIT SEQUENCE OF VisibleString OPTIONAL,
//	sharable		[2] IMPLICIT BOOLEAN,
//	fileName		[4] IMPLICIT FileName
//	}
	public BerNode create(int tag, BerInputStream stream) {
		switch (tag) {
		case Tag.CONTEXT | 0:
			return new Identifier().init("domainName", "domainName", tag, stream);
		case Tag.CONTEXT | 1:
			return Asn1Utils.createBerSequenceOf("listOfCapabilities", "listOfCapabilities", tag, stream, BerVisibleString.class);
		case Tag.CONTEXT | 2:
			return Asn1Utils.createBerBoolean("sharable", "sharable", tag, stream);
		case Tag.CONTEXT | 4:
			return new FileName().init("fileName", "fileName", tag, stream);
		default:
			return Asn1Utils.createUnknown(tag, stream);
		}
	}

}