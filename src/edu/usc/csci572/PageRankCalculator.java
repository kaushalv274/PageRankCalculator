package edu.usc.csci572;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.*;

public class PageRankCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GraphBuilder build = new GraphBuilder();
		build.extractlinksfromCSV("C:\\Users\\kaush\\CSCI_572\\ABCNewsData\\mapABCNewsDataFile.csv");
		Graph g = build.buildGraph("C:\\Users\\kaush\\CSCI_572\\ABCNewsData\\ABCNewsDownloadData");
		calculatePageRank(g);
		writePageRank("pageRank.txt",g);
		
		for(Node n :g.getallNodes())
		{
			//System.out.println(n.getPageRank());
		}
		
	}

	public static void writePageRank(String fileName,Graph g)
	{
		try	{
			
			FileWriter out = new FileWriter(fileName); 
			BufferedWriter bufferedWriter = new BufferedWriter(out);
			for(Node node : g.getallNodes())
			{
				bufferedWriter.append("/home/kaushal/shared/ABCNewsData/ABCNewsDownloadData/"+node.getId()+" "+Double.toString(node.getPageRank()));
				bufferedWriter.newLine();
			}
			
			bufferedWriter.close();
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void calculatePageRank(Graph g)
	{
		int totalNodes = g.getallNodes().size();
		
		double initial_score = (1 / totalNodes) ;
		double damping_factor = 0.85;
		int maxItr = 30;
		double tol = 1e-6;
		for(Node node : g.getallNodes())
		{
			System.out.println("Outgoing links for this node "+node.numofOutgoinglinks());
			System.out.println("Incoming links for this node "+node.numofIncominglinks());
			
			node.setPageRank(initial_score);
		}
		int itr = 0;
		
		while(itr<maxItr)
		{
			//iterate all nodes and calculate their pagerank for this iteration
			for(Node current :g.getallNodes())
			{
				//Extract all incoming links for this node and add that node's contribution to this node.
				double new_pageRankval = 0;
				for(Node incoming : current.getIncominglinks())
				{
					new_pageRankval += ((incoming.getPageRank())/(incoming.numofOutgoinglinks()));
				}
				
				//Update current node's pageRank
				
				new_pageRankval = (1-damping_factor)  +  ((damping_factor)*new_pageRankval);
				current.setPageRank(new_pageRankval);
			}
			
			itr++;
		}
		
		
	}
}
