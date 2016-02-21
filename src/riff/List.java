package riff;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class List extends Container {

	LinkedHashMap<String, ArrayList<Container>> containers;
	ArrayList<String> types;
	ArrayList<Integer> indices;
	
	public List(int listSize, String listType) {
		super(listSize, listType);
		containers = new LinkedHashMap<String, ArrayList<Container>>();
		types = new ArrayList<String>();
		indices = new ArrayList<Integer>();
	}

	public void addContainer(Container container, String type) {
		ArrayList<Container> tmp = null;
		if (containers.containsKey(type)) {
			tmp = containers.get(type);
			tmp.add(container);
			containers.put(type, tmp);
		} else {
			tmp = new ArrayList<Container>();
			tmp.add(container);
			containers.put(type, tmp);
		}

		indices.add(tmp.size()-1);
		types.add(type);
	}
	
	public Container getContainer(String type) {
		return containers.get(type).get(0);
	}

	public Container getContainer(String type, int index) {
		return containers.get(type).get(index);
	}
	
	public Container getContainer(int index) {
		return containers.get(types.get(index)).get(indices.get(index));
	}
	
	public int getNumContainers() {
		return types.size();
	}
}
