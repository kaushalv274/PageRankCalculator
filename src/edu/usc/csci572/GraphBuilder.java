package edu.usc.csci572;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GraphBuilder {

	public GraphBuilder()
	{
		this.linktoId = new HashMap<String,String>();
		this.idtoNode = new HashMap<String,Node>();
	}
	
	private HashMap<String,String> linktoId;
	private HashMap<String,Node> idtoNode; 
	
	public void extractlinksfromCSV(String fileName)
	{
		try {
			
			FileReader filereader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(filereader);
			String line = null;
			while((line=bufferedReader.readLine())!=null)
			{
				String[] parts = line.split(",");
				linktoId.put(parts[1].trim(), parts[0].trim());
				//System.out.println("creating node");
				Node n = new Node(parts[1].trim(),parts[0].trim());
				
				idtoNode.put(parts[0].trim(),n);
			}
			bufferedReader.close();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public Graph buildGraph(String folderLocation)
	{
		Graph g = new Graph();
		Set<String> set = idtoNode.keySet();
		System.out.println("Kaushal");
		int i = 1;
		// Following process is repeated for each HTML File present in the specified folder.
		for(String current : set)
		{

			System.out.println(current);
			try {
				System.out.println("Creating links for "+i);
				i++;
				
				if(i==10)
				{
					break;
				}
				
				File curFile = new File(folderLocation+"/"+current);
				Document doc = Jsoup.parse(curFile, "UTF-8","http://abcnews.go.com/");
				Node currentNode= idtoNode.get(current);
				g.addNode(currentNode);
				Elements links = doc.select("a[href]");
				Elements media = doc.select("[src]");
		        Elements imports = doc.select("link[href]");
		        final String base = "http://abcnews.go.com";
		        HashMap<String,Integer> removeDupMap = new HashMap<String,Integer>();
		        for(Element link: links)
		        {
		        	
		        	String linkName = link.attr("href").trim(); // check link completion
		        	//System.out.println(linkName);
		        	if(linkName.length()>0 && linkName.charAt(0)=='/')
		        	{
		        		linkName = base + linkName;
		        	}
		        	if(linktoId.containsKey(linkName) && !removeDupMap.containsKey(linkName))
		        	{
		        		//System.out.println(link.attr("href").trim());
		        		removeDupMap.put(linkName, 1);
		        		Node tmp = idtoNode.get(linktoId.get(linkName));
		        		currentNode.addOutgoing(tmp);
		        		tmp.addIncominglink(currentNode);
		        	}
		        }
		        
		        for(Element link : imports)
		        {
		        	String linkName = link.attr("href").trim();
		        	
		        	if(linkName.charAt(0)=='/')
		        	{
		        		linkName = base + linkName;
		        	}
		        	if(linktoId.containsKey(linkName) && !removeDupMap.containsKey(linkName))
		        	{
		        		//System.out.println(link.attr("href").trim());
		        		removeDupMap.put(linkName, 1);
		        		Node tmp = idtoNode.get(linktoId.get(linkName));
		        		currentNode.addOutgoing(tmp);
		        		tmp.addIncominglink(currentNode);		        		
		        	}
		        }
		        
		        for(Element src : media)
		        {
		        	String linkName = src.attr("abs:src").trim();
		        	//System.out.println(linkName);
		        	if(linkName.charAt(0)=='/')
		        	{
		        		linkName = base + linkName;
		        	}
		        	if(linktoId.containsKey(linkName) && !removeDupMap.containsKey(linkName))
		        	{
		        		//System.out.println(src.attr("abs:src").trim());
		        		removeDupMap.put(linkName, 1);
		        		Node tmp = idtoNode.get(linktoId.get(linkName));
		        		currentNode.addOutgoing(tmp);
		        		tmp.addIncominglink(currentNode);	
		        	}
		        }
		        
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		
		return g;
	}
}
