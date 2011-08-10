package org.hxzon.asn1.mms.choice;

import org.hxzon.asn1.Asn1Utils;
import org.hxzon.asn1.BerChoice;
import org.hxzon.asn1.goose.GooseUtcTime;
import org.hxzon.asn1.mms.common.FloatingPoint;
import org.hxzon.asn1.mms.common.MMSString;
import org.hxzon.asn1.mms.common.TimeOfDay;

import com.chaosinmotion.asn1.BerInputStream;
import com.chaosinmotion.asn1.BerNode;
import com.chaosinmotion.asn1.Tag;

public class Data extends BerChoice {

	public Data() {
		setName("Data");
	}

//	Data ::= CHOICE
//	{
//	-- context tag 0 is reserved for AccessResult
//	array			    [1] IMPLICIT SEQUENCE OF Data,
//	structure		    [2] IMPLICIT SEQUENCE OF Data,
//	boolean			    [3] IMPLICIT BOOLEAN,	
//	bit-string		    [4] IMPLICIT BIT STRING,
//	integer			    [5] IMPLICIT INTEGER,
//	unsigned		    [6] IMPLICIT INTEGER,
//	floating-point		[7] IMPLICIT FloatingPoint,
//-- XXX asn2wrs and packet-ber can not handle REAL yet
//--	real		 	    [8] IMPLICIT REAL,
//	octet-string		[9] IMPLICIT OCTET STRING,
//	visible-string		[10] IMPLICIT VisibleString,
//	binary-time		    [12] IMPLICIT TimeOfDay,
//	bcd			        [13] IMPLICIT INTEGER,
//	booleanArray		[14] IMPLICIT BIT STRING,
//    objId               [15] IMPLICIT OBJECT IDENTIFIER,
//  ...,
//    mMSString           [16] IMPLICIT MMSString,
//    utc-time            [17] IMPLICIT UtcTime				-- added by IEC61850 8.1 G3	
//    }
	public BerNode create(int tag, BerInputStream stream) {
		switch (tag) {
		case Tag.CONTEXT | 1:
			return Asn1Utils.createBerSequenceOf("array", "array", tag, stream, Data.class);
		case Tag.CONTEXT | 2:
			return Asn1Utils.createBerSequenceOf("structure", "structure", tag, stream, Data.class);
		case Tag.CONTEXT | 3:
			return Asn1Utils.createBerBoolean("boolean", "boolean", tag, stream);
		case Tag.CONTEXT | 4:
			return Asn1Utils.createBerBitString("bit-string", "bit-string", tag, stream);
		case Tag.CONTEXT | 5:
			return Asn1Utils.createBerInteger("integer", "integer", tag, stream);
		case Tag.CONTEXT | 6:
			return Asn1Utils.createBerUnsignedInteger("unsigned", "unsigned", tag, stream);
		case Tag.CONTEXT | 7:
			return new FloatingPoint().init("floatingPoint", "floatingPoint", tag, stream);
		case Tag.CONTEXT | 8:
			return Asn1Utils.createBerReal("real", "real", tag, stream);
		case Tag.CONTEXT | 9:
			return Asn1Utils.createBerOctetString("octet-string", "octet-string", tag, stream);
		case Tag.CONTEXT | 10:
			return Asn1Utils.createBerVisibleString("visible-string", "visible-string", tag, stream);
		case Tag.CONTEXT | 12:
			return new TimeOfDay().init("binary-time", "binary-time", tag, stream);
		case Tag.CONTEXT | 13:
			return Asn1Utils.createBerInteger("bcd", "bcd", tag, stream);
		case Tag.CONTEXT | 14:
			return Asn1Utils.createBerBitString("booleanArray", "booleanArray", tag, stream);
		case Tag.CONTEXT | 15:
			return Asn1Utils.createBerOID("objId", "objId", tag, stream);
		case Tag.CONTEXT | 16:
			return new MMSString().init("mmsString", "mmsString", tag, stream);
		case Tag.CONTEXT | 17:
			return new GooseUtcTime().init("utc-time", "utc-time", tag, stream);
		case Tag.UNIVERSAL | Tag.SEQUENCE:
			//FIXME why is ObjectName
			return Asn1Utils.createBerSequenceOf("", "", tag, stream, ObjectName.class, true);
		default:
			return Asn1Utils.createUnknown(tag, stream);
		}
	}
}
