package xyz.xiangjun.kubeutils.view

import io.fabric8.kubernetes.api.model.Pod
import javafx.scene.layout.HBox
import tornadofx.*
import xyz.xiangjun.kubeutils.style.Styles

class KubeView : View("Kube属性") {
    lateinit var metadata : HBox

    override val root = vbox {
        addClass(Styles.content)
        text("Metadata") {
            addClass(Styles.h1)
        }
        metadata = hbox()
        text("Spec") {
            addClass(Styles.h1)
        }
        text("status") {
            addClass(Styles.h1)
        }
    }

    fun select(pod: Pod?) {
        with(metadata) {
            clear()
            label("name")
            text(pod?.metadata?.name ?: "")
        }
    }
}