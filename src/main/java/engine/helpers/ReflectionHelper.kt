package engine.helpers

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
fun <R : Any?> Any.readProperty(propertyName: String): R =
        this.javaClass.kotlin.declaredMemberProperties
                .first { it.name == propertyName }
                .apply { isAccessible = true }.get(this) as R
