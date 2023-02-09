package es.usal.pa;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import es.usal.pa.Main;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.DenseInstance;
import weka.core.Instance;


public class AgenteQuienEsQuien extends Agent{
	public void setup() {
		DFAgentDescription dfd=new DFAgentDescription();
		dfd.setName(getAID());
		
		ServiceDescription sd=new ServiceDescription();
		sd.setName("Quien_es_quien");
		sd.setType("Pregunta_qeq");
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
		
		addBehaviour(new ParalellBehaviour(this));
		
	}
}
