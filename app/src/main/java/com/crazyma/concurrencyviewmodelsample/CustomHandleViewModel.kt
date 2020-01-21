package com.crazyma.concurrencyviewmodelsample

import android.util.Log
import kotlinx.coroutines.*
import java.lang.RuntimeException

class CustomHandleViewModel : CustomViewModel() {

    val result = SingleLiveEvent<Int>()

    private val controlledRunner = ConcurrencyHelper.ControlledRunner<Unit>()

    fun runSuccessfulTest(delayTime: Int){
        launchTest(delayTime)
        launchTest(delayTime)
        launchTest(delayTime)
    }

    fun runFailTest2(){
        launchTest2(-1)
        launchTest2(-2)
        launchTest2(-3)
    }

    private fun launchTest(delayTime: Int){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("badu", "[Custom Handle] run test with $delayTime seconds delayed}")
            controlledRunner.customJoinPreviousOrRun {
                task(delayTime)
            }
        }
    }

    private fun launchTest2(errorCode: Int){
        viewModelScope.launch(Dispatchers.IO) {
            Log.v("badu", "launch CoroutineScope for test 2")
            try {
                controlledRunner.customJoinPreviousOrRun2 {
                    val number = failTask(errorCode)
                    result.postValue(number)
                }
            }catch (t: Throwable){
                result.postValue(-100)
            }
        }
    }

    private suspend fun task(delayTime: Int) {
        delay(delayTime * 1000L)
        Log.v("badu", "[Custom Handle] end delayed after $delayTime seconds}")
        result.postValue(delayTime)
    }

    private suspend fun failTask(errorCode: Int = 0): Int{
        delay(3 * 1000L)
        Log.v("badu", "get fail api result")
        throw RuntimeException("XD ($errorCode)")
        return 5566
    }


}