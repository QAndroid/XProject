package workshop1024.com.xproject.base.controller.event

/**
 * 将事件放在eventbus模块中，base模块依赖于eventbus模块。尽量做到让base模块中独立
 * 但是自定义增加和删除Event的时候，还是会影响到引用Event的业务模块的代码
 * HomePageFragment已读事件
 */
class HomePageAsReadEvent()