package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private BordersDAO bDAO;
	private Map<Integer, Country> idMap;
	private Graph<Country, DefaultEdge> graph;

	public Model() {
		
		bDAO = new BordersDAO();
		idMap = new HashMap<>();
	}
	
	public void createGraph(int year) {
		
		graph = new SimpleGraph<>(DefaultEdge.class);
		bDAO.loadAllCountries(idMap);
		Graphs.addAllVertices(graph, bDAO.getCountryWithBorder(year, idMap));
		List<Border> borders = bDAO.getCountryPairs(year, idMap);
		for(Border b : borders) {
			graph.addEdge(b.getState1(), b.getState2());
		}
	}
	
	public Map<Integer, Country> getCountries() {
		return idMap;
	}
	
	public Set<Country> graphVertices() {
		return graph.vertexSet();
	}
	
	public Map<Country, Integer> countryBorders() {
		Map<Country, Integer> result = new HashMap<>();
		for(Country c : graph.vertexSet())
			result.put(c, graph.degreeOf(c));
		return result;
	}
	
	public Integer connectivity() {
		ConnectivityInspector<Country, DefaultEdge> conn = new ConnectivityInspector<>(graph);
		return conn.connectedSets().size();
	}

	public List<Country> connectedCountries (Country start) {
		List<Country> result = new ArrayList<>();
		BreadthFirstIterator<Country, DefaultEdge> bfi = new BreadthFirstIterator<>(graph, start);
		bfi.next();
		while(bfi.hasNext()) {
			result.add(bfi.next());
		}
		return result;
	}
}
