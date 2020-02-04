package xyz.xiangjun.kubeutils.model

import io.fabric8.kubernetes.api.model.Pod
import io.fabric8.kubernetes.api.model.PodBuilder
import io.fabric8.kubernetes.client.DefaultKubernetesClient
import tornadofx.ViewModel
import java.nio.file.Paths


class MainViewModel : ViewModel() {
    val client = DefaultKubernetesClient()

    fun getPodUnderNamespace(nameSpace: String): List<Pod> {
        val pods = client.inNamespace(nameSpace).pods().list()
        return pods.items
    }

    fun restart(pod: Pod, nameSpace: String): String {
//        client.pods().inNamespace(nameSpace).delete(pod)
        pod.metadata.resourceVersion = null
        client.inNamespace(nameSpace).pods().delete(pod)
        val newPod = client.inNamespace(nameSpace).pods().createOrReplace(pod)

        return newPod.metadata.name
    }
}