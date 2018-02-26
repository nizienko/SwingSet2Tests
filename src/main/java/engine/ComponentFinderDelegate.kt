package engine

import configuration.app
import org.fest.swing.fixture.FrameFixture
import kotlin.reflect.KProperty

fun <T> finder(frame: FrameFixture = app.mainFrame, cachingEnabled: Boolean = false, findFunc: FrameFixture.() -> T?): ComponentFinderDelegate<T> =
        ComponentFinderDelegate(frame, cachingEnabled, findFunc)

class ComponentFinderDelegate<out T>(private val frame: FrameFixture, private val cachingEnabled: Boolean, private val findFunc: FrameFixture.() -> T?) {
    private var cachedObject: T? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (cachingEnabled) {
            if (cachedObject == null) {
                cachedObject = frame.findFunc()
            }
            cachedObject!!
        } else {
            frame.findFunc()!!
        }
    }
}