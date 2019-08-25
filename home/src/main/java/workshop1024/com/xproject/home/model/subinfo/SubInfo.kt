package workshop1024.com.xproject.home.model.subinfo


data class SubInfo(//Info的id var
        var infoId: String, //Info图标URL
        var iconUrl: String, //Info名称
        var name: String, //Info未阅读新闻数量
        var unreadCount: String)