package main.kotlin

import com.tinder.StateMachine
import java.util.logging.Logger

sealed class WHBState {
    object Work: WHBState()
    object Home: WHBState()
    object Bed: WHBState()
}

sealed class WHBEvent {
    object TakeTrain: WHBEvent()
    object Sleep: WHBEvent()
    object Wake: WHBEvent()
}

sealed class WHBSideEffect {
    object LogTakeTrainTime: WHBSideEffect()
    object LogSleepTime: WHBSideEffect()
    object LogWakeTime: WHBSideEffect()
}

class WHBStateMachine {

    companion object {
        val LOG = Logger.getLogger(WHBStateMachine::class.java.name)
    }

    val whbStateMachine = StateMachine.create<WHBState, WHBEvent, WHBSideEffect> {
        initialState(WHBState.Bed)

        state<WHBState.Bed> {
            on<WHBEvent.Wake> {
                transitionTo(WHBState.Home, WHBSideEffect.LogWakeTime)
            }
        }

        state<WHBState.Home> {
            on<WHBEvent.TakeTrain> {
                transitionTo(WHBState.Work, WHBSideEffect.LogTakeTrainTime)
            }
        }

        state<WHBState.Work> {
            on<WHBEvent.TakeTrain> {
                transitionTo(WHBState.Home, WHBSideEffect.LogTakeTrainTime)
            }
        }

        state<WHBState.Home> {
            on<WHBEvent.Sleep> {
                transitionTo(WHBState.Bed, WHBSideEffect.LogSleepTime)
            }
        }

        onTransition{
            val validTransition = it as? StateMachine.Transition.Valid ?: return@onTransition
            when(validTransition.sideEffect){
                WHBSideEffect.LogWakeTime -> LOG.info("Wake time recorded")
                WHBSideEffect.LogSleepTime -> LOG.info("Sleep time recorded")
                WHBSideEffect.LogTakeTrainTime -> LOG.info("Train time recorded")
            }
        }
    }
}