package com.sap.pi.xi.basis.other;
import java.util.Comparator;
import com.sap.xi.basis.*;


public class CommunicationComponentCompare implements Comparator<CommunicationComponentID> {
	public int compare(CommunicationComponentID o1, CommunicationComponentID o2){
		String str1 = o1.getPartyID() + "|" + o1.getComponentID();
		String str2 = o2.getPartyID() + "|" + o2.getComponentID();
		str1=str1.replaceAll("\\x2a", "");
		str2=str2.replaceAll("\\x2a", "");
		return str1.compareTo(str2);
	}
	
	public static CommunicationComponentCompare getinstance(){
		return new CommunicationComponentCompare();
	}
}
