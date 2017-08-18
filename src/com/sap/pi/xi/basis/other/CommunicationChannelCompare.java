package com.sap.pi.xi.basis.other;
import java.util.*;
import com.sap.xi.basis.*;


public class CommunicationChannelCompare implements Comparator<CommunicationChannelID> {
	public int compare(CommunicationChannelID o1, CommunicationChannelID o2){
		String str1 = o1.getPartyID() + "|" + o1.getComponentID() + "|" + o1.getChannelID();
		String str2 = o2.getPartyID() + "|" + o2.getComponentID() + "|" + o2.getChannelID();
		str1=str1.replaceAll("\\x2a", "");
		str2=str2.replaceAll("\\x2a", "");
		
		return str1.compareTo(str2);
	}
	
	public static CommunicationChannelCompare getinstance(){
		return new CommunicationChannelCompare();
	}
}
