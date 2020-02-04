package xyz.xiangjun.kubeutils.view

import io.fabric8.kubernetes.api.model.Pod
import javafx.beans.property.ReadOnlyObjectWrapper
import javafx.scene.control.TabPane
import javafx.scene.input.KeyCode
import javafx.scene.layout.Priority
import tornadofx.*
import xyz.xiangjun.kubeutils.conroller.MainController

class MainView : View() {
    val controller: MainController by inject()

    override val root = vbox {
        toolbar {
            combobox(controller.selectedNamespace, controller.namespaces) {
                enableWhen(controller.canChangeNamespace)
            }
            button("Add")
            separator()
            button("Restart") {
                setOnAction {
                    controller.restart()
                }
            }
        }
        splitpane {
            useMaxHeight = true
            setDividerPositions(0.2)
            vgrow = Priority.ALWAYS
            vbox {
                hgrow = Priority.ALWAYS
                hbox {
                    textfield(controller.podFilter) {
                        hgrow = Priority.ALWAYS
                        setOnKeyPressed {
                            if (it.code == KeyCode.ENTER) {
                                controller.filterPod()
                            }
                        }
                    }
                    button("Search") {
                        setOnAction {
                            controller.filterPod()
                        }
                    }
                }
                tableview(controller.showPods) {
                    vgrow = Priority.ALWAYS
                    bindSelected(controller.selectedPodProperty())
                    val nameColumn = column<Pod, String>("name") {
                        ReadOnlyObjectWrapper(it.value.metadata.name)
                    }
                    nameColumn.prefWidth = 80.0
                    column<Pod, String>("status") {
                        ReadOnlyObjectWrapper(it.value.status.phase)
                    }
                    resizeColumnsToFitContent()
                }
            }
            tabpane {
                tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
                tab<KubeView> {

                }
                tab("Spring属性") {

                }
            }

        }
    }

    override fun onDock() {
        controller.init()
    }
}