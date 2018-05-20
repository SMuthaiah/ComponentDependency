package InstallApplication;
import java.util.ArrayList;
import java.util.HashMap;

public class Installer {

	private static Installer installerInstance = null;
	private static Object mutex = new Object();
	InstallerUtility tools = new InstallerUtility();
	
	HashMap<String, Component> installedComponents = new HashMap<String,Component>();
	HashMap<String, Component> availableComponents = new HashMap<String, Component>();

	
	private Installer(){
	}
	
			/*
			 * Make the Installer class as Singleton and thread safe.
			 */
			public static Installer getInstance(){
				if(installerInstance == null){
					synchronized(mutex){
						if(installerInstance == null){
							installerInstance = new Installer();
						}
						
					}
				}	
				return installerInstance;
			}
	
	/*
	 * Getters and Setters for the Installer attribute
	 */
	public HashMap<String,Component> getInstalledComponents() {
		return installedComponents;
	}

	//Set Installed Components
	public void setInstalledComponents(HashMap<String,Component> installedComponents) {
		this.installedComponents = installedComponents;
	} 
	
	public HashMap<String, Component> getAvailableComponents() {
		return availableComponents;
	}

	public void setAvailableComponents(HashMap<String, Component> availableComponents) {
		this.availableComponents = availableComponents;
	}

	
	public synchronized boolean InstallComponent(String component){
		
		Dependency dep = Dependency.getInstance();	
		
		try{
		if(!tools.checkComponentIsInstalled(component)){
		
			if(!tools.checkComponentIsAvailable(component)){
			tools.addComponent(component);	
			}
		
			Component compObj = tools.getComponentObjectFromName(component);
			ArrayList<Component> dependencies = dep.getAllDependenciesForComponent(compObj);
		
		
			for(Component c:dependencies){
			
			String ComponentName = c.getComponentName();
			
			
			/*
			 * Installing the dependencies first but check that 
			 * it is not already installed and it is not the actual component.
			 */
			if(!this.installedComponents.containsKey(ComponentName) && (!c.getComponentName().equals(component))){
				System.out.println("Installing Dependency Component" + " " + ComponentName);
				this.installedComponents.put(ComponentName, c);
			}
			
			
		}
		
			/*
			 * Installing the actual component here. 
			 */
			System.out.println(" Installing..." + component);
			this.installedComponents.put(component, compObj);
		
			return true;
		}
		else{
			return false;
		}
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
		
		return false;
		
	}
	
	/*
	 * Return int values to the UI to print success/failure message accordingly
	 */
	public synchronized int removeComponent(String component){
		
		if(!tools.checkComponentIsInstalled(component)){
			return 0;
		}
		
		if(tools.checkDependantComponentsNotInstalled(component)){
			
			Component comp = tools.getComponentObjectFromName(component);
			ArrayList<Component> dependencyComponents =  comp.getDependencyList();
			
			tools.removeCompFromInstalledHashMap(component);
			
			for(Component c: dependencyComponents){
				
				boolean isOKtoUnistall =  true;
				ArrayList<Component> depandants = c.getDependantList();
				
				for(Component com : depandants){
					
					if(tools.checkComponentIsInstalled(com.getComponentName()))
						isOKtoUnistall = false;
					
					
				}
				
				if(isOKtoUnistall){
					
					tools.removeCompFromInstalledHashMap(c.getComponentName());
					
				}	
				
			}
			return 1;
		}
		
		else{
			return 2;
		}
			
		
	}
	
}
