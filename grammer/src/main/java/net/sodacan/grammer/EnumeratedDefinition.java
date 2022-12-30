package net.sodacan.grammer;

import java.util.ArrayList;
import java.util.List;

public class EnumeratedDefinition extends Definition {
	List<String> options = new ArrayList<>();
	
	public EnumeratedDefinition(String name) {
		super(name);
	}

	public boolean hasOption(String option) {
		if (options.contains(option)) return true;
		return false;
	}
	public void addOption(String option) {
		options.add(option);
	}
	
	public String getFirstOption( ) {
		if (options.size()>0) {
			return options.get(0);
		}
		return null;
	}

	public String getNextOption( String currentOption ) {
		if (options.size()==0) {
			return null;
		}
		for (int x=0;x<options.size();x++) {
			if (currentOption.contentEquals(options.get(x))) {
				int y = x++;
				if (x>=options.size()) {
					y = 1;
					return options.get(y);
				}
			}
		}
		throw new RuntimeException("Option not found in " + getName());
	}
}
