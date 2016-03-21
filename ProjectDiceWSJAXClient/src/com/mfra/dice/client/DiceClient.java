package com.mfra.dice.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import com.mfra.dice.jaxws.IDiceWS;
import com.mfra.dice.jaxws.MyRequest;

public class DiceClient {
    
    static {
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
            {
                public boolean verify(String hostname, SSLSession session)
                {
                    // ip address of the service URL(like.23.28.244.244)
                    if (hostname.equals("192.168.1.2"))
                        return true;
                    return false;
                }

            });
    }
    
    public static void main(String[] args) throws MalformedURLException {

//        System.setProperty("javax.net.ssl.keyStore",
//                "C:\\Program Files\\Java\\jdk1.7.0_55\\bin\\client.jks");
//        System.setProperty("javax.net.ssl.keyStorePassword", "client");
        
//        String WS_URL = "http://192.168.1.2:8080/ProjectDiceWSJAX/projectDice?wsdl";
//        String WS_URL = "https://192.168.1.2:8443/ProjectDiceWSJAX/projectDice?wsdl";
//        String WS_URL = "https://192.168.1.2:8181/ProjectDiceWSJAX/projectDice?wsdl";
        String WS_URL = "https://192.168.1.2:8181/DiceJAXWS/dice?wsdl";
        URL url = new URL(WS_URL);

        // 1st argument service URI, refer to wsdl document above
        // 2nd argument is service name, refer to wsdl document above
//        QName qname = new QName("http://dice.mfra.com/", "DiceWSService");
        QName qname = new QName("http://jaxws.dice.mfra.com/", "DiceWSService");

        Service service = Service.create(url, qname);

        IDiceWS hello = service.getPort(IDiceWS.class);
        
        /*******************UserName & Password ******************************/
        Map<String, Object> req_ctx = ((BindingProvider)hello).getRequestContext();
        req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WS_URL);

        Map<String, List<String>> headers = new HashMap<String, List<String>>();
        headers.put("Username", Collections.singletonList("username"));
        headers.put("Password", Collections.singletonList("password"));
        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);
        /**********************************************************************/
        
        
        
        
        MyRequest myRequest = new MyRequest();
        myRequest.setFaces(20);
        myRequest.setModifier(1);
        myRequest.setRepetitions(1);
        myRequest.setName("ssadsa");
        
        String rollDice3 = hello.rollDice3(myRequest);
        System.out.println(rollDice3);
        
        

    }
}
