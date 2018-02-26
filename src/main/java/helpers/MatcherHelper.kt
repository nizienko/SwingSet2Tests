package helpers

import org.fest.swing.core.GenericTypeMatcher
import java.awt.Component


inline fun <reified T : Component> matcher(crossinline matchFun: (component: T) -> Boolean): GenericTypeMatcher<T> =
        object : GenericTypeMatcher<T>(T::class.java) {
            override fun isMatching(component: T): Boolean {
                return matchFun(component)
            }
        }