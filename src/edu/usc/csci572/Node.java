package edu.usc.csci572;

import java.util.ArrayList;
import java.util.List;

public class Node {

	private String link;
	private String id;
	private ArrayList<Node> incomingLinks;
	private ArrayList<Node> outgoingLinks;
	private double pageRank;
	public Node(String link,String id) {
		
		this.link = link;
		this.id = id;
		this.incomingLinks = new ArrayList<Node>();
		this.outgoingLinks = new ArrayList<Node>();
		pageRank = 0;
	}
	
	public void addIncominglink(Node from)
	{
		incomingLinks.add(from);
		return;
	}

	public void addOutgoing(Node to)
	{
		outgoingLinks.add(to);
		return;
	}
	
	public String getId()
	{
		return this.id;
	}
	public List<Node> getIncominglinks()
	{
		return this.incomingLinks;
	}
	
	public List<Node> getOutgoinglinks()
	{
		return this.outgoingLinks;
	}
	
	public int numofOutgoinglinks()
	{
		return this.outgoingLinks.size();
	}

	public int numofIncominglinks()
	{
		return this.incomingLinks.size();
	}
	
	public double getPageRank()
	{
		return this.pageRank;
	}
	
	public void setPageRank(double val)
	{
		this.pageRank = val;
	}
}
