package com.patsergames.robovm.bindings.gpgs;

import org.robovm.cocoatouch.foundation.NSObject;
import org.robovm.cocoatouch.foundation.NSString;
import org.robovm.objc.ObjCClass;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.Selector;
import org.robovm.objc.annotation.NativeClass;
import org.robovm.rt.bro.annotation.Bridge;
import org.robovm.rt.bro.annotation.Library;

/**
 * An object that represents the completion of a task or goal.
 * Achievement objects must be created with an achievement ID. Once an achievement object is created, you may interact with it using the provided set of actions.
 */
@Library(Library.INTERNAL)
@NativeClass()
public class GPGAchievement extends NSObject{
	private static final ObjCClass objCClass = ObjCClass.getByType(GPGAchievement.class);
	
	static {
		ObjCRuntime.bind(GPGAchievement.class);
	}
	
	
	//it should also have a convenience constructor, I have no idea how to implement that one.
	/**
	 * Initializes a newly allocated achievement object with the given achievement identifier. This is the designated initializer.
	 * 
	 * @param achievementId
	 *            The achievement identifier to bind to this object.
	 * @return The GPGAchievement object initialized with the given achievement identifier.
	 */
	public GPGAchievement(String achievementId) {
		objc_init(this, initWithAchievementId, new NSString(achievementId));
	}
	
	/**
	 * Initiates a request to mark this achievement as unlocked.
	 * If showsCompletionNotification is YES and this achievement has not previously been unlocked, then an achievement completion notification will be shown immediately.
	 * The completionHandler block will be called upon completion of the request. If the request fails in any way then the error parameter will be non-nil and newlyUnlocked undefined.
	 * Completing an achievement implicitly reveals it. Calling the unlock action will cancel any active reveal actions.
	 * If the device is offline when an achievement is unlocked then the achievement's state will be synced with the Google Play Games server once the Internet connection returns. The achievement will still be marked as unlocked locally.
	 * 
	 * @param completionHandler
	 *            	(optional) A block of the form: ^(BOOL newlyUnlocked, NSError *error). newlyUnlocked will be YES if the achievement was newly unlocked after this request was made.
	 */
	public void unlockAchievementWithCompletionHandler(GPGAchievementDidUnlockBlock completionHandler){
		//can I pass completionHandler like this?
		objc_unlock(this, unlock, completionHandler);
	}
	
	/**
	 * Initiates a request to reveal this achievement.
	 * The completionHandler block will be called upon completion of the request. If the request fails in any way then the error parameter will be non-nil.
	 * If the device is offline when an achievement is revealed then the achievement's state will be synced with the Google Play Games server once the Internet connection returns.
	 * 
	 * @param completionHandler
	 *            	(optional) A block of the form: ^(GPGAchievementState state, NSError *error).
	 */
	public void revealAchievementWithCompletionHandler(GPGAchievementDidRevealBlock completionHandler){
		objc_reveal(this, reveal, completionHandler);
	}
	
	/**
	 * Initiates a request to increment this achievement.
	 * The completionHandler block will be called upon completion of the request. If the request fails in any way then the error parameter will be non-nil.
 	 * If the device is offline when an achievement is incremented then the achievement's state will be synced with the Google Play Games server once the Internet connection returns.
	 * 
	 * @param steps
	 * 				The number of steps by which to increment this partial achievement.
	 * @param completionHandler
	 *            	(optional) A block of the form: ^(BOOL newlyUnlocked, int currentSteps, NSError *error).
	 */
	public void incrementAchievementNumSteps(int steps, GPGAchievementDidIncrementBlock completionHandler){
		//I tried new NSInteger(steps) but it could not compile, can I just pass it?
		objc_increment(this, increment, steps, completionHandler);
	}
	
	// ================
	// SELECTORS
	// ================
	
	//what are these exactly used for?
	private static final Selector initWithAchievementId = Selector.register("initWithAchievementId");
	private static final Selector achievementWithId = Selector.register("achievementWithId");
	private static final Selector unlock = Selector.register("unlock");
	private static final Selector reveal = Selector.register("reveal");
	private static final Selector increment = Selector.register("increment");
	
	//these are supposed to be fields? How can you specify field type? e.g. int, string, boolean. Is default value automatically passed?
	private static final Selector achievementId$ = Selector.register("achievementId");
	private static final Selector showsCompletionNotification$ = Selector.register("showsCompletionNotification");//should be public? How to assign default value?
	
	// ================
	// BRIDGES
	// ================
	
	//so you have to pass 'self' in the first argument?
	@Bridge
	private native static GPGAchievement objc_init(NSObject __self__, Selector __cmd__, NSString achievementId);
	private native static void objc_unlock(NSObject __self__, Selector __cmd__, GPGAchievementDidUnlockBlock completionHandler);
	private native static void objc_reveal(NSObject __self__, Selector __cmd__, GPGAchievementDidRevealBlock completionHandler);
	private native static void objc_increment(NSObject __self__, Selector __cmd__, int steps, GPGAchievementDidIncrementBlock completionHandler);//could not find NSInteger
}
