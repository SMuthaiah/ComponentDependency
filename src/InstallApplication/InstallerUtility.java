package InstallApplication;
import java.util.ArrayList;

/*
 * Utility Methods used by the application.
 */
public class InstallerUtility {

		
	
		public synchronized void addComponent(String component){
			
			Installer installer = Installer.getInstance();
			Component newComponent = new Component(component,false);
			installer.availableComponents.put(component, newComponent);
			return;
			
			}
		
		/*
		 * Exceptions are Possible, Add Try-Catch
		 */
		public void removeCompFromInstalledHashMap(String ComponentName){
			
			Installer installer = Installer.getInstance();
			System.out.println("Removing Component" + " "+ ComponentName);
			installer.installedComponents.remove(ComponentName);
		
		}
		
		
		/*
		 * Exceptions are Possible, Add Try-Catch
		 */
		public boolean checkDependantComponentsNotInstalled(String CompononentName){
			
			Installer installer = Installer.getInstance();
			
			Component compObj = installer.availableComponents.get(CompononentName);
			ArrayList<Component> depandents = null;
			
			if(compObj!=null){
				depandents = compObj.getDependantList();
			}
			
			if(depandents==null){
				return true;
			}
			
			if(depandents.size() == 0)
				return true;
			
			for(Component c:depandents){
				
				if(installer.installedComponents.containsKey(c.getComponentName()))
					return false;	
			}
			return true;
			
			
		}
		
		/*
		 * Exceptions are Possible, Add Try-Catch
		 * Returns True if ComponentIsAvailable
		 */
		public boolean checkComponentIsAvailable(String componentName){
			Installer installer = Installer.getInstance();
			
			if (installer.availableComponents.containsKey(componentName))	
					return true;
			else
					return false;
				
		}
		
		/*
		 * Exceptions are Possible, Add Try-Catch
		 * Returns True if ComponentIsInstalled
		 */
		
		public boolean checkComponentIsInstalled(String componentName){
			Installer installer = Installer.getInstance();
			
			if(installer.installedComponents.containsKey(componentName))
				return true;
			else
				return false;
			
		}

		
		public Component getComponentObjectFromName(String componentName){
			Installer installer = Installer.getInstance();
			
			Component c;
			if(installer.availableComponents.containsKey(componentName))
				c = installer.availableComponents.get(componentName);
			else
				return null;
			
			return c;
		}
		
		
		/*
		 * Print the Listed Components to Console. 
		 */
		
		public boolean printListedComponents(){
			
			Installer installer = Installer.getInstance();
			try{
			if(installer.installedComponents.size()>0){
			for(Component c : installer.installedComponents.values()){		
				System.out.println(".......Component" + " " + c.getComponentName() + " " + "Is Installed");
			}
			return true;
			}
			else{
				return false;
			}
			}
			catch(Exception e){
				System.err.println(e.getMessage());
			}
			return false;
		}




}
