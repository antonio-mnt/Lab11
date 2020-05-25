package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private List<Flow> flows;
	private River r;
	private Simulator s;
	
	public Model() {
		dao = new RiversDAO();
	}
	
	public List<River> getAllRivers() {
		return dao.getAllRivers();
	}
	
	public void getFlows(River river) {
		this.flows = new ArrayList<>(dao.getAllFlow(river));
		this.r = river;
		this.r.setFlows(flows);
		calcolaFlussoMedio(flows);
	}
	
	public void calcolaFlussoMedio(List<Flow> flow) {
		
		double somma = 0;
		
		for(Flow f: flow) {
			somma+=f.getFlow();
		}
		
		double media = 0;
		
		media = somma/flow.size();
		
		this.r.setFlowAvg(media);
		
	}
	
	public LocalDate primaMisura() {
		return this.flows.get(0).getDay();
	}
	
	public LocalDate ultimaMisura() {
		return this.flows.get(flows.size()-1).getDay();
	}
	
	public int nMisure() {
		return this.r.getFlows().size();
	}
	
	public double avgMisura() {
		return this.r.getFlowAvg();
	}
	
	public void simula(double k) {
		s = new Simulator();
		s.run(k, avgMisura(), this.flows);
	}
	
	public int numNienteErogazione() {
		return s.getNienteErogazione();
	}
	
	public double avgC() {
		
		double media = s.getCmed()/s.getNumGiorni();
		
		return media;
	}
	

}
