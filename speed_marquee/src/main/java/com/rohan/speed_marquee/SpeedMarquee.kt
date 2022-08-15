package com.rohan.speed_marquee

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import android.widget.TextView

/**
 *  Date - 15/8/2022 (Rohan Patil)
 */

@SuppressLint("AppCompatCustomView")
class SpeedMarquee(context: Context?, attrs: AttributeSet?, defStyle: Int) :
    TextView(context, attrs, defStyle) {
    lateinit var typedArray: TypedArray

    private var textScroller: Scroller? = null

    // x value when pauseScroll() =|= Utilized to resume from the same point
    private var mXPaused = 0

    // Pause Flag
    var isPaused = true
        private set

    //Default Speed
    private var mScrollSpeed = 222f

    constructor(context: Context) : this(context, null) {
        setSingleLine()
        ellipsize = null
        visibility = VISIBLE
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener) //added listener check
    }

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        android.R.attr.textViewStyle
    ) {
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpeedMarquee)
        mScrollSpeed = typedArray.getFloat(R.styleable.SpeedMarquee_marquee_speed, 222f)

        setSingleLine()
        ellipsize = null
        visibility = VISIBLE
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onDetachedFromWindow() {
        removeGlobalListener()
        super.onDetachedFromWindow()
    }

    /**
     * Start scroll the text from the original position
     */
    fun startScroll() {
        val needsScrolling = checkIfNeedsScrolling()
        // begin from the middle
        mXPaused = -1 * (width / 2)
        // assume it's paused
        isPaused = true
        if (needsScrolling) {
            resumeScroll()
        } else {
            pauseScroll()
        }
        removeGlobalListener()
    }

    /**
     * Resume the text from the point at which the speed was updated
     */
    private fun startScrollAfterUpdate(currX: Int) {
        val needsScrolling = checkIfNeedsScrolling()
        mXPaused = currX  //Point where speed was updated

        isPaused = true
        if (needsScrolling) {
            resumeScroll()
        } else {
            pauseScroll()
        }
        removeGlobalListener()
    }

    /**
     * Removing global listener
     */
    @Synchronized
    private fun removeGlobalListener() {
        try {
            if (onGlobalLayoutListener != null) viewTreeObserver.removeOnGlobalLayoutListener(
                onGlobalLayoutListener
            )
            onGlobalLayoutListener = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Waiting for layout to initiate
     */
    private var onGlobalLayoutListener: OnGlobalLayoutListener? =
        OnGlobalLayoutListener { startScroll() }

    /**
     * Checking if we need scrolling
     */
    private fun checkIfNeedsScrolling(): Boolean {
        measure(0, 0)
        val textViewWidth = width
        if (textViewWidth == 0) return false
        val textWidth = textLength.toFloat()
        return textWidth > textViewWidth
    }

    /**
     * Resume scroll from the point where it was paused
     */
    fun resumeScroll() {
        if (!isPaused) return
        setHorizontallyScrolling(true)
        textScroller = Scroller(this.context, LinearInterpolator())
        setScroller(textScroller)
        val scrollingLen = calculateScrollingLen()
        val distance = scrollingLen - (width + mXPaused)
        val duration = (1000f * distance / mScrollSpeed).toInt()
        visibility = VISIBLE
        textScroller!!.startScroll(mXPaused, 0, distance, 0, duration)
        invalidate()
        isPaused = false
    }

    /**
     * Get the scrolling length of the text in pixel
     */
    private fun calculateScrollingLen(): Int {
        val length = textLength
        return length + width //scrolling length in pixels
    }

    /**
     * Get text length
     */
    private val textLength: Int
        get() {
            val tp = paint
            var rect: Rect? = Rect()
            val strTxt = text.toString()
            tp.getTextBounds(strTxt, 0, strTxt.length, rect)
            val length = rect!!.width()
            rect = null
            return length
        }

    /**
     * Pause scrolling the text & save position to resume
     */
    fun pauseScroll() {
        if (null == textScroller) return
        if (isPaused) return
        isPaused = true

        mXPaused = textScroller!!.currX
        textScroller!!.abortAnimation()
    }

    /**
     * override the computeScroll to restart scrolling when finished so as that
     * the text is scrolled forever
     */
    override fun computeScroll() {
        super.computeScroll()
        if (null == textScroller) return
        if (textScroller!!.isFinished && !isPaused) {
            startScroll()
        }
    }

    /**
     * Allow user to set the speed of scrolling
     */
    fun setSpeed(value: Float) {

        mScrollSpeed = value
        startScrollAfterUpdate(textScroller!!.currX)
    }

    /**
     * Get current speed of scrolling
     */
    fun getSpeed(): Float = mScrollSpeed

    init {
        setSingleLine()
        ellipsize = null
        visibility = VISIBLE
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }
}