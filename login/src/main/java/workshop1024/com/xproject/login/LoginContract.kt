package workshop1024.com.xproject.login

/**
 * 定义Presenter和View的关系
 */
interface LoginContract {
    /**
     * 定义更新页面UI
     */
    interface View {
        /**
         * 显示Introduce页面
         */
        fun showIntroduceActivity()

        /**
         * 显示登录Toast提示
         */
        fun showLoginToast();
    }

    /**
     * 定义响应页面用户的行为
     */
    interface Presenter {
        /**
         * 尝试按钮点击
         */
        fun tryButtonClick()

        /**
         * 登录按钮点击
         */
        fun loginButtonClick()
    }
}