package com.sap.pi.xi.basis.other;
import java.util.Comparator;

import com.sap.xi.basis.*;


public class MessageHeaderCompare implements Comparator<MessageHeaderID> {
	public int compare(MessageHeaderID o1, MessageHeaderID o2){
		String str1 = o1.getSenderPartyID() + "|" + o1.getSenderComponentID() + "|" + o1.getInterfaceName() + "|" + o1.getInterfaceNamespace() + "," + o1.getReceiverPartyID() + "," + o1.getReceiverComponentID();
		String str2 = o2.getSenderPartyID() + "|" + o2.getSenderComponentID() + "|" + o2.getInterfaceName() + "|" + o2.getInterfaceNamespace() + "," + o2.getReceiverPartyID() + "," + o2.getReceiverComponentID();
		str1=str1.replaceAll("\\x2a", "");
		str2=str2.replaceAll("\\x2a", "");
		return str1.compareTo(str2);
	}
	
	public static MessageHeaderCompare getinstance(){
		return new MessageHeaderCompare();
	}
}