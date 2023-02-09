package es.usal.pa;

import java.util.List;
import java.util.Scanner;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteCliente extends Agent {
	
	public void setup() {
		DFAgentDescription dfd=new DFAgentDescription();
		dfd.setName(getAID());
		
		ServiceDescription sd=new ServiceDescription();
		sd.setName("Quien_es_quien");
		sd.setType("Respuesta");
		sd.addOntologies("ontologia");
		sd.addLanguages(new SLCodec().getName());
		dfd.addServices(sd);
		
		try 
		{
			DFService.register(this, dfd);
		} 
		catch (FIPAException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addBehaviour(new CyclicBehaviourCliente());
		
	}
}
