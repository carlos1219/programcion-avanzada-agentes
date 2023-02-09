package es.usal.pa;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class CyclicBehaviourCliente extends CyclicBehaviour {

	@Override
	public void action()
	{
		//ENVIAMOS EL REQUEST
		ACLMessage msg_cliente = new ACLMessage(ACLMessage.REQUEST);
		msg_cliente.setConversationId(myAgent.getName());
		Utils.enviarMensaje(myAgent, "Pregunta_qeq", msg_cliente, myAgent.getName());
		//ESPERAMOS LA PREGUNTA
		String pregunta;
		ACLMessage msg_pregunta=this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(myAgent.getName()));
		pregunta = msg_pregunta.getContent();
		
		//IMPRIMIMOS LA PREGUNTA
		System.out.println("hola " + myAgent.getName());
		System.out.println("*********Quién Es Quién de JF*****************");
		System.out.println("PREGUNTA: " + pregunta);
		
		//PEDIMOS LA RESPUESTA Y LA ENVIAMOS
		Scanner sc = new Scanner(System.in);
		String respuesta = sc.nextLine();
		msg_cliente = msg_pregunta.createReply();
		msg_cliente.setContent(respuesta);
		myAgent.send(msg_cliente);
		
		//IMPRIMIMOS LA SIGUIENTE PREGUNTA
		msg_pregunta = this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(myAgent.getName()));
		
		//BUCLE DE PREGUNTAS Y RESPUESTAS
		while(msg_pregunta.getPerformative()!=ACLMessage.PROPOSE) {
			pregunta = msg_pregunta.getContent();
			System.out.println("PREGUNTA: " + pregunta);
			//PEDIMOS LA RESPUESTA
			respuesta = sc.nextLine();
			//ENVIAMOS LA RESPUESTA
			msg_cliente = msg_pregunta.createReply();
			msg_cliente.setContent(respuesta);
			myAgent.send(msg_cliente);
			//ESPERAR LA PREGUNTA
			msg_pregunta=this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(myAgent.getName()));
			
		}
		String personaje;
		personaje = msg_pregunta.getContent();
		System.out.println(personaje);
		System.out.printf("\n");
	}

}
