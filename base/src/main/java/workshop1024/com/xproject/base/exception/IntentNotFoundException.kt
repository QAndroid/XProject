package workshop1024.com.xproject.base.exception

class IntentNotFoundException : Throwable {
    //如果有一个主构函数，次构函数需要委托为主构函数
    //参考：次构函数-https://www.kotlincn.net/docs/reference/classes.html
    constructor()

    constructor(message: String) : super(message)

    constructor(case: Throwable) : super(case)

    constructor(message: String, case: Throwable) : super(message, case)
}
