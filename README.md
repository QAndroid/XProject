# XProject
用于保存和记录XProject项目，通过一个实例案例，不断的实践Android开发技术，优化Android项目架构。

## 项目分支
这个项目以一个项目案例为主线，将使用不同架构和技术实现保存在单独的分支上。查看不同分支的README.md文件来了解该分支的详细介绍。下列表列举了不同分支实现的功能，和使用架构、技术（如下）：

| 分支        | 描述           |
| ------------- |------------- |
| master| 项目的最新稳定代码分支，主要用README.md更新和维护 |
| java-mvc-materialdesign| 该分支使用java语言，mvc结构，使用materialdesign控件实现项目的基本功能 |
| java-mvc-databinding| 该分支基于java-mvc-materialdesign开发，使用databinding组件完成了相关功能的实现 |
| kotlin-mvc| 该分支基于java-mvc-databinding开发，使用Kotlin语言完成了相关功能的实现 |
| kotlin-gradle| 该分支基于kotlin-mvc开发，完成基本的Gradle构建配置优化，包含模块化等 |
| kotlin-componential| 该分支基于kotlin-gradle开发，完成基本的组件化架构的实现，如引入EventBus,迁移AndroidX |
| kotlin-autotest| 该分支基于kotlin-componential开发，使用JUnit、Espresso、UIAnimator等完成自动化测试 |
| kotlin-mvp| 该分支基于kotlin-autotest开发，使用MVP功能实现相关功能 |
| kotlin-mvvm-jetpack| 该分支基于kotlin-mvp开发，使用Jectpack套件实现相关功能(Navigation适用单Activity或Fragment App，Paging目前无引入分页，WorkManager...) |
