package InstallApplication;
import java.util.ArrayList;

/**Class to define Individual Components**/

public class Component {
	
	private String componentName;
	boolean isVisited;
	private ArrayList<Component> dependencyList;
	private ArrayList<Component> dependantList; 
	
	public Component(String name, boolean isVisited){
		
		this.componentName = name;
		this.isVisited = isVisited; 
		dependencyList = new ArrayList<Component>(); 
		dependantList = new ArrayList<Component>();
		
	}
	
	
	/******GETTERS & SETTERS For Component Attributes******/
	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void setVisited(boolean isVisited) {
		this.isVisited = isVisited;
	}

	public ArrayList<Component> getDependencyList() {
		return dependencyList;
	}

	public void setDependencyList(ArrayList<Component> dependencyList) {
		this.dependencyList = dependencyList;
	}

	public ArrayList<Component> getDependantList() {
		return dependantList;
	}

	public void setDependantList(ArrayList<Component> dependantList) {
		this.dependantList = dependantList;
	}
}
