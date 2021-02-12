package com.example.kafka.Springbootkafka.config;

import com.example.kafka.Springbootkafka.mypackage.GetCountryResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class SOAPConnector extends WebServiceGatewaySupport {
    public GetCountryResponse callWebService(String url, GetCountryResponse request){
        return (GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
