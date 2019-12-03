package workshop1024.com.xproject.login

/**
 * 负责响应登录页面用户的行为，然后处理相关逻辑后，更新登录页面的展示
 */
class LoginPresenter(private val mLoginView: LoginContract.View) : LoginContract.Presenter {
    override fun tryButtonClick() {
        //此处可以网络请求等相关业务逻辑，然后调用View更新页面
        mLoginView.showIntroduce()
    }

    override fun loginButtonClick() {
        mLoginView.showLoginToast()
    }
}