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
	    
	    //protected String client_id = "Rpxe0QzxsUFDsZW7zqWd1VrA";
	   // protected String client_secret = "5d14afd3ab9a3e4ed0d0749b2c13c855";

	    
	    
	 /*  
	    protected String conv_bluemix_username = "6fa4fac3-4c0b-40b4-9ce5-f13654916155";
	    protected String conv_bluemix_password = "PfqWtMqCPpz3";
	    protected String conv_bluemix_id = "7e0224b5-b107-497f-84d6-881bc33261ab";

	    public SimpleServlet() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    /**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		//protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//	response.getWriter().append("Hi");
//		}
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
	/*	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String category = request.getParameter("category");
			String payload = request.getParameter("payload");
			
			JSONObject result = new JSONObject();
			result.put("code", 200);
			result.put("message", "This is service response from IBM Blockchain ("+category+").");
			
			response.getWriter().append(result.toString());

			System.out.println("### Payload received from Blockchain ###");
			System.out.println(payload);
			System.out.println("### /Payload received from Blockchain ###");
		}
	    }
	    
	    
	    */
	    
	  
	   
	    private String FbToken = "EAAFnNrHsBbMBAOPD5uc0WiuZBeVnNtsiFFDTWT9ngd6khsoMBQYNtkdFUlSZA9MFQj9O7fKvejEUh0jHOHAEAMGnZC47Nef6dx0HNZAmwgq0ulVFpgc6HGRoys1IL1PhUmtHj1svMtzbCNLyj2opMZBeEBH0UXE80wPqBVKIEW0QlNo2gZAeTu";
		private String verifyToken = "StcPoc";
		
		static BufferedReader br = null;
		static MessageResponse response = null;
		static Map context = new HashMap();
		static String userName ;
		
		static JSONObject finObject;
		static JSONArray sportsArray;
		static int count = 0 ;
		static String date;
		static String fullName;
		static String lastName;
		static String firstName;
		static String newName;
		static String newLastName;
		static String number;
		static String newNumber;
		static String email;
		static String emailSplits[];
		
		static Scanner sa = new Scanner(System.in);
		
		
	    /**
	     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	     */
	    @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       
	    	
	    	
	    	     response.setContentType("application/json");
	        //response.getWriter().print("Hello World!");
	        
	        String hubToken = request.getParameter("hub.verify_token");
			String hubChallenge = request.getParameter("hub.challenge");
			
			if (verifyToken.equals(hubToken)) {
				response.getWriter().write(hubChallenge);
				response.getWriter().flush();
				response.getWriter().close();
				//response.getWriter().write("Here We are lets"+newMessage());
			}
			else {
				response.getWriter().write("incorrectTOken");
			}
			
		}
		
	    

	    
	    
	    
	    
	    
	  
	    
	    //==========
	    
	    
	    
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
			 
			 //Parameter.with("response", response),
			// Parameter.with("context", context));
		
		    
		
	}

	public static String newMessage() { 
		
		
		BufferedReader br = null;
		MessageResponse response = null;
		Map context = new HashMap();
		
		try {
		br = new BufferedReader(new InputStreamReader(System.in));
		
		//System.out.println("Enter your Name: ");
		
		// Add userName to context to be used by Conversation.
		
		
		while (true) {
		
			
		
			String input = br.readLine();
		
			response = conversationAPI(input, context);
			context = response.getContext();
			return("Watson Response: " + response.getText().get(0));
		
			
		
			//return("———–");
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
	//MessageResponse response,Map context) {*/
		
		
		
	/*	try {   
		   // احتاج الى تصريح زائر ل أحمد
	   	 
	    	String jsonData = readFile("document.json");
		JSONObject jobj = new JSONObject(jsonData);
	    sportsArray = jobj.getJSONArray("arabic");
		
			
	    
	    
	   
			
	     }
	     
	     
			//Ends
	    catch(Exception e) {
	    	
	    	System.out.println("ERROR");
	    	 
	     }
	    	
	    	
	    	
	        System.out.println( " كيف يمكنني مساعدتك" );
	    
	        	try {
	        		
	      	br = new BufferedReader(new InputStreamReader(System.in));
	        	// Add userName to context to be used by Conversation.
	 
	        	while (true) {
	        		
	        		
	        		String input = br.readLine();
	        		response = conversationAPI(input, context);
	        		//userName = br.readLine();
	        		
	        		 for (int i = 0 ;i<sportsArray.length();i++) {
	        				
	        				
	        				try {
	        					
	        				    finObject = sportsArray.getJSONObject(i);
		        				
	        				    firstName = finObject.getString("firstName"); 
		        				lastName= finObject.getString("lastName");
		        				date = finObject.getString("previousVisitDate");
		        				number = finObject.getString("mobile");
		        				email = finObject.getString("email");
		        				emailSplits = email.split("@");
	        				
		        				
		        				
		        				if (response.getInputText().contains(firstName) && response.getInputText().contains(lastName) ) {
			       				
		        					count++;
		        					newName=firstName;
			         			newLastName =lastName;
			         			fullName = newName +" "+newLastName;
			         			//System.out.println(newName+" "+newLastName+" (last visited "+date+")");		 
			         					 System.out.println(count);
		        				}
		        				
		        				else if (response.getInputText().contains(number)) {
			         				
		      			         
		      			          
		      			          count++;
		      			          newName = firstName;
		      			          newLastName=lastName;
		      			          newNumber = number;
		      			        	  return(newName+" "+newLastName+" (اخر زيارة "+date+")");
		      	        			
		      	        			}
		        				
		        				
		        				else if (response.getInputText().contains(emailSplits[0]) || response.getInputText().contains(email)) {
			        				
			        				count++;
			        				newName = firstName;
			        				newLastName=lastName;
			        				return(newName+" "+newLastName+" (اخر زيارة "+date+")");
			        			
			        			}
		        		
		        				
		        				else if (response.getInputText().contains(firstName)) {
		        				
		        				    
		        				
			        				count++;
			        				newName = firstName;
			        				newLastName=lastName;
			        				System.out.println(newName+" "+newLastName+" (اخر زيارة "+date+")");
					        	    System.out.println("-------------");
					        	    
				        	  		//ويتمور
					        	    if (count>1) {
				        		  
			 	        			 System.out.println("\nهنالك "+count+ " اسماء للزائرين: "+newName + "\n "+newName+"  اختار اسم؟");
			 	        			 userName = sa.next();
				 	        		
				 	        	for (int x = 0 ;x<sportsArray.length();x++) {
				 	       
				 	        		try {
				 	        			finObject = sportsArray.getJSONObject(x);
				 	        			firstName = finObject.getString("firstName");
				 	        			lastName= finObject.getString("lastName");
							    				
				 	        			
				 	        		if (userName.equals(lastName) || userName.equals(firstName ) ) {
					    				
					    				System.out.println("شكرا, لقد تم تقديم طلب تصريح زائر ل "+ firstName+" "+lastName +" ليوم "+response.getEntities().get(0).getValue() );
				 	        		break;
				 	        		}
					    				}catch(Exception e) {
					    					
					    				}
				 	        		}
				 	        	
				 	        	for (int a = 0;a<response.getText().size();a++) {
		        	        		
		        	        		if (response.getText().get(a)!=null) {
		        	        			
		        	        			System.out.println(response.getText().get(a));
		        	        			
		        	        			} 
		        	        		
		        	        	}
				 	        	
				 	        	
		        				}
		        			}
		        				
		        				else if (response.getInputText().contains(lastName)) {
			        				
		        				    
		        				
			        				count++;
			        				newName = firstName;
			        				newLastName=lastName;
					        	    System.out.println(newName+" "+newLastName+" (last visited "+date+")");
					        	    System.out.println("-------------");
					        	    
				        	  		
					        	    if (count>1) {
				        		  
			 	        			 System.out.println("\nThere are "+count+ " visitors called: "+newLastName + "\nWhich "+newLastName+" do you want to choose?");
			 	        			
			 	        			 userName = sa.next();
				 	        		
				 	        		
				 	        
				 	        		 
				 	        		
				 	        	for (int y = 0 ;y<sportsArray.length();y++) {
				 	       
				 	        		try {
				 	        			finObject = sportsArray.getJSONObject(y);
				 	        			firstName = finObject.getString("firstName");
				 	        			lastName= finObject.getString("lastName");
							    				
				 	        			
				 	        		if (userName.equals(lastName) || userName.equals(firstName ) ) {
					    				
				 	        			System.out.println("شكرا, لقد تم تقديم طلب تصريح زائر ل "+ firstName+" "+lastName +" ليوم "+response.getEntities().get(0).getValue() );
					    				break;
				 	        		}
					    				}catch(Exception e) {
					    					
					    				}
				 	        		}
				 	        	
				 	        	for (int a = 0;a<response.getText().size();a++) {
		        	        		
		        	        		if (response.getText().get(a)!=null) {
		        	        			
		        	        				return(response.getText().get(a));
		        	        			
		        	        			} 
		        	        		
		        	        	}
				 	        	
				 	        	
		        				}
					        	    }//Outer IF ends
		   
		        				
	        				}       				
	        				catch(Exception e ) {
	        				
	        					return("here we go");
	        				
	        				}
	        				
	        			}
	        		 
	      if(count==1) {
	        			
	        			 try {
	        			
	        				 return("شكرا, لقد تم تقديم طلب تصريح زائر ل "+newName+" "+newLastName +" ليوم "+response.getEntities().get(0).getValue() );
	        			}catch(Exception e ) {
	           			 
	           		 }
	        		
	        				for (int a = 0;a<response.getText().size();a++) {
	        	        		
	        	        		if (response.getText().get(a)!=null) {
	        	        			
	        	        			System.out.println(response.getText().get(a));
	        	        			
	        	        			} 
	        	        	}
	        		 
	        		 }
	        		 
	       
	        	context = response.getContext();
	        
	        	
		if (count==0) {
			return("لا اظن ان  قام بزياره سابقه, هل الاسم صحيح او انك تملك تفاصيل اخرى عنه؟");
	       }
		}
	        	}
	        
	        	catch (IOException e) {
	        	System.out.println("Error Occured:");;
	        	
	        	}
	    
	        	catch (Exception e) {
	            	return("Error Occured 2:");
	            	
	            	}
		return response.getEntities().get(0).getValue();
	}

	*/
		public static MessageResponse conversationAPI(String input, Map context) {
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
		// Credentials of Workspace of Conversation
		service.setUsernameAndPassword("6fa4fac3-4c0b-40b4-9ce5-f13654916155", "PfqWtMqCPpz3");

		MessageRequest newMessage = new MessageRequest.Builder().inputText(input).context(context).build();
		// Workspace ID of Conversation current workspace
		String workspaceId = "7e0224b5-b107-497f-84d6-881bc33261ab";

		MessageResponse response = service.message(workspaceId, newMessage).execute();

		return response;

		}




	
/*	public static String readFile(String filename) {
	    
		String result = "";
	    
		try {
	    
			BufferedReader br = new BufferedReader(new FileReader(filename));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	  */
	
	
}
	//478472-26782



