package workshop1024.com.xproject.introduce

class IntroducePresenter(private val mIntroduceView: IntroduceContract.View) : IntroduceContract.Presenter {

    override fun skipButtonClick() {
        mIntroduceView.showMainActivity()
    }

    override fun nextButtonClick() {
        mIntroduceView.showNextViewPage()
    }

    override fun doneButtonClick() {
        mIntroduceView.showMainActivity()
    }
}