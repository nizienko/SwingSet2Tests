package engine

import org.fest.swing.core.BasicRobot
import org.fest.swing.core.GenericTypeMatcher
import org.fest.swing.core.Robot
import org.fest.swing.finder.WindowFinder
import org.fest.swing.fixture.FrameFixture
import java.awt.Frame
import java.io.File
import java.net.URL
import java.net.URLClassLoader

data class TargetApplication(
        private val jarPath: String,
        private val className: String,
        private val frameMatcher: GenericTypeMatcher<Frame>) {

    private var thread: Thread? = null
    private var appFrame: FrameFixture? = null
    private var robot: Robot? = null

    val mainFrame: FrameFixture
        get() {
            if (appFrame == null) {
                start()
            }
            return appFrame!!
        }


    fun start() = synchronized(this) {
        if (thread == null) {
            thread = Thread(Runnable {
                val classLoader = URLClassLoader(arrayOf<URL>(File(jarPath).toURI().toURL()))
                try {
                    val clazz = classLoader.loadClass(className)
                    val main = clazz.getMethod("main", Array<String>::class.java)
                    main.invoke(null, arrayOf<Any>(*arrayOf<String>()))
                } catch (e: ClassNotFoundException) {
                    throw IllegalArgumentException("Bad className $className defined in config", e)
                } catch (e: NoSuchMethodException) {
                    throw IllegalArgumentException("$className doesn't contains main()", e)
                }
            })

            thread!!.start()

            robot = BasicRobot.robotWithCurrentAwtHierarchy()
            appFrame = WindowFinder.findFrame(frameMatcher).using(robot)
        }
    }


    fun stop() {
        synchronized(this) {
            robot?.cleanUp()
            robot = null
            appFrame = null
            thread?.join()
            thread = null
        }
    }
}