package com.github.arachnidium.model.mobile;

import org.openqa.selenium.Rotatable;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.interactions.internal.Coordinates;

import com.github.arachnidium.core.MobileScreen;
import com.github.arachnidium.model.common.FunctionalPart;
import com.github.arachnidium.model.support.HowToGetByFrames;

/**
 * This general representation of any mobile screen or its part.
 * Only common behavior is defined here
 *
 */
abstract class MobileContent extends FunctionalPart<MobileScreen> implements TouchScreen, Rotatable{
	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart)}
	 */
	protected MobileContent(MobileContent parent) {
		super(parent, new HowToGetByFrames());
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(FunctionalPart, HowToGetByFrames)}
	 */
	protected MobileContent(MobileContent parent, HowToGetByFrames path) {
		super(parent, path);
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle)
	 */
	protected MobileContent(MobileScreen screen) {
		this(screen, new HowToGetByFrames());
	}

	/**
	 * @see {@link FunctionalPart#FunctionalPart(com.github.arachnidium.core.Handle, HowToGetByFrames))
	 */
	protected MobileContent(MobileScreen screen, HowToGetByFrames path) {
		super(screen, path);
	}

	@Override
	@InteractiveMethod
	public void singleTap(Coordinates where) {
		touch.singleTap(where);		
	}

	@Override
	@InteractiveMethod
	public void down(int x, int y) {
		touch.down(x, y);		
	}

	@Override
	@InteractiveMethod
	public void up(int x, int y) {
		touch.up(x, y);		
	}

	@Override
	@InteractiveMethod
	public void move(int x, int y) {
		touch.move(x, y);		
	}

	@Override
	@InteractiveMethod
	public void scroll(Coordinates where, int xOffset, int yOffset) {
		touch.scroll(xOffset, yOffset);		
	}

	@Override
	@InteractiveMethod
	public void doubleTap(Coordinates where) {
		touch.doubleTap(where);		
	}

	@Override
	@InteractiveMethod
	public void longPress(Coordinates where) {
		touch.longPress(where);		
	}

	@Override
	@InteractiveMethod
	public void scroll(int xOffset, int yOffset) {
		touch.scroll(xOffset, yOffset);
		
	}

	@Override
	@InteractiveMethod
	public void flick(int xSpeed, int ySpeed) {
		touch.flick(xSpeed, ySpeed);		
	}

	@Override
	@InteractiveMethod
	public void flick(Coordinates where, int xOffset, int yOffset, int speed) {
		touch.flick(where, xOffset, yOffset, speed);		
	}

	@Override
	public void rotate(ScreenOrientation orientation) {
		rotator.rotate(orientation);		
	}

	@Override
	public ScreenOrientation getOrientation() {
		return rotator.getOrientation();
	}
}
