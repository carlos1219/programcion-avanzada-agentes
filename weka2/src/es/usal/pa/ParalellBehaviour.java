package es.usal.pa;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.ThreadedBehaviourFactory;

public class ParalellBehaviour extends ParallelBehaviour {
	protected CyclicBehaviourPregunta cyclicBehaviourPregunta;
	
	
	public ParalellBehaviour(Agent agent)
	{
		super();
		
		//para insertar cada comportamiento en un hilo independiente
		ThreadedBehaviourFactory tf=new ThreadedBehaviourFactory();
		
		cyclicBehaviourPregunta=new CyclicBehaviourPregunta();
		addSubBehaviour(tf.wrap(cyclicBehaviourPregunta));
		
		cyclicBehaviourPregunta=new CyclicBehaviourPregunta();
		addSubBehaviour(tf.wrap(cyclicBehaviourPregunta));
		
		cyclicBehaviourPregunta=new CyclicBehaviourPregunta();
		addSubBehaviour(tf.wrap(cyclicBehaviourPregunta));
		
		cyclicBehaviourPregunta=new CyclicBehaviourPregunta();
		addSubBehaviour(tf.wrap(cyclicBehaviourPregunta));
		
		cyclicBehaviourPregunta=new CyclicBehaviourPregunta();
		addSubBehaviour(tf.wrap(cyclicBehaviourPregunta));
	}
	
}
