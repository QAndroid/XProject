package workshop1024.com.xproject.model.subscribe.source;

import java.util.List;

import workshop1024.com.xproject.model.subscribe.Subscribe;

/**
 * 已订阅发布者数据源接口，定义了关于已订阅发布者数据相关的处理接口
 */
public interface SubscribeDataSource {
    /**
     * 获取已订阅的发布者信息
     *
     * @param loadSubscribesCallback 加载已订阅发布者信息回调
     */
    void getSubscribes(LoadSubscribesCallback loadSubscribesCallback);

    /**
     * 取消订阅指定id的已发布者
     *
     * @param subscribeId 要取消订阅的已订阅发布者id
     */
    void unSubscribeById(String subscribeId);

    /**
     * 重命名指定id的发布者名称
     *
     * @param subscribeId   重命名的发布者id
     * @param newNameString 发布者重命名的名称
     */
    void reNameSubscribeById(String subscribeId, String newNameString);

    /**
     * 删除所有已订阅的发布者信息
     */
    void deleteAllSubscribes();

    /**
     * 保存已订阅发布者信息
     *
     * @param subscribe
     */
    void saveSubscribe(Subscribe subscribe);

    /**
     * 刷新已订阅的发布者信息
     */
    void refreshSubscribes();

    /**
     * 获取已订阅发布者信息回调
     */
    interface LoadSubscribesCallback {
        /**
         * 加载已订阅发布者信息完毕
         *
         * @param subscribeList 加载返回的有已订阅发布者信息
         */
        void onPublishersLoaded(List<Subscribe> subscribeList);

        /**
         * 没有有效的已订阅发布者信息
         */
        void onDataNotAvailable();
    }
}
