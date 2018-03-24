package fr.az.perfimpact.util;

import java.util.ArrayList;
import java.util.HashMap;

public class Node extends Tree {

	private long executionTime = 0L;
	private float impact = 0.f;
	private long calls = 0L;
	
	private Node father;
	private HashMap<Node,Float> childImpact = new HashMap<Node,Float>();
	
	public Node(String path, Node ... children) {
		super(path, children);
		for (Node child : children) {
			this.childImpact.put(child, 0.f);
		}
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public float getImpact() {
		return impact;
	}

	public void setImpact(float impact) {
		this.impact = impact;
		father.setImpact(this, impact);
	}

	private void setImpact(Node child, float impact) {
		if (childImpact.containsKey(child)) {
			childImpact.put(child, impact);
			this.updateImpact();
		}
	}

	private void updateImpact() {
		for (float impact : childImpact.values()) {
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
		this.childImpact.put(child, 0.f);
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
