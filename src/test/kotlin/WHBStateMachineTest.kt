package test.kotlin

import main.kotlin.WHBEvent
import main.kotlin.WHBState
import main.kotlin.WHBStateMachine
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("unitTest")
class WHBStateMachineTest {
    private lateinit var stateMachine: WHBStateMachine

    @BeforeEach
    fun configureSystemUnderTest() {
        stateMachine = WHBStateMachine()
    }

    @Test
    @DisplayName("Should return initial state")
    fun initialTest(){
        assertThat(stateMachine.whbStateMachine.state).isEqualTo(WHBState.Bed)
    }

    @Test
    @DisplayName("Should changed proper state on particular transaction ")
    fun transitionState() {
        val transaction = stateMachine.whbStateMachine.transition(WHBEvent.Wake)
        assertThat(stateMachine.whbStateMachine.state).isEqualTo(WHBState.Home)
    }
}