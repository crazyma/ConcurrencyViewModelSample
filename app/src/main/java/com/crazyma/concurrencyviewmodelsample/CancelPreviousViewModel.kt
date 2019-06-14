package com.crazyma.concurrencyviewmodelsample

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CancelPreviousViewModel: CustomViewModel()  {

    val result = SingleLiveEvent<Int>()

    private val controlledRunner = ConcurrencyHelper.ControlledRunner<Unit>()

    fun runTest(delayTime: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            Log.i("badu", "[Handle Last] run test with $delayTime seconds delayed}")
            controlledRunner.cancelPreviousThenRun {
                task(delayTime)
            }
        }

    }

    private suspend fun task(delayTime: Int) {
        delay(delayTime * 1000L)
        Log.v("badu", "[Handle Last] end delayed after $delayTime seconds}")
        result.postValue(delayTime)
    }

}