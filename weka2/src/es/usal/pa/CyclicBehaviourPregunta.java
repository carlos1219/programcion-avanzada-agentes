package es.usal.pa;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.List;

import es.usal.pa.Pregunta;

public class CyclicBehaviourPregunta extends CyclicBehaviour{

	@Override
	public void action(){
		// TODO Auto-generated method stub
		DataSource source = null;
		try {
			source = new DataSource("famosos.csv");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Instances dataEntrenamiento = null;
		try {
			dataEntrenamiento = source.getDataSet();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (dataEntrenamiento.classIndex() == -1)
			 dataEntrenamiento.setClassIndex(0);
		
		J48 j48 = new J48();
		try {
			j48.setOptions(new String[] {"-C", "0.25", "-M", "1"});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		j48.setUnpruned(true);
		try {
			j48.buildClassifier(dataEntrenamiento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Pregunta p = new Pregunta(j48);
			//ESPERAMOS EL REQUEST
			ACLMessage msg_cliente=this.myAgent.blockingReceive(MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),MessageTemplate.MatchOntology("ontologia")));
			String id = msg_cliente.getConversationId();
			//ENVIAMOS LA PREGUNTA AL CLIENTE
			String pregunta = p.obtenerPreguntaNodo();
			ACLMessage msg_Pregunta = msg_cliente.createReply();
			msg_Pregunta.setContent(pregunta);
			msg_Pregunta.setPerformative(ACLMessage.INFORM);
			myAgent.send(msg_Pregunta);
			//ESPERAMOS LA RESPUESTA
			msg_cliente =this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(id));
			String respuesta = msg_cliente.getContent();
			p.navegarNodoRespuesta(respuesta);
			while(!p.esNodoFinal()) {
				//ENVIAMOS LA SIGUIENTE PREGUNTA
				pregunta = p.obtenerPreguntaNodo();
				msg_Pregunta = msg_cliente.createReply();
				msg_Pregunta.setContent(pregunta);
				myAgent.send(msg_Pregunta);
				//ESPERAMOS LA RESPUESTA Y NAVEGAMOS AL SIGUIENTE NODO
				msg_cliente =this.myAgent.blockingReceive(MessageTemplate.MatchConversationId(id));
				respuesta = msg_cliente.getContent();
				p.navegarNodoRespuesta(respuesta);
			}
			//SI HEMOS LLEGADO AL NODO FINAL, ENVIAMOS LA RESPUESTA FINAL EN UN MENSAJE DE TIPO PROPOSE
			String personaje = p.obtenerPreguntaNodo();
			msg_Pregunta = msg_cliente.createReply();
			msg_Pregunta.setContent("ES " + personaje);
			msg_Pregunta.setPerformative(ACLMessage.PROPOSE);
			myAgent.send(msg_Pregunta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
