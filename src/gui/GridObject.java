package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;

public class GridObject {
	private int x;
	private int y;
	private int height;
	private int width;
	private int fill;
	private Component component;
	/**
	 * @return the component
	 */
	public Component getComponent() {
		return component;
	}
	/**
	 * @param component the component to set
	 */
	public void setComponent(Component component) {
		this.component = component;
	}
	/**
	 * @return the fill
	 */
	public int getFill() {
		return fill;
	}
	/**
	 * @param fill the fill to set
	 */
	public void setFill(int fill) {
		this.fill = fill;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
}
