package org.hxzon.asn1.osipresentation;

import org.hxzon.asn1.core.parse.BerInputStream;
import org.hxzon.asn1.core.parse.Tag;
import org.hxzon.asn1.core.parse.ext.Asn1Utils;
import org.hxzon.asn1.core.type.BerSequence;
import org.hxzon.asn1.core.type.base.BerNode;
import org.hxzon.asn1.core.type.ext.BerChoice;
import org.hxzon.asn1.core.type.ext.UnknownBerNode;
import org.hxzon.asn1.mms.MmsPdu;

public class PdvList extends BerSequence implements UserDataContainer {
//FullyEncodedData
    private PresentationContextIdentifier contextIdentifier;

    public PdvList() {
        setId("item");
        setName("item");
    }

//	--  contains one or more PDV-list values.
//	--  See 8.4.2.
//	PDV-list ::= SEQUENCE {
//	  transfer-syntax-name             Transfer-syntax-name OPTIONAL,
//	  presentation-context-identifier  Presentation-context-identifier,
//	  presentation-data-values
//	    CHOICE {single-ASN1-type	[0] ANY,
//	--              [0]  ABSTRACT-SYNTAX.&Type
//	--                     (CONSTRAINED BY {
//	                        
//	                        --  Type corresponding to presentation context identifier  }),
//	            octet-aligned     [1] IMPLICIT OCTET STRING,
//	            arbitrary         [2] IMPLICIT BIT STRING}
//	  --  Contains one or more presentation data values from the same
//	  --  presentation context.
//	  --  See 8.4.2.
//	}

    @Override
    public BerNode create(int tag, BerInputStream stream) {
        switch (tag) {
        case Tag.UNIVERSAL | Tag.OBJECTID:
            return new TransferSyntaxName().init(tag, stream);
        case Tag.UNIVERSAL | Tag.INTEGER:
            contextIdentifier = (PresentationContextIdentifier) new PresentationContextIdentifier().init(tag, stream);
            return contextIdentifier;
        default:
            long contextValue = contextIdentifier == null ? 0 : contextIdentifier.getValue();
            return new PresentationDataValues(contextValue).init(tag, stream, false);
        }
    }

    public BerNode[] getUserData() {
        for (BerNode child : getChildren()) {
            if (child instanceof UserDataContainer) {
                return ((UserDataContainer) child).getUserData();
            }
        }
        return null;
    }

    public static class PresentationDataValues extends BerChoice implements UserDataContainer {

        private long _contextValue = 0;

        public PresentationDataValues(long contextValue) {
            setId("presentation data values");
            setName("presentation data values");
            _contextValue = contextValue;
        }

//		  presentation-data-values
//	    CHOICE {single-ASN1-type	[0] ANY,
//	--              [0]  ABSTRACT-SYNTAX.&Type
//	--                     (CONSTRAINED BY {
//	                        
//	                        --  Type corresponding to presentation context identifier  }),
//	            octet-aligned     [1] IMPLICIT OCTET STRING,
//	            arbitrary         [2] IMPLICIT BIT STRING}
        @Override
        public BerNode create(int tag, BerInputStream stream) {
            switch (tag) {
            case Tag.CONTEXT | 0:
                if (_contextValue == 3) {
                    return new SingleAsn1Type(MmsPdu.class).init("single-ASN1-type", "single-ASN1-type", tag, stream);
                } else {
                    return new SingleAsn1Type(UnknownBerNode.class).init("single-ASN1-type", "single-ASN1-type", tag, stream);
                }
            case Tag.CONTEXT | 1:
                return Asn1Utils.createBerOctetString("octet aligned", "octet aligned", tag, stream);
            case Tag.CONTEXT | 2:
                return Asn1Utils.createBerBitString("arbitrary", "arbitrary", tag, stream);
            default:
                return Asn1Utils.createUnknown(tag, stream);
            }
        }

        public BerNode[] getUserData() {
            BerNode child = this.getRealNode();
            if (child instanceof UserDataContainer) {
                //single asn1 type
                return ((UserDataContainer) child).getUserData();
            }
            return new BerNode[] { child };
        }

    }
}