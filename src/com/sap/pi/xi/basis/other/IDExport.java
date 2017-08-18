package com.sap.pi.xi.basis.other;
import java.io.*;
import java.util.*;
import javax.xml.ws.*;

import com.sap.engine.services.webservices.espbase.client.api.*;
import com.sap.xi.basis.*;
import com.sap.xi.basis.global.*;

public class IDExport {
	public String host;
	public String user;
	public String password;
	public String proxy_host;
	public String proxy_port;
	public String proxy_user;
	public String proxy_password;
	public int bundle_size;
	public boolean integrated_configuration_support = false;
	
	public boolean export_CommunicationChannel = false;
	public boolean export_SenderAgreement = false;
	public boolean export_ReceiverAgreement = false;
	public boolean export_InterfaceDetermination = false;
	public boolean export_IntegratedConfiguration = false;
	
	
	public PrintStream file_link;
	public ArrayList<String> party_table = new ArrayList<String>();
	public ArrayList<String> component_table = new ArrayList<String>();
	public ArrayList<String> channel_table = new ArrayList<String>();
	public ArrayList<String> receiverdetermination_table = new ArrayList<String>();
	public ArrayList<String> receiverrule_table = new ArrayList<String>();
	public ArrayList<String> interfacedetermination_table = new ArrayList<String>();
	public ArrayList<String> senderagreement_table = new ArrayList<String>();
	public ArrayList<String> receiveragreement_table = new ArrayList<String>();
	public ArrayList<String> directconnection_table = new ArrayList<String>();
	public ArrayList<String> integratedconfiguration_table = new ArrayList<String>();
	
	public HashMap<String,String> map_Channel_ID_to_Adapter_type=new HashMap<String,String>();
	public HashMap<String,String> map_SendX4_to_Channel=new HashMap<String,String>();
	public HashMap<String,String> map_SendX4_to_Channel_OnlyID=new HashMap<String,String>();
	public HashMap<String,String> map_ReceiverX8_to_Channel=new HashMap<String,String>();
	public HashMap<String,String> map_ReceiverX8_to_Channel_OnlyID=new HashMap<String,String>();
	public static final String connector="!@#$%^&"; 
	
	public PrintStream printStream_for_CommunicationChannel;
	public PrintStream printStream_for_SenderAgreement;
	public PrintStream printStream_for_ReceiverAgreement;
	public PrintStream printStream_for_InterfaceDetermination;
	public PrintStream printStream_for_IntegratedConfiguration;
	
//	public PrintStream CommunicationChannel;
	
	public FileOutputStream log;
	public PrintStream file_printer_of_log;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		IDExport idexport = new IDExport();
		idexport.read_properties();
		

		idexport.prepare_export();
		
//		idexport.CommunicationChannel.println("#Sender Party,"+"#Sender Component,"+"#Sender_InterfaceName,"+"#Sender_Namespace,"+"#Sender_Channel_ID,"+"#Sender_Channel_AdapterType,"
//		+"#Receiver Party,"+"#Receiver Component,"+"#Receiver_InterfaceName,"+"#Receiver_Namespace,"+"#Receiver_Channel_ID,"+"#Receiver_Channel_AdapterType");

		//ChannelID AdapterType
		idexport.log=new FileOutputStream("result/log.log");
		idexport.file_printer_of_log =new PrintStream(idexport.log);
		idexport.file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" begin!!!");

		
		System.out.println("export_CommunicationChannel: "+idexport.export_CommunicationChannel);
		System.out.println("export_SenderAgreement: "+idexport.export_SenderAgreement);
		System.out.println("export_ReceiverAgreement: "+idexport.export_ReceiverAgreement);
		System.out.println("export_InterfaceDetermination: "+idexport.export_InterfaceDetermination);
		System.out.println("export_IntegratedConfiguration: "+idexport.export_IntegratedConfiguration);
		
		
//		idexport.export_CommunicationParty();
//		idexport.export_BusinessComponent();
//		idexport.export_BusinessSystem();
//		idexport.export_IntegrationProcess();
		
		if(idexport.export_CommunicationChannel||idexport.export_InterfaceDetermination||idexport.export_IntegratedConfiguration){
			idexport.export_CommunicationChannel();//1
		}
		
//		idexport.export_ReceiverRule();
		//home;
//		idexport.export_ReceiverDetermination();
		
		if(idexport.export_SenderAgreement||idexport.export_InterfaceDetermination){
			idexport.export_SenderAgreement();//2
		}
		
		if(idexport.export_ReceiverAgreement||idexport.export_InterfaceDetermination){
			idexport.export_ReceiverAgreement();//3
		}
		
		if(idexport.export_InterfaceDetermination){
			idexport.export_InterfaceDetermination();//4
		}
		
//		idexport.export_DirectConnection();
		
		if(idexport.export_IntegratedConfiguration){
			idexport.export_IntegratedConfiguration();//5
		}
		
//		idexport.export_ConfigurationScenario();
//		idexport.export_ValueMapping();
		
		idexport.file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" finish!!!");
		idexport.file_printer_of_log.close();
		
		
		idexport.end_export();
		
		System.out.println("finished!");
	}

	public void read_properties()throws Exception{
		FileInputStream in=new FileInputStream("IDExport.properties");
		Properties prop = new Properties();
		prop.load(in);
		host = prop.getProperty("host");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		proxy_host = prop.getProperty("proxy_host");
		proxy_port = prop.getProperty("proxy_port");
		proxy_user = prop.getProperty("proxy_user");
		proxy_password = prop.getProperty("proxy_password");
		bundle_size = Integer.parseInt(prop.getProperty("bundle_size"));
		
		export_CommunicationChannel = Boolean.parseBoolean(prop.getProperty("export_CommunicationChannel"));
		export_SenderAgreement = Boolean.parseBoolean(prop.getProperty("export_SenderAgreement"));
		export_ReceiverAgreement = Boolean.parseBoolean(prop.getProperty("export_ReceiverAgreement"));
		export_InterfaceDetermination = Boolean.parseBoolean(prop.getProperty("export_InterfaceDetermination"));
		export_IntegratedConfiguration = Boolean.parseBoolean(prop.getProperty("export_IntegratedConfiguration"));
		
//		System.out.println("export_CommunicationChannel: "+export_CommunicationChannel);
//		System.out.println("export_SenderAgreement: "+export_SenderAgreement);
//		System.out.println("export_ReceiverAgreement: "+export_ReceiverAgreement);
//		System.out.println("export_InterfaceDetermination: "+export_InterfaceDetermination);
//		System.out.println("export_IntegratedConfiguration: "+export_IntegratedConfiguration);
		
		
		
		
	}
	
	public void prepare_export() throws Exception{
		FileOutputStream out=new FileOutputStream("result/link.html");
		file_link=new PrintStream(out);
		print_link_file_header(file_link);
		
		File temp_file = new File ("result/link_json");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
	}
	
	public void set_security (BindingProvider port, String url_path) throws Exception{
		BindingProvider bp = (BindingProvider)port;
		Map<String,Object> context = bp.getRequestContext();
		context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, host + url_path);
		context.put(BindingProvider.USERNAME_PROPERTY, user);
		context.put(BindingProvider.PASSWORD_PROPERTY, password);
		if (proxy_host == null || proxy_host.length()==0)
			return;
		HTTPControlInterface httpControl = HTTPControlFactory.getInterface(port);
		httpControl.setHTTPProxy(proxy_host, Integer.parseInt(proxy_port));
		httpControl.setHTTPProxyUserPass(proxy_user, proxy_password);
	}
	
	public void end_export() throws Exception{
		print_link_file_footer(file_link);
		file_link.close();	
		
	}
		
	public void export_CommunicationParty() throws Exception{
		System.out.println("generating Communication parties ...");
		
		CommunicationPartyInService service = new CommunicationPartyInService();
		CommunicationPartyIn port = service.getCommunicationPartyIn_Port();
		set_security((BindingProvider)port,"/CommunicationPartyInService/CommunicationPartyInImplBean");
		
		CommunicationPartyQueryIn input1 = new CommunicationPartyQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setPartyID(null);
		CommunicationPartyQueryOut output1 = port.query(input1);
		
		List<String> list_temp = output1.getPartyID();
		List<String> list = new ArrayList<String>();
		Collections.sort(list_temp);
		
		// generate party files
		File temp_file = new File ("result/communication_party");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		CommunicationPartyReadIn input2 = new CommunicationPartyReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<String> input_list = input2.getPartyID();
		
		for (int i=0;i<list_temp.size();i++){
			input_list.add(list_temp.get(i));
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			CommunicationPartyReadOut party_item = port.read(input2);
			input2.getPartyID().clear();
			
			List<CommunicationParty> party_array = party_item.getParty();
			for (int j=0; j<party_array.size(); j++){
				System.out.println("	generating Communication party " + party_array.get(j).getPartyID());
				export_CommunicationParty_item_json(party_array.get(j), file_no);
				export_CommunicationParty_item(party_array.get(j), file_no++);
				list.add(party_array.get(j).getPartyID());
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/communication_party/list.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			party_table.add(list.get(i));
			file_printer.println("			<tr>");
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i) + "</a></td>");			
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=CommunicationParty\"  onclick=\"DoMenu('CommunicationParty')\">Communication Party</a>");
		file_link.println("					<ul id=\"CommunicationParty\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"communication_party/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"communication_party/" + i + ".html\" target=\"a2\">" + list.get(i) + "</a></li>");			
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/party.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"CommunicationParty\":{");
		file_json.print("		\"name\":\"Communication Party\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.print("			{\"name\":\"" + list.get(i) + "\"}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_CommunicationParty_item(CommunicationParty item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_party/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("				<td>" + print_table_cell(item.getPartyID()) + "</td>");
		file_printer.println("			</tr>");
		
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		
		file_printer.println("		<br />");

		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(2,1);\">Identifiers</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(2,2);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Additional Identifiers</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Agency</th>");
		file_printer.println("							<th>Schema</th>");
		file_printer.println("							<th>Name</th>");
		file_printer.println("						</tr>");
		List<CommunicationPartyAdditionalIdentifier> add_id = item.getAdditionalIdentifier();
		for(int i=0; i< add_id.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getSchemeAgencyID()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getSchemeID()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 2, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void export_CommunicationParty_item_json(CommunicationParty item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_party/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"name\":\"" + item.getPartyID() + "\",");		
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<CommunicationPartyAdditionalIdentifier> add_id = item.getAdditionalIdentifier();
		if (add_id.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"identifier\":[");
			for(int i=0; i< add_id.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"agency\":\"" + add_id.get(i).getSchemeAgencyID() + "\",");
				file_printer.println("			\"schema\":\"" + add_id.get(i).getSchemeID() + "\",");
				file_printer.println("			\"name\":\"" + add_id.get(i).getValue() + "\"");
				file_printer.print("		}");
				
			}
			file_printer.println();
			file_printer.print("	]");
		}
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_BusinessComponent() throws Exception{
		System.out.println("generating Business Components ...");
		
		BusinessComponentInService service = new BusinessComponentInService();
		BusinessComponentIn port = service.getBusinessComponentIn_Port();
		set_security((BindingProvider)port,"/BusinessComponentInService/BusinessComponentInImplBean");
		
		BusinessComponentQueryIn input1 = new BusinessComponentQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setBusinessComponentID(null);
		BusinessComponentQueryOut output1 = port.query(input1);
		
		List<CommunicationComponentID> list_temp = output1.getBusinessComponentID();
		List<CommunicationComponentID> list = new ArrayList<CommunicationComponentID>();
		Collections.sort(list_temp, CommunicationComponentCompare.getinstance());
		
		// generate business component files
		File temp_file = new File ("result/communication_component");
		if(!temp_file.exists())
			temp_file.mkdirs();
		temp_file = new File ("result/business_component");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int component_table_size = component_table.size();
		int file_no=0, count=0;
		BusinessComponentReadIn input2 = new BusinessComponentReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<CommunicationComponentID> input_list = input2.getBusinessComponentID();
		for (int i=0;i<list_temp.size();i++){
			CommunicationComponentID item = list_temp.get(i);
			CommunicationComponentID element = new CommunicationComponentID();
			element.setPartyID(item.getPartyID());
			element.setComponentID(item.getComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			BusinessComponentReadOut result_item = port.read(input2);
			input2.getBusinessComponentID().clear();
			
			List<BusinessComponent> result_element = result_item.getBusinessComponent();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getBusinessComponentID();
				System.out.println("	generating Business Component " + item.getPartyID() + "," + item.getComponentID());
				export_BusinessComponent_item_json(result_element.get(j), file_no);
				export_BusinessComponent_item(result_element.get(j), component_table_size+file_no);
				file_no++;
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/communication_component/componentlist.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("				<th>Communication Component</th>");
		file_printer.println("			</tr>");
		
		for (int i=0;i<list.size();i++){
			component_table.add((list.get(i).getPartyID() + "|" + list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			file_printer.println("			<tr>");
			int index_no = party_table.indexOf(list.get(i).getPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getPartyID() + "</a></td>");
			file_printer.println("				<td><a href=\"" + (component_table_size+i) + ".html\">" + list.get(i).getComponentID() + "</a></td>");
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=BusinessComponent\"  onclick=\"DoMenu('BusinessComponent')\">Business Component</a>");
		file_link.println("					<ul id=\"BusinessComponent\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"communication_component/componentlist.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"communication_component/" + (component_table_size+i) + ".html\" target=\"a2\">" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/businesscomponent.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"BusinessComponent\":{");
		file_json.print("		\"name\":\"Business Component\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "\",");
				file_json.println("				\"party\":\"" + list.get(i).getPartyID() + "\",");
				file_json.println("				\"component\":\"" + list.get(i).getComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_BusinessComponent_item(BusinessComponent item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_component/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Business Component</th>");
		file_printer.println("				<td>" + item.getBusinessComponentID().getComponentID() + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		int index_no = party_table.indexOf(item.getBusinessComponentID().getPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(item.getBusinessComponentID().getPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\"  style=\"color:blue\">" + item.getBusinessComponentID().getPartyID() + "</a></td>");
		file_printer.println("			</tr>");
		
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");

		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(6,1);\">Receiver</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(6,2);\">Sender</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(6,3);\">Assigned Users</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(6,4);\">IDoc Partner</a></li>");
		file_printer.println("					<li id=\"current5\"><a href=\"#\" onclick=\"change_option(6,5);\">Other Attributes</a></li>");
		file_printer.println("					<li id=\"current6\"><a href=\"#\" onclick=\"change_option(6,6);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		print_sender_receiver (file_printer, 1, "Receiver", item.getInboundInterface());
		print_sender_receiver (file_printer, 2, "Sender", item.getOutboundInterface());
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Assigned Users</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>User Name</th>");
		file_printer.println("						</tr>");
		List<String> user_list = item.getAssignedUser();
		for(int i=0; i< user_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(user_list.get(i)) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_additional_identifier(file_printer, 4, item.getAdditionalIdentifier());
		
		file_printer.println("			<div id=\"content5\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Other Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Third-Party Communication Component(Changeable)</th>");
		file_printer.println("							<td>" + item.isIsThirdParty() + "</th>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 6, item.getAdministrativeData(), item.getMasterLanguage());
		file_printer.println("		</div>");

		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_BusinessComponent_item_json(BusinessComponent item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/business_component/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"party\":\"" + item.getBusinessComponentID().getPartyID() + "\",");
		file_printer.println("	\"component\":\"" + item.getBusinessComponentID().getComponentID() + "\",");
		file_printer.println("	\"3rdparty\":\"" + item.isIsThirdParty() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<String> users = item.getAssignedUser();
		if(users.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"assigneduser\":[");
			for(int i=0; i<users.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"name\":\"" + users.get(i) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<DesignObjectID> inbound_interface = item.getInboundInterface();
		if(inbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"inboundinterface\":[");
			for(int i=0; i<inbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + inbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + inbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<DesignObjectID> outbound_interface = item.getOutboundInterface();
		if(outbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"outboundinterface\":[");
			for(int i=0; i<outbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + outbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + outbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<CommunicationComponentAdditionalIdentifier> add_id = item.getAdditionalIdentifier();
		if (add_id.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"idocpartner\":[");
			for(int i=0; i< add_id.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"schema\":\"" + add_id.get(i).getSchemeID() + "\",");
				file_printer.println("			\"name\":\"" + add_id.get(i).getValue() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
		
	}
	
	public void export_BusinessSystem() throws Exception{
		System.out.println("generating Business Systems ...");
		
		BusinessSystemInService service = new BusinessSystemInService();
		BusinessSystemIn port = service.getBusinessSystemIn_Port();
		set_security((BindingProvider)port,"/BusinessSystemInService/BusinessSystemInImplBean");
		
		BusinessSystemQueryIn input1 = new BusinessSystemQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setBusinessSystemID(null);
		BusinessSystemQueryOut output1 = port.query(input1);
		
		List<CommunicationComponentID> list_temp = output1.getBusinessSystemID();
		Collections.sort(list_temp, CommunicationComponentCompare.getinstance());
		List<CommunicationComponentID> list = new ArrayList<CommunicationComponentID>();
		
		// generate business system files
		File temp_file = new File ("result/communication_component");
		if(!temp_file.exists())
			temp_file.mkdirs();
		temp_file = new File ("result/business_system");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int component_table_size = component_table.size();
		int file_no=0, count=0;
		BusinessSystemReadIn input2 = new BusinessSystemReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<CommunicationComponentID> input_list = input2.getBusinessSystemID();
		for (int i=0;i<list_temp.size();i++){
			CommunicationComponentID item = list_temp.get(i);
			CommunicationComponentID element = new CommunicationComponentID();
			element.setPartyID(item.getPartyID());
			element.setComponentID(item.getComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			BusinessSystemReadOut result_item = port.read(input2);
			input2.getBusinessSystemID().clear();
			
			List<BusinessSystem> result_element = result_item.getBusinessSystem();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getBusinessSystemID();
				System.out.println("	generating Business System " + item.getPartyID() + "," + item.getComponentID());
				export_BusinessSystem_item(result_element.get(j), component_table_size+file_no);
				export_BusinessSystem_item_json(result_element.get(j), file_no);
				file_no++;
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/communication_component/systemlist.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("				<th>Communication Component</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			component_table.add((list.get(i).getPartyID() + "|" + list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			file_printer.println("			<tr>");
			int index_no = party_table.indexOf(list.get(i).getPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getPartyID() + "</a></td>");
			file_printer.println("				<td><a href=\"" + (component_table_size+i) + ".html\">" + list.get(i).getComponentID() + "</a></td>");
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=BusinessSystem\"  onclick=\"DoMenu('BusinessSystem')\">Business System</a>");
		file_link.println("					<ul id=\"BusinessSystem\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"communication_component/systemlist.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"communication_component/" + (component_table_size+i) + ".html\" target=\"a2\">" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/businesssystem.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"BusinessSystem\":{");
		file_json.print("		\"name\":\"Business System\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "\",");
				file_json.println("				\"party\":\"" + list.get(i).getPartyID() + "\",");
				file_json.println("				\"system\":\"" + list.get(i).getComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_BusinessSystem_item(BusinessSystem item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_component/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Business System</th>");
		file_printer.println("				<td>" + item.getBusinessSystemID().getComponentID() + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		int index_no = party_table.indexOf(item.getBusinessSystemID().getPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(item.getBusinessSystemID().getPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + item.getBusinessSystemID().getPartyID() + "</a></td>");
		file_printer.println("			</tr>");
		
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");

		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(7,1);\">Receiver</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(7,2);\">Sender</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(7,3);\">Assigned Users</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(7,4);\">IDoc Partner</a></li>");
		file_printer.println("					<li id=\"current5\"><a href=\"#\" onclick=\"change_option(7,5);\">Other Attributes</a></li>");
		file_printer.println("					<li id=\"current6\"><a href=\"#\" onclick=\"change_option(7,6);\">Logon Data</a></li>");
		file_printer.println("					<li id=\"current7\"><a href=\"#\" onclick=\"change_option(7,7);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		print_sender_receiver (file_printer, 1, "Receiver", item.getInboundInterface());
		print_sender_receiver (file_printer, 2, "Sender", item.getOutboundInterface());
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Assigned Users</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>User Name</th>");
		file_printer.println("						</tr>");
		List<String> user_list = item.getAssignedUser();
		for(int i=0; i< user_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(user_list.get(i)) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");

		print_additional_identifier(file_printer, 4, item.getAdditionalIdentifier());
		
		file_printer.println("			<div id=\"content5\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Other Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Third-Party Communication Component(Changeable)</th>");
		file_printer.println("							<td>" + item.isIsThirdParty() + "</th>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content6\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Logon Data</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Support Communication Using WS Adapter</th>");
		file_printer.println("							<td>" + item.getCacheNotification().isDistributeCacheIndicator() + "</th>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>User</th>");
		file_printer.println("							<td>" + print_table_cell(item.getCacheNotification().getCacheUser()) + "</th>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 7, item.getAdministrativeData(), item.getMasterLanguage());
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_BusinessSystem_item_json(BusinessSystem item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/business_system/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"party\":\"" + item.getBusinessSystemID().getPartyID() + "\",");
		file_printer.println("	\"system\":\"" + item.getBusinessSystemID().getComponentID() + "\",");
		file_printer.println("	\"3rdparty\":\"" + item.isIsThirdParty() + "\",");
		file_printer.println("	\"wssupport\":\"" + item.getCacheNotification().isDistributeCacheIndicator() + "\",");
		file_printer.println("	\"cacheuser\":\"" + item.getCacheNotification().getCacheUser() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<String> users = item.getAssignedUser();
		if(users.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"assigneduser\":[");
			for(int i=0; i<users.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"name\":\"" + users.get(i) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<DesignObjectID> inbound_interface = item.getInboundInterface();
		if(inbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"inboundinterface\":[");
			for(int i=0; i<inbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + inbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + inbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<DesignObjectID> outbound_interface = item.getOutboundInterface();
		if(outbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"outboundinterface\":[");
			for(int i=0; i<outbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + outbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + outbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<CommunicationComponentAdditionalIdentifier> add_id = item.getAdditionalIdentifier();
		if (add_id.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"idocpartner\":[");
			for(int i=0; i< add_id.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"schema\":\"" + add_id.get(i).getSchemeID() + "\",");
				file_printer.println("			\"name\":\"" + add_id.get(i).getValue() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_IntegrationProcess() throws Exception{
		System.out.println("generating Integration Processes ...");
		
		IntegrationProcessInService service = new IntegrationProcessInService();
		IntegrationProcessIn port = service.getIntegrationProcessIn_Port();
		set_security((BindingProvider)port,"/IntegrationProcessInService/IntegrationProcessInImplBean");
		
		IntegrationProcessQueryIn input1 = new IntegrationProcessQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setIntegrationProcessID(null);
		IntegrationProcessQueryOut output1 = port.query(input1);
		
		List<CommunicationComponentID> list_temp = output1.getIntegrationProcessID();
		Collections.sort(list_temp, CommunicationComponentCompare.getinstance());
		List<CommunicationComponentID> list = new ArrayList<CommunicationComponentID>();
		
		// generate business system files
		File temp_file = new File ("result/communication_component");
		if(!temp_file.exists())
			temp_file.mkdirs();
		temp_file = new File ("result/integration_process");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int component_table_size = component_table.size();
		int file_no=0, count=0;
		IntegrationProcessReadIn input2 = new IntegrationProcessReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<CommunicationComponentID> input_list = input2.getIntegrationProcessID();
		for (int i=0;i<list_temp.size();i++){
			CommunicationComponentID item = list_temp.get(i);
			CommunicationComponentID element = new CommunicationComponentID();
			element.setPartyID(item.getPartyID());
			element.setComponentID(item.getComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			IntegrationProcessReadOut result_item = port.read(input2);
			input2.getIntegrationProcessID().clear();
			
			List<IntegrationProcess> result_element = result_item.getIntegrationProcess();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getIntegrationProcessID();
				System.out.println("	generating Integration Process " + item.getPartyID() + "," + item.getComponentID());
				export_IntegrationProcess_item(result_element.get(j), component_table_size+file_no);
				export_IntegrationProcess_item_json(result_element.get(j), file_no);
				file_no++;
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/communication_component/processList.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("				<th>Integration Process</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			component_table.add((list.get(i).getPartyID() + "|" + list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			file_printer.println("			<tr>");
			int index_no = party_table.indexOf(list.get(i).getPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getPartyID() + "</a></td>");
			file_printer.println("				<td><a href=\"" + (component_table_size+i) + ".html\">" + list.get(i).getComponentID() + "</a></td>");
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=IntegrationProcess\"  onclick=\"DoMenu('IntegrationProcess')\">Integration Process</a>");
		file_link.println("					<ul id=\"IntegrationProcess\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"communication_component/processList.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"communication_component/" + (component_table_size+i) + ".html\" target=\"a2\">" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/integrationprocess.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"IntegrationProcess\":{");
		file_json.print("		\"name\":\"Integration Process\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "\",");
				file_json.println("				\"party\":\"" + list.get(i).getPartyID() + "\",");
				file_json.println("				\"process\":\"" + list.get(i).getComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_IntegrationProcess_item(IntegrationProcess item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_component/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Integration Process</th>");
		file_printer.println("				<td>" + item.getIntegrationProcessID().getComponentID() + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		int index_no = party_table.indexOf(item.getIntegrationProcessID().getPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(item.getIntegrationProcessID().getPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\"  style=\"color:blue\">" + item.getIntegrationProcessID().getPartyID() + "</a></td>");
		file_printer.println("			</tr>");
		
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");

		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>ESR Name</th>");
		file_printer.println("				<th>ESR NameSpace</th>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<td>" + print_table_cell(item.getRepositoryIntegrationProcess().getName()) + "</td>");
		file_printer.println("				<td>" + print_table_cell(item.getRepositoryIntegrationProcess().getNamespace()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(3,1);\">Receiver</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(3,2);\">Sender</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(3,3);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");

		print_sender_receiver (file_printer, 1, "Receiver", item.getInboundInterface());
		print_sender_receiver (file_printer, 2, "Sender", item.getOutboundInterface());
		print_admin_data(file_printer, 3, item.getAdministrativeData(), item.getMasterLanguage());
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_IntegrationProcess_item_json(IntegrationProcess item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/integration_process/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"party\":\"" + item.getIntegrationProcessID().getPartyID() + "\",");
		file_printer.println("	\"process\":\"" + item.getIntegrationProcessID().getComponentID() + "\",");
		file_printer.println("	\"esrname\":\"" + item.getRepositoryIntegrationProcess().getName() + "\",");
		file_printer.println("	\"esrnamespace\":\"" + item.getRepositoryIntegrationProcess().getNamespace() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		if(item.getProcessParameters() != null){
			boolean empty = true;
			List<AgentProperty> agent_list = item.getProcessParameters().getAgent();
			if(agent_list.size()>0){
				file_printer.println(",");
				file_printer.println("	\"parameter\":[");
				empty = false;
				for(int i=0; i<agent_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"Agent\",");
					file_printer.println("			\"name\":\"" + agent_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":\"" + agent_list.get(i).getValue() + "\"");
					file_printer.print("		}");
				}
			}
			
			List<ChannelProperty> channel_list = item.getProcessParameters().getChannel();
			if(channel_list.size()>0){
				file_printer.println(",");
				if (empty){
					file_printer.println("				\"parameter\":[");
				}
				empty = false;
				for(int i=0; i<channel_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"Channel\",");
					file_printer.println("			\"name\":\"" + channel_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":{");
					file_printer.println("				\"party\":\"" + channel_list.get(i).getValue().getPartyID() + "\",");
					file_printer.println("				\"component\":\"" + channel_list.get(i).getValue().getComponentID() + "\",");
					file_printer.println("				\"channel\":\"" + channel_list.get(i).getValue().getChannelID() + "\"");
					file_printer.println("			}");
					file_printer.print("		}");
				}
			}
			
			List<DateProperty> date_list = item.getProcessParameters().getDate();
			if(date_list.size()>0){
				file_printer.println(",");
				if (empty){
					file_printer.println("				\"parameter\":[");
				}
				empty = false;
				for(int i=0; i<date_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"Date\",");
					file_printer.println("			\"name\":\"" + date_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":\"" + date_list.get(i).getValue() + "\"");
					file_printer.print("		}");
				}
			}
			
			List<TimeProperty> time_list = item.getProcessParameters().getTime();
			if(time_list.size()>0){
				file_printer.println(",");
				if (empty){
					file_printer.println("				\"parameter\":[");
				}
				empty = false;
				for(int i=0; i<time_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"Time\",");
					file_printer.println("			\"name\":\"" + time_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":\"" + time_list.get(i).getValue() + "\"");
					file_printer.print("		}");
				}
			}
			
			List<IntegerProperty> integer_list = item.getProcessParameters().getInteger();
			if(integer_list.size()>0){
				file_printer.println(",");
				if (empty){
					file_printer.println("				\"parameter\":[");
				}
				empty = false;
				for(int i=0; i<integer_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"Integer\",");
					file_printer.println("			\"name\":\"" + integer_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":\"" + integer_list.get(i).getValue() + "\"");
					file_printer.print("		}");
				}
			}
			
			List<RestrictedGenericProperty> str_list = item.getProcessParameters().getString();
			if(str_list.size()>0){
				file_printer.println(",");
				if (empty){
					file_printer.println("				\"parameter\":[");
				}
				empty = false;
				for(int i=0; i<str_list.size();i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("		{");
					file_printer.println("			\"type\":\"String\",");
					file_printer.println("			\"name\":\"" + str_list.get(i).getName() + "\",");
					file_printer.println("			\"value\":\"" + ((str_list.get(i).getValue() == null)? "" : str_list.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
					file_printer.print("		}");
				}
			}
			
			if(!empty){
				file_printer.println();
				file_printer.print("				]");
			}
		}

		List<DesignObjectID> inbound_interface = item.getInboundInterface();
		if(inbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"inboundinterface\":[");
			for(int i=0; i<inbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + inbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + inbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<DesignObjectID> outbound_interface = item.getOutboundInterface();
		if(outbound_interface.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"outboundinterface\":[");
			for(int i=0; i<outbound_interface.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + outbound_interface.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + outbound_interface.get(i).getNamespace() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<CommunicationComponentAdditionalIdentifier> add_id = item.getAdditionalIdentifier();
		if (add_id.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"idocpartner\":[");
			for(int i=0; i< add_id.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"schema\":\"" + add_id.get(i).getSchemeID() + "\",");
				file_printer.println("			\"name\":\"" + add_id.get(i).getValue() + "\"");
				file_printer.print("		}");
				
			}
			file_printer.println();
			file_printer.print("	]");
		}
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_CommunicationChannel() throws Exception{
		System.out.println("generating Communication Channels ...");
		
		printStream_for_CommunicationChannel =new PrintStream(new FileOutputStream("result/CommunicationChannel.csv"));
		printStream_for_CommunicationChannel.println("#PartyID,"+"#ComponentID,"+"#ChannelID,"+"#ChannelPrimaryKey,"+"#CommunicationChannelID,"+"#AdapterType");
		
		CommunicationChannelInService service = new CommunicationChannelInService();
		CommunicationChannelIn port = service.getCommunicationChannelIn_Port();
		set_security((BindingProvider)port,"/CommunicationChannelInService/CommunicationChannelInImplBean");
		
		CommunicationChannelQueryIn input1 = new CommunicationChannelQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setCommunicationChannelID(null);
		CommunicationChannelQueryOut output1 = port.query(input1);
		
		List<CommunicationChannelID> list_temp = output1.getCommunicationChannelID();
		Collections.sort(list_temp, CommunicationChannelCompare.getinstance());
		List<CommunicationChannelID> list = new ArrayList<CommunicationChannelID>();
		
		// generate communication channel files
		File temp_file = new File ("result/communication_channel");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		CommunicationChannelReadIn input2 = new CommunicationChannelReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<CommunicationChannelID> input_list = input2.getCommunicationChannelID();
		for (int i=0;i<list_temp.size();i++){
			CommunicationChannelID item = list_temp.get(i);
			CommunicationChannelID element = new CommunicationChannelID();
			element.setPartyID(item.getPartyID());
			element.setComponentID(item.getComponentID());
			element.setChannelID(item.getChannelID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			CommunicationChannelReadOut result_item = port.read(input2);
			input2.getCommunicationChannelID().clear();
			
			List<CommunicationChannel> result_element = result_item.getCommunicationChannel();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getCommunicationChannelID();
				System.out.println("	generating Communication Channel " + item.getPartyID() + "," + item.getComponentID() + "," + item.getChannelID());
				export_CommunicationChannel_item_json(result_element.get(j), file_no);
				export_CommunicationChannel_item(result_element.get(j), file_no++);
				
				String channel_properties=changeNullToEmptyString(item.getPartyID())+connector
						+changeNullToEmptyString(item.getComponentID())+connector
						+changeNullToEmptyString(item.getChannelID());
				
				map_Channel_ID_to_Adapter_type.put(channel_properties, result_element.get(j).getAdapterMetadata().getName());
				printStream_for_CommunicationChannel.println(
						item.getPartyID()+","
						+item.getComponentID()+","
						+item.getChannelID()+","
						+channel_properties+","
						+item.getChannelID()+","
						+result_element.get(j).getAdapterMetadata().getName());
				list.add(item);
			}
		}
		
		file_printer_of_log.println("\n\n######################map_Channel_ID_to_Adapter_type.size(): "+map_Channel_ID_to_Adapter_type.size());
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/communication_channel/list.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		file_printer.println("				<th>Communication Component</th>");
		file_printer.println("				<th>Communication Channel</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			channel_table.add((list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "|" + list.get(i).getChannelID()).replaceAll("\\x2a", ""));
			file_printer.println("			<tr>");
			int index_no = party_table.indexOf(list.get(i).getPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getPartyID() + "</a></td>");
			
			index_no = component_table.indexOf((list.get(i).getPartyID() + "|" + list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getComponentID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\">" + list.get(i).getComponentID() + "</a></td>");
			
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i).getChannelID() + "</a></td>");
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=CommunicationChannel\"  onclick=\"DoMenu('CommunicationChannel')\">Communication Channel</a>");
		file_link.println("					<ul id=\"CommunicationChannel\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"communication_channel/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"communication_channel/" + i + ".html\" target=\"a2\">" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "|" + list.get(i).getChannelID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/communicationchannel.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"CommunicationChannel\":{");
		file_json.print("		\"name\":\"Communication Channel\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "|" + list.get(i).getChannelID() + "\",");
				file_json.println("				\"party\":\"" + list.get(i).getPartyID() + "\",");
				file_json.println("				\"component\":\"" + list.get(i).getComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getPartyID() + "|" + list.get(i).getComponentID() + "\",");
				file_json.println("				\"channel\":\"" + list.get(i).getChannelID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
		
		printStream_for_CommunicationChannel.close();
		file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" end of export_CommunicationChannel");
	}
	
	public void export_CommunicationChannel_item(CommunicationChannel item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_channel/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Communication Channel</th>");
		file_printer.println("				<td>" + item.getCommunicationChannelID().getChannelID() + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Party</th>");
		int index_no = party_table.indexOf(item.getCommunicationChannelID().getPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(item.getCommunicationChannelID().getPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\"  style=\"color:blue\">" + item.getCommunicationChannelID().getPartyID() + "</a></td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Communication Component</th>");
		index_no = component_table.indexOf((item.getCommunicationChannelID().getPartyID() + "|" + item.getCommunicationChannelID().getComponentID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(item.getCommunicationChannelID().getPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + item.getCommunicationChannelID().getComponentID() + "</a></td>");
		file_printer.println("			</tr>");
		
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Adapter Type</th>");
		file_printer.println("				<td>" + print_table_cell(item.getAdapterMetadata().getName()) + "</td>");
		file_printer.println("				<th>Transport Protocol</th>");
		file_printer.println("				<td>" + print_table_cell(item.getTransportProtocol()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Adapter NameSpace</th>");
		file_printer.println("				<td>" + print_table_cell(item.getAdapterMetadata().getNamespace()) + "</td>");
		file_printer.println("				<th>Transport Protocol Version</th>");
		file_printer.println("				<td>" + print_table_cell(item.getTransportProtocolVersion()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Adapter Direction</th>");
		file_printer.println("				<td>" + print_table_cell(item.getDirection().value()) + "</td>");
		file_printer.println("				<th>Message Protocol</th>");
		file_printer.println("				<td>" + print_table_cell(item.getMessageProtocol()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Adapter Engine</th>");
		if (item.getAdapterEngineName() == null || item.getAdapterEngineName().length() == 0)
			file_printer.println("				<td>Central Adapter Engine</td>");
		else
			file_printer.println("				<td>" + item.getAdapterEngineName() + "</td>");
		file_printer.println("				<th>Message Protocol Version</th>");
		file_printer.println("				<td>" + print_table_cell(item.getMessageProtocolVersion()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(5,1);\">Parameters</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(5,2);\">Table Parameters</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(5,3);\">Identifiers</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(5,4);\">Module</a></li>");
		file_printer.println("					<li id=\"current5\"><a href=\"#\" onclick=\"change_option(5,5);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Parameters</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Sender</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Agency</th>");
		file_printer.println("							<th>Schema</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<td>" + print_table_cell(item.getSenderIdentifier().getSchemeAgencyID()) + "</td>");
		file_printer.println("							<td>" + print_table_cell(item.getSenderIdentifier().getSchemeID()) + "</td>");
		file_printer.println("							<td>" + print_table_cell(item.getSenderIdentifier().getValue()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Receiver</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Agency</th>");
		file_printer.println("							<th>Schema</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<td>" + print_table_cell(item.getReceiverIdentifier().getSchemeAgencyID()) + "</td>");
		file_printer.println("							<td>" + print_table_cell(item.getReceiverIdentifier().getSchemeID()) + "</td>");
		file_printer.println("							<td>" + print_table_cell(item.getReceiverIdentifier().getValue()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content4\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Module Sequence</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Number</th>");
		file_printer.println("							<th>Module Name</th>");
		file_printer.println("							<th>Module Type</th>");
		file_printer.println("							<th>Module Key</th>");
		file_printer.println("						</tr>");
		List<ProcessStep> process_step = item.getModuleProcess().getProcessStep();
		for (int i=0; i<process_step.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + i + "</td>");
			file_printer.println("							<td>" + print_table_cell(process_step.get(i).getModuleName()) + "</td>");
			file_printer.println("							<td>" + process_step.get(i).getModuleType() + "</td>");
			file_printer.println("							<td>" + print_table_cell(process_step.get(i).getParameterGroupID()) + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Module Parameters</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Module Key</th>");
		file_printer.println("							<th>Parameter Name</th>");
		file_printer.println("							<th>Parameter Value</th>");		
		file_printer.println("						</tr>");
		List<ParameterGroup> module_par = item.getModuleProcess().getParameterGroup();
		for (int i=0; i<module_par.size(); i++){
			List<RestrictedGenericProperty> par_list = module_par.get(i).getParameter();
			for (int j=0; j<par_list.size(); j++){
				file_printer.println("						<tr>");
				file_printer.println("							<td>" + print_table_cell(module_par.get(i).getParameterGroupID()) + "</td>");
				file_printer.println("							<td>" + print_table_cell(par_list.get(j).getName()) + "</td>");
				file_printer.println("							<td>" + print_table_cell(par_list.get(j).getValue()) + "</td>");
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");

		print_admin_data(file_printer, 5, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_CommunicationChannel_item_json(CommunicationChannel item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/communication_channel/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"party\":\"" + item.getCommunicationChannelID().getPartyID() + "\",");
		file_printer.println("	\"component\":\"" + item.getCommunicationChannelID().getComponentID() + "\",");
		file_printer.println("	\"channel\":\"" + item.getCommunicationChannelID().getChannelID() + "\",");
		file_printer.println("	\"adapterengine\":\"" + item.getAdapterEngineName() + "\",");
		file_printer.println("	\"adaptername\":\"" + item.getAdapterMetadata().getName() + "\",");
		file_printer.println("	\"adapternamespace\":\"" + item.getAdapterMetadata().getNamespace() + "\",");
		file_printer.println("	\"adapterdirection\":\"" + item.getDirection().value() + "\",");
		file_printer.println("	\"messageprotocol\":\"" + item.getMessageProtocol() + "\",");
		file_printer.println("	\"messageprotocolversion\":\"" + item.getMessageProtocolVersion() + "\",");
		file_printer.println("	\"receiveragency\":\"" + item.getReceiverIdentifier().getSchemeAgencyID() + "\",");
		file_printer.println("	\"receiverscheme\":\"" + item.getReceiverIdentifier().getSchemeID() + "\",");
		file_printer.println("	\"receivervalue\":\"" + item.getReceiverIdentifier().getValue() + "\",");
		file_printer.println("	\"senderagency\":\"" + item.getSenderIdentifier().getSchemeAgencyID() + "\",");
		file_printer.println("	\"senderscheme\":\"" + item.getSenderIdentifier().getSchemeID() + "\",");
		file_printer.println("	\"sendervalue\":\"" + item.getSenderIdentifier().getValue() + "\",");
		file_printer.println("	\"protocol\":\"" + item.getTransportProtocol() + "\",");
		file_printer.println("	\"protocolversion\":\"" + item.getTransportProtocolVersion() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericProperty> adp_attr = item.getAdapterSpecificAttribute();
		if (adp_attr.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"parameter\":[");
			for(int i=0; i< adp_attr.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + adp_attr.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + adp_attr.get(i).getNamespace() + "\",");
				file_printer.println("			\"value\":\"" + ((adp_attr.get(i).getValue() == null)? "" : adp_attr.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		int count = 0;
		if (tab_attr_list.size() > 0){
			for(int i=0; i< tab_attr_list.size(); i++){
				List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
				for(int j=0; j<tabrow.size(); j++){
					List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
					for(int k=0; k<cell.size(); k++){
						file_printer.println(",");
						if(count == 0){
							file_printer.println("	\"tabparameter\":[");
						}
						count++;
						file_printer.println("		{");
						file_printer.println("			\"tablename\":\"" + tab_attr_list.get(i).getName() + "\",");
						file_printer.println("			\"tablenamespace\":\"" + tab_attr_list.get(i).getNamespace() + "\",");
						file_printer.println("			\"column\":\"" + cell.get(k).getColumnName() + "\",");
						file_printer.println("			\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("		}");
					}
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("	]");
			}
		}

		List<ProcessStep> module_step = item.getModuleProcess().getProcessStep();
		if (module_step.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"module\":[");
			for(int i=0; i< module_step.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"number\":\"" + i + "\",");
				file_printer.println("			\"module\":\"" + module_step.get(i).getModuleName() + "\",");
				file_printer.println("			\"type\":\"" + module_step.get(i).getModuleType() + "\",");
				file_printer.println("			\"key\":\"" + module_step.get(i).getParameterGroupID() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<ParameterGroup> module_parameter = item.getModuleProcess().getParameterGroup();
		count = 0;
		if (module_parameter.size() > 0){
			for(int i=0; i< module_parameter.size(); i++){
				List<RestrictedGenericProperty> mod_list = module_parameter.get(i).getParameter();
				for (int j=0; j<mod_list.size();j++){
					file_printer.println(",");
					if(count == 0){
						file_printer.println("	\"moduleparameter\":[");
					}
					count++;
					file_printer.println("		{");
					file_printer.println("			\"key\":\"" + module_parameter.get(i).getParameterGroupID() + "\",");
					file_printer.println("			\"name\":\"" + mod_list.get(j).getName() + "\",");
					file_printer.println("			\"value\":\"" + ((mod_list.get(j).getValue() == null)? "" : mod_list.get(j).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
					file_printer.print("		}");
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("	]");
			}
		}

		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_ReceiverRule() throws Exception{
		System.out.println("generating Receiver Rule ...");
		
		ReceiverRuleInService service = new ReceiverRuleInService();
		ReceiverRuleIn port = service.getReceiverRuleIn_Port();
		set_security((BindingProvider)port,"/ReceiverRuleInService/ReceiverRuleInImplBean");
		
		ReceiverRuleQueryIn input1 = new ReceiverRuleQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setReceiverRuleID(null);
		ReceiverRuleQueryOut output1;
		try {
			output1 = port.query(input1);
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Receiver Rule, maybe current PI version doesn't support");
			return;
		}
		
		List<String> list_temp = output1.getReceiverRuleID();
		Collections.sort(list_temp);
		List<String> list = new ArrayList<String>();
		
		// generate receiver rule files
		File temp_file = new File ("result/receiver_rule");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		ReceiverRuleReadIn input2 = new ReceiverRuleReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<String> input_list = input2.getReceiverRuleID();
		for (int i=0;i<list_temp.size();i++){
			input_list.add(list_temp.get(i));
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			ReceiverRuleReadOut result_item = port.read(input2);
			input2.getReceiverRuleID().clear();
			
			List<ReceiverRule> result_element = result_item.getReceiverRule();
			for (int j=0; j<result_element.size(); j++){
				String item = result_element.get(j).getReceiverRuleID();
				System.out.println("	generating Receiver Rule " + item);
				export_ReceiverRule_item_json(result_element.get(j), file_no);
				export_ReceiverRule_item(result_element.get(j), file_no++);
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/receiver_rule/list.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Receiver Rule</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			receiverrule_table.add(list.get(i));
			file_printer.println("			<tr>");
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i) + "</a></td>");			
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=ReceiverRule\"  onclick=\"DoMenu('ReceiverRule')\">Receiver Rule</a>");
		file_link.println("					<ul id=\"ReceiverRule\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"receiver_rule/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"receiver_rule/" + i + ".html\" target=\"a2\">" + list.get(i) + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/receiverrule.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"ReceiverRule\":{");
		file_json.print("		\"name\":\"Receiver Rule\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.print("			{\"name\":\"" + list.get(i) + "\"}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_ReceiverRule_item(ReceiverRule item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_rule/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Receiver Rule</th>");
		file_printer.println("				<td>" + print_table_cell(item.getReceiverRuleID()) + "</td>");
		file_printer.println("			</tr>");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(2,1);\">Rule</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(2,2);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Rule</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Condition</th>");
		file_printer.println("							<th>Communication Party</th>");
		file_printer.println("							<th>Communication Component</th>");
		file_printer.println("						</tr>");
		List<ReceiverRulePart> local_rule_list = item.getRule();
		for(int i=0; i<local_rule_list.size();i++){
			List<CommunicationPartnerExtractor> receiver_list = local_rule_list.get(i).getReceiver();
			for (int j=0; j<receiver_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0){
					if (local_rule_list.get(i).getCondition()==null || local_rule_list.get(i).getCondition().getAtomicConditionBlock() == null || local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() == 0)
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\">&nbsp;</td>");
					else{
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\"><a href=\"" + file_no + "a_" + i + ".html\" style=\"color:blue\" target=\"a3\">Condition</a></td>");
						generate_condition_file("receiver_rule/" + file_no + "a_" + i, local_rule_list.get(i).getCondition().getAtomicConditionBlock());
					}
				}
				boolean constant_party = true;
				if(receiver_list.get(j).getCommunicationParty().getTypeID().value().equals("Constant")){
					int index = party_table.indexOf(receiver_list.get(j).getCommunicationParty().getValue());
					if (index < 0)
						file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationParty().getValue()) + "</td>");
					else
						file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\">" + receiver_list.get(j).getCommunicationParty().getValue() + "</a></td>");
				}
				else{
					constant_party = false;
					file_printer.println("							<td><a href=\"" + file_no + "b_" + i + "_" + j + ".html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
					generate_extractor_file("receiver_determination/" + file_no + "b_" + i + "_" + j , receiver_list.get(j).getCommunicationParty());
				}
				if(receiver_list.get(j).getCommunicationComponent().getTypeID().value().equals("Constant")){
					if (constant_party){
						int index = component_table.indexOf((receiver_list.get(j).getCommunicationParty().getValue() + "|" + receiver_list.get(j).getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
						if (index < 0)
							file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationComponent().getValue()) + "</td>");
						else
							file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + receiver_list.get(j).getCommunicationComponent().getValue() + "</a></td>");
					}
					else
						file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationComponent().getValue()) + "</td>");
				}
				else{
					file_printer.println("							<td><a href=\"" + file_no + "c_" + i + "_" + j + ".html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
					generate_extractor_file("receiver_determination/" + file_no + "c_" + i + "_" + j, receiver_list.get(j).getCommunicationComponent());
				}				
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Condition Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a4\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 2, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_ReceiverRule_item_json(ReceiverRule item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_rule/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"rulename\":\"" + item.getReceiverRuleID() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<ReceiverRulePart> local_rule_list = item.getRule();
		if(local_rule_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"rule\":[");
			for(int i=0; i<local_rule_list.size();i++){
				if(i > 0)
					file_printer.println(",");
				file_printer.println("		{");
				boolean hasprefix = false;
				
				if (local_rule_list.get(i).getCondition()!=null && local_rule_list.get(i).getCondition().getAtomicConditionBlock() != null && local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() > 0){
					hasprefix = true;
					file_printer.println("			\"condition\":[");
					List<AtomicConditionBlock> cond_block = local_rule_list.get(i).getCondition().getAtomicConditionBlock();
					for (int k=0; k<cond_block.size(); k++){
						if (k>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"or\":[");
						List<AtomicCondition> cond_atomic = cond_block.get(k).getAtomicCondition();
						for (int n=0; n<cond_atomic.size(); n++){
							if (n>0)
								file_printer.println(",");
							file_printer.println("						{");
							file_printer.println("							\"left\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getLeftExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getLeftExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getLeftExtractor().getValue() == null)? "" : cond_atomic.get(n).getLeftExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							},");
							file_printer.println("							\"operator\":\"" + cond_atomic.get(n).getOperator() + "\",");
							file_printer.println("							\"right\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getRightExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getRightExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getRightExtractor().getValue() == null)? "" : cond_atomic.get(n).getRightExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							}");
							file_printer.print("						}");
						}
						file_printer.println();
						file_printer.println("					]");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				List<CommunicationPartnerExtractor> receiver_list = local_rule_list.get(i).getReceiver();
				if(receiver_list.size()>0){
					if (hasprefix)
						file_printer.println(",");
					hasprefix = true;
					file_printer.println("			\"receiver\":[");
					for(int j=0; j< receiver_list.size(); j++){
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"party\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationParty().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationParty().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationParty().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationParty().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationParty().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"partyagency\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"partyschema\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationPartySchema().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationPartySchema().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationPartySchema().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationPartySchema().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationPartySchema().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"component\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationComponent().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationComponent().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationComponent().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationComponent().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationComponent().getValue() + "\"");
						file_printer.println("					}");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				
				file_printer.println();
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		file_printer.println();
		file_printer.print("}");		
		file_printer.close();
	}
	
	public void export_ReceiverDetermination() throws Exception{
		System.out.println("generating Receiver Determinations ...");
		
		ReceiverDeterminationInService service = new ReceiverDeterminationInService();
		ReceiverDeterminationIn port = service.getReceiverDeterminationIn_Port();
		set_security((BindingProvider)port,"/ReceiverDeterminationInService/ReceiverDeterminationInImplBean");
		
		ReceiverDeterminationQueryIn input1 = new ReceiverDeterminationQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setReceiverDeterminationID(null);
		ReceiverDeterminationQueryOut output1 = port.query(input1);
		
		List<MessageHeaderID> list_temp = output1.getReceiverDeterminationID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate receiver determination files
		File temp_file = new File ("result/receiver_determination");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		ReceiverDeterminationReadIn input2 = new ReceiverDeterminationReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getReceiverDeterminationID();
		
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			ReceiverDeterminationReadOut result_item = port.read(input2);
			input2.getReceiverDeterminationID().clear();
			
			
			
			List<ReceiverDetermination> result_element = result_item.getReceiverDetermination();
			
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getReceiverDeterminationID();
				
				System.out.println("	generating Receiver Determination " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName());
				
				export_ReceiverDetermination_item_json(result_element.get(j), file_no);
				export_ReceiverDetermination_item(result_element.get(j), file_no++);
				list.add(item);
			}
		}
		
		
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/receiver_determination/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,receiverdetermination_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=ReceiverDetermination\"  onclick=\"DoMenu('ReceiverDetermination')\">Receiver Determination</a>");
		file_link.println("					<ul id=\"ReceiverDetermination\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"receiver_determination/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"receiver_determination/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/receiverdetermination.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"ReceiverDetermination\":{");
		file_json.print("		\"name\":\"Receiver Determination\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_ReceiverDetermination_item(ReceiverDetermination item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_determination/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		int index_no;
		print_message_header(file_printer, "Receiver Determination", item.getReceiverDeterminationID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
	
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(5,1);\">Local Rule</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(5,2);\">External Rule</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(5,3);\">Extend</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(5,4);\">No Receiver</a></li>");
		file_printer.println("					<li id=\"current5\"><a href=\"#\" onclick=\"change_option(5,5);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Local Rule</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Operation</th>");
		file_printer.println("							<th>Condition</th>");
		file_printer.println("							<th>Communication Party</th>");
		file_printer.println("							<th>Communication Component</th>");
		file_printer.println("						</tr>");
		List<ReceiverDeterminationRule> local_rule_list = item.getRule();
		for(int i=0; i<local_rule_list.size();i++){
			List<CommunicationPartnerExtractor> receiver_list = local_rule_list.get(i).getReceiver();
			for (int j=0; j<receiver_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0){
					file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\">" + print_table_cell(local_rule_list.get(i).getOperation()) + "</td>");
					if (local_rule_list.get(i).getCondition()==null || local_rule_list.get(i).getCondition().getAtomicConditionBlock() == null || local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() == 0)
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\">&nbsp;</td>");
					else{
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\"><a href=\"" + file_no + "a_" + i + ".html\" style=\"color:blue\" target=\"a3\">Condition</a></td>");
						generate_condition_file("receiver_determination/" + file_no + "a_" + i, local_rule_list.get(i).getCondition().getAtomicConditionBlock());
					}
				}
				boolean constant_party = true;
				if(receiver_list.get(j).getCommunicationParty().getTypeID().value().equals("Constant")){
					int index = party_table.indexOf(receiver_list.get(j).getCommunicationParty().getValue());
					if (index < 0)
						file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationParty().getValue()) + "</td>");
					else
						file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\">" + receiver_list.get(j).getCommunicationParty().getValue() + "</a></td>");
				}
				else{
					constant_party = false;
					file_printer.println("							<td><a href=\"" + file_no + "b_" + i + "_" + j + ".html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
					generate_extractor_file("receiver_determination/" + file_no + "b_" + i + "_" + j , receiver_list.get(j).getCommunicationParty());
				}
				if(receiver_list.get(j).getCommunicationComponent().getTypeID().value().equals("Constant")){
					if (constant_party){
						int index = component_table.indexOf((receiver_list.get(j).getCommunicationParty().getValue() + "|" + receiver_list.get(j).getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
						if (index < 0)
							file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationComponent().getValue()) + "</td>");
						else
							file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + receiver_list.get(j).getCommunicationComponent().getValue() + "</a></td>");
					}
					else
						file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getCommunicationComponent().getValue()) + "</td>");
				}
				else{
					file_printer.println("							<td><a href=\"" + file_no + "c_" + i + "_" + j + ".html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
					generate_extractor_file("receiver_determination/" + file_no + "c_" + i + "_" + j, receiver_list.get(j).getCommunicationComponent());
				}				
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Condition Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a4\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">External Rules</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Operation</th>");
		file_printer.println("							<th>Rule</th>");
		file_printer.println("						</tr>");
		List<ReceiverDeterminationInclude> rule_list = item.getInclude();
		for(int i=0; i<rule_list.size();i++){
			List<String> rulename_list = rule_list.get(i).getReceiverRuleID();
			for (int j=0; j<rulename_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0)
					file_printer.println("							<td rowspan=\"" + rulename_list.size() + "\">" + print_table_cell(rule_list.get(i).getOperation()) + "</td>");
				
				index_no = receiverrule_table.indexOf(rulename_list.get(j));
				if (index_no < 0)
					file_printer.println("							<td>" + print_table_cell(rulename_list.get(j)) + "</td>");
				else
					file_printer.println("							<td><a href=\"../receiver_rule/" + index_no + ".html\" style=\"color:blue\">" + rulename_list.get(j) + "</a></td>");
				
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		List<ReceiverDeterminationMapping> operation_list = item.getDynamicReceiverRule();
		for (int i=0; i<operation_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">Operation:  " + operation_list.get(i).getOperation() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Operation Mapping Name</th>");
			file_printer.println("							<th>Operation Mapping Namespace</th>");
			file_printer.println("						</tr>");
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + operation_list.get(i).getMapping().getName() + "</td>");
			file_printer.println("							<td>" + operation_list.get(i).getMapping().getNamespace() + "</td>");
			file_printer.println("						</tr>");			
			file_printer.println("					</table>");
			if(operation_list.get(i).getMappingParamters()!=null){
				file_printer.println("					<table border=\"1\">");
				file_printer.println("						<tr>");   
				file_printer.println("							<th>Parameter Name</th>");
				file_printer.println("							<th>Parameter Type</th>");
				file_printer.println("							<th>Parameter Value</th>");
				file_printer.println("						</tr>");
				List<RestrictedGenericProperty> str_para_list = operation_list.get(i).getMappingParamters().getString();
				for (int j=0; j<str_para_list.size(); j++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + str_para_list.get(j).getName() + "</td>");
					file_printer.println("							<td>String</td>");
					file_printer.println("							<td>" + str_para_list.get(j).getValue() + "</td>");
					file_printer.println("						</tr>");			
				}
				List<IntegerProperty> int_para_list = operation_list.get(i).getMappingParamters().getInteger();
				for (int j=0; j<int_para_list.size(); j++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + int_para_list.get(j).getName() + "</td>");
					file_printer.println("							<td>Integer</td>");
					file_printer.println("							<td>" + int_para_list.get(j).getValue() + "</td>");
					file_printer.println("						</tr>");			
				}
				List<ChannelProperty> chan_para_list = operation_list.get(i).getMappingParamters().getChannel();
				for (int j=0; j<chan_para_list.size(); j++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + chan_para_list.get(j).getName() + "</td>");
					file_printer.println("							<td>Channel</td>");
					index_no = channel_table.indexOf((chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()).replaceAll("\\x2a", ""));
					if (index_no < 0)
						file_printer.println("							<td>" + print_table_cell(chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()) + "</td>");
					else
						file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID() + "</a></td>");
					file_printer.println("						</tr>");			
				}
				file_printer.println("					</table>");
			}
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content4\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">No Receiver</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver not found Behavior</th>");
		file_printer.println("							<td>" + item.getNoReceiverBehaviour() + "</td>");
		file_printer.println("						</tr>");
		CommunicationPartnerExtractor ext = item.getNoReceiverReceiver();
		if (ext != null){
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Exception Party</th>");
			index_no = party_table.indexOf(ext.getCommunicationParty().getValue());
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(ext.getCommunicationParty().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + ext.getCommunicationParty().getValue() + "</a></td>");
			file_printer.println("						</tr>");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Exception Component</th>");
			index_no = component_table.indexOf((ext.getCommunicationParty().getValue() + "|" + ext.getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(ext.getCommunicationComponent().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + ext.getCommunicationComponent().getValue() + "</a></td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");

		print_admin_data(file_printer, 5, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_ReceiverDetermination_item_json(ReceiverDetermination item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_determination/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getReceiverDeterminationID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getReceiverDeterminationID().getSenderComponentID() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getReceiverDeterminationID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getReceiverDeterminationID().getReceiverComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getReceiverDeterminationID().getInterfaceName() + "\",");
		file_printer.println("	\"interfacenamespace\":\"" + item.getReceiverDeterminationID().getInterfaceNamespace() + "\",");
		file_printer.println("	\"noreceiver\":\"" + item.getNoReceiverBehaviour() + "\",");
		CommunicationPartnerExtractor ext = item.getNoReceiverReceiver();
		if (ext != null){
			file_printer.println("	\"noreceiverparty\":{");
			file_printer.println("		\"type\":\"" + item.getNoReceiverReceiver().getCommunicationParty().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getNoReceiverReceiver().getCommunicationParty().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getNoReceiverReceiver().getCommunicationParty().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getNoReceiverReceiver().getCommunicationParty().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getNoReceiverReceiver().getCommunicationParty().getValue() + "\"");
			file_printer.println("	},");
			file_printer.println("	\"noreceiverpartyagency\":{");
			file_printer.println("		\"type\":\"" + item.getNoReceiverReceiver().getCommunicationPartyAgency().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getNoReceiverReceiver().getCommunicationPartyAgency().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getNoReceiverReceiver().getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getNoReceiverReceiver().getCommunicationPartyAgency().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getNoReceiverReceiver().getCommunicationPartyAgency().getValue() + "\"");
			file_printer.println("	},");
			file_printer.println("	\"noreceiverpartyschema\":{");
			file_printer.println("		\"type\":\"" + item.getNoReceiverReceiver().getCommunicationPartySchema().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getNoReceiverReceiver().getCommunicationPartySchema().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getNoReceiverReceiver().getCommunicationPartySchema().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getNoReceiverReceiver().getCommunicationPartySchema().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getNoReceiverReceiver().getCommunicationPartySchema().getValue() + "\"");
			file_printer.println("	},");
			file_printer.println("	\"noreceivercomponent\":{");
			file_printer.println("		\"type\":\"" + item.getNoReceiverReceiver().getCommunicationComponent().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getNoReceiverReceiver().getCommunicationComponent().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getNoReceiverReceiver().getCommunicationComponent().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getNoReceiverReceiver().getCommunicationComponent().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getNoReceiverReceiver().getCommunicationComponent().getValue() + "\"");
			file_printer.println("	},");
		}		
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		if (prefix_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"prefixnamespace\":[");
			for(int i=0; i< prefix_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"prefix\":\"" + prefix_list.get(i).getPrefix() + "\",");
				file_printer.println("			\"URI\":\"" + prefix_list.get(i).getURI() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<ReceiverDeterminationInclude> rule_list = item.getInclude();
		if (rule_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"externalrule\":[");
			boolean notbegin = false;
			for(int i=0; i< rule_list.size(); i++){
				List<String> rulename_list = rule_list.get(i).getReceiverRuleID();
				for (int j=0; j<rulename_list.size();j++){
						if(notbegin){
							file_printer.println(",");
						}
						notbegin = true;
						file_printer.println("		{");
						file_printer.println("			\"operation\":\"" + rule_list.get(i).getOperation() + "\",");
						file_printer.println("			\"rule\":\"" + rulename_list.get(j) + "\"");
						file_printer.print("		}");
					}
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<ReceiverDeterminationRule> local_rule_list = item.getRule();
		if(local_rule_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"localrule\":[");
			for(int i=0; i<local_rule_list.size();i++){
				if(i > 0)
					file_printer.println(",");
				file_printer.println("		{");
				boolean hasoperation = false;
				if(local_rule_list.get(i).getOperation()!=null){
					file_printer.print("			\"operation\":\"" + local_rule_list.get(i).getOperation() + "\"");
					hasoperation = true;
				}
				
				if (local_rule_list.get(i).getCondition()!=null && local_rule_list.get(i).getCondition().getAtomicConditionBlock() != null && local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() > 0){
					if (hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"condition\":[");
					List<AtomicConditionBlock> cond_block = local_rule_list.get(i).getCondition().getAtomicConditionBlock();
					for (int k=0; k<cond_block.size(); k++){
						if (k>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"or\":[");
						List<AtomicCondition> cond_atomic = cond_block.get(k).getAtomicCondition();
						for (int n=0; n<cond_atomic.size(); n++){
							if (n>0)
								file_printer.println(",");
							file_printer.println("						{");
							file_printer.println("							\"left\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getLeftExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getLeftExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getLeftExtractor().getValue() == null)? "" : cond_atomic.get(n).getLeftExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							},");
							file_printer.println("							\"operator\":\"" + cond_atomic.get(n).getOperator() + "\",");
							file_printer.println("							\"right\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getRightExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getRightExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getRightExtractor().getValue() == null)? "" : cond_atomic.get(n).getRightExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							}");
							file_printer.print("						}");
						}
						file_printer.println();
						file_printer.println("					]");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				List<CommunicationPartnerExtractor> receiver_list = local_rule_list.get(i).getReceiver();
				if(receiver_list.size()>0){
					if (hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"receiver\":[");
					for(int j=0; j< receiver_list.size(); j++){
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"party\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationParty().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationParty().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationParty().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationParty().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationParty().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"partyagency\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationPartyAgency().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"partyschema\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationPartySchema().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationPartySchema().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationPartySchema().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationPartySchema().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationPartySchema().getValue() + "\"");
						file_printer.println("					},");
						file_printer.println("					\"component\":{");
						file_printer.println("						\"type\":\"" + receiver_list.get(j).getCommunicationComponent().getTypeID() + "\",");
						file_printer.println("						\"context\":\"" + receiver_list.get(j).getCommunicationComponent().getContextObjectName() + "\",");
						file_printer.println("						\"contextnamespace\":\"" + receiver_list.get(j).getCommunicationComponent().getContextObjectNamespace() + "\",");
						file_printer.println("						\"datatype\":\"" + receiver_list.get(j).getCommunicationComponent().getDatatype() + "\",");
						file_printer.println("						\"value\":\"" + receiver_list.get(j).getCommunicationComponent().getValue() + "\"");
						file_printer.println("					}");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				
				file_printer.println();
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<ReceiverDeterminationMapping> operation_list = item.getDynamicReceiverRule();
		if (operation_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"extend\":[");
			for(int i=0; i< operation_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"operation\":\"" + operation_list.get(i).getOperation() + "\",");
				file_printer.println("			\"mappingname\":\"" + operation_list.get(i).getMapping().getName() + "\",");
				file_printer.print("			\"mappingnamespace\":\"" + operation_list.get(i).getMapping().getNamespace() + "\"");
				if(operation_list.get(i).getMappingParamters()!=null){
					file_printer.println(",");
					file_printer.println("			\"mappingparameter\":[");
					boolean empty = true;
					
					List<RestrictedGenericProperty> str_para_list = operation_list.get(i).getMappingParamters().getString();
					for(int j=0; j<str_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"String\",");
						file_printer.println("					\"name\":\"" + str_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":\"" + ((str_para_list.get(j).getValue() == null)? "" : str_para_list.get(j).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("				}");
					}
					
					List<IntegerProperty> int_para_list = operation_list.get(i).getMappingParamters().getInteger();
					for(int j=0; j<int_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"Integer\",");
						file_printer.println("					\"name\":\"" + int_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":\"" + int_para_list.get(j).getValue() + "\"");
						file_printer.print("				}");
					}
					
					List<ChannelProperty> chan_para_list = operation_list.get(i).getMappingParamters().getChannel();
					for(int j=0; j<chan_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"Channel\",");
						file_printer.println("					\"name\":\"" + chan_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":{");
						file_printer.println("						\"party\":\"" + chan_para_list.get(j).getValue().getPartyID() + "\",");
						file_printer.println("						\"component\":\"" + chan_para_list.get(j).getValue().getComponentID() + "\",");
						file_printer.println("						\"channel\":\"" + chan_para_list.get(j).getValue().getChannelID() + "\"");
						file_printer.println("					}");
						file_printer.print("				}");
					}
					
					file_printer.println();
					file_printer.print("				]");
				}
				file_printer.println();
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		file_printer.println();
		file_printer.print("}");		
		file_printer.close();
	}
	
	public void export_InterfaceDetermination() throws Exception{
		System.out.println("generating Interface Determinations ...");
		
		printStream_for_InterfaceDetermination=new PrintStream(new FileOutputStream("result/InterfaceDetermination.csv"));
		printStream_for_InterfaceDetermination.println(
				"#SenderPartyID,"+"#SenderComponentID,"+"#SenderInterfaceName,"+"#SenderInterfaceNamespace,"+"#SenderPrimaryKey,"
				+"#SenderChannelPrimaryKey,"+"#SenderChannelID,"+"#SenderAdapterType,"
				+"#ReceiverPartyID,"+"#ReceiverComponentID,"+"#ReceiverInterfaceName,"+"#ReceiverInterfaceNamespace,"+"#ReceiverPrimaryKey,"
				+"#ReceiverChannelPrimaryKey,"+"#ReceiverChannelID,"+"#ReceiverAdapterType");
		
		InterfaceDeterminationInService service = new InterfaceDeterminationInService();
		InterfaceDeterminationIn port = service.getInterfaceDeterminationIn_Port();
		set_security((BindingProvider)port,"/InterfaceDeterminationInService/InterfaceDeterminationInImplBean");
		
		InterfaceDeterminationQueryIn input1 = new InterfaceDeterminationQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setInterfaceDeterminationID(null);
		InterfaceDeterminationQueryOut output1 = port.query(input1);
		
		List<MessageHeaderID> list_temp = output1.getInterfaceDeterminationID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate interface determination files
		File temp_file = new File ("result/interface_determination");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		InterfaceDeterminationReadIn input2 = new InterfaceDeterminationReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getInterfaceDeterminationID();
		
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			InterfaceDeterminationReadOut result_item = port.read(input2);
			input2.getInterfaceDeterminationID().clear();
			
			List<InterfaceDetermination> result_element = result_item.getInterfaceDetermination();
			for (int j=0; j<result_element.size(); j++){//
				item = result_element.get(j).getInterfaceDeterminationID();
				
				System.out.println("	generating Interface Determination " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName() + "," + item.getReceiverPartyID() + "," + item.getReceiverComponentID());
				
				export_InterfaceDetermination_item_json(result_element.get(j), file_no);
				export_InterfaceDetermination_item(result_element.get(j),item, file_no++);
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/interface_determination/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,interfacedetermination_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=InterfaceDetermination\"  onclick=\"DoMenu('InterfaceDetermination')\">Interface Determination</a>");
		file_link.println("					<ul id=\"InterfaceDetermination\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"interface_determination/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"interface_determination/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/interfacedetermination.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"InterfaceDetermination\":{");
		file_json.print("		\"name\":\"Interface Determination\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
		printStream_for_InterfaceDetermination.close();
		file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" end of export_InterfaceDetermination");
	}
	
	public void export_InterfaceDetermination_item(InterfaceDetermination item, MessageHeaderID item_MessageHeaderID, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/interface_determination/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		print_message_header(file_printer, "Interface Determination", item.getInterfaceDeterminationID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Maintain Order at Runtime</th>");
		file_printer.println("				<td>" + item.getQualityOfService().value() + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(2,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(2,2);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Local Rule</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Operation</th>");
		file_printer.println("							<th>Condition</th>");
		file_printer.println("							<th>Operation Mapping</th>");
		file_printer.println("							<th>Interface</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("						</tr>");
		List<InterfaceDeterminationRule> rule_list = item.getRule();
		for(int i=0; i<rule_list.size();i++){
			List<DesignObjectID> interface_list = rule_list.get(i).getInterface();
			for (int j=0; j<interface_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0){
					file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">" + print_table_cell(rule_list.get(i).getOperation()) + "</td>");
					if (rule_list.get(i).getCondition()==null || rule_list.get(i).getCondition().getAtomicConditionBlock()==null || rule_list.get(i).getCondition().getAtomicConditionBlock().size() == 0)
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">&nbsp;</td>");
					else{
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\"><a href=\"" + file_no + "a_" + i + ".html\" style=\"color:blue\" target=\"a3\">Condition</a></td>");
						generate_condition_file("interface_determination/" + file_no + "a_" + i, rule_list.get(i).getCondition().getAtomicConditionBlock());
					}
					if(rule_list.get(i).getMapping()==null){
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">&nbsp;</td>");
					}
					else{
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\"><a href=\"" + file_no + "b_" + i + ".html\" style=\"color:blue\" target=\"a3\">" + rule_list.get(i).getMapping().getName() + "</a></td>");
						generate_operation_mapping_file("interface_determination/" + file_no + "b_" + i, rule_list.get(i));
					}
				}
				file_printer.println("							<td>" + interface_list.get(j).getName() + "</td>");
				file_printer.println("							<td>" + interface_list.get(j).getNamespace() + "</td>");
				file_printer.println("						</tr>");
				
				
				String sender_properties=changeNullToEmptyString(item_MessageHeaderID.getSenderPartyID())+connector
						+changeNullToEmptyString(item_MessageHeaderID.getSenderComponentID())+connector
						+changeNullToEmptyString(item_MessageHeaderID.getInterfaceName())+connector
						+changeNullToEmptyString(item_MessageHeaderID.getInterfaceNamespace());


				String receiver_properties=changeNullToEmptyString(item_MessageHeaderID.getSenderPartyID())+connector
						+changeNullToEmptyString(item_MessageHeaderID.getSenderComponentID())+connector
//						+changeNullToEmptyString(item_MessageHeaderID.getInterfaceName())+connector
//						+changeNullToEmptyString(item_MessageHeaderID.getInterfaceNamespace())+connector
						
						+changeNullToEmptyString(item_MessageHeaderID.getReceiverPartyID())+connector
						+changeNullToEmptyString(item_MessageHeaderID.getReceiverComponentID())+connector
						+changeNullToEmptyString(interface_list.get(j).getName())+connector
						+changeNullToEmptyString(interface_list.get(j).getNamespace());
		
				
				printStream_for_InterfaceDetermination.println(
						item_MessageHeaderID.getSenderPartyID()+","
						+item_MessageHeaderID.getSenderComponentID()+","
						+item_MessageHeaderID.getInterfaceName()+","
						+item_MessageHeaderID.getInterfaceNamespace()+","
						+sender_properties+","
						+map_SendX4_to_Channel.get(sender_properties)+","
						+map_SendX4_to_Channel_OnlyID.get(sender_properties)+","
						+map_Channel_ID_to_Adapter_type.get(map_SendX4_to_Channel.get(sender_properties))+","
						
						+item_MessageHeaderID.getReceiverPartyID()+","
						+item_MessageHeaderID.getReceiverComponentID()+","
						+interface_list.get(j).getName()+","
						+interface_list.get(j).getNamespace()+","
						+receiver_properties+","
						+map_ReceiverX8_to_Channel.get(receiver_properties)+","
						+map_ReceiverX8_to_Channel_OnlyID.get(receiver_properties)+","
						+map_Channel_ID_to_Adapter_type.get(map_ReceiverX8_to_Channel.get(receiver_properties))
						);
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Condition Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a4\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 2, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_InterfaceDetermination_item_json(InterfaceDetermination item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/interface_determination/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getInterfaceDeterminationID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getInterfaceDeterminationID().getSenderComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getInterfaceDeterminationID().getInterfaceName() + "\",");		
		file_printer.println("	\"interfacenamespace\":\"" + item.getInterfaceDeterminationID().getInterfaceNamespace() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getInterfaceDeterminationID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getInterfaceDeterminationID().getReceiverComponentID() + "\",");
		file_printer.println("	\"qos\":\"" + item.getQualityOfService().value() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		if (prefix_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"prefixnamespace\":[");
			for(int i=0; i< prefix_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"prefix\":\"" + prefix_list.get(i).getPrefix() + "\",");
				file_printer.println("			\"URI\":\"" + prefix_list.get(i).getURI() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<InterfaceDeterminationRule> rule_list = item.getRule();
		if(rule_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"rule\":[");
			for(int i=0; i<rule_list.size();i++){
				if(i > 0)
					file_printer.println(",");
				file_printer.println("		{");
				boolean hasoperation = false;
				if(rule_list.get(i).getOperation()!=null){
					file_printer.print("			\"operation\":\"" + rule_list.get(i).getOperation() + "\"");
					hasoperation = true;
				}
				if (rule_list.get(i).getCondition()!=null && rule_list.get(i).getCondition().getAtomicConditionBlock() != null && rule_list.get(i).getCondition().getAtomicConditionBlock().size() > 0){
					if(hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"condition\":[");
					List<AtomicConditionBlock> cond_block = rule_list.get(i).getCondition().getAtomicConditionBlock();
					for (int k=0; k<cond_block.size(); k++){
						if (k>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"or\":[");
						List<AtomicCondition> cond_atomic = cond_block.get(k).getAtomicCondition();
						for (int n=0; n<cond_atomic.size(); n++){
							if (n>0)
								file_printer.println(",");
							file_printer.println("						{");
							file_printer.println("							\"left\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getLeftExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getLeftExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getLeftExtractor().getValue() == null)? "" : cond_atomic.get(n).getLeftExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							},");
							file_printer.println("							\"operator\":\"" + cond_atomic.get(n).getOperator() + "\",");
							file_printer.println("							\"right\":{");
							file_printer.println("								\"type\":\"" + cond_atomic.get(n).getRightExtractor().getTypeID() + "\",");
							file_printer.println("								\"contextname\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectName() + "\",");
							file_printer.println("								\"contextnamespace\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("								\"datatype\":\"" + cond_atomic.get(n).getRightExtractor().getDatatype() + "\",");
							file_printer.println("								\"value\":\"" + ((cond_atomic.get(n).getRightExtractor().getValue() == null)? "" : cond_atomic.get(n).getRightExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("							}");
							file_printer.print("						}");
						}
						file_printer.println();
						file_printer.println("					]");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				
				if(rule_list.get(i).getMapping()!=null){
					if(hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"mapping\":{");
					file_printer.println("				\"name\":\"" + rule_list.get(i).getMapping().getName() + "\",");
					file_printer.println("				\"namespace\":\"" + rule_list.get(i).getMapping().getNamespace() + "\"");
					file_printer.print("			}");
				}
				
				if(rule_list.get(i).getMappingParameters()!=null){
					if(hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"mappingparameter\":[");
					boolean empty = true;
					
					List<RestrictedGenericProperty> str_para_list = rule_list.get(i).getMappingParameters().getString();
					for(int j=0; j<str_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"String\",");
						file_printer.println("					\"name\":\"" + str_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":\"" + ((str_para_list.get(j).getValue() == null)? "" : str_para_list.get(j).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("				}");
					}
					
					List<IntegerProperty> int_para_list = rule_list.get(i).getMappingParameters().getInteger();
					for(int j=0; j<int_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"Integer\",");
						file_printer.println("					\"name\":\"" + int_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":\"" + int_para_list.get(j).getValue() + "\"");
						file_printer.print("				}");
					}
					
					List<ChannelProperty> chan_para_list = rule_list.get(i).getMappingParameters().getChannel();
					for(int j=0; j<chan_para_list.size();j++){
						if(j==0 && !empty){
							file_printer.println(",");
						}
						empty = false;
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"type\":\"Channel\",");
						file_printer.println("					\"name\":\"" + chan_para_list.get(j).getName() + "\",");
						file_printer.println("					\"value\":{");
						file_printer.println("						\"party\":\"" + chan_para_list.get(j).getValue().getPartyID() + "\",");
						file_printer.println("						\"component\":\"" + chan_para_list.get(j).getValue().getComponentID() + "\",");
						file_printer.println("						\"channel\":\"" + chan_para_list.get(j).getValue().getChannelID() + "\"");
						file_printer.println("					}");
						file_printer.print("				}");
					}
					
					file_printer.println();
					file_printer.print("				]");
				}
				
				List<DesignObjectID> interface_list = rule_list.get(i).getInterface();
				if(interface_list.size()>0){
					if(hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("			\"interface\":[");
					for(int j=0; j< interface_list.size(); j++){
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("						\"name\":\"" + interface_list.get(j).getName() + "\",");
						file_printer.println("						\"namespace\":\"" + interface_list.get(j).getNamespace() + "\"");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				
				file_printer.println();
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	public void export_SenderAgreement() throws Exception{
		System.out.println("generating Sender Agreements ...");
		
		printStream_for_SenderAgreement=new PrintStream(new FileOutputStream("result/SenderAgreement.csv"));
		printStream_for_SenderAgreement.println("#SenderPartyID,"+"#SenderComponentID,"+"#SenderInterfaceName,"+"#SenderInterfaceNamespace,"+"#SenderPrimaryKey,"+"#ChannelPrimaryKey,"+"#CommunicationChannelID");
		
		SenderAgreementInService service = new SenderAgreementInService();
		SenderAgreementIn port = service.getSenderAgreementIn_Port();
		
		set_security((BindingProvider)port,"/SenderAgreementInService/SenderAgreementInImplBean");
		
		SenderAgreementQueryIn input1 = new SenderAgreementQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setSenderAgreementID(null);
		SenderAgreementQueryOut output1 = port.query(input1);
		
		List<MessageHeaderID> list_temp = output1.getSenderAgreementID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate sender agreement files
		File temp_file = new File ("result/sender_agreement");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		SenderAgreementReadIn input2 = new SenderAgreementReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getSenderAgreementID();
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			SenderAgreementReadOut result_item = port.read(input2);
			input2.getSenderAgreementID().clear();
			
			List<SenderAgreement> result_element = result_item.getSenderAgreement();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getSenderAgreementID();
				System.out.println("	generating Sender Agreement " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName() + "," + item.getReceiverPartyID() + "," + item.getReceiverComponentID());
				export_SenderAgreement_item_json(result_element.get(j), file_no);
				export_SenderAgreement_item(result_element.get(j),item, file_no++);
				list.add(item);
			}
		}
		file_printer_of_log.println("\n######################map_SendX4_to_Channel.size(): "+map_SendX4_to_Channel.size());
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/sender_agreement/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,senderagreement_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=SenderAgreement\"  onclick=\"DoMenu('SenderAgreement')\">Sender Agreement</a>");
		file_link.println("					<ul id=\"SenderAgreement\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"sender_agreement/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"sender_agreement/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/senderagreement.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"SenderAgreement\":{");
		file_json.print("		\"name\":\"Sender Agreement\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
		
		printStream_for_SenderAgreement.close();
		file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" end of export_SenderAgreement");
	}
	
	public void export_SenderAgreement_item(SenderAgreement item, MessageHeaderID item_MessageHeaderID, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/sender_agreement/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		int index_no;
		print_message_header(file_printer, "Sender Agreement", item.getSenderAgreementID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(4,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(4,2);\">Adapter Table Attributes</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(4,3);\">Assigned Users</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(4,4);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Content</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Sender Channel</th>");
		index_no = channel_table.indexOf((item.getCommunicationChannel().getPartyID() + "|" + item.getCommunicationChannel().getComponentID() + "|" + item.getCommunicationChannel().getChannelID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("							<td>" + print_table_cell(item.getCommunicationChannel().getChannelID()) + "</td>");
		else
			file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + item.getCommunicationChannel().getChannelID()+ "</a></td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Schema Validation</th>");
		file_printer.println("							<td>" + item.getSchemaValidation().value() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Adapter Specific Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Assigned Users</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>User Name</th>");
		file_printer.println("						</tr>");
		List<String> user_list = item.getAssignedUser();
		for(int i=0; i< user_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(user_list.get(i)) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 4, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
		String sender_properties=changeNullToEmptyString(item_MessageHeaderID.getSenderPartyID())+connector
				+changeNullToEmptyString(item_MessageHeaderID.getSenderComponentID())+connector
				+changeNullToEmptyString(item_MessageHeaderID.getInterfaceName())+connector
				+changeNullToEmptyString(item_MessageHeaderID.getInterfaceNamespace());


//		file_printer_of_log.println(sender_properties+"-->"+item.getCommunicationChannel().getChannelID());
				
		String channel_properties=changeNullToEmptyString(item.getCommunicationChannel().getPartyID())+connector
		+changeNullToEmptyString(item.getCommunicationChannel().getComponentID())+connector
		+changeNullToEmptyString(item.getCommunicationChannel().getChannelID());
		
		map_SendX4_to_Channel.put(sender_properties, channel_properties);//old: item.getCommunicationChannel().getChannelID()
		map_SendX4_to_Channel_OnlyID.put(sender_properties, item.getCommunicationChannel().getChannelID());
		
		printStream_for_SenderAgreement.println(
				item_MessageHeaderID.getSenderPartyID()+","
				+item_MessageHeaderID.getSenderComponentID()+","
				+item_MessageHeaderID.getInterfaceName()+","
				+item_MessageHeaderID.getInterfaceNamespace()+","
				+sender_properties+","
				
				+channel_properties+","
				+item.getCommunicationChannel().getChannelID()
		);
	}
	
	public void export_SenderAgreement_item_json(SenderAgreement item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/sender_agreement/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getSenderAgreementID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getSenderAgreementID().getSenderComponentID() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getSenderAgreementID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getSenderAgreementID().getReceiverComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getSenderAgreementID().getInterfaceName() + "\",");
		file_printer.println("	\"interfacenamespace\":\"" + item.getSenderAgreementID().getInterfaceNamespace() + "\",");
		file_printer.println("	\"channelparty\":\"" + item.getCommunicationChannel().getPartyID() + "\",");
		file_printer.println("	\"channelcomponent\":\"" + item.getCommunicationChannel().getComponentID() + "\",");
		file_printer.println("	\"channel\":\"" + item.getCommunicationChannel().getChannelID() + "\",");
		file_printer.println("	\"schemavalid\":\"" + item.getSchemaValidation().value() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<String> users = item.getAssignedUser();
		if(users.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"assigneduser\":[");
			for(int i=0; i<users.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"name\":\"" + users.get(i) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		if(attr_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"adapterattribute\":[");
			for(int i=0; i<attr_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + attr_list.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + attr_list.get(i).getNamespace() + "\",");
				file_printer.println("			\"value\":\"" + ((attr_list.get(i).getValue() == null)? "" : attr_list.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		int count = 0;
		if (tab_attr_list.size() > 0){
			for(int i=0; i< tab_attr_list.size(); i++){
				List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
				for(int j=0; j<tabrow.size(); j++){
					List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
					for(int k=0; k<cell.size(); k++){
						file_printer.println(",");
						if(count == 0){
							file_printer.println("	\"adaptertableattribute\":[");
						}
						count++;
						file_printer.println("		{");
						file_printer.println("			\"tablename\":\"" + tab_attr_list.get(i).getName() + "\",");
						file_printer.println("			\"tablenamespace\":\"" + tab_attr_list.get(i).getNamespace() + "\",");
						file_printer.println("			\"column\":\"" + cell.get(k).getColumnName() + "\",");
						file_printer.println("			\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("		}");
					}
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("	]");
			}
		}

		file_printer.println();
		file_printer.print("}");		
		file_printer.close();
	}
	
	public void export_ReceiverAgreement() throws Exception{
		System.out.println("generating Receiver Agreements ...");
		
		printStream_for_SenderAgreement=new PrintStream(new FileOutputStream("result/ReceiverAgreement.csv"));
		printStream_for_SenderAgreement.println("#SenderPartyID,"+"#SenderComponentID,"+"#SenderInterfaceName,"+"#SenderInterfaceNamespace,"
		+"#ReceiverPartyID,"+"#ReceiverComponentID,"+"#ReceiverInterfaceName,"+"#ReceiverInterfaceNamespace,"+"#ReceiverPrimaryKey,"+"#ChannelPrimaryKey,"+"#CommunicationChannelID");
		
		ReceiverAgreementInService service = new ReceiverAgreementInService();
		ReceiverAgreementIn port = service.getReceiverAgreementIn_Port();
		
		set_security((BindingProvider)port,"/ReceiverAgreementInService/ReceiverAgreementInImplBean");
		
		ReceiverAgreementQueryIn input1 = new ReceiverAgreementQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setReceiverAgreementID(null);
		ReceiverAgreementQueryOut output1 = port.query(input1);
		
		List<MessageHeaderID> list_temp = output1.getReceiverAgreementID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate Receiver agreement files
		File temp_file = new File ("result/receiver_agreement");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		ReceiverAgreementReadIn input2 = new ReceiverAgreementReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getReceiverAgreementID();
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			ReceiverAgreementReadOut result_item = port.read(input2);
			input2.getReceiverAgreementID().clear();
			
			List<ReceiverAgreement> result_element = result_item.getReceiverAgreement();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getReceiverAgreementID();
				System.out.println("	generating Receiver Agreement " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName() + "," + item.getReceiverPartyID() + "," + item.getReceiverComponentID());
				export_ReceiverAgreement_item_json(result_element.get(j), file_no);
				export_ReceiverAgreement_item(result_element.get(j),item, file_no++);
				list.add(item);
			}
		}
		file_printer_of_log.println("\n######################map_ReceiverX8_to_Channel.size(): "+map_ReceiverX8_to_Channel.size());
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/receiver_agreement/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,receiveragreement_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=ReceiverAgreement\"  onclick=\"DoMenu('ReceiverAgreement')\">Receiver Agreement</a>");
		file_link.println("					<ul id=\"ReceiverAgreement\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"receiver_agreement/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"receiver_agreement/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/receiveragreement.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"ReceiverAgreement\":{");
		file_json.print("		\"name\":\"Receiver Agreement\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
		printStream_for_SenderAgreement.close();
		file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" end of export_ReceiverAgreement");
	}
	
	public void export_ReceiverAgreement_item(ReceiverAgreement item,MessageHeaderID item_MessageHeaderID, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_agreement/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		int index_no;
		print_message_header(file_printer, "Receiver Agreement", item.getReceiverAgreementID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(4,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(4,2);\">Adapter Table Attributes</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(4,3);\">Header Mapping</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(4,4);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Content</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver Channel</th>");
		index_no = channel_table.indexOf((item.getCommunicationChannel().getPartyID() + "|" + item.getCommunicationChannel().getComponentID() + "|" + item.getCommunicationChannel().getChannelID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("							<td>" + print_table_cell(item.getCommunicationChannel().getChannelID()) + "</td>");
		else
			file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + item.getCommunicationChannel().getChannelID() + "</a></td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Schema Validation</th>");
		file_printer.println("							<td>" + item.isSchemaValidationIndicator() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Adapter Specific Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		

		
		String receiver_properties=changeNullToEmptyString(item_MessageHeaderID.getSenderPartyID())+connector
				+changeNullToEmptyString(item_MessageHeaderID.getSenderComponentID())+connector //problem?
//				+changeNullToEmptyString(item_MessageHeaderID.getInterfaceName())+connector
//				+changeNullToEmptyString(item_MessageHeaderID.getInterfaceNamespace())+connector
				
				+changeNullToEmptyString(item_MessageHeaderID.getReceiverPartyID())+connector
				+changeNullToEmptyString(item_MessageHeaderID.getReceiverComponentID())+connector
				+changeNullToEmptyString(item.getReceiverAgreementID().getInterfaceName())+connector
				+changeNullToEmptyString(item.getReceiverAgreementID().getInterfaceNamespace());
				
				
		String channel_properties=changeNullToEmptyString(item.getCommunicationChannel().getPartyID())+connector
		+changeNullToEmptyString(item.getCommunicationChannel().getComponentID())+connector
		+changeNullToEmptyString(item.getCommunicationChannel().getChannelID());
		
		
//		file_printer_of_log.println(receiver_properties+"-->"+item.getCommunicationChannel().getChannelID());	
		map_ReceiverX8_to_Channel.put(receiver_properties, channel_properties);
		map_ReceiverX8_to_Channel_OnlyID.put(receiver_properties, item.getCommunicationChannel().getChannelID());
		
		printStream_for_SenderAgreement.println(
				item_MessageHeaderID.getSenderPartyID()+","
				+item_MessageHeaderID.getSenderComponentID()+","
				+item_MessageHeaderID.getInterfaceName()+","
				+item_MessageHeaderID.getInterfaceNamespace()+","
				
				+item_MessageHeaderID.getReceiverPartyID()+","
				+item_MessageHeaderID.getReceiverComponentID()+","
				+item.getReceiverAgreementID().getInterfaceName()+","
				+item.getReceiverAgreementID().getInterfaceNamespace()+","
				+receiver_properties+","
				
				+channel_properties+","
				+item.getCommunicationChannel().getChannelID()
		);		
		
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Header Mapping</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Sender Party</th>");
		int constant_party;
		if (item.getHeaderMapping().getSender().getCommunicationParty()==null){
			file_printer.println("							<td>&nbsp;</td>");
			constant_party = 0;
		}
		else if(item.getHeaderMapping().getSender().getCommunicationParty().getTypeID().value().equals("Constant")){
			constant_party = 1;
			int index = party_table.indexOf(item.getHeaderMapping().getSender().getCommunicationParty().getValue());
			if (index < 0)
				file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getSender().getCommunicationParty().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getSender().getCommunicationParty().getValue() + "</a></td>");
		}
		else{
			constant_party = 2;
			file_printer.println("							<td><a href=\"" + file_no + "_" + "1.html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
			generate_extractor_file("receiver_agreement/" + file_no + "_" + "1" , item.getHeaderMapping().getSender().getCommunicationParty());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Sender Component</th>");
		if(item.getHeaderMapping().getSender().getCommunicationComponent() == null)
			file_printer.println("							<td>&nbsp;</td>");
		else if(item.getHeaderMapping().getSender().getCommunicationComponent().getTypeID().value().equals("Constant")){
			if (constant_party == 0){
				int index = component_table.indexOf(("|" + item.getHeaderMapping().getSender().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getSender().getCommunicationComponent().getValue() + "</a></td>");
			}
			else if (constant_party == 0){
				int index = component_table.indexOf((item.getHeaderMapping().getSender().getCommunicationParty().getValue() + "|" + item.getHeaderMapping().getSender().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getSender().getCommunicationComponent().getValue() + "</a></td>");
			}
			else
				file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
		}
		else{
			file_printer.println("							<td><a href=\"" + file_no + "_" + "2.html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
			generate_extractor_file("receiver_agreement/" + file_no + "_" + "2" , item.getHeaderMapping().getSender().getCommunicationComponent());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver Party</th>");
		if (item.getHeaderMapping().getReceiver().getCommunicationParty()==null){
			constant_party = 0;
			file_printer.println("							<td>&nbsp;</td>");
		}
		else if(item.getHeaderMapping().getReceiver().getCommunicationParty().getTypeID().value().equals("Constant")){
			constant_party = 1;
			int index = party_table.indexOf(item.getHeaderMapping().getReceiver().getCommunicationParty().getValue());
			if (index < 0)
				file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getReceiver().getCommunicationParty().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "</a></td>");
		}
		else{
			constant_party = 2;
			file_printer.println("							<td><a href=\"" + file_no + "_" + "3.html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
			generate_extractor_file("receiver_agreement/" + file_no + "_" + "3" , item.getHeaderMapping().getReceiver().getCommunicationParty());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Receiver Component</th>");
		if(item.getHeaderMapping().getReceiver().getCommunicationComponent() == null)
			file_printer.println("							<td>&nbsp;</td>");
		else if(item.getHeaderMapping().getReceiver().getCommunicationComponent().getTypeID().value().equals("Constant")){
			if (constant_party == 0){
				int index = component_table.indexOf(("|" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "</a></td>");
			}
			else if (constant_party == 1){
				int index = component_table.indexOf((item.getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "|" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\">" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "</a></td>");
			}
			else
				file_printer.println("							<td>" + print_table_cell(item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
		}
		else{
			file_printer.println("							<td><a href=\"" + file_no + "_" + "4.html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
			generate_extractor_file("receiver_agreement/" + file_no + "_" + "4" , item.getHeaderMapping().getReceiver().getCommunicationComponent());
		}
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 4, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_ReceiverAgreement_item_json(ReceiverAgreement item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/receiver_agreement/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getReceiverAgreementID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getReceiverAgreementID().getSenderComponentID() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getReceiverAgreementID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getReceiverAgreementID().getReceiverComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getReceiverAgreementID().getInterfaceName() + "\",");
		file_printer.println("	\"interfacenamespace\":\"" + item.getReceiverAgreementID().getInterfaceNamespace() + "\",");
		file_printer.println("	\"channelparty\":\"" + item.getCommunicationChannel().getPartyID() + "\",");
		file_printer.println("	\"channelcomponent\":\"" + item.getCommunicationChannel().getComponentID() + "\",");
		file_printer.println("	\"channel\":\"" + item.getCommunicationChannel().getChannelID() + "\",");
		file_printer.println("	\"schemavalid\":\"" + item.isSchemaValidationIndicator() + "\",");
		if (item.getHeaderMapping().getSender().getCommunicationParty()!=null){
			file_printer.println("	\"headermappingsenderparty\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getSender().getCommunicationParty().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getSender().getCommunicationParty().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getSender().getCommunicationParty().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getSender().getCommunicationParty().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getSender().getCommunicationParty().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getSender().getCommunicationPartyAgency()!=null){
			file_printer.println("	\"headermappingsenderpartyagency\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getSender().getCommunicationPartyAgency().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getSender().getCommunicationPartyAgency().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getSender().getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getSender().getCommunicationPartyAgency().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getSender().getCommunicationPartyAgency().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getSender().getCommunicationPartySchema()!=null){
			file_printer.println("	\"headermappingsenderpartySchema\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getSender().getCommunicationPartySchema().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getSender().getCommunicationPartySchema().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getSender().getCommunicationPartySchema().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getSender().getCommunicationPartySchema().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getSender().getCommunicationPartySchema().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getSender().getCommunicationComponent()!=null){
			file_printer.println("	\"headermappingsendercomponent\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getSender().getCommunicationComponent().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getSender().getCommunicationComponent().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getSender().getCommunicationComponent().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getSender().getCommunicationComponent().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getSender().getCommunicationComponent().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getReceiver().getCommunicationParty()!=null){
			file_printer.println("	\"headermappingreceiverparty\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getReceiver().getCommunicationParty().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getReceiver().getCommunicationParty().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getReceiver().getCommunicationParty().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getReceiver().getCommunicationParty().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getReceiver().getCommunicationPartyAgency()!=null){
			file_printer.println("	\"headermappingreceiverpartyagency\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartyAgency().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartyAgency().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartyAgency().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartyAgency().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getReceiver().getCommunicationPartySchema()!=null){
			file_printer.println("	\"headermappingreceiverpartySchema\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartySchema().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartySchema().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartySchema().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartySchema().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getReceiver().getCommunicationPartySchema().getValue() + "\"");
			file_printer.println("	},");
		}
		if (item.getHeaderMapping().getReceiver().getCommunicationComponent()!=null){
			file_printer.println("	\"headermappingreceivercomponent\":{");
			file_printer.println("		\"type\":\"" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getTypeID() + "\",");
			file_printer.println("		\"context\":\"" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getContextObjectName() + "\",");
			file_printer.println("		\"contextnamespace\":\"" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getContextObjectNamespace() + "\",");
			file_printer.println("		\"datatype\":\"" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getDatatype() + "\",");
			file_printer.println("		\"value\":\"" + item.getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "\"");
			file_printer.println("	},");
		}
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		if (prefix_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"prefixnamespace\":[");
			for(int i=0; i< prefix_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"prefix\":\"" + prefix_list.get(i).getPrefix() + "\",");
				file_printer.println("			\"URI\":\"" + prefix_list.get(i).getURI() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		if(attr_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"adapterattribute\":[");
			for(int i=0; i<attr_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + attr_list.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + attr_list.get(i).getNamespace() + "\",");
				file_printer.println("			\"value\":\"" + ((attr_list.get(i).getValue() == null)? "" : attr_list.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		int count = 0;
		if (tab_attr_list.size() > 0){
			for(int i=0; i< tab_attr_list.size(); i++){
				List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
				for(int j=0; j<tabrow.size(); j++){
					List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
					for(int k=0; k<cell.size(); k++){
						file_printer.println(",");
						if(count == 0){
							file_printer.println("	\"adaptertableattribute\":[");
						}
						count++;
						file_printer.println("		{");
						file_printer.println("			\"tablename\":\"" + tab_attr_list.get(i).getName() + "\",");
						file_printer.println("			\"tablenamespace\":\"" + tab_attr_list.get(i).getNamespace() + "\",");
						file_printer.println("			\"column\":\"" + cell.get(k).getColumnName() + "\",");
						file_printer.println("			\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("		}");
					}
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("	]");
			}
		}

		file_printer.println();
		file_printer.print("}");		
		file_printer.close();
	}
	
	public void export_DirectConnection() throws Exception{
		System.out.println("generating Direct Connections ...");
		
		DirectConnectionInService service = new DirectConnectionInService();
		DirectConnectionIn port = service.getDirectConnectionIn_Port();
		
		set_security((BindingProvider)port,"/DirectConnectionInService/DirectConnectionInImplBean");
		
		DirectConnectionQueryIn input1 = new DirectConnectionQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setDirectConnectionID(null);
		DirectConnectionQueryOut output1 = port.query(input1);
		
		List<MessageHeaderID> list_temp = output1.getDirectConnectionID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate Receiver agreement files
		File temp_file = new File ("result/direct_connection");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		DirectConnectionReadIn input2 = new DirectConnectionReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getDirectConnectionID();
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			DirectConnectionReadOut result_item = port.read(input2);
			input2.getDirectConnectionID().clear();
			
			List<DirectConnection> result_element = result_item.getDirectConnection();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getDirectConnectionID();
				System.out.println("	generating Direct Connection " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName() + "," + item.getReceiverPartyID() + "," + item.getReceiverComponentID());
				export_DirectConnection_item_json(result_element.get(j), file_no);
				export_DirectConnection_item(result_element.get(j), file_no++);
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/direct_connection/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,directconnection_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=DirectConnection\"  onclick=\"DoMenu('DirectConnection')\">Direct Connection</a>");
		file_link.println("					<ul id=\"DirectConnection\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"direct_connection/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"direct_connection/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/directconnection.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"DirectConnection\":{");
		file_json.print("		\"name\":\"Direct Connection\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_DirectConnection_item(DirectConnection item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/direct_connection/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		int index_no;
		print_message_header(file_printer, "Direct Connection", item.getDirectConnectionID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(3,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(3,2);\">Adapter Table Attributes</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(3,3);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Content</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver Channel</th>");
		index_no = channel_table.indexOf((item.getCommunicationChannel().getPartyID() + "|" + item.getCommunicationChannel().getComponentID() + "|" + item.getCommunicationChannel().getChannelID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("							<td>" + print_table_cell(item.getCommunicationChannel().getChannelID()) + "</td>");
		else
			file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + item.getCommunicationChannel().getChannelID() + "</a></td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Receiver Interface Name</th>");
		file_printer.println("							<td>" + item.getReceiverInterface().getName() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Receiver Interface Namespace</th>");
		file_printer.println("							<td>" + item.getReceiverInterface().getNamespace() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Adapter Specific Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");

		print_admin_data(file_printer, 3, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void export_DirectConnection_item_json(DirectConnection item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/direct_connection/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getDirectConnectionID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getDirectConnectionID().getSenderComponentID() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getDirectConnectionID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getDirectConnectionID().getReceiverComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getDirectConnectionID().getInterfaceName() + "\",");
		file_printer.println("	\"interfacenamespace\":\"" + item.getDirectConnectionID().getInterfaceNamespace() + "\",");
		file_printer.println("	\"channelparty\":\"" + item.getCommunicationChannel().getPartyID() + "\",");
		file_printer.println("	\"channelcomponent\":\"" + item.getCommunicationChannel().getComponentID() + "\",");
		file_printer.println("	\"channel\":\"" + item.getCommunicationChannel().getChannelID() + "\",");
		file_printer.println("	\"receiverinterface\":\"" + item.getReceiverInterface().getName() + "\",");
		file_printer.println("	\"receiverinterfacenamespace\":\"" + item.getReceiverInterface().getNamespace() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericProperty> attr_list = item.getAdapterSpecificAttribute();
		if(attr_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"adapterattribute\":[");
			for(int i=0; i<attr_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"name\":\"" + attr_list.get(i).getName() + "\",");
				file_printer.println("			\"namespace\":\"" + attr_list.get(i).getNamespace() + "\",");
				file_printer.println("			\"value\":\"" + ((attr_list.get(i).getValue() == null)? "" : attr_list.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<GenericPropertyTable> tab_attr_list = item.getAdapterSpecificTableAttribute();
		int count = 0;
		if (tab_attr_list.size() > 0){
			for(int i=0; i< tab_attr_list.size(); i++){
				List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
				for(int j=0; j<tabrow.size(); j++){
					List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
					for(int k=0; k<cell.size(); k++){
						file_printer.println(",");
						if(count == 0){
							file_printer.println("	\"adaptertableattribute\":[");
						}
						count++;
						file_printer.println("		{");
						file_printer.println("			\"tablename\":\"" + tab_attr_list.get(i).getName() + "\",");
						file_printer.println("			\"tablenamespace\":\"" + tab_attr_list.get(i).getNamespace() + "\",");
						file_printer.println("			\"column\":\"" + cell.get(k).getColumnName() + "\",");
						file_printer.println("			\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("		}");
					}
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("	]");
			}
		}

		file_printer.println();
		file_printer.print("}");		
		file_printer.close();
	}
	
	public void export_IntegratedConfiguration() throws Exception{
		System.out.println("generating Integrated Configurations ...");
		
		printStream_for_IntegratedConfiguration=new PrintStream(new FileOutputStream("result/IntegratedConfiguration.csv"));
		printStream_for_IntegratedConfiguration.println(
				"#SenderPartyID,"+"#SenderComponentID,"+"#SenderInterfaceName,"+"#SenderInterfaceNamespace,"
				+"#SenderChannelPrimaryKey,"+"#SenderChannelID,"+"#SenderAdapterType,"
				+"#ReceiverPartyID,"+"#ReceiverComponentID,"+"#ReceiverInterfaceName,"+"#ReceiverInterfaceNamespace,"
				+"#ReceiverChannelPrimaryKey,"+"#ReceiverChannelID,"+"#ReceiverAdapterType");
	

		IntegratedConfigurationInService service = new IntegratedConfigurationInService();
		IntegratedConfigurationIn port = service.getIntegratedConfigurationIn_Port();
		
		set_security((BindingProvider)port,"/IntegratedConfigurationInService/IntegratedConfigurationInImplBean");
		
		IntegratedConfigurationQueryIn input1 = new IntegratedConfigurationQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setIntegratedConfigurationID(null);
		IntegratedConfigurationQueryOut output1;
		try {
			output1 = port.query(input1);
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Integrated Configurations, maybe current PI version doesn't support");
			return;
		}
		
		integrated_configuration_support = true;
		List<MessageHeaderID> list_temp = output1.getIntegratedConfigurationID();
		Collections.sort(list_temp, MessageHeaderCompare.getinstance());
		List<MessageHeaderID> list = new ArrayList<MessageHeaderID>();
		
		// generate Receiver agreement files
		File temp_file = new File ("result/integrated_configuration");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		IntegratedConfigurationReadIn input2 = new IntegratedConfigurationReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<MessageHeaderID> input_list = input2.getIntegratedConfigurationID();
		for (int i=0;i<list_temp.size();i++){
			MessageHeaderID item = list_temp.get(i);
			MessageHeaderID element = new MessageHeaderID();
			element.setSenderPartyID(item.getSenderPartyID());
			element.setSenderComponentID(item.getSenderComponentID());
			element.setInterfaceName(item.getInterfaceName());
			element.setInterfaceNamespace(item.getInterfaceNamespace());
			element.setReceiverPartyID(item.getReceiverPartyID());
			element.setReceiverComponentID(item.getReceiverComponentID());
			input_list.add(element);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			IntegratedConfigurationReadOut result_item = port.read(input2);
			input2.getIntegratedConfigurationID().clear();
			
			List<IntegratedConfiguration> result_element = result_item.getIntegratedConfiguration();
			for (int j=0; j<result_element.size(); j++){
				item = result_element.get(j).getIntegratedConfigurationID();
				System.out.println("	generating Integrated Configuration " + item.getSenderPartyID() + "," + item.getSenderComponentID() + "," + item.getInterfaceName() + "," + item.getReceiverPartyID() + "," + item.getReceiverComponentID());
				export_IntegratedConfiguration_item_json(result_element.get(j), file_no);
				export_IntegratedConfiguration_item(result_element.get(j), file_no++);
				list.add(item);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/integrated_configuration/list.html");
		PrintStream file_printer =new PrintStream(out);
		generate_message_header_file(file_printer,integratedconfiguration_table,list);
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=IntegratedConfiguration\"  onclick=\"DoMenu('IntegratedConfiguration')\">Integrated Configuration</a>");
		file_link.println("					<ul id=\"IntegratedConfiguration\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"integrated_configuration/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"integrated_configuration/" + i + ".html\" target=\"a2\">" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "</a></li>");
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/integratedconfiguration.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"IntegratedConfiguration\":{");
		file_json.print("		\"name\":\"Integrated Configuration\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.println("			{");
				file_json.println("				\"name\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"senderparty\":\"" + list.get(i).getSenderPartyID() + "\",");
				file_json.println("				\"sendercomponent\":\"" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"interface\":\"" + list.get(i).getInterfaceName() + "\",");
				file_json.println("				\"namespace\":\"" + list.get(i).getInterfaceNamespace() + "\",");
				file_json.println("				\"receiverparty\":\"" + list.get(i).getReceiverPartyID() + "\",");
				file_json.println("				\"receivercomponent\":\"" + list.get(i).getReceiverComponentID() + "\",");
				file_json.println("				\"sender\":\"" + list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "\",");
				file_json.println("				\"receiver\":\"" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID() + "\"");
				file_json.print("			}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
		printStream_for_IntegratedConfiguration.close();
		file_printer_of_log.println(new java.sql.Timestamp(System.currentTimeMillis())+" end of export_IntegratedConfiguration");
	}
	
	public void export_IntegratedConfiguration_item(IntegratedConfiguration item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/integrated_configuration/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		int index_no;
		print_message_header(file_printer, "Integrated Configuration", item.getIntegratedConfigurationID());
		file_printer.println("		<table border=\"1\">");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");
		file_printer.println("		<br />");
		
		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(5,1);\">Inbound Processing</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(5,2);\">Receiver</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(5,3);\">Receiver Interfaces</a></li>");
		file_printer.println("					<li id=\"current4\"><a href=\"#\" onclick=\"change_option(5,4);\">Outbound Processing</a></li>");
		file_printer.println("					<li id=\"current5\"><a href=\"#\" onclick=\"change_option(5,5);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<iframe name=\"b1\" src=\"" + file_no + "a.html\" width=\"1500px\" height=\"900\"></iframe>");
		generate_inboundprocess_file("integrated_configuration/" + file_no + "a",item.getInboundProcessing());
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<iframe name=\"b2\" src=\"" + file_no + "b.html\" width=\"1500px\" height=\"900\"></iframe>");
		generate_receivers_file("integrated_configuration/" + file_no + "b",item.getReceivers(),item.getPrefixNamespaceMapping());
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Type</th>");
		file_printer.println("							<th>Party</th>");
		file_printer.println("							<th>Communication Component</th>");
		file_printer.println("							<th>QoS</th>");
		file_printer.println("						</tr>");
		List<ReceiverInterfaces> receiver_list = item.getReceiverInterfaces();
		for (int i=0; i<receiver_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td><a href=\"" + file_no + "c_" + i + ".html\" style=\"color:blue\" target=\"b3\">Communication Component</a></td>");
			generate_interface_file("integrated_configuration/" + file_no + "c_" + i,receiver_list.get(i).getReceiverInterfaceRule(),item.getPrefixNamespaceMapping(), file_no);
			index_no = party_table.indexOf(receiver_list.get(i).getReceiver().getPartyID());
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(receiver_list.get(i).getReceiver().getPartyID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + receiver_list.get(i).getReceiver().getPartyID() + "</a></td>");
			index_no = component_table.indexOf((receiver_list.get(i).getReceiver().getPartyID() + "|" + receiver_list.get(i).getReceiver().getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("						<td>" + print_table_cell(receiver_list.get(i).getReceiver().getComponentID()) + "</td>");
			else
				file_printer.println("						<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + receiver_list.get(i).getReceiver().getComponentID() + "</a></td>");
			try{
				QualityOfService interface_qos = receiver_list.get(i).getQualityOfService();
				if (interface_qos != null){
					file_printer.println("						<td>" + print_table_cell(interface_qos.value()) + "</td>");
				} else{
					file_printer.println("						<td>" + print_table_cell("Not Support") + "</td>");
				}
			}catch (javax.xml.ws.WebServiceException e){
				e.printStackTrace();
				System.out.println("skip Integrated Configurations receiver interface qos, maybe current PI version doesn't support");
				file_printer.println("						<td>" + print_table_cell("Not Support") + "</td>");
			}
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<iframe name=\"b3\" src=\"\" width=\"1500px\" height=\"900\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content4\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Party</th>");
		file_printer.println("							<th>Component</th>");
		file_printer.println("							<th>Interface Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("						</tr>");
		List<OutboundProcessing> outbound_list = item.getOutboundProcessing();
		for (int i=0; i<outbound_list.size(); i++){
			file_printer.println("						<tr>");
			index_no = party_table.indexOf(outbound_list.get(i).getReceiver().getPartyID());
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(outbound_list.get(i).getReceiver().getPartyID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + outbound_list.get(i).getReceiver().getPartyID() + "</a></td>");
			index_no = component_table.indexOf((outbound_list.get(i).getReceiver().getPartyID() + "|" + outbound_list.get(i).getReceiver().getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("						<td>" + print_table_cell(outbound_list.get(i).getReceiver().getComponentID()) + "</td>");
			else
				file_printer.println("						<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + outbound_list.get(i).getReceiver().getComponentID() + "</a></td>");

			file_printer.println("							<td><a href=\"" + file_no + "d_" + i + ".html\" style=\"color:blue\" target=\"b4\">" + outbound_list.get(i).getReceiverInterface().getName() + "</a></td>");
			generate_outboundprocess_file("integrated_configuration/" + file_no + "d_" + i,outbound_list.get(i), item.getPrefixNamespaceMapping());
			
			String channel_properties_for_sender=changeNullToEmptyString(item.getInboundProcessing().getCommunicationChannel().getPartyID())+connector
					+changeNullToEmptyString(item.getInboundProcessing().getCommunicationChannel().getComponentID())+connector
					+changeNullToEmptyString(item.getInboundProcessing().getCommunicationChannel().getChannelID());
			
			String channel_properties_for_receiver=changeNullToEmptyString(outbound_list.get(i).getCommunicationChannel().getPartyID())+connector
					+changeNullToEmptyString(outbound_list.get(i).getCommunicationChannel().getComponentID())+connector
					+changeNullToEmptyString(outbound_list.get(i).getCommunicationChannel().getChannelID());
			
			
			printStream_for_IntegratedConfiguration.println(
					item.getIntegratedConfigurationID().getSenderPartyID()+","
					+item.getIntegratedConfigurationID().getSenderComponentID()+","
					+item.getIntegratedConfigurationID().getInterfaceName()+","
					+item.getIntegratedConfigurationID().getInterfaceNamespace()+","
					+channel_properties_for_sender+","
					+item.getInboundProcessing().getCommunicationChannel().getChannelID()+","
					+map_Channel_ID_to_Adapter_type.get(channel_properties_for_sender)+","
					
					+outbound_list.get(i).getReceiver().getPartyID()+","
					+outbound_list.get(i).getReceiver().getComponentID()+","
					+outbound_list.get(i).getReceiverInterface().getName()+","
					+outbound_list.get(i).getReceiverInterface().getNamespace()+","
					+channel_properties_for_receiver+","
					+outbound_list.get(i).getCommunicationChannel().getChannelID()+","
					+map_Channel_ID_to_Adapter_type.get(channel_properties_for_receiver)
			);	
			
			
			
			
			file_printer.println("						<td>" + print_table_cell(outbound_list.get(i).getReceiverInterface().getNamespace()) + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<iframe name=\"b4\" src=\"\" width=\"1500px\" height=\"900\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 5, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		
		
	
		
		file_printer.close();
	}
	
	public void export_IntegratedConfiguration_item_json(IntegratedConfiguration item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/integrated_configuration/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"senderparty\":\"" + item.getIntegratedConfigurationID().getSenderPartyID() + "\",");
		file_printer.println("	\"sendercomponent\":\"" + item.getIntegratedConfigurationID().getSenderComponentID() + "\",");
		file_printer.println("	\"receiverparty\":\"" + item.getIntegratedConfigurationID().getReceiverPartyID() + "\",");
		file_printer.println("	\"receivercomponent\":\"" + item.getIntegratedConfigurationID().getReceiverComponentID() + "\",");
		file_printer.println("	\"interface\":\"" + item.getIntegratedConfigurationID().getInterfaceName() + "\",");
		file_printer.println("	\"interfacenamespace\":\"" + item.getIntegratedConfigurationID().getInterfaceNamespace() + "\",");

		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");

		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<PrefixNamespaceMapping> prefix_list = item.getPrefixNamespaceMapping();
		if (prefix_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"prefixnamespace\":[");
			for(int i=0; i< prefix_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"prefix\":\"" + prefix_list.get(i).getPrefix() + "\",");
				file_printer.println("			\"URI\":\"" + prefix_list.get(i).getURI() + "\"");
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		file_printer.println(",");
		file_printer.println("	\"inboundprocessing\":{");
		file_printer.println("		\"channelparty\":\"" + item.getInboundProcessing().getCommunicationChannel().getPartyID() + "\",");
		file_printer.println("		\"channelcomponent\":\"" + item.getInboundProcessing().getCommunicationChannel().getComponentID() + "\",");
		file_printer.println("		\"channel\":\"" + item.getInboundProcessing().getCommunicationChannel().getChannelID() + "\",");
		file_printer.print("		\"schemavalid\":\"" + item.getInboundProcessing().isSchemaValidationIndicator() + "\"");
		List<String> users = item.getInboundProcessing().getAssignedUser();
		if(users.size() > 0){
			file_printer.println(",");
			file_printer.println("		\"assigneduser\":[");
			for(int i=0; i<users.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("			{\"name\":\"" + users.get(i) + "\"}");
			}
			file_printer.println();
			file_printer.print("		]");
		}

		List<GenericProperty> attr_list = item.getInboundProcessing().getAdapterSpecificAttribute();
		if(attr_list.size() > 0){
			file_printer.println(",");
			file_printer.println("		\"adapterattribute\":[");
			for(int i=0; i<attr_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("			{");
				file_printer.println("				\"name\":\"" + attr_list.get(i).getName() + "\",");
				file_printer.println("				\"namespace\":\"" + attr_list.get(i).getNamespace() + "\",");
				file_printer.println("				\"value\":\"" + ((attr_list.get(i).getValue() == null)? "" : attr_list.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("			}");
			}
			file_printer.println();
			file_printer.print("		]");
		}
		
		List<GenericPropertyTable> tab_attr_list = item.getInboundProcessing().getAdapterSpecificTableAttribute();
		int count = 0;
		if (tab_attr_list.size() > 0){
			for(int i=0; i< tab_attr_list.size(); i++){
				List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
				for(int j=0; j<tabrow.size(); j++){
					List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
					for(int k=0; k<cell.size(); k++){
						file_printer.println(",");
						if(count == 0){
							file_printer.println("		\"tabparameter\":[");
						}
						count++;
						file_printer.println("			{");
						file_printer.println("				\"tablename\":\"" + tab_attr_list.get(i).getName() + "\",");
						file_printer.println("				\"tablenamespace\":\"" + tab_attr_list.get(i).getNamespace() + "\",");
						file_printer.println("				\"column\":\"" + cell.get(k).getColumnName() + "\",");
						file_printer.println("				\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("			}");
					}
				}
			}
			if(count>0){
				file_printer.println();
				file_printer.print("		]");
			}
		}
		
		file_printer.println();
		file_printer.println("	},");
		file_printer.println("	\"receiver\":{");
		file_printer.print("		\"noreceiver\":\"" + item.getReceivers().getNoReceiverBehaviour() + "\"");
		CommunicationComponentID ext = item.getReceivers().getNoReceiverReceiver();
		if (ext != null){
			file_printer.println(",");
			file_printer.println("		\"noreceiverparty\":\"" + ext.getPartyID() + "\",");
			file_printer.print("		\"noreceivercomponent\":\"" + ext.getComponentID() + "\"");
		}
		
		try{
			List<ReceiverDeterminationMapping> operation_list = item.getReceivers().getDynamicReceiverRule();
			if (operation_list!= null && operation_list.size() > 0){
				file_printer.println(",");
				file_printer.println("		\"extend\":[");
				for(int i=0; i< operation_list.size(); i++){
					if (i>0)
						file_printer.println(",");
					file_printer.println("			{");
					file_printer.println("				\"operation\":\"" + operation_list.get(i).getOperation() + "\",");
					file_printer.println("				\"mappingname\":\"" + operation_list.get(i).getMapping().getName() + "\",");
					file_printer.print("				\"mappingnamespace\":\"" + operation_list.get(i).getMapping().getNamespace() + "\"");
					if(operation_list.get(i).getMappingParamters()!=null){
						file_printer.println(",");
						file_printer.println("				\"mappingparameter\":[");
						boolean empty = true;
						
						List<RestrictedGenericProperty> str_para_list = operation_list.get(i).getMappingParamters().getString();
						for(int j=0; j<str_para_list.size();j++){
							if(j==0 && !empty){
								file_printer.println(",");
							}
							empty = false;
							if (j>0)
								file_printer.println(",");
							file_printer.println("					{");
							file_printer.println("						\"type\":\"String\",");
							file_printer.println("						\"name\":\"" + str_para_list.get(j).getName() + "\",");
							file_printer.println("						\"value\":\"" + ((str_para_list.get(j).getValue() == null)? "" : str_para_list.get(j).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.print("					}");
						}
						
						List<IntegerProperty> int_para_list = operation_list.get(i).getMappingParamters().getInteger();
						for(int j=0; j<int_para_list.size();j++){
							if(j==0 && !empty){
								file_printer.println(",");
							}
							empty = false;
							if (j>0)
								file_printer.println(",");
							file_printer.println("					{");
							file_printer.println("						\"type\":\"Integer\",");
							file_printer.println("						\"name\":\"" + int_para_list.get(j).getName() + "\",");
							file_printer.println("						\"value\":\"" + int_para_list.get(j).getValue() + "\"");
							file_printer.print("					}");
						}
						
						List<ChannelProperty> chan_para_list = operation_list.get(i).getMappingParamters().getChannel();
						for(int j=0; j<chan_para_list.size();j++){
							if(j==0 && !empty){
								file_printer.println(",");
							}
							empty = false;
							if (j>0)
								file_printer.println(",");
							file_printer.println("					{");
							file_printer.println("						\"type\":\"Channel\",");
							file_printer.println("						\"name\":\"" + chan_para_list.get(j).getName() + "\",");
							file_printer.println("						\"value\":{");
							file_printer.println("							\"party\":\"" + chan_para_list.get(j).getValue().getPartyID() + "\",");
							file_printer.println("							\"component\":\"" + chan_para_list.get(j).getValue().getComponentID() + "\",");
							file_printer.println("							\"channel\":\"" + chan_para_list.get(j).getValue().getChannelID() + "\"");
							file_printer.println("						}");
							file_printer.print("					}");
						}
						
						file_printer.println();
						file_printer.print("				]");
					}
					file_printer.println();
					file_printer.print("			}");
				}
				file_printer.println();
				file_printer.print("		]");
			}
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Integrated Configurations dynamic receiver, maybe current PI version doesn't support");
		}
		
		List<IntegratedConfigurationReceiverRule> local_rule_list = item.getReceivers().getReceiverRule();
		if(local_rule_list.size() > 0){
			file_printer.println(",");
			file_printer.println("		\"localrule\":[");
			for(int i=0; i<local_rule_list.size();i++){
				if(i > 0)
					file_printer.println(",");
				file_printer.println("			{");
				boolean hasoperation = false;
				if(local_rule_list.get(i).getOperation()!=null){
					file_printer.print("				\"operation\":\"" + local_rule_list.get(i).getOperation() + "\"");
					hasoperation = true;
				}
				
				if (local_rule_list.get(i).getCondition()!=null && local_rule_list.get(i).getCondition().getAtomicConditionBlock() != null && local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() > 0){
					if (hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("				\"condition\":[");
					List<AtomicConditionBlock> cond_block = local_rule_list.get(i).getCondition().getAtomicConditionBlock();
					for (int k=0; k<cond_block.size(); k++){
						if (k>0)
							file_printer.println(",");
						file_printer.println("					{");
						file_printer.println("						\"or\":[");
						List<AtomicCondition> cond_atomic = cond_block.get(k).getAtomicCondition();
						for (int n=0; n<cond_atomic.size(); n++){
							if (n>0)
								file_printer.println(",");
							file_printer.println("							{");
							file_printer.println("								\"left\":{");
							file_printer.println("									\"type\":\"" + cond_atomic.get(n).getLeftExtractor().getTypeID() + "\",");
							file_printer.println("									\"contextname\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectName() + "\",");
							file_printer.println("									\"contextnamespace\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("									\"datatype\":\"" + cond_atomic.get(n).getLeftExtractor().getDatatype() + "\",");
							file_printer.println("									\"value\":\"" + ((cond_atomic.get(n).getLeftExtractor().getValue() == null)? "" : cond_atomic.get(n).getLeftExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("								},");
							file_printer.println("								\"operator\":\"" + cond_atomic.get(n).getOperator() + "\",");
							file_printer.println("								\"right\":{");
							file_printer.println("									\"type\":\"" + cond_atomic.get(n).getRightExtractor().getTypeID() + "\",");
							file_printer.println("									\"contextname\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectName() + "\",");
							file_printer.println("									\"contextnamespace\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectNamespace() + "\",");
							file_printer.println("									\"datatype\":\"" + cond_atomic.get(n).getRightExtractor().getDatatype() + "\",");
							file_printer.println("									\"value\":\"" + ((cond_atomic.get(n).getRightExtractor().getValue() == null)? "" : cond_atomic.get(n).getRightExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
							file_printer.println("								}");
							file_printer.print("							}");
						}
						file_printer.println();
						file_printer.println("						]");
						file_printer.print("					}");
					}
					file_printer.println();
					file_printer.print("				]");
				}
				
				List<CommunicationComponentID> receiver_list = local_rule_list.get(i).getReceiver();
				if(receiver_list.size()>0){
					if (hasoperation)
						file_printer.println(",");
					hasoperation = true;
					file_printer.println("				\"receiver\":[");
					for(int j=0; j< receiver_list.size(); j++){
						if (j>0)
							file_printer.println(",");
						file_printer.println("					{");
						file_printer.println("						\"party\":\"" + receiver_list.get(j).getPartyID() + "\",");
						file_printer.println("						\"component\":\"" + receiver_list.get(j).getComponentID() + "\"");
						file_printer.print("					}");
					}
					file_printer.println();
					file_printer.print("				]");
				}
				
				file_printer.println();
				file_printer.print("			}");
			}
			file_printer.println();
			file_printer.print("		]");
		}
		file_printer.println();
		file_printer.print("	}");

		List<ReceiverInterfaces> recv_inter_list = item.getReceiverInterfaces();
		if(recv_inter_list.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"receiverinterface\":[");
			for (int i=0; i< recv_inter_list.size(); i++){
				if(i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"party\":\"" + recv_inter_list.get(i).getReceiver().getPartyID() + "\",");
				file_printer.print("			\"component\":\"" + recv_inter_list.get(i).getReceiver().getComponentID() + "\"");
				try{
					QualityOfService interface_qos = recv_inter_list.get(i).getQualityOfService();
					if (interface_qos != null){
						file_printer.println(",");
						file_printer.print("			\"qos\":\"" + interface_qos.value() + "\"");
					}
				}catch (javax.xml.ws.WebServiceException e){
					e.printStackTrace();
					System.out.println("skip Integrated Configurations receiver interface qos, maybe current PI version doesn't support");
				}
				
				List<IntegratedConfigurationReceiverInterfaceRule> recv_interrule = recv_inter_list.get(i).getReceiverInterfaceRule();
				if(recv_interrule.size()>0){
					file_printer.println(",");
					file_printer.println("			\"interfacerule\":[");
					for(int j=0; j<recv_interrule.size(); j++){
						if(j>0)
							file_printer.println(",");
						boolean needcomma = false;
						file_printer.println("				{");
						if(recv_interrule.get(j).getOperation()!=null){
							if(needcomma)
								file_printer.println(",");
							needcomma = true;
							file_printer.print("					\"operation\":\"" + recv_interrule.get(j).getOperation() + "\"");
						}
						
						if(recv_interrule.get(j).getMapping()!=null){
							if(needcomma)
								file_printer.println(",");
							needcomma = true;
							file_printer.println("					\"mapping\":{");
							file_printer.println("						\"name\":\"" + recv_interrule.get(j).getMapping().getName() + "\",");
							file_printer.println("						\"namespace\":\"" + recv_interrule.get(j).getMapping().getNamespace() + "\"");
							file_printer.print("					}");
						}
						
						try{
							if(recv_interrule.get(j).getMappingParameters()!=null){
								if(needcomma)
									file_printer.println(",");
								needcomma = true;
								file_printer.println("					\"mappingparameter\":[");
								boolean empty = true;
								
								List<RestrictedGenericProperty> str_para_list = recv_interrule.get(j).getMappingParameters().getString();
								for(int k=0; k<str_para_list.size();k++){
									if(k==0 && !empty){
										file_printer.println(",");
									}
									empty = false;
									if (k>0)
										file_printer.println(",");
									file_printer.println("						{");
									file_printer.println("							\"type\":\"String\",");
									file_printer.println("							\"name\":\"" + str_para_list.get(k).getName() + "\",");
									file_printer.println("							\"value\":\"" + ((str_para_list.get(k).getValue() == null)? "" : str_para_list.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
									file_printer.print("						}");
								}
								
								List<IntegerProperty> int_para_list = recv_interrule.get(j).getMappingParameters().getInteger();
								for(int k=0; k<int_para_list.size();k++){
									if(k==0 && !empty){
										file_printer.println(",");
									}
									empty = false;
									if (k>0)
										file_printer.println(",");
									file_printer.println("						{");
									file_printer.println("							\"type\":\"Integer\",");
									file_printer.println("							\"name\":\"" + int_para_list.get(k).getName() + "\",");
									file_printer.println("							\"value\":\"" + int_para_list.get(k).getValue() + "\"");
									file_printer.print("						}");
								}
								
								List<ChannelProperty> chan_para_list = recv_interrule.get(j).getMappingParameters().getChannel();
								for(int k=0; k<chan_para_list.size();k++){
									if(k==0 && !empty){
										file_printer.println(",");
									}
									empty = false;
									if (k>0)
										file_printer.println(",");
									file_printer.println("						{");
									file_printer.println("							\"type\":\"Channel\",");
									file_printer.println("							\"name\":\"" + chan_para_list.get(k).getName() + "\",");
									file_printer.println("							\"value\":{");
									file_printer.println("								\"party\":\"" + chan_para_list.get(k).getValue().getPartyID() + "\",");
									file_printer.println("								\"component\":\"" + chan_para_list.get(k).getValue().getComponentID() + "\",");
									file_printer.println("								\"channel\":\"" + chan_para_list.get(k).getValue().getChannelID() + "\"");
									file_printer.println("							}");
									file_printer.print("						}");
								}
								
								file_printer.println();
								file_printer.print("					]");
							}
						} catch (javax.xml.ws.WebServiceException e){
							e.printStackTrace();
							System.out.println("skip Integrated Configurations mapping parameter, maybe current PI version doesn't support");
						}
						
						List<DesignObjectID> interface_list = recv_interrule.get(j).getInterface();
						if(interface_list.size() > 0){
							if(needcomma)
								file_printer.println(",");
							needcomma = true;
							file_printer.println("					\"interface\":[");
							for (int k=0; k< interface_list.size(); k++){
								if(k>0)
									file_printer.println(",");
								file_printer.println("						{");
								file_printer.println("							\"name\":\"" + interface_list.get(k).getName() + "\",");
								file_printer.println("							\"namespace\":\"" + interface_list.get(k).getNamespace() + "\"");
								file_printer.print("						}");
							}
							file_printer.println();
							file_printer.print("					]");
						}

						if (recv_interrule.get(j).getCondition()!=null && recv_interrule.get(j).getCondition().getAtomicConditionBlock() != null && recv_interrule.get(j).getCondition().getAtomicConditionBlock().size() > 0){
							if (needcomma)
								file_printer.println(",");
							needcomma = true;
							file_printer.println("					\"condition\":[");
							List<AtomicConditionBlock> cond_block = recv_interrule.get(j).getCondition().getAtomicConditionBlock();
							for (int k=0; k<cond_block.size(); k++){
								if (k>0)
									file_printer.println(",");
								file_printer.println("						{");
								file_printer.println("							\"or\":[");
								List<AtomicCondition> cond_atomic = cond_block.get(k).getAtomicCondition();
								for (int n=0; n<cond_atomic.size(); n++){
									if (n>0)
										file_printer.println(",");
									file_printer.println("								{");
									file_printer.println("									\"left\":{");
									file_printer.println("										\"type\":\"" + cond_atomic.get(n).getLeftExtractor().getTypeID() + "\",");
									file_printer.println("										\"contextname\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectName() + "\",");
									file_printer.println("										\"contextnamespace\":\"" + cond_atomic.get(n).getLeftExtractor().getContextObjectNamespace() + "\",");
									file_printer.println("										\"datatype\":\"" + cond_atomic.get(n).getLeftExtractor().getDatatype() + "\",");
									file_printer.println("										\"value\":\"" + ((cond_atomic.get(n).getLeftExtractor().getValue() == null)? "" : cond_atomic.get(n).getLeftExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
									file_printer.println("									},");
									file_printer.println("									\"operator\":\"" + cond_atomic.get(n).getOperator() + "\",");
									file_printer.println("									\"right\":{");
									file_printer.println("										\"type\":\"" + cond_atomic.get(n).getRightExtractor().getTypeID() + "\",");
									file_printer.println("										\"contextname\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectName() + "\",");
									file_printer.println("										\"contextnamespace\":\"" + cond_atomic.get(n).getRightExtractor().getContextObjectNamespace() + "\",");
									file_printer.println("										\"datatype\":\"" + cond_atomic.get(n).getRightExtractor().getDatatype() + "\",");
									file_printer.println("										\"value\":\"" + ((cond_atomic.get(n).getRightExtractor().getValue() == null)? "" : cond_atomic.get(n).getRightExtractor().getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
									file_printer.println("									}");
									file_printer.print("								}");
								}
								file_printer.println();
								file_printer.println("							]");
								file_printer.print("						}");
							}
							file_printer.println();
							file_printer.print("					]");
						}
						file_printer.println();
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}				
				file_printer.println();
				file_printer.print("		}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		List<OutboundProcessing> outbound_list = item.getOutboundProcessing();
		if (outbound_list.size()>0){
			file_printer.println(",");
			file_printer.println("	\"outboundprocessing\":[");
			for(int i=0; i<outbound_list.size(); i++){
				if(i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"channelparty\":\"" + item.getOutboundProcessing().get(i).getCommunicationChannel().getPartyID() + "\",");
				file_printer.println("			\"channelcomponent\":\"" + item.getOutboundProcessing().get(i).getCommunicationChannel().getComponentID() + "\",");
				file_printer.println("			\"channel\":\"" + item.getOutboundProcessing().get(i).getCommunicationChannel().getChannelID() + "\",");
				file_printer.println("			\"party\":\"" + item.getOutboundProcessing().get(i).getReceiver().getPartyID() + "\",");
				file_printer.println("			\"component\":\"" + item.getOutboundProcessing().get(i).getReceiver().getComponentID() + "\",");
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty()!=null){
					file_printer.println("			\"headermappingsenderparty\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationParty().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency()!=null){
					file_printer.println("			\"headermappingsenderpartyagency\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartyAgency().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema()!=null){
					file_printer.println("			\"headermappingsenderpartySchema\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationPartySchema().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent()!=null){
					file_printer.println("			\"headermappingsendercomponent\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getSender().getCommunicationComponent().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty()!=null){
					file_printer.println("			\"headermappingreceiverparty\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency()!=null){
					file_printer.println("			\"headermappingreceiverpartyagency\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartyAgency().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema()!=null){
					file_printer.println("			\"headermappingreceiverpartySchema\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationPartySchema().getValue() + "\"");
					file_printer.println("			},");
				}
				if (item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent()!=null){
					file_printer.println("			\"headermappingreceivercomponent\":{");
					file_printer.println("				\"type\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent().getTypeID() + "\",");
					file_printer.println("				\"context\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent().getContextObjectName() + "\",");
					file_printer.println("				\"contextnamespace\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent().getContextObjectNamespace() + "\",");
					file_printer.println("				\"datatype\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent().getDatatype() + "\",");
					file_printer.println("				\"value\":\"" + item.getOutboundProcessing().get(i).getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "\"");
					file_printer.println("			},");
				}
				file_printer.println("			\"schemavalid\":\"" + item.getOutboundProcessing().get(i).isSchemaValidationIndicator() + "\",");
				file_printer.println("			\"interface\":\"" + item.getOutboundProcessing().get(i).getReceiverInterface().getName() + "\",");
				file_printer.print("			\"interfacenamespace\":\"" + item.getOutboundProcessing().get(i).getReceiverInterface().getNamespace() + "\"");

				attr_list = item.getOutboundProcessing().get(i).getAdapterSpecificAttribute();
				if(attr_list.size() > 0){
					file_printer.println(",");
					file_printer.println("			\"adapterattribute\":[");
					for(int j=0; j<attr_list.size(); j++){
						if (j>0)
							file_printer.println(",");
						file_printer.println("				{");
						file_printer.println("					\"name\":\"" + attr_list.get(j).getName() + "\",");
						file_printer.println("					\"namespace\":\"" + attr_list.get(j).getNamespace() + "\",");
						file_printer.println("					\"value\":\"" + ((attr_list.get(j).getValue() == null)? "" : attr_list.get(j).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
						file_printer.print("				}");
					}
					file_printer.println();
					file_printer.print("			]");
				}
				
				tab_attr_list = item.getOutboundProcessing().get(i).getAdapterSpecificTableAttribute();
				count = 0;
				if (tab_attr_list.size() > 0){
					for(int n=0; n< tab_attr_list.size(); n++){
						List<GenericTableRow> tabrow = tab_attr_list.get(n).getValueTableRow();
						for(int j=0; j<tabrow.size(); j++){
							List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
							for(int k=0; k<cell.size(); k++){
								file_printer.println(",");
								if(count == 0){
									file_printer.println("			\"tabparameter\":[");
								}
								count++;
								file_printer.println("				{");
								file_printer.println("					\"tablename\":\"" + tab_attr_list.get(n).getName() + "\",");
								file_printer.println("					\"tablenamespace\":\"" + tab_attr_list.get(n).getNamespace() + "\",");
								file_printer.println("					\"column\":\"" + cell.get(k).getColumnName() + "\",");
								file_printer.println("					\"value\":\"" + ((cell.get(k).getValue() == null)? "" : cell.get(k).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
								file_printer.print("				}");
							}
						}
					}
					if(count>0){
						file_printer.println();
						file_printer.print("			]");
					}
				}
				
				file_printer.println();
				file_printer.print("		}");
			}
			
			file_printer.println();
			file_printer.print("	]");
		}
		
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_ConfigurationScenario() throws Exception{
		System.out.println("generating Configuration Scenarios ...");
		
		ConfigurationScenarioInService service = new ConfigurationScenarioInService();
		ConfigurationScenarioIn port = service.getConfigurationScenarioIn_Port();
		set_security((BindingProvider)port,"/ConfigurationScenarioInService/ConfigurationScenarioInImplBean");
		
		ConfigurationScenarioQueryIn input1 = new ConfigurationScenarioQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setConfigurationScenarioID(null);
		ConfigurationScenarioQueryOut output1 = port.query(input1);
		
		List<String> list_temp = output1.getConfigurationScenarioID();
		Collections.sort(list_temp);
		List<String> list = new ArrayList<String>();
		
		// generate configurationscenario files
		File temp_file = new File ("result/configuration_scenario");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		ConfigurationScenarioReadIn input2 = new ConfigurationScenarioReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<String> input_list = input2.getConfigurationScenarioID();
		for (int i=0;i<list_temp.size();i++){
			String scenario_name = list_temp.get(i);
			input_list.add(scenario_name);
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			ConfigurationScenarioReadOut result_item = port.read(input2);
			input2.getConfigurationScenarioID().clear();
			
			List<ConfigurationScenario> party_array = result_item.getConfigurationScenario();
			for (int j=0; j<party_array.size(); j++){
				scenario_name = party_array.get(j).getConfigurationScenarioID();
				System.out.println("	generating Configuration Scenario " + scenario_name);
				export_ConfigurationScenario_item_json(party_array.get(j), file_no);
				export_ConfigurationScenario_item(party_array.get(j), file_no++);
				list.add(scenario_name);
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/configuration_scenario/list.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Configuration Scenario</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			file_printer.println("			<tr>");
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i) + "</a></td>");			
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=ConfigurationScenario\"  onclick=\"DoMenu('ConfigurationScenario')\">Configuration Scenario</a>");
		file_link.println("					<ul id=\"ConfigurationScenario\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"configuration_scenario/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"configuration_scenario/" + i + ".html\" target=\"a2\">" + list.get(i) + "</a></li>");			
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/configurationscenario.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"ConfigurationScenario\":{");
		file_json.print("		\"name\":\"Configuration Scenario\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.print("			{\"name\":\"" + list.get(i) + "\"}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_ConfigurationScenario_item(ConfigurationScenario item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/configuration_scenario/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Configuration Scenario</th>");
		file_printer.println("				<td>" + print_table_cell(item.getConfigurationScenarioID()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Repository Scenario</th>");
		if (item.getRepositoryScenario()==null)
			file_printer.println("				<td>&nbsp;</td>");
		else
			file_printer.println("				<td>" + print_table_cell(item.getRepositoryScenario().getName()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Repository Namespace</th>");
		if (item.getRepositoryScenario()==null)
			file_printer.println("				<td>&nbsp;</td>");
		else
			file_printer.println("				<td>" + print_table_cell(item.getRepositoryScenario().getNamespace()) + "</td>");
		file_printer.println("			</tr>");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("		</table>");		
		file_printer.println("		<br />");

		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(2,1);\">Objects</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(2,2);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Type</th>");
		file_printer.println("							<th>Object</th>");
		file_printer.println("						</tr>");
		
		List<String> party_list = item.getParty();
		for (int i=0;i<party_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Party</td>");
			int index_no = party_table.indexOf(party_list.get(i));
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(party_list.get(i)) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + party_list.get(i) + "</a></td>");
			file_printer.println("						</tr>");
		}
		
		List<CommunicationComponentID> businesscomponent_list = item.getBusinessComponent();
		for (int i=0;i<businesscomponent_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Business Component</td>");
			int index_no = component_table.indexOf((businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("						<td>" + print_table_cell(businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID()) + "</td>");
			else
				file_printer.println("						<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID() + "</a></td>");
			file_printer.println("						</tr>");
		}
	
		List<CommunicationComponentID> businesssystem_list = item.getBusinessSystem();
		for (int i=0;i<businesssystem_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Business System</td>");
			int index_no = component_table.indexOf((businesssystem_list.get(i).getPartyID() + "|" + businesssystem_list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("						<td>" + print_table_cell(businesssystem_list.get(i).getPartyID() + "|" + businesssystem_list.get(i).getComponentID()) + "</td>");
			else
				file_printer.println("						<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + businesssystem_list.get(i).getPartyID() + "|" + businesssystem_list.get(i).getComponentID() + "</a></td>");
			file_printer.println("						</tr>");
		}
		
		List<CommunicationComponentID> integrationprocess_list = item.getIntegrationProcess();
		for (int i=0;i<integrationprocess_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Integration Process</td>");
			int index_no = component_table.indexOf((integrationprocess_list.get(i).getPartyID() + "|" + integrationprocess_list.get(i).getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("						<td>" + print_table_cell(integrationprocess_list.get(i).getPartyID() + "|" + integrationprocess_list.get(i).getComponentID()) + "</td>");
			else
				file_printer.println("						<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + integrationprocess_list.get(i).getPartyID() + "|" + integrationprocess_list.get(i).getComponentID() + "</a></td>");
			file_printer.println("						</tr>");
		}
		
		List<CommunicationChannelID> communicationchannel_list = item.getCommunicationChannel();
		for (int i=0;i<communicationchannel_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Communication Channel</td>");
			int index_no = channel_table.indexOf((communicationchannel_list.get(i).getPartyID() + "|" + communicationchannel_list.get(i).getComponentID() + "|" + communicationchannel_list.get(i).getChannelID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(communicationchannel_list.get(i).getPartyID() + "|" + communicationchannel_list.get(i).getComponentID() + "|" + communicationchannel_list.get(i).getChannelID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + communicationchannel_list.get(i).getPartyID() + "|" + communicationchannel_list.get(i).getComponentID() + "|" + communicationchannel_list.get(i).getChannelID() + "</a></td>");
			file_printer.println("						</tr>");
		}
		
		List<MessageHeaderID> messageheader_list = item.getReceiverDetermination();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element=messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Receiver Determination</td>");
			int index_no = receiverdetermination_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../receiver_determination/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
			file_printer.println("						</tr>");
		}
		
		List<String> rule_list = item.getReceiverRule();
		for (int i=0;i<rule_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Receiver Rule</td>");
			int index_no = receiverrule_table.indexOf(rule_list.get(i));
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(rule_list.get(i)) + "</td>");
			else
				file_printer.println("							<td><a href=\"../receiver_rule/" + index_no + ".html\" style=\"color:blue\">" + rule_list.get(i) + "</a></td>");
			file_printer.println("						</tr>");
		}		
		
		messageheader_list = item.getInterfaceDetermination();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element = messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Interface Determination</td>");
			int index_no = interfacedetermination_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../interface_determination/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
		}		
		
		messageheader_list = item.getSenderAgreement();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element = messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Sender Agreement</td>");
			int index_no = senderagreement_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../sender_agreement/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
		}
		
		messageheader_list = item.getReceiverAgreement();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element = messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Receiver Agreement</td>");
			int index_no = receiveragreement_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../receiver_agreement/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
		}
		
		messageheader_list = item.getDirectConnection();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element = messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Direct Connection</td>");
			int index_no = directconnection_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../direct_connection/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
		}		
		
		messageheader_list = item.getIntegratedConfiguration();
		for (int i=0;i<messageheader_list.size();i++){
			MessageHeaderID element = messageheader_list.get(i);
			file_printer.println("						<tr>");
			file_printer.println("							<td>Integrated Configuration</td>");
			int index_no = integratedconfiguration_table.indexOf((element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no <0)
				file_printer.println("							<td>" + print_table_cell(element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../integrated_configuration/" + index_no + ".html\" style=\"color:blue\">" + element.getSenderPartyID() + "|" + element.getSenderComponentID() + "|" + element.getInterfaceName() + "|" + element.getInterfaceNamespace() + "|" + element.getReceiverPartyID() + "|" + element.getReceiverComponentID() + "</a></td>");
		}		
		
		List<String> processcomponent_list = item.getProcessComponent();
		for (int i=0;i<processcomponent_list.size();i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>Process Component</td>");
			file_printer.println("							<td>" + print_table_cell(processcomponent_list.get(i)) + "</td>");
			file_printer.println("						</tr>");
		}		
		
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("		</div>");
		
		print_admin_data(file_printer, 2, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
		
	}
	
	public void export_ConfigurationScenario_item_json(ConfigurationScenario item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/configuration_scenario/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"name\":\"" + item.getConfigurationScenarioID() + "\",");		
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}
		
		boolean begin = true;
		
		List<String> party_list = item.getParty();
		if (party_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< party_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Party\",");
				file_printer.println("			\"name\":\"" + party_list.get(i) + "\"");
				file_printer.print("		}");
			}
		}
		
		List<CommunicationComponentID> businesscomponent_list = item.getBusinessComponent();
		if (businesscomponent_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< businesscomponent_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Business Component\",");
				file_printer.println("			\"name\":\"" + businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		businesscomponent_list = item.getBusinessSystem();
		if (businesscomponent_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< businesscomponent_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Business System\",");
				file_printer.println("			\"name\":\"" + businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		businesscomponent_list = item.getIntegrationProcess();
		if (businesscomponent_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< businesscomponent_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Integration Process\",");
				file_printer.println("			\"name\":\"" + businesscomponent_list.get(i).getPartyID() + "|" + businesscomponent_list.get(i).getComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		List<CommunicationChannelID> channel_list = item.getCommunicationChannel();
		if (channel_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< channel_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Communication Channel\",");
				file_printer.println("			\"name\":\"" + channel_list.get(i).getPartyID() + "|" + channel_list.get(i).getComponentID() + "|" + channel_list.get(i).getChannelID() + "\"");
				file_printer.print("		}");
			}
		}
		
		
		List<String> rule_list = item.getReceiverRule();
		if (rule_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< rule_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Receiver Rule\",");
				file_printer.println("			\"name\":\"" + rule_list.get(i) + "\"");
				file_printer.print("		}");
			}
		}
		
		List<MessageHeaderID> msghead_list = item.getReceiverDetermination();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Receiver Determination\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "\"");
				file_printer.print("		}");
			}
		}
		
		msghead_list = item.getInterfaceDetermination();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Interface Determination\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "|" + msghead_list.get(i).getReceiverPartyID() + "|" + msghead_list.get(i).getReceiverComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		msghead_list = item.getSenderAgreement();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Sender Agreement\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "|" + msghead_list.get(i).getReceiverPartyID() + "|" + msghead_list.get(i).getReceiverComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		msghead_list = item.getReceiverAgreement();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Receiver Agreement\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "|" + msghead_list.get(i).getReceiverPartyID() + "|" + msghead_list.get(i).getReceiverComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		msghead_list = item.getDirectConnection();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Direct Connection\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "|" + msghead_list.get(i).getReceiverPartyID() + "|" + msghead_list.get(i).getReceiverComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		msghead_list = item.getIntegratedConfiguration();
		if (msghead_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< msghead_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Integrated Configuration\",");
				file_printer.println("			\"name\":\"" + msghead_list.get(i).getSenderPartyID() + "|" + msghead_list.get(i).getSenderComponentID() + "|" + msghead_list.get(i).getInterfaceName() + "|" + msghead_list.get(i).getReceiverPartyID() + "|" + msghead_list.get(i).getReceiverComponentID() + "\"");
				file_printer.print("		}");
			}
		}
		
		party_list = item.getProcessComponent();
		if (party_list.size() > 0){
			file_printer.println(",");
			if(begin){
				file_printer.println("	\"object\":[");
			}
			begin = false;
			for(int i=0; i< party_list.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"type\":\"Process Component\",");
				file_printer.print("			\"name\":\"" + party_list.get(i) + "\"");
				file_printer.print("		}");
			}
		}

		if(!begin){
			file_printer.println();
			file_printer.print("	]");
		}
		
		if (item.getRepositoryScenario()!=null){
			file_printer.println(",");
			file_printer.println("	\"repositoryscenario\":{");
			file_printer.println("		\"name\":\"" + item.getRepositoryScenario().getName() + "\",");
			file_printer.println("		\"namespace\":\"" + item.getRepositoryScenario().getNamespace() + "\"");
			file_printer.print("	}");
		}
		
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void export_ValueMapping() throws Exception{
		System.out.println("generating Value Mappings ...");
		
		ValueMappingInService service = new ValueMappingInService();
		ValueMappingIn port = service.getValueMappingIn_Port();
		set_security((BindingProvider)port,"/ValueMappingInService/ValueMappingInImplBean");
		
		ValueMappingQueryIn input1 = new ValueMappingQueryIn();
		input1.setAdministrativeData(null);
		input1.setDescription(null);
		input1.setGroupName(null);
		input1.setRepresentation(null);
		input1.setValueMappingID(null);
		ValueMappingQueryOut output1;

		try {
			output1 = port.query(input1);
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Value Mapping, maybe current PI version doesn't support");
			return;
		}
		
		List<String> list_temp = output1.getValueMappingID();
		List<String> list = new ArrayList<String>();
		Collections.sort(list_temp);
		
		// generate party files
		File temp_file = new File ("result/value_mapping");
		if(!temp_file.exists())
			temp_file.mkdirs();
		
		int file_no=0, count=0;
		ValueMappingReadIn input2 = new ValueMappingReadIn();
		input2.setReadContext(ReadContextCode.fromValue("Active"));
		List<String> input_list = input2.getValueMappingID();
		
		for (int i=0;i<list_temp.size();i++){
			input_list.add(list_temp.get(i));
			count++;
			if(count != bundle_size && i!=(list_temp.size()-1))
				continue;
			count=0;
			ValueMappingReadOut result_item = port.read(input2);
			input2.getValueMappingID().clear();
			
			List<ValueMapping> result_array = result_item.getValueMapping();
			for (int j=0; j<result_array.size(); j++){
				System.out.println("	generating Value Mapping " + result_array.get(j).getGroupName());
				export_ValueMapping_item_json(result_array.get(j), file_no);
				export_ValueMapping_item(result_array.get(j), file_no++);
				list.add(result_array.get(j).getGroupName());
			}
		}
		
		// generate List.html
		FileOutputStream out=new FileOutputStream("result/value_mapping/list.html");
		PrintStream file_printer =new PrintStream(out);
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Value Mapping</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			file_printer.println("			<tr>");
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i) + "</a></td>");			
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
		file_printer.close();

		//generate link.html
		file_link.println("				<li><a href=\"#Menu=ValueMapping\"  onclick=\"DoMenu('ValueMapping')\">Value Mapping</a>");
		file_link.println("					<ul id=\"ValueMapping\" class=\"collapsed\">");
		file_link.println("						<li><a href=\"value_mapping/list.html\" target=\"a2\">List</a></li>");
		for (int i=0;i<list.size();i++){
			file_link.println("						<li><a href=\"value_mapping/" + i + ".html\" target=\"a2\">" + list.get(i) + "</a></li>");			
		}
		file_link.println("					</ul>");
		file_link.println("				</li>");
		
		// generate list.json
		FileOutputStream out1=new FileOutputStream("result/link_json/valuemapping.json");
		PrintStream file_json =new PrintStream(out1);
		file_json.println("{");
		file_json.println("	\"ValueMapping\":{");
		file_json.print("		\"name\":\"Value Mapping\"");
		if(list.size()>0){
			file_json.println(",");
			file_json.println("		\"list\":[");
			for (int i=0;i<list.size();i++){
				if (i>0)
					file_json.println(",");
				file_json.print("			{\"name\":\"" + list.get(i) + "\"}");
			}
			file_json.println();
			file_json.print("		]");
		}
		file_json.println();
		file_json.println("	}");
		file_json.print("}");
		file_json.close();
	}
	
	public void export_ValueMapping_item(ValueMapping item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/value_mapping/" + file_no + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		// print up part
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Group ID</th>");
		file_printer.println("				<td>" + print_table_cell(item.getValueMappingID()) + "</td>");
		file_printer.println("			</tr>");
		List<LONGDescription> desc = item.getDescription();
		print_description(file_printer, desc);
		file_printer.println("			<tr>");
		file_printer.println("				<th>Group Name</th>");
		file_printer.println("				<td>" + print_table_cell(item.getGroupName()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
		
		file_printer.println("		<br />");

		// print lower part
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(2,1);\">Value Mapping</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(2,2);\">Admin</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Value Mapping</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Agency</th>");
		file_printer.println("							<th>Schema</th>");
		file_printer.println("							<th>Name</th>");
		file_printer.println("						</tr>");
		List<ValueMappingRepresentation> add_id = item.getRepresentation();
		for(int i=0; i< add_id.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getSchemeAgencyID()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getSchemeID()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(add_id.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		print_admin_data(file_printer, 2, item.getAdministrativeData(), item.getMasterLanguage());
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void export_ValueMapping_item_json(ValueMapping item, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/value_mapping/" + file_no + ".json");
		PrintStream file_printer =new PrintStream(out);
		
		file_printer.println("{");
		file_printer.println("	\"groupname\":\"" + item.getGroupName() + "\",");
		file_printer.println("	\"valuemappingid\":\"" + item.getValueMappingID() + "\",");
		file_printer.println("	\"response\":\"" + item.getAdministrativeData().getResponsibleUserAccountID() + "\",");
		file_printer.println("	\"lastuser\":\"" + item.getAdministrativeData().getLastChangeUserAccountID() + "\",");
		file_printer.println("	\"lasttime\":\"" + item.getAdministrativeData().getLastChangeDateTime() + "\",");
		file_printer.println("	\"folder\":\"" + item.getAdministrativeData().getFolderPathID() + "\",");
		file_printer.print("	\"language\":\"" + item.getMasterLanguage() + "\"");
		
		List<LONGDescription> desc = item.getDescription();
		if (desc.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"description\":[");
			for(int i=0; i< desc.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.print("		{\"value\":\"" + ((desc.get(i).getValue() == null)? "" : desc.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"}");
			}
			file_printer.println();
			file_printer.print("	]");
		}

		List<ValueMappingRepresentation> add_id = item.getRepresentation();
		if (add_id.size() > 0){
			file_printer.println(",");
			file_printer.println("	\"representation\":[");
			for(int i=0; i< add_id.size(); i++){
				if (i>0)
					file_printer.println(",");
				file_printer.println("		{");
				file_printer.println("			\"agency\":\"" + add_id.get(i).getSchemeAgencyID() + "\",");
				file_printer.println("			\"schema\":\"" + add_id.get(i).getSchemeID() + "\",");
				file_printer.println("			\"name\":\"" + ((add_id.get(i).getValue() == null)? "" : add_id.get(i).getValue().replace("\\", "\\\\").replace("\"", "\\\"")) + "\"");
				file_printer.print("		}");
				
			}
			file_printer.println();
			file_printer.print("	]");
		}
		file_printer.println();
		file_printer.print("}");
		
		file_printer.close();
	}
	
	public void print_link_file_header(PrintStream out) throws Exception {
		out.println("<html>");
		out.println("	<head>"); 
		out.println("		<link rel=\"stylesheet\" href=\"css/tree.css\" type=\"text/css\"/>");     
		out.println("		<script language=\"javascript\" type=\"text/javascript\" src=\"css/tree.js\"/></script>"); 
		out.println("	</head>");
		out.println("	<body>");
		out.println("		<div id=\"PARENT\">");
		out.println("			<ul id=\"nav\">");
	}
	
	public void print_link_file_footer(PrintStream out) throws Exception {
		out.println("			</ul>");
		out.println("		</div>");
		out.println("	</body>");
		out.println("</html>");
	}
	
	public void print_detail_file_header(PrintStream out) throws Exception {
		out.println("<html>");
		out.println("	<head>"); 
		out.print("		<link rel=\"stylesheet\" href=\"../css/tab.css\" type=\"text/css\"/>");
		out.print("		<script language=\"javascript\" type=\"text/javascript\" src=\"../css/tab.js\"/></script>");
		out.println("	</head>");
		out.println("	<body>");
	}
	
	public void print_detail_file_footer(PrintStream out) throws Exception {
		out.println("	</body>");
		out.println("</html>");
	}
	
	public void print_description(PrintStream out, List<LONGDescription> desc) throws Exception {
		if (desc.size() <= 0)
			return;
		for (int i = 0; i< desc.size(); i++){
			out.println("			<tr>");
			if (i==0)
				out.println("				<th rowspan=\"" + desc.size() + "\">Description</th>");
			out.println("				<td>" + print_table_cell(desc.get(i).getValue()) + "</td>");
			out.println("			</tr>");
		}
	}
	
	public void print_admin_data(PrintStream file_printer, int tab_seq, ObjectAdministrativeData data, String lang) throws Exception{
		if (tab_seq == 1)
			file_printer.println("			<div id=\"content" + tab_seq + "\" class=\"content\">");
		else
			file_printer.println("			<div id=\"content" + tab_seq + "\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Administrative Data</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Responsible User</th>");
		file_printer.println("							<td>" + print_table_cell(data.getResponsibleUserAccountID()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Last Change User</th>");
		file_printer.println("							<td>" + print_table_cell(data.getLastChangeUserAccountID()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Last Change Time</th>");
		file_printer.println("							<td>" + print_table_cell(data.getLastChangeDateTime().toString()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Folder Path</th>");
		file_printer.println("							<td>" + print_table_cell(data.getFolderPathID()) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Master Language</th>");
		file_printer.println("							<td>" + print_table_cell(lang) + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
	}
	
	public void print_sender_receiver (PrintStream file_printer, int tab_seq, String sender_receiver, List<DesignObjectID> inb_list) throws Exception{
		if (tab_seq == 1)
			file_printer.println("			<div id=\"content" + tab_seq + "\" class=\"content\">");
		else
			file_printer.println("			<div id=\"content" + tab_seq + "\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">" + sender_receiver + "</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("						</tr>");
		for(int i=0; i< inb_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(inb_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(inb_list.get(i).getNamespace()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
	}
	
	public void print_additional_identifier(PrintStream file_printer, int tab_seq, List<CommunicationComponentAdditionalIdentifier> addid_list) throws Exception{
		file_printer.println("			<div id=\"content" + tab_seq + "\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">IDoc Partner</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Schema ID</th>");
		file_printer.println("							<th>Name</th>");
		file_printer.println("						</tr>");
		for(int i=0; i< addid_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(addid_list.get(i).getSchemeID()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(addid_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
	}
	
	public void print_message_header(PrintStream file_printer, String Obj_categary, MessageHeaderID data) throws Exception{
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th colspan=\"6\">" + Obj_categary + "</th>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Sender Party</th>");
		file_printer.println("				<th>Sender Component</th>");
		file_printer.println("				<th>Interface</th>");
		file_printer.println("				<th>Namespace</th>");
		file_printer.println("				<th>Receiver Party</th>");
		file_printer.println("				<th>Receiver Component</th>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");
		int index_no = party_table.indexOf(data.getSenderPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(data.getSenderPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + data.getSenderPartyID() + "</a></td>");
		index_no = component_table.indexOf((data.getSenderPartyID() + "|" + data.getSenderComponentID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(data.getSenderComponentID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + data.getSenderComponentID() + "</a></td>");
		file_printer.println("				<td>" + data.getInterfaceName() + "</td>");
		file_printer.println("				<td>" + data.getInterfaceNamespace() + "</td>");
		index_no = party_table.indexOf(data.getReceiverPartyID());
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(data.getReceiverPartyID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\">" + data.getReceiverPartyID() + "</a></td>");
		index_no = component_table.indexOf((data.getReceiverPartyID() + "|" + data.getReceiverComponentID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("				<td>" + print_table_cell(data.getReceiverComponentID()) + "</td>");
		else
			file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\">" + data.getReceiverComponentID() + "</a></td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
	}
	
	public String print_table_cell(String input){
		if (input == null || input.length() == 0)
			return "&nbsp;";
		else
			return input;
	}
	
	public String changeNullToEmptyString(String input){
		if (input == null || input.length() == 0)
			return "";
		else
			return input;
	}	
	
	public void generate_extractor_file(String file_name , Extractor data) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");   
		file_printer.println("				<th>Type</th>");
		file_printer.println("				<td>" + print_table_cell(data.getTypeID().value()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("				<th>Value</th>");
		file_printer.println("				<td>" + print_table_cell(data.getValue()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("				<th>Data Type</th>");
		file_printer.println("				<td>" + print_table_cell(data.getDatatype()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("				<th>Context Object Name</th>");
		file_printer.println("				<td>" + print_table_cell(data.getContextObjectName()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("				<th>Context Object Namespace</th>");
		file_printer.println("				<td>" + print_table_cell(data.getContextObjectNamespace()) + "</td>");
		file_printer.println("			</tr>");
		file_printer.println("		</table>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_condition_file(String file_name , List<AtomicConditionBlock> data) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");   
		file_printer.println("				<th>OR</th>");
		file_printer.println("				<th>Left Operand</th>");
		file_printer.println("				<th>Operator</th>");
		file_printer.println("				<th>Right Operand</th>");
		file_printer.println("			</tr>");
		
		for(int i=0; i< data.size(); i++){
			List<AtomicCondition> condition_list = data.get(i).getAtomicCondition();
			for (int j=0; j<condition_list.size(); j++){
				file_printer.println("			<tr>");
				if (j==0){
					file_printer.println("			<td rowspan=\"" + condition_list.size() + "\">&nbsp;</td>");
				}
				file_printer.println("			<td><a href=\"../" + file_name + "_" + i + "_" + j + "_" + "1.html\" style=\"color:blue\" target=\"a4\">L Operand</a></td>");
				generate_extractor_file(file_name + "_" + i + "_" + j + "_" + "1", condition_list.get(j).getLeftExtractor());				
				file_printer.println("			<td>" + condition_list.get(j).getOperator() + "</td>");
				file_printer.println("			<td><a href=\"../" + file_name + "_" + i + "_" + j + "_" + "2.html\" style=\"color:blue\" target=\"a4\">R Operand</a></td>");
				generate_extractor_file(file_name + "_" + i + "_" + j + "_" + "2", condition_list.get(j).getRightExtractor());
				file_printer.println("			</tr>");
			}
		}		
		file_printer.println("		</table>");
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_operation_mapping_file(String file_name , InterfaceDeterminationRule data) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");   
		file_printer.println("				<th>Operation Mapping Name</th>");
		file_printer.println("				<th>Operation Mapping Namespace</th>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");   
		file_printer.println("				<td>" + data.getMapping().getName() + "</td>");
		file_printer.println("				<td>" + data.getMapping().getNamespace() + "</td>");
		file_printer.println("			</tr>");			
		file_printer.println("		</table>");
		if (data.getMappingParameters()!=null){
			file_printer.println("		<table border=\"1\">");
			file_printer.println("			<tr>");   
			file_printer.println("				<th>Parameter Name</th>");
			file_printer.println("				<th>Parameter Type</th>");
			file_printer.println("				<th>Parameter Value</th>");
			file_printer.println("			</tr>");
			List<RestrictedGenericProperty> str_para_list = data.getMappingParameters().getString();
			for (int j=0; j<str_para_list.size(); j++){
				file_printer.println("			<tr>");
				file_printer.println("				<td>" + str_para_list.get(j).getName() + "</td>");
				file_printer.println("				<td>String</td>");
				file_printer.println("				<td>" + str_para_list.get(j).getValue() + "</td>");
				file_printer.println("			</tr>");			
			}
			List<IntegerProperty> int_para_list = data.getMappingParameters().getInteger();
			for (int j=0; j<int_para_list.size(); j++){
				file_printer.println("			<tr>");
				file_printer.println("				<td>" + int_para_list.get(j).getName() + "</td>");
				file_printer.println("				<td>Integer</td>");
				file_printer.println("				<td>" + int_para_list.get(j).getValue() + "</td>");
				file_printer.println("			</tr>");			
			}
			List<ChannelProperty> chan_para_list = data.getMappingParameters().getChannel();
			for (int j=0; j<chan_para_list.size(); j++){
				file_printer.println("			<tr>");
				file_printer.println("				<td>" + chan_para_list.get(j).getName() + "</td>");
				file_printer.println("				<td>Channel</td>");
				int index_no = channel_table.indexOf((chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()).replaceAll("\\x2a", ""));
				if (index_no < 0)
					file_printer.println("				<td>" + print_table_cell(chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()) + "</td>");
				else
					file_printer.println("				<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID() + "</a></td>");
				file_printer.println("			</tr>");			
			}
			file_printer.println("		</table>");
		}
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_operation_mapping_file(String file_name , IntegratedConfigurationReceiverInterfaceRule data) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");   
		file_printer.println("				<th>Operation Mapping Name</th>");
		file_printer.println("				<th>Operation Mapping Namespace</th>");
		file_printer.println("			</tr>");
		file_printer.println("			<tr>");   
		file_printer.println("				<td>" + data.getMapping().getName() + "</td>");
		file_printer.println("				<td>" + data.getMapping().getNamespace() + "</td>");
		file_printer.println("			</tr>");			
		file_printer.println("		</table>");
		try{
			if (data.getMappingParameters()!=null){
				file_printer.println("		<table border=\"1\">");
				file_printer.println("			<tr>");   
				file_printer.println("				<th>Parameter Name</th>");
				file_printer.println("				<th>Parameter Type</th>");
				file_printer.println("				<th>Parameter Value</th>");
				file_printer.println("			</tr>");
				List<RestrictedGenericProperty> str_para_list = data.getMappingParameters().getString();
				for (int j=0; j<str_para_list.size(); j++){
					file_printer.println("			<tr>");
					file_printer.println("				<td>" + str_para_list.get(j).getName() + "</td>");
					file_printer.println("				<td>String</td>");
					file_printer.println("				<td>" + str_para_list.get(j).getValue() + "</td>");
					file_printer.println("			</tr>");			
				}
				List<IntegerProperty> int_para_list = data.getMappingParameters().getInteger();
				for (int j=0; j<int_para_list.size(); j++){
					file_printer.println("			<tr>");
					file_printer.println("				<td>" + int_para_list.get(j).getName() + "</td>");
					file_printer.println("				<td>Integer</td>");
					file_printer.println("				<td>" + int_para_list.get(j).getValue() + "</td>");
					file_printer.println("			</tr>");			
				}
				List<ChannelProperty> chan_para_list = data.getMappingParameters().getChannel();
				for (int j=0; j<chan_para_list.size(); j++){
					file_printer.println("			<tr>");
					file_printer.println("				<td>" + chan_para_list.get(j).getName() + "</td>");
					file_printer.println("				<td>Channel</td>");
					int index_no = channel_table.indexOf((chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()).replaceAll("\\x2a", ""));
					if (index_no < 0)
						file_printer.println("				<td>" + print_table_cell(chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()) + "</td>");
					else
						file_printer.println("				<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID() + "</a></td>");
					file_printer.println("			</tr>");			
				}
				file_printer.println("		</table>");
			}
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Integrated Configurations mapping parameter, maybe current PI version doesn't support");
		}
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_inboundprocess_file(String file_name , InboundProcessing data) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(3,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(3,2);\">Adapter Table Attributes</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(3,3);\">Assigned Users</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Content</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Sender Channel</th>");
		int index_no = channel_table.indexOf((data.getCommunicationChannel().getPartyID() + "|" + data.getCommunicationChannel().getComponentID() + "|" + data.getCommunicationChannel().getChannelID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("							<td>" + print_table_cell(data.getCommunicationChannel().getChannelID()) + "</td>");
		else
			file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + data.getCommunicationChannel().getChannelID() + "</a></td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Schema Validation</th>");
		file_printer.println("							<td>" + data.isSchemaValidationIndicator() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Adapter Specific Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		List<GenericProperty> attr_list = data.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = data.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Assigned Users</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>User Name</th>");
		file_printer.println("						</tr>");
		List<String> user_list = data.getAssignedUser();
		for(int i=0; i< user_list.size(); i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + print_table_cell(user_list.get(i)) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_receivers_file(String file_name , Receivers data, List<PrefixNamespaceMapping> prefix_list) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(3,1);\">Standard</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(3,2);\">Extended</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(3,3);\">No Receiver</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Local Rule</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Operation</th>");
		file_printer.println("							<th>Condition</th>");
		file_printer.println("							<th>Communication Party</th>");
		file_printer.println("							<th>Communication Component</th>");
		file_printer.println("						</tr>");
		
		List<IntegratedConfigurationReceiverRule> local_rule_list = data.getReceiverRule();
		for(int i=0; i<local_rule_list.size();i++){
			List<CommunicationComponentID> receiver_list = local_rule_list.get(i).getReceiver();
			for (int j=0; j<receiver_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0){
					file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\">" + print_table_cell(local_rule_list.get(i).getOperation()) + "</td>");
					if (local_rule_list.get(i).getCondition()==null || local_rule_list.get(i).getCondition().getAtomicConditionBlock() == null || local_rule_list.get(i).getCondition().getAtomicConditionBlock().size() == 0)
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\">&nbsp;</td>");
					else{
						file_printer.println("							<td rowspan=\"" + receiver_list.size() + "\"><a href=\"../" + file_name + "a_" + i + ".html\" style=\"color:blue\" target=\"a3\">Condition</a></td>");
						generate_condition_file(file_name + "a_" + i, local_rule_list.get(i).getCondition().getAtomicConditionBlock());
					}
				}
				int index = party_table.indexOf(receiver_list.get(j).getPartyID());
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getPartyID()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + receiver_list.get(j).getPartyID() + "</a></td>");
				index = component_table.indexOf((receiver_list.get(j).getPartyID() + "|" + receiver_list.get(j).getComponentID()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(receiver_list.get(j).getComponentID()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + receiver_list.get(j).getComponentID() + "</a></td>");
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Condition Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a4\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		try{
			file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
			List<ReceiverDeterminationMapping> operation_list = data.getDynamicReceiverRule();
			for (int i=0; i<operation_list.size(); i++){
				file_printer.println("				<div class=\"contentHeader\">Operation:  " + operation_list.get(i).getOperation() + "</div>");
				file_printer.println("				<div class=\"contentMain\">");
				file_printer.println("					<table border=\"1\">");
				file_printer.println("						<tr>");   
				file_printer.println("							<th>Operation Mapping Name</th>");
				file_printer.println("							<th>Operation Mapping Namespace</th>");
				file_printer.println("						</tr>");
				file_printer.println("						<tr>");   
				file_printer.println("							<td>" + operation_list.get(i).getMapping().getName() + "</td>");
				file_printer.println("							<td>" + operation_list.get(i).getMapping().getNamespace() + "</td>");
				file_printer.println("						</tr>");			
				file_printer.println("					</table>");
				if(operation_list.get(i).getMappingParamters()!=null){
					file_printer.println("					<table border=\"1\">");
					file_printer.println("						<tr>");   
					file_printer.println("							<th>Parameter Name</th>");
					file_printer.println("							<th>Parameter Type</th>");
					file_printer.println("							<th>Parameter Value</th>");
					file_printer.println("						</tr>");
					List<RestrictedGenericProperty> str_para_list = operation_list.get(i).getMappingParamters().getString();
					for (int j=0; j<str_para_list.size(); j++){
						file_printer.println("						<tr>");
						file_printer.println("							<td>" + str_para_list.get(j).getName() + "</td>");
						file_printer.println("							<td>String</td>");
						file_printer.println("							<td>" + str_para_list.get(j).getValue() + "</td>");
						file_printer.println("						</tr>");			
					}
					List<IntegerProperty> int_para_list = operation_list.get(i).getMappingParamters().getInteger();
					for (int j=0; j<int_para_list.size(); j++){
						file_printer.println("						<tr>");
						file_printer.println("							<td>" + int_para_list.get(j).getName() + "</td>");
						file_printer.println("							<td>Integer</td>");
						file_printer.println("							<td>" + int_para_list.get(j).getValue() + "</td>");
						file_printer.println("						</tr>");			
					}
					List<ChannelProperty> chan_para_list = operation_list.get(i).getMappingParamters().getChannel();
					for (int j=0; j<chan_para_list.size(); j++){
						file_printer.println("						<tr>");
						file_printer.println("							<td>" + chan_para_list.get(j).getName() + "</td>");
						file_printer.println("							<td>Channel</td>");
						int index_no = channel_table.indexOf((chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()).replaceAll("\\x2a", ""));
						if (index_no < 0)
							file_printer.println("							<td>" + print_table_cell(chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID()) + "</td>");
						else
							file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\">" + chan_para_list.get(j).getValue().getPartyID() + "|" + chan_para_list.get(j).getValue().getComponentID() + "|" + chan_para_list.get(j).getValue().getChannelID() + "</a></td>");
						file_printer.println("						</tr>");			
					}
					file_printer.println("					</table>");
				}
				file_printer.println("				</div>");
			}
			file_printer.println("			</div>");
		} catch (javax.xml.ws.WebServiceException e){
			e.printStackTrace();
			System.out.println("skip Integrated Configurations dynamic receiver, maybe current PI version doesn't support");
		}
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">No Receiver</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver not found Behavior</th>");
		file_printer.println("							<td>" + data.getNoReceiverBehaviour() + "</td>");
		file_printer.println("						</tr>");
		CommunicationComponentID ext = data.getNoReceiverReceiver();
		if (ext != null){
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Exception Party</th>");
			int index_no = party_table.indexOf(ext.getPartyID());
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(ext.getPartyID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + ext.getPartyID() + "</a></td>");
			file_printer.println("						</tr>");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Exception Component</th>");
			index_no = component_table.indexOf((ext.getPartyID() + "|" + ext.getComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("							<td>" + print_table_cell(ext.getComponentID()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_component/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + ext.getComponentID() + "</a></td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");

		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}

	public void generate_interface_file(String file_name , List<IntegratedConfigurationReceiverInterfaceRule> rule_list, List<PrefixNamespaceMapping> prefix_list, int file_no) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);

		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(1,1);\">Content</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Local Rule</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Operation</th>");
		file_printer.println("							<th>Condition</th>");
		file_printer.println("							<th>Operation Mapping</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Interface</th>");
		file_printer.println("						</tr>");
		for(int i=0; i<rule_list.size();i++){
			List<DesignObjectID> interface_list = rule_list.get(i).getInterface();
			for (int j=0; j<interface_list.size(); j++){
				file_printer.println("						<tr>");
				if (j==0){
					file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">" + print_table_cell(rule_list.get(i).getOperation()) + "</td>");
					if (rule_list.get(i).getCondition()==null || rule_list.get(i).getCondition().getAtomicConditionBlock()==null || rule_list.get(i).getCondition().getAtomicConditionBlock().size() == 0)
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">&nbsp;</td>");
					else{
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\"><a href=\"../" + file_name + "a_" + i + ".html\" style=\"color:blue\" target=\"a3\">Condition</a></td>");
						generate_condition_file(file_name + "a_" + i, rule_list.get(i).getCondition().getAtomicConditionBlock());
					}
					if(rule_list.get(i).getMapping()==null){
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">&nbsp;</td>");
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\">&nbsp;</td>");
					}
					else{
						file_printer.println("							<td rowspan=\"" + interface_list.size() + "\"><a href=\"" + file_no + "b_" + i + ".html\" style=\"color:blue\" target=\"a3\">" + rule_list.get(i).getMapping().getName() + "</a></td>");
						generate_operation_mapping_file("integrated_configuration/" + file_no + "b_" + i, rule_list.get(i));
					}
				}
				file_printer.println("							<td>" + interface_list.get(j).getName() + "</td>");
				file_printer.println("							<td>" + interface_list.get(j).getNamespace() + "</td>");
				file_printer.println("						</tr>");
			}
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Item Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Condition Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a4\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}

	public void generate_outboundprocess_file(String file_name , OutboundProcessing data, List<PrefixNamespaceMapping> prefix_list) throws Exception{
		FileOutputStream out=new FileOutputStream("result/" + file_name + ".html");
		PrintStream file_printer =new PrintStream(out);
		print_detail_file_header(file_printer);
		
		file_printer.println("		<div id=\"navbar\">");
		file_printer.println("			<div id=\"header\">");
		file_printer.println("				<ul>");
		file_printer.println("					<li id=\"current1\"><a href=\"#\" onclick=\"change_option(3,1);\">Content</a></li>");
		file_printer.println("					<li id=\"current2\"><a href=\"#\" onclick=\"change_option(3,2);\">Adapter Table Attributes</a></li>");
		file_printer.println("					<li id=\"current3\"><a href=\"#\" onclick=\"change_option(3,3);\">Header Mapping</a></li>");
		file_printer.println("				</ul>");
		file_printer.println("			</div>");
				
		file_printer.println("			<div id=\"content1\" class=\"content\">");
		file_printer.println("				<div class=\"contentHeader\">Content</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver Channel</th>");
		int index_no = channel_table.indexOf((data.getCommunicationChannel().getPartyID() + "|" + data.getCommunicationChannel().getComponentID() + "|" + data.getCommunicationChannel().getChannelID()).replaceAll("\\x2a", ""));
		if (index_no < 0)
			file_printer.println("							<td>" + print_table_cell(data.getCommunicationChannel().getChannelID()) + "</td>");
		else
			file_printer.println("							<td><a href=\"../communication_channel/" + index_no + ".html\" style=\"color:blue\" target=\"a2\">" + data.getCommunicationChannel().getChannelID() + "</a></td>");
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Schema Validation</th>");
		file_printer.println("							<td>" + data.isSchemaValidationIndicator() + "</td>");
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Adapter Specific Attributes</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Name</th>");
		file_printer.println("							<th>Namespace</th>");
		file_printer.println("							<th>Value</th>");
		file_printer.println("						</tr>");
		List<GenericProperty> attr_list = data.getAdapterSpecificAttribute();
		for(int i=0; i< attr_list.size(); i++){
			file_printer.println("						<tr>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getName()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getNamespace()) + "</th>");
			file_printer.println("							<td>" + print_table_cell(attr_list.get(i).getValue()) + "</th>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content2\" class=\"content\" style=\"display:none\">");
		List<GenericPropertyTable> tab_attr_list = data.getAdapterSpecificTableAttribute();
		for(int i=0; i< tab_attr_list.size(); i++){
			file_printer.println("				<div class=\"contentHeader\">" + tab_attr_list.get(i).getName() + "	" + tab_attr_list.get(i).getNamespace() + "</div>");
			file_printer.println("				<div class=\"contentMain\">");
			file_printer.println("					<table border=\"1\">");
			file_printer.println("						<tr>");   
			file_printer.println("							<th>Column Name</th>");
			file_printer.println("							<th>Value</th>");
			file_printer.println("						</tr>");
			List<GenericTableRow> tabrow = tab_attr_list.get(i).getValueTableRow();
			for(int j=0; j<tabrow.size(); j++){
				List<GenericTableRowTableCell> cell = tabrow.get(j).getValueTableCell();
				for(int k=0; k<cell.size(); k++){
					file_printer.println("						<tr>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getColumnName()) + "</th>");
					file_printer.println("							<td>" + print_table_cell(cell.get(k).getValue()) + "</th>");
					file_printer.println("						</tr>");
				}
			}
			file_printer.println("					</table>");
			file_printer.println("				</div>");
		}
		file_printer.println("			</div>");
		
		file_printer.println("			<div id=\"content3\" class=\"content\" style=\"display:none\">");
		file_printer.println("				<div class=\"contentHeader\">Header Mapping</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Sender Party</th>");
		int constant_party;
		if (data.getHeaderMapping().getSender().getCommunicationParty()==null){
			constant_party = 0;
			file_printer.println("							<td>&nbsp;</td>");
		}
		else if(data.getHeaderMapping().getSender().getCommunicationParty().getTypeID().value().equals("Constant")){
			constant_party = 1;
			int index = party_table.indexOf(data.getHeaderMapping().getSender().getCommunicationParty().getValue());
			if (index < 0)
				file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getSender().getCommunicationParty().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getSender().getCommunicationParty().getValue() + "</a></td>");
		}
		else{
			constant_party = 2;
			file_printer.println("							<td><a href=\"../" + file_name + "_" + "1.html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
			generate_extractor_file(file_name + "_" + "1" , data.getHeaderMapping().getSender().getCommunicationParty());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Sender Component</th>");
		if(data.getHeaderMapping().getSender().getCommunicationComponent() == null)
			file_printer.println("							<td>&nbsp;</td>");
		else if(data.getHeaderMapping().getSender().getCommunicationComponent().getTypeID().value().equals("Constant")){
			if (constant_party == 0){
				int index = component_table.indexOf(("|" + data.getHeaderMapping().getSender().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getSender().getCommunicationComponent().getValue() + "</a></td>");
			}
			else if (constant_party == 1){
				int index = component_table.indexOf((data.getHeaderMapping().getSender().getCommunicationParty().getValue() + "|" + data.getHeaderMapping().getSender().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getSender().getCommunicationComponent().getValue() + "</a></td>");
			}
			else
				file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getSender().getCommunicationComponent().getValue()) + "</td>");
		}
		else{
			file_printer.println("							<td><a href=\"../" + file_name + "_" + "2.html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
			generate_extractor_file(file_name + "_" + "2" , data.getHeaderMapping().getSender().getCommunicationComponent());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Receiver Party</th>");
		if (data.getHeaderMapping().getReceiver().getCommunicationParty()==null){
			constant_party = 0;
			file_printer.println("							<td>&nbsp;</td>");
		}
		else if(data.getHeaderMapping().getReceiver().getCommunicationParty().getTypeID().value().equals("Constant")){
			constant_party = 1;
			int index = party_table.indexOf(data.getHeaderMapping().getReceiver().getCommunicationParty().getValue());
			if (index < 0)
				file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getReceiver().getCommunicationParty().getValue()) + "</td>");
			else
				file_printer.println("							<td><a href=\"../communication_party/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "</a></td>");
		}
		else{
			constant_party = 2;
			file_printer.println("							<td><a href=\"../" + file_name + "_" + "3.html\" style=\"color:blue\" target=\"a3\">Dynamic Party</a></td>");
			generate_extractor_file(file_name + "_" + "3" , data.getHeaderMapping().getReceiver().getCommunicationParty());
		}
		file_printer.println("						</tr>");
		file_printer.println("						<tr>");
		file_printer.println("							<th>Receiver Component</th>");
		if(data.getHeaderMapping().getReceiver().getCommunicationComponent() == null)
			file_printer.println("							<td>&nbsp;</td>");
		else if(data.getHeaderMapping().getReceiver().getCommunicationComponent().getTypeID().value().equals("Constant")){
			if (constant_party == 0){
				int index = component_table.indexOf(("|" + data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "</a></td>");
			}
			else if (constant_party == 1){
				int index = component_table.indexOf((data.getHeaderMapping().getReceiver().getCommunicationParty().getValue() + "|" + data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()).replaceAll("\\x2a", ""));
				if (index < 0)
					file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
				else
					file_printer.println("							<td><a href=\"../communication_component/" + index + ".html\" style=\"color:blue\" target=\"a2\">" + data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue() + "</a></td>");
			}
			else
				file_printer.println("							<td>" + print_table_cell(data.getHeaderMapping().getReceiver().getCommunicationComponent().getValue()) + "</td>");
		}
		else{
			file_printer.println("							<td><a href=\"../" + file_name + "_" + "4.html\" style=\"color:blue\" target=\"a3\">Dynamic Component</a></td>");
			generate_extractor_file(file_name + "_" + "4" , data.getHeaderMapping().getReceiver().getCommunicationComponent());
		}
		file_printer.println("						</tr>");
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">data Detail</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("				<iframe name=\"a3\" src=\"\" width=\"700px\" height=\"150\"></iframe>");
		file_printer.println("				</div>");
		file_printer.println("				<div class=\"contentHeader\">Prefix Namespace</div>");
		file_printer.println("				<div class=\"contentMain\">");
		file_printer.println("					<table border=\"1\">");
		file_printer.println("						<tr>");   
		file_printer.println("							<th>Prefix</th>");
		file_printer.println("							<th>URI</th>");
		file_printer.println("						</tr>");
		for(int i=0; i<prefix_list.size();i++){
			file_printer.println("						<tr>");   
			file_printer.println("							<td>" + prefix_list.get(i).getPrefix() + "</td>");
			file_printer.println("							<td>" + prefix_list.get(i).getURI() + "</td>");
			file_printer.println("						</tr>");
		}
		file_printer.println("					</table>");
		file_printer.println("				</div>");
		file_printer.println("			</div>");
		
		file_printer.println("		</div>");
		
		print_detail_file_footer(file_printer);
		file_printer.close();
	}
	
	public void generate_message_header_file(PrintStream file_printer, ArrayList<String> lookup_table, List<MessageHeaderID> list) throws Exception{
		file_printer.println("<html>");
		file_printer.println("	<body>");
		file_printer.println("		<table border=\"1\">");
		file_printer.println("			<tr>");
		file_printer.println("				<th>Sender Party</th>");
		file_printer.println("				<th>Sender Component</th>");
		file_printer.println("				<th>Interface</th>");
		file_printer.println("				<th>Namespace</th>");
		file_printer.println("				<th>Receiver Party</th>");
		file_printer.println("				<th>Receiver Component</th>");
		file_printer.println("			</tr>");
		for (int i=0;i<list.size();i++){
			lookup_table.add((list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID() + "|" + list.get(i).getInterfaceName() + "|" + list.get(i).getInterfaceNamespace() + "|" + list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID()).replaceAll("\\x2a", ""));
			file_printer.println("			<tr>");
			int index_no = party_table.indexOf(list.get(i).getSenderPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getSenderPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getSenderPartyID() + "</a></td>");
			
			index_no = component_table.indexOf((list.get(i).getSenderPartyID() + "|" + list.get(i).getSenderComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getSenderComponentID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\">" + list.get(i).getSenderComponentID() + "</a></td>");
			
			file_printer.println("				<td><a href=\"" + i + ".html\">" + list.get(i).getInterfaceName() + "</a></td>");
			file_printer.println("				<td>" + list.get(i).getInterfaceNamespace() + "</td>");
			
			index_no = party_table.indexOf(list.get(i).getReceiverPartyID());
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getReceiverPartyID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_party/" + index_no + ".html\">" + list.get(i).getReceiverPartyID() + "</a></td>");
			
			index_no = component_table.indexOf((list.get(i).getReceiverPartyID() + "|" + list.get(i).getReceiverComponentID()).replaceAll("\\x2a", ""));
			if (index_no < 0)
				file_printer.println("				<td>" + print_table_cell(list.get(i).getReceiverComponentID()) + "</td>");
			else
				file_printer.println("				<td><a href=\"../communication_component/" + index_no + ".html\">" + list.get(i).getReceiverComponentID() + "</a></td>");
			
			file_printer.println("			</tr>");
		}		
		file_printer.println("		</table>");
		file_printer.println("	</body>");
		file_printer.println("</html>");
	}
}