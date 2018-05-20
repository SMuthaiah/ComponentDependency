package InstallApplication;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Dependency {
	
	private static Object mutex = new Object();
	private static Dependency dependencyInstance = null;
	Installer installer = Installer.getInstance();
	InstallerUtility tools = new InstallerUtility();
	
	private Dependency(){	
	}
	
	/*Make the dependency class as Singleton.
	 * Also make it thread-safe. 
	 */
	public static Dependency getInstance(){
		
			if(dependencyInstance == null){
				synchronized(mutex){
					if(dependencyInstance == null){
						dependencyInstance = new Dependency();
					}
					
				}
			}	
			return dependencyInstance;
		}
	
		
		public void makeDependenciesForComponent(String Component, String[] dependencies){
			
			Component comp;
			Component dep;
			
			if(tools.checkComponentIsAvailable(Component))
					comp = tools.getComponentObjectFromName(Component);
			else{
				tools.addComponent(Component);
				  	comp = tools.getComponentObjectFromName(Component);
			}
			
			/*
			 * check if the dependency components are in the available components.
			 * And add it to the dependencies list. 
			 * And Add the component to the respective depandant's list. 
			 */	
			for(int i=0; i<dependencies.length;i++){
				
				if(tools.checkComponentIsAvailable(dependencies[i])){
					dep = tools.getComponentObjectFromName(dependencies[i]);
					if(comp!=null && dep!=null){
						comp.getDependencyList().add(dep);
						dep.getDependantList().add(comp);
					}
				}
					
			else{
					tools.addComponent(dependencies[i]);
				  	dep = tools.getComponentObjectFromName(dependencies[i]);
				  	if(comp!=null && dep!=null){
						comp.getDependencyList().add(dep);
						dep.getDependantList().add(comp);
					}
			}
					
			}
			
		}
		
		
		/*This is the Breadth-first search algorithm (BFS) for traversing our graph 
		 * and identifying all the dependencies for a particular Component.
		 */
		
		public ArrayList<Component> getAllDependenciesForComponent(Component Component){
			
			ArrayList<Component> dependencies = new ArrayList<Component>();
			Queue<Component> q = new LinkedList<Component>();
			
			q.offer(Component);
			Component.setVisited(true);
			
			while(!q.isEmpty()){
			
			Component compFromQ = q.poll();
			dependencies.add(compFromQ);
			
			ArrayList<Component> comp = compFromQ.getDependencyList();
			
			
			if(comp!=null && comp.size() > 0){
				
				for(Component c: comp){
					
					if(c.isVisited() == false)
					{
						c.setVisited(true);
						q.offer(c);
					}
					
				}
				
			}
			
			}
			
			//Reset the Flags back to false. 
			HashMap<String, Component> hm= installer.getAvailableComponents();
			for(Component c:hm.values()){	
				c.setVisited(false);	
			}
			
			
			return dependencies;
		}
	
}
