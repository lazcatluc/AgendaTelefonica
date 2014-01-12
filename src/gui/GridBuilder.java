package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class GridBuilder {
	private GridBagConstraints gbc;
	private JPanel container;
	private List<GridObject> objects = new ArrayList<>();

	private int columns;

	public GridBuilder() {
		gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);
		gbc.anchor = GridBagConstraints.NORTHEAST;
	}

	public GridBuilder withColumns(int columns) {
		this.columns = columns;
		return this;
	}
	
	public GridBuilder withContainer(JPanel container) {
		this.container = container;
		return this;
	}
	
	public GridObject getGridObject(Component component) {
		GridObject gridObject = new GridObject();
		gridObject.setComponent(component);
		gridObject.setX(objects.size() % columns);
		gbc.gridx = gridObject.getX();
		gridObject.setY(objects.size() / columns);
		gbc.gridy = gridObject.getY();
		objects.add(gridObject);
		return gridObject;
	}
	
	public GridObject getGridObject(Component component, int fill) {
		GridObject object = getGridObject(component);
		object.setFill(fill);
		gbc.fill = fill;
		return object;
	}

	public GridBuilder withObject(Component component, int fill,
			int width, int height) {
		GridObject gridObject = getGridObject(component, fill);
		gridObject.setWidth(width);
		gbc.gridwidth = width;
		gridObject.setHeight(height);
		gbc.gridheight = height;
		container.add(component,gbc);
		return this;
	}
	
	public GridBuilder withHorizontal(Component component, int width, int height) {
		return withObject(component, GridBagConstraints.HORIZONTAL, width, height);
	}

	public GridBuilder withObject(Component component, int fill,
			int width) {
		GridObject gridObject = getGridObject(component, fill);
		gridObject.setWidth(width);
		gbc.gridwidth = width;
		container.add(component,gbc);
		return this;
	}
	
	public GridBuilder withHorizontal(Component component, int width) {
		return withObject(component, GridBagConstraints.HORIZONTAL, width);
	}
	
	public GridBuilder withNone(Component component, int width) {
		return withObject(component, GridBagConstraints.NONE, width);
	}
	
	public GridBuilder withObject(Component component, int fill) {
		getGridObject(component, fill);
		container.add(component,gbc);
		return this;
	}
	
	public GridBuilder withHorizontal(Component component) {
		return withObject(component, GridBagConstraints.HORIZONTAL);
	}
	
	public GridBuilder withNone(Component component) {
		return withObject(component, GridBagConstraints.NONE);
	}
	
	public GridBuilder withObject(Component component) {
		getGridObject(component);
		container.add(component,gbc);
		return this;
	}

}
