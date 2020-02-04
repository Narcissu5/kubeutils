package xyz.xiangjun.kubeutils.model


import org.junit.Before
import org.junit.Test

class MainModelTest {

    @Before
    fun init() {

    }

    @Test
    fun getPodTest() {
        val model = MainViewModel()
        val podUnderNamespace = model.getPodUnderNamespace("dev")
    }
}
