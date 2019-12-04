package workshop1024.com.xproject.introduce

interface IntroduceContract {
    interface View {
        /**
         * 跳转下一个ViewPager的页面
         */
        fun showNextViewPage()

        /**
         * 跳转MainActivity页面
         */
        fun showMainActivity()
    }

    interface Presenter {
        fun skipButtonClick()

        fun nextButtonClick()

        fun doneButtonClick()
    }
}