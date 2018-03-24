package fr.az.perfimpact.util;

import java.util.ArrayList;

public abstract class Tree {

	protected String path = "";
	protected ArrayList<Tree> children = new ArrayList<Tree>();
	protected Tree father;
	
	public Tree(String path, Tree ... children) {
		this.path = path;
		
		if (children != null) {
			for (Tree child : children) this.children.add(child);
		}
	}
	
	
	public ArrayList<String> getTree() {
		ArrayList<String> tree = new ArrayList<String>();
		
		if (path != null && path != "") {
			tree.add(path);
			
			for (Tree node : children) {
				for (String path : node.getTree()) {
					tree.add("|    " + path);
				}
			}
		}

		else {
			for (Tree node : children) {
				for (String path : node.getTree()) {
					tree.add(path);
				}
			}
		}
		
		return tree;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public ArrayList<Tree> getChildren() {
		return this.children;
	}
	
	public ArrayList<Tree> addChild(Tree child) {
		this.children.add(child);
		
		return children;
	}
}
