Voice-Enabled Timer
===================

Improved by Zack Freedman of Voidstar Lab

This is Google's Timer sample from the GDK, enhanced with an intuitive natural-language voice prompt.

Just say "OK Glass, set a timer" and tell it how many hours, minutes, and seconds to count. The timer will start immediately.

If you also have the official Timer Glassware installed, you can say "OK Glass, set a timer with voice" to use this app, and "OK Glass, set a timer with Timer" to use Google's version. It's not a bad idea to have both, since Google requires an Internet connection to use voice.

These will all work correctly:
- "2 hours, 20 minutes, 30 seconds"
- "One hour fifteen minutes"
- "A minute"
- "Half an hour"
- "2 and a half hours"

If you add "paused" to your command, the timer will wait for you to start it manually. This is useful if you want to set up a timer in advance. For example:
- "Fifteen minutes paused"

If you add "quietly" to your command, the timer will run in the background. This is useful if you want to run more hands-free commands. When opened quietly, the timer starts sooner. For example:
- "Two and a half minutes quietly"

Most extra words are just ignored, so talk naturally. For example, all of these commands work:
- "One hour and 45 minutes"
- "30 seconds start paused and quietly"
- "15 minutes please"
- "Three hours thirty minutes 45 seconds and a partridge in a pear tree"

If the app can't interpret your command, the card opens with a blank timer so you can configure it by hand.

Once it's set and launched, the timer behaves exactly like Google's version. Tap its card for the menu, which allows you to set, start, pause, resume, reset, change, and stop the timer.

## Running the sample on Glass

You can use your IDE to compile and install the sample or use
[`adb`](https://developer.android.com/tools/help/adb.html)
on the command line:

    $ adb install -r VoiceEnabledTimer.apk

To start the sample, say "OK Glass, set a timer for..." from the Glass clock screen or use the touch menu, then tell it how long.

## More Information

Voice-Enabled Timer was modified from Google's platform sample by Zack Freedman of Voidstar Lab. Visit [his site](http://zackfreedman.com "the greatest site on the Web since Zombo.com") for more custom Glassware, first-person video blogging, and more.

## Changelog:
12/11/13: 
	- Now parses "X and a half Y" phrases
	- Timer now starts running automatically:
		- Removed power word 'starting now'
		- Added power word 'paused'
	- Implemented power word 'quietly' to run in background
	- Properly parses a couple of corner cases like "1&a"
	- Removed parsing for unnecessary words like "starting"
	- Added more Logcat debug strings for parsing

4/16/14:
- Updated for XE16, which actually exists, much to my surprise
- Now ignores leading 'for'
- Uses listed "Start a Timer" voice command
- Uses Glass' requirements to prevent from launching without Internet or mic available