package configuration

import engine.TargetApplication
import engine.helpers.matcher

val app = TargetApplication(
        jarPath = "src/test/resources/targetApp/SwingSet2.jar",
        className = "SwingSet2",
        frameMatcher = matcher { "SwingSet2" == it.title && it.isShowing }
)