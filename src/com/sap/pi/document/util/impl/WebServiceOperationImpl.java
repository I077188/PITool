package com.sap.pi.document.util.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.ws.BindingProvider;

import com.sap.pi.document.dao.Sender;
import com.sap.pi.document.dao.Staging;
import com.sap.pi.document.util.WebServiceOperation;
import com.sap.pi.document.util.dao.SetSecurity;
import com.sap.xi.basis.IntegratedConfiguration;
import com.sap.xi.basis.IntegratedConfigurationIn;
import com.sap.xi.basis.IntegratedConfigurationInService;
import com.sap.xi.basis.IntegratedConfigurationQueryIn;
import com.sap.xi.basis.IntegratedConfigurationQueryOut;
import com.sap.xi.basis.IntegratedConfigurationReadIn;
import com.sap.xi.basis.IntegratedConfigurationReadOut;
import com.sap.xi.basis.MessageHeaderID;

public class WebServiceOperationImpl implements WebServiceOperation {


	@Override
	public IntegratedConfigurationIn getIntegrationPort() {
		// TODO Auto-generated method stub
		IntegratedConfigurationInService icoInService;
		SetSecurity setSecurity;
		try {
			icoInService = new IntegratedConfigurationInService();
			IntegratedConfigurationIn port = icoInService.getIntegratedConfigurationIn_Port();
			setSecurity = new SetSecurity();

			try {
				setSecurity.set_security((BindingProvider) port,
						"/IntegratedConfigurationInService/IntegratedConfigurationInImplBean");
				return port;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<MessageHeaderID> getIntegratedConfigurationID() {
		// TODO Auto-generated method stub

		IntegratedConfigurationIn port = this.getIntegrationPort();
		IntegratedConfigurationQueryIn queryIn = new IntegratedConfigurationQueryIn();
		queryIn.setAdministrativeData(null);
		queryIn.setDescription(null);
		queryIn.setIntegratedConfigurationID(null);

		// query ICO
		IntegratedConfigurationQueryOut queryOut = new IntegratedConfigurationQueryOut();
		queryOut = port.query(queryIn);
		List<MessageHeaderID> headerIDs = queryOut.getIntegratedConfigurationID();

		return headerIDs;
	}



	@Override
	public Staging getStagingInfomation(IntegratedConfiguration integratedConfiguration) {
		// TODO Auto-generated method stub

		System.out.println(integratedConfiguration.getIntegratedConfigurationID().getInterfaceName());
		return null;
	}


	@Override
	public Sender getSenderInformation(MessageHeaderID headerID) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IntegratedConfiguration getIntegrationConfiguration(MessageHeaderID messageHeaderID) {
		// TODO Auto-generated method stub

		IntegratedConfigurationIn port = this.getIntegrationPort();
		IntegratedConfigurationReadIn readIn = new IntegratedConfigurationReadIn();
		readIn.getIntegratedConfigurationID().add(messageHeaderID);
		IntegratedConfigurationReadOut readOut = port.read(readIn);

		return readOut.getIntegratedConfiguration().get(0);
	}

}
