package com.github.arachnidium.util.logging;

import java.awt.Color;

/**
 * Stores the list of available {@link Color}
 * for page highlighting 
 */
public enum eLogColors {
	/**
	 * Green
	 */
	CORRECTSTATECOLOR(new Color(0, 255, 0), "Green"), 
	/**
	 * Yellow
	 */
	WARNSTATECOLOR(new Color(
			255, 255, 0), "Yellow"), 
	/**
	 * Snow		
	 */
	DEBUGCOLOR(new Color(255, 250, 250),
			"Snow"), 
	/**
	 * Red		
	 */
	SEVERESTATECOLOR(new Color(255, 0, 0), "Red");

	private Color stateColor;
	private String htmlColorDescription;

	private eLogColors(Color color, String htmlColorDescription) {
		this.stateColor = color;
		this.htmlColorDescription = htmlColorDescription;
	}

	public String getHTMLColorDescription() {
		return htmlColorDescription;
	}

	public Color getStateColor() {
		return stateColor;
	}

}
