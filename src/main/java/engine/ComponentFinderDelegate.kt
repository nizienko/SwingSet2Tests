package engine

import configuration.app
import org.fest.swing.fixture.FrameFixture
import kotlin.reflect.KProperty

fun <T> finder(frameSupplier: () -> FrameFixture = { app.mainFrame },
               cachingEnabled: Boolean = false,
               findFunc: FrameFixture.() -> T?): ComponentFinderDelegate<T> =
        ComponentFinderDelegate(frameSupplier, cachingEnabled, findFunc)

class ComponentFinderDelegate<out T>(
        private val frameSupplier: () -> FrameFixture,
        private val cachingEnabled: Boolean,
        private val findFunc: FrameFixture.() -> T?) {
    private var cachedObject: T? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (cachingEnabled) {
            if (cachedObject == null) {
                cachedObject = frameSupplier().findFunc()
            }
            cachedObject!!
        } else {
            frameSupplier().findFunc()!!
        }
    }
}