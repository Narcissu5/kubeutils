package xyz.xiangjun.kubeutils.style

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val content by cssclass()
        val h1 by cssclass()
    }

    init {
        toolBar {
            fillWidth = true
            backgroundColor = multi(Color.ALICEBLUE)
        }
        splitPane {
            splitPaneDivider {
                padding = box(1.px)
            }
        }
        content {
            padding = box(5.px)
            spacing = 5.px
        }
        h1 {
            fontSize = 30.px
        }
    }
}