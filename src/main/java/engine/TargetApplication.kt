package engine


import org.fest.swing.core.BasicRobot
import org.fest.swing.core.GenericTypeMatcher
import org.fest.swing.core.Robot
import org.fest.swing.finder.WindowFinder
import org.fest.swing.fixture.FrameFixture
import org.fest.swing.launcher.ApplicationLauncher.application
import java.awt.Frame
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import kotlin.concurrent.thread

data class TargetApplication(
        private val jarPath: String,
        private val className: String,
        private val frameMatcher: GenericTypeMatcher<Frame>) {

    private var applicationThread: Thread? = null
    private var appFrame: FrameFixture? = null
    private var robot: Robot? = null

    val mainFrame: FrameFixture
        get() {
            if (appFrame == null) {
                start()
            }
            return appFrame!!
        }


    fun start() {
        if (applicationThread == null) {
            applicationThread = thread {
                val classLoader = URLClassLoader(arrayOf<URL>(File(jarPath).toURI().toURL()))
                try {
                    val clazz = classLoader.loadClass(className)
                    application(clazz).start()
                } catch (e: ClassNotFoundException) {
                    throw IllegalArgumentException("Bad className $className defined in Config.kt", e)
                }
            }
            robot = BasicRobot.robotWithCurrentAwtHierarchy()
            appFrame = WindowFinder.findFrame(frameMatcher).using(robot)
        }
    }


    fun stop() {
        robot?.cleanUp()
        robot = null
        appFrame = null
        applicationThread?.join()
        applicationThread = null
    }
}
