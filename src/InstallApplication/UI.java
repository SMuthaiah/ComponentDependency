package InstallApplication;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

	private static Scanner sc;
	private static Parser parser;
	private static Dependency dependency;
	private static Installer installer;
	private static InstallerUtility tools;
	
	/*Initialize all the classes needed to run the application*/
	public UI(){
		sc = new Scanner(System.in);
		parser = new Parser();
		tools= new InstallerUtility();
		dependency = Dependency.getInstance();
		installer = Installer.getInstance();
	}
	
	private static void GoToSelectedOption(){
		
		
		boolean remainInConsole = true;
		String userCommand;
		String command;
		String component;
		
		System.out.println("WELCOME TO SALESFORCE INSTALLER");
		System.out.println("Enter your commands below");
		System.out.println("================================");
		System.out.println("                                ");
		
		while(remainInConsole){
		
		try{
			userCommand = sc.nextLine();
		}
		catch(InputMismatchException e){
			userCommand = "ERROR";
		}
		
		command = parser.getCommandFromUserInput(userCommand);
		component = parser.getComponentFromUserInput(userCommand);
		
		switch(command){
		
		case "DEPEND":
			
			boolean dependSuccess = false;
			
			String[] dependencies = parser.getDependencyFromUserInput(userCommand);
			
			if(dependencies.length>0){
			try{
			dependency.makeDependenciesForComponent(component,dependencies);
			dependSuccess=true;
			}
			catch(Exception e)
			{
				System.err.println(e.getMessage());
			}
			}
			else{
				System.err.println(".......Incorrect Command!");
			}
			
			if(dependSuccess)
				System.out.println(".......Dependencies are saved!");
			break;
			
		case "INSTALL":
			
			boolean installSuccess = false;
			if(component!=null){
				try{
				installSuccess = installer.InstallComponent(component);
				}
				catch(Exception e){
					System.err.println(e.getMessage());
				}
				if(installSuccess)
					System.out.println(".......Component Successfully Installed!");
				else
					System.err.println(".......Component Might have already been Installed!");
			}
			else
				System.err.println(".......Please Provide Component Name");
			
			break;
		
		case "REMOVE":
			
			int removeSuccess = 3;
			
			if(component!=null){
			try{
				removeSuccess=installer.removeComponent(component);
			}
			catch (Exception e){
				System.err.println(e.getMessage());
			}
			if(removeSuccess==1)
				System.out.println(".......Component Successfully removed");
			else if(removeSuccess==2)
				System.err.println(".......This component cannot be removed as other Components depend on it");
			else if(removeSuccess==0)
				System.err.println(".......This Component has never been Installed");
			else
				System.err.println(".......Some Exception in removing the Component");
			}
			else
				System.err.println("........Please Provide Component Name");
			break;
			
		case "LIST":
			
			boolean listSuccess = false;
			
			try{
			listSuccess = tools.printListedComponents();
			}
			catch(Exception e){
				System.err.println(e.getMessage());
			}
			if(!listSuccess)
				System.out.println(".......There are no Installed Components");
			
			break;
			
		case "END":

			remainInConsole=false;
			
			break;
			
		case "ERROR":
				
			break;
			
		default:
			
			System.out.println("Please enter a valid command, example:DEPEND TCPIP NETSTAT");
			
			break;
		
		
		}
		
		}		
		
	}
	
	//Entry Point of the Program. 
	//Initializes the different Components of the application. 
	public static void main(String args[]){	
		UI ui = new UI();
		GoToSelectedOption();
	
	}
	
	
	
}
