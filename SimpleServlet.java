package wasdev.sample.servlet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultJsonMapper;
import com.restfb.FacebookClient;
import com.restfb.JsonMapper;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;
import com.restfb.types.webhook.WebhookEntry;
import com.restfb.types.webhook.WebhookObject;
import com.restfb.types.webhook.messaging.MessagingItem;

/**
 * Servlet implementation class SimpleServlet
 */
@WebServlet("/SimpleServlet")
public class SimpleServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	    
	  
	   
	    private String FbToken = "EAAFnNrHsBbMBAOPD5uc0WiuZBeVnNtsiFFDTWT9ngd6khsoMBQYNtkdFUlSZA9MFQj9O7fKvejEUh0jHOHAEAMGnZC47Nef6dx0HNZAmwgq0ulVFpgc6HGRoys1IL1PhUmtHj1svMtzbCNLyj2opMZBeEBH0UXE80wPqBVKIEW0QlNo2gZAeTu";
		private String verifyToken = "StcPoc";
		
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       
	    	
	    	
	    	 

	    	 response.setContentType("application/json");
	 
	        String hubToken = request.getParameter("hub.verify_token");
			String hubChallenge = request.getParameter("hub.challenge");
			
			if (verifyToken.equals(hubToken)) {
				response.getWriter().write(hubChallenge);
				response.getWriter().flush();
				response.getWriter().close();
				
			}
			else {
				response.getWriter().write("incorrectTOken");
			}
			
		}

	    
	    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//=====
		StringBuffer sb = new StringBuffer();
		BufferedReader br = request.getReader();
		String line = "";
		while ((line = br.readLine())!=null) {
			sb.append(line);
		}
		
		JsonMapper mapper = new DefaultJsonMapper();
		WebhookObject webhookObj = mapper.toJavaObject(sb.toString(), WebhookObject.class);
		
		for (WebhookEntry entry: webhookObj.getEntryList()) {
			if(entry.getMessaging()!=null) {
				for(MessagingItem mItem: entry.getMessaging()) {
				
					String senderId = mItem.getSender().getId();
					IdMessageRecipient recipient = new IdMessageRecipient(senderId);
					String a = null;
					
					
					if(mItem.getMessage()!=null && mItem.getMessage().getText()!=null) {
						
						SendMessage(recipient,new Message("Hello "+SimpleServlet.newMessage()));
						
					}
					
				}
			}
		}
	}
	public void SendMessage(IdMessageRecipient recipient ,Message message) {//,MessageResponse response,Map context) {
		
		FacebookClient pageClient = new DefaultFacebookClient(FbToken, Version.VERSION_2_6);

		SendResponse resp = pageClient.publish("me/messages", SendResponse.class,
		     Parameter.with("recipient", recipient), // the id or phone recipient
			 Parameter.with("message", message));
			
		    
		
	}

	public  String newMessage() { 
		
		
		BufferedReader br = null;
		MessageResponse response = null;
		Map context = new HashMap();
		
		try {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		
		while (true) {
		
			
		
			String input = br.readLine();
		
			response = conversationAPI(input, context);
			context = response.getContext();
			return("Watson Response: " + response.getText().get(0));
		
			
		
			
		}
		} catch (IOException e) {
		
			//return ("Error");
		}
		
		
		
	 	for (int a = 0;a<response.getText().size();a++) {
    		
    		if (response.getText().get(a)!=null) {
    			
    			return(response.getText().get(a));
    			
    			} 
   	}
		
		
		
		return response.getEntities().get(0).getValue();
	}

		public  MessageResponse conversationAPI(String input, Map context) {
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
		// Credentials of Workspace of Conversation
		service.setUsernameAndPassword("6fa4fac3-4c0b-40b4-9ce5-f13654916155", "PfqWtMqCPpz3");

		MessageRequest newMessage = new MessageRequest.Builder().inputText(input).context(context).build();
		// Workspace ID of Conversation current workspace
		String workspaceId = "7e0224b5-b107-497f-84d6-881bc33261ab";

		MessageResponse response = service.message(workspaceId, newMessage).execute();

		return response;

		}




	



