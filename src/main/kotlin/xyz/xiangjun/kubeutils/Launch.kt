package xyz.xiangjun.kubeutils

import javafx.application.Application
import javafx.stage.Stage
import tornadofx.*
import xyz.xiangjun.kubeutils.style.Styles
import xyz.xiangjun.kubeutils.view.MainView

class KubeUtils : App(MainView::class, Styles::class) {
    init {
        reloadViewsOnFocus()
    }

    override fun start(stage: Stage) {
        super.start(stage)
        with(stage) {
            width = 800.0
            height = 600.0
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(KubeUtils::class.java, *args)
}