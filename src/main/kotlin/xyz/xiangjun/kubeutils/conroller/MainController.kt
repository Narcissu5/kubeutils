package xyz.xiangjun.kubeutils.conroller

import io.fabric8.kubernetes.api.model.Pod
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.control.Alert
import kotlinx.coroutines.runBlocking
import tornadofx.Controller
import tornadofx.getProperty
import tornadofx.property
import xyz.xiangjun.kubeutils.model.MainViewModel
import xyz.xiangjun.kubeutils.view.KubeView

class MainController : Controller() {
    val model by inject<MainViewModel>()
    val kubeView by inject<KubeView>()

    var canChangeNamespace = SimpleBooleanProperty(true)

    val namespaces = FXCollections.observableArrayList<String>("citest", "dev")
    val selectedNamespace = SimpleStringProperty()

    val podFilter = SimpleStringProperty()
    val allPods = mutableListOf<Pod>()
    val showPods = FXCollections.observableArrayList<Pod>()
    var selectedPod by property<Pod>()
    fun selectedPodProperty() = getProperty(MainController::selectedPod)

    fun init() {
        selectedNamespace.addListener { _, _, newValue ->
            canChangeNamespace.set(false)
            showPods.clear()
            if (newValue != null) {
                runAsync {
                    model.getPodUnderNamespace(newValue)
                } ui {
                    allPods.addAll(it)
                    showPods.addAll(it)
                    canChangeNamespace.set(true)
                }
            }
        }
        selectedPodProperty().addListener { _, _, newValue ->
            if (newValue != null) {
                kubeView.select(newValue)
            }
        }
    }

    fun restart() {
        runAsync {
            model.restart(selectedPod, selectedNamespace.get())
        } ui {
            val alert =  Alert(Alert.AlertType.INFORMATION)
            alert.contentText = "new pod is $it"
            alert.showAndWait()
        }
    }

    fun filterPod() {
        showPods.setAll(allPods.filter { it.metadata.name.contains(podFilter.get(), true) })
    }
}