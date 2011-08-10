package org.hxzon.asn1.mms.choice;

import org.hxzon.asn1.BerChoice;
import org.hxzon.asn1.mms.choice.Data;
import org.hxzon.asn1.mms.common.DataAccessError;

import com.chaosinmotion.asn1.BerInputStream;
import com.chaosinmotion.asn1.BerNode;
import com.chaosinmotion.asn1.Tag;

public class AccessResult extends BerChoice {

	public AccessResult() {
		setName("access result");
		setDisplayString("access result");
	}

//	AccessResult ::= CHOICE 
//  {
//  failure  [0] IMPLICIT DataAccessError,
//  success  Data
//  }
	public BerNode create(int tag, BerInputStream stream) {
		switch (tag) {
		case Tag.CONTEXT | 0:
			return new DataAccessError().init("failure", "failure", tag, stream);
		default:
			return new Data().init("success", "success", tag, stream, false);
		}
	}
}