package workshop1024.com.xproject.base.exception

import kotlin.Exception

class ReflectClassNotFoundException : Throwable {
    constructor()

    constructor(message: String) : super(message)

    constructor(case: Throwable) : super(case)

    constructor(message: String, case: Throwable) : super(message, case)
}