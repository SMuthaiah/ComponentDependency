import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.HashMap;


import org.junit.Test;

import InstallApplication.Component;
import InstallApplication.Dependency;
import InstallApplication.Installer;
import InstallApplication.*;

public class InstallerTests {
	
	final static String component = "CHROME";
	final static String[] dependencies = {"BROWSER","WINDOWS"};
	final static String componentB = "BROWSER";
	final static String[] dependenciesB = {"MONITOR"};
	static Dependency dep = Dependency.getInstance();
	static Installer ins = Installer.getInstance();
	InstallerUtility iu = new InstallerUtility();
	
	@BeforeClass
	public static void setUpClass(){	
		dep.makeDependenciesForComponent(component, dependencies);
		dep.makeDependenciesForComponent(componentB, dependenciesB);		
	}
	
	
	/*
	 * Integration Tests
	 */
	@Test
	public void MakeDependencytest(){
		
		Component c = iu.getComponentObjectFromName(component);	
		String[] actualOutput = new String[c.getDependencyList().size()];
		int i=0;
		for(Component comp: c.getDependencyList()){
			
			actualOutput[i++] = comp.getComponentName();	
		}
		
		assertArrayEquals("The Arrays are not equals", dependencies, actualOutput);
		
	}

	
	@Test
	public void CheckGetDependencyTest(){

		Component c = iu.getComponentObjectFromName(component);
		
		
		ArrayList<Component> components = dep.getAllDependenciesForComponent(c);
		String[] actualOutput = new String[components.size()];
		int i=0;
		
		for(Component comp: components){
			actualOutput[i++] = comp.getComponentName();	
		}
		
		String[] expectedOutput = {"CHROME", "BROWSER","WINDOWS","MONITOR"};
		
		assertEquals("The dependencies are not set correctly", expectedOutput, actualOutput);
		
	}
	
	@Test
	public void InstallTest() {
		
		ins.InstallComponent(component);

		HashMap<String,Component> hm = ins.getInstalledComponents();
		
		String s = hm.get(component).getComponentName();
		 
		assertEquals("Failure in installing component", component, s);
	
	}
	
	@Test
	public void removeComponentTest(){	
		ins.removeComponent(component);
		HashMap<String,Component> hm = ins.getInstalledComponents();
		assert(hm.size()==0);
		
	}
	
	/*
	 * Write tests for the UI
	 */
	
	@Test
	public void testDecideOptions(){
		/*
		 * Assert giving a wrong user Input and check if the function returns appropriate error
		 */	
	}
	
	
	/*
	 * Unit tests for the Utility classes
	 * 
	 */
	
	@Test
	public void testCheckComponentIsInstalled(){
		/*
		 * Call the method for a not installed component and see if it says the right
		 * error
		 */
	}
	
	

}
