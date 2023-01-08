package com.example.msgshareapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class CoroutineActivity: AppCompatActivity() {
    private val TAG: String = "CoroutineActivityHere"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        val bundle : Bundle? = intent.extras
        val msg : String = bundle!!.get("user_msg") as String

        val newMsg = findViewById<TextView>(R.id.showMsg)
        newMsg.text = msg
//async and await
        GlobalScope.launch(Dispatchers.IO) {
            val time = measureTimeMillis {
                val answer1 = async { networkCall1() }
                val answer2 = async{networkCall2()}
                Log.d(TAG,"Call1 Success ${answer1.await()}")
                Log.d(TAG,"Call2 Success ${answer2.await()}")
            }
            Log.d(TAG,"Call Success takes times $time")
        }
//        GlobalScope.launch {
//            delay(3000L)
//            val ans1 = networkCall()
//            val ans2 = networkCall2()
//            Log.d(TAG,"Coroutine said Hello from thread ${Thread.currentThread().name}")
//            Log.d(TAG, ans1)
//            Log.d(TAG, ans2)
//        }

//        GlobalScope.launch(Dispatchers.IO) {
//            val ans1 = networkCall()
//            val ans2 = networkCall2()
//            Log.d(TAG,"Coroutine said Hello from thread ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main){
//                val bundle : Bundle? = intent.extras
//               val msg : String = bundle!!.get("user_msg") as String
//
//                val newMsg = findViewById<TextView>(R.id.showMsg)
//                newMsg.text = ans1
//                Log.d(TAG,"Setting text in Coroutine said Hello from thread ${Thread.currentThread().name}")
//            }
//
//            Log.d(TAG, ans1)
//            Log.d(TAG, ans2)
//        }
//        Log.d(TAG, "before run-blocking")
//        runBlocking(Dispatchers.IO) {
//            Log.d(TAG, "starting run-blocking")
//            delay(5000L)
//            Log.d(TAG, "end of run-blocking")
//        }
//        val job = GlobalScope.launch(Dispatchers.Default) {
//            repeat(5){
//                Log.d(TAG, "Coroutine is still working...")
//                delay(1000L)
//            }
//            Log.d(TAG, "Starting long running calculation..")
//            if(isActive)
//            withTimeout(2000L){
//            for(i  in 30..40) {
//                Log.d(TAG, "Result for i = $i -> ${fib(i)}")
//            }
//            }
//            Log.d(TAG, "Ending long running calculation..")
//        }

//        runBlocking {
//            delay(2000L)
//            job.cancel()
//            Log.d(TAG,"the main thread is continuing ${Thread.currentThread().name}")
//
//        }
        Log.d(TAG,"After run-blocking said Hello from thread ${Thread.currentThread().name}")

    }
    private fun fib(n: Int): Long{
        return if(n==0) 0
        else if(n==1) 1
        else fib(n-1) + fib(n-2)
    }

    suspend fun networkCall1(): String {
        delay(4000L)
        return "Call Successful1"
    }

    suspend fun networkCall2(): String {
        delay(4000L)
        return "Call Successful2"
    }
}