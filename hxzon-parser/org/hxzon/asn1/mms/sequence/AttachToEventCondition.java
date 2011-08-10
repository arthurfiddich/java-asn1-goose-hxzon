package org.hxzon.asn1.mms.sequence;

import org.hxzon.asn1.Asn1Utils;
import org.hxzon.asn1.mms.choice.ObjectName;
import org.hxzon.asn1.mms.common.Transitions;

import com.chaosinmotion.asn1.BerInputStream;
import com.chaosinmotion.asn1.BerNode;
import com.chaosinmotion.asn1.BerSequence;
import com.chaosinmotion.asn1.Tag;

public class AttachToEventCondition extends BerSequence {

//	AttachToEventCondition ::= SEQUENCE
//	{
//	eventEnrollmentName		[0] ObjectName,
//	eventConditionName		[1] ObjectName,
//	causingTransitions		[2] IMPLICIT Transitions,
//	acceptableDelay			[3] IMPLICIT Unsigned32 OPTIONAL
//	}

	public BerNode create(int tag, BerInputStream stream) {
		switch (tag) {
		case Tag.CONTEXT | 0:
			return new ObjectName().init("eventEnrollmentName", "eventEnrollmentName", tag, stream);
		case Tag.CONTEXT | 1:
			return new ObjectName().init("eventConditionName", "eventConditionName", tag, stream);
		case Tag.CONTEXT | 2:
			return new Transitions().init("causingTransitions", "causingTransitions", tag, stream);
		case Tag.CONTEXT | 3:
			return Asn1Utils.createBerUnsigned32("acceptableDelay", "acceptableDelay", tag, stream);
		default:
			return Asn1Utils.createUnknown(tag, stream);
		}
	}
}
