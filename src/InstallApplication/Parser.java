package InstallApplication;

/*Contains Parsing Methods to parse the given UserCommand in the Console*/

public class Parser {
	

	public String[] getDependencyFromUserInput(String input){
		
		String[] parsedSet = input.split("\\s+");
		String[] resultSet = {};
		try{
			resultSet = new String[parsedSet.length-2];
		}
		catch(NegativeArraySizeException e){
		}
		
		if(parsedSet.length>2){
		for(int i=0;i<resultSet.length;i++){
			resultSet[i] = parsedSet[i+2];
		}
		}
		return resultSet;
		
	}
	
	public String getComponentFromUserInput(String input){
		
		String[] parsedSet = input.split("\\s+");
		String component = null;
		if(parsedSet.length > 1){
			component = parsedSet[1];
		}
		return component;
	}
	
	
	public String getCommandFromUserInput(String input){
		
		String[] parsedSet = input.split("\\s+");
		String command = null;
		
		if(parsedSet.length > 0){
			command = parsedSet[0];
		}
		
		return command;
	}
	
}
