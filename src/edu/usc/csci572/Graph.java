package edu.usc.csci572;

import java.util.HashSet;
import java.util.Set;

public class Graph {

	public Graph()
	{
		this.nodes = new HashSet<Node>();
	}
	
	
	public void addNode(Node n)
	{
		nodes.add(n);
	}
	public Set<Node> getallNodes()
	{
		return this.nodes;
	}
	private Set<Node> nodes;
}
