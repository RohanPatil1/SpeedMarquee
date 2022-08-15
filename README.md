# SpeedMarquee

> An Android library which provides textview with marquee behaviour with option to
> configure marquee speed & other configurations.
>> The default TextView in Android doesn't allow to update marquee's speed. 




## Installation

> Step 1. Add the JitPack repository to your build file



```gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
> Step 2. Add the dependency
  ```gradle
    dependencies {
	        implementation 'com.github.RohanPatil1:SpeedMarquee:Tag'
	}
  ```



## Usage

> Using XML
```
<com.rohan.speed_marquee.SpeedMarquee
        android:id="@+id/marqueeTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/dummy_text"
        android:maxLines="1"
        android:textSize="24sp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:marquee_speed="180.0" />
```

## Methods


> Programmatically set marquee speed
* setSpeed() - takes float value to update marquee speed
* getSpeed() - returns the current marquee speed

```
   findViewById<Button>(R.id.speedButton).setOnClickListener {

            //Increment Text3's speed by 100.0
            marqueeTextView3.setSpeed(marqueeTextView3.getSpeed() + 100.0f)
        }
    }
```

> Hold to pause, release to resume
* pauseScroll()  - pauses the marquee text animation
* resumeScroll() - resumes the animation from the point it was paused

```
 marqueeTextView.setOnTouchListener(View.OnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    marqueeTextView.pauseScroll()
                }
                MotionEvent.ACTION_UP -> {
                    marqueeTextView.resumeScroll()
                }
            }
            return@OnTouchListener true
        })
```

## Demo

<img src="https://github.com/RohanPatil1/SpeedMarquee/blob/master/demo.gif" width="360" height="777" />

