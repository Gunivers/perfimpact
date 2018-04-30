package fr.az.perfimpact.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Node extends Tree {

	private double executionTime = 0d;
	private double impact = 0d;
	private long calls = 0L;
	
	private Node father;
	private HashMap<Node,Double> childImpact = new HashMap<Node,Double>();
	
	public Node(String path, Node ... children) {
		super(path, children);
		for (Node child : children) {
			this.childImpact.put(child, 0d);
		}
	}

	public double getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(double executionTime) {
		this.executionTime = executionTime;
	}

	public double getImpact() {
		return impact;
	}

	public void setImpact(double impact) {
		this.impact = impact;
		father.setImpact(this, impact);
	}

	private void setImpact(Node child, double impact) {
		if (childImpact.containsKey(child)) {
			childImpact.put(child, impact);
			this.updateImpact();
		}
	}

	private void updateImpact() {
		for (double impact : childImpact.values()) {
			this.impact += impact;
		}
		this.impact /= childImpact.size();
	}

	public long getCallCount() {
		return calls;
	}

	public void setCallCount(long calls) {
		this.calls = calls;
	}
	
	public void addChild(Node child) {
		super.addChild(child);
		this.childImpact.put(child, 0d);
	}

	@Override
	public ArrayList<String> getTree() {
		ArrayList<String> tree = new ArrayList<String>();
		
		
		tree.add(path);
		
		for (Tree node : children) {
			for (String path : node.getTree()) {
				if (path.endsWith(".mcfunction")) {
					path = path.substring(-11);
					tree.add("|    "+ path +"["+ impact*100 +"%; Calls:"+ calls +"; ExecutionTime:"+ executionTime +"]");
				}
				
				else tree.add("|    "+ path +"["+ impact*100 +"%]");
			}
		}
		
		return tree;
	}
}
