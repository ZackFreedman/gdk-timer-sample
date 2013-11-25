Voice-Enabled Timer
===================

Improved by Zack Freedman of Voidstar Lab

This is Google's Timer sample from the GDK, enhanced with an intuitive natural-language voice prompt.

Just say "OK Glass, set timer for..." and tell it how many hours, minutes, and seconds to count. 

You don't have to say all three, and you can use natural language. These all work:
- "2 hours, 20 minutes, 30 seconds".
- "One hour fifteen minutes".
- "A minute."
- "Half an hour."

You can also add "starting now" to begin the timer right away. For example, this will start immediately:
- "Fifteen minutes starting now."

If it can't interpret your command, the card opens with a blank timer so you can configure it by hand.

Once it's set and launched, the timer behaves exactly like Google's version. Tap its card for the menu:

- Set timer: set the time for the timer
- Start: start the timer
- Pause: pause the timer
- Resume: resume the timer
- Reset: reset the timer
- Change timer: change the time of the timer
- Stop: remove the timer from the timeline

## Running the sample on Glass

You can use your IDE to compile and install the sample or use
[`adb`](https://developer.android.com/tools/help/adb.html)
on the command line:

    $ adb install -r VoiceEnabledTimer.apk

To start the sample, say "OK Glass, set a timer for..." from the Glass clock screen or use the touch menu, then tell it how long.

## More Information

Voice-Enabled Timer was modified from Google's platform sample by Zack Freedman of Voidstar Lab. Visit [his site](http://zackfreedman.com "the greatest site on the Web since Zombo.com") for more custom Glassware, first-person video bloggin', and more.