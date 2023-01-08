package com.example.msgshareapp

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.filament.*
import com.google.android.filament.utils.*
import java.nio.ByteBuffer
import android.util.Log

class ModelActivity : AppCompatActivity() {

    companion object {
        init {
            Utils.init()

        }
    }

    private lateinit var surfaceView: SurfaceView
    private lateinit var choreographer: Choreographer
    private lateinit var modelViewer: ModelViewer
    private val viewerContent = AutomationEngine.ViewerContent()
    private val automation = AutomationEngine()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Iam","Iam called")
        surfaceView = SurfaceView(this).apply { setContentView(this) }
        choreographer = Choreographer.getInstance()
        modelViewer = ModelViewer(surfaceView)
        surfaceView.setOnTouchListener(modelViewer)
        loadGlb("mbap.glb")
        modelViewer.scene.skybox = Skybox.Builder().build(modelViewer.engine)
        createNeutralIndirectLight()

    }
     private fun readCompressedAsset(assetName: String): ByteBuffer {
        val input = assets?.open(assetName)
        val bytes = input?.let { ByteArray(it.available()) }
        input?.read(bytes)
        return ByteBuffer.wrap(bytes)
    }


    private fun createNeutralIndirectLight() {
        val engine = modelViewer.engine
        readCompressedAsset("neutral_ibl.ktx").let {
            modelViewer.scene.indirectLight = KTX1Loader.createIndirectLight(engine, it)
            modelViewer.scene.indirectLight!!.intensity = 40_000.0f
            viewerContent.indirectLight = modelViewer.scene.indirectLight
        }
    }

    private val frameCallback = object : Choreographer.FrameCallback {
        private val startTime = System.nanoTime()
        override fun doFrame(currentTime: Long) {
            val seconds = (currentTime - startTime).toDouble() / 1_000_000_000
            choreographer.postFrameCallback(this)
            modelViewer.animator?.apply {
                if (animationCount > 0) {
                    applyAnimation(2, seconds.toFloat())
                }
                updateBoneMatrices()
            }
            modelViewer.render(currentTime)
            modelViewer.asset?.apply {
                modelViewer.transformToUnitCube()
//                modelViewer.clearRootTransform()
//                val rootTransform = this.root.getTransform()
//                val degrees = 20f * seconds.toFloat()
//                val zAxis = Float3(0f, -1f, 1f)
//                this.root.setTransform(rootTransform * rotation(zAxis, degrees))
            }
        }
    }
    private fun Int.getTransform(): Mat4 {
        val tm = modelViewer.engine.transformManager
        val arr1 = FloatArray(16)
        return Mat4.of(*tm.getTransform(tm.getInstance(this), arr1))
//        return Mat4.of(0.0f)

    }


    private fun Int.setTransform(mat: Mat4) {
        val tm = modelViewer.engine.transformManager
        tm.setTransform(tm.getInstance(this), mat.toFloatArray())
    }

//    private val frameCallback = object : Choreographer.FrameCallback {
//        override fun doFrame(currentTime: Long) {
//            choreographer.postFrameCallback(this)
//            modelViewer.render(currentTime)
//        }
//    }

    override fun onResume() {
        super.onResume()
        choreographer.postFrameCallback(frameCallback)
    }

    override fun onPause() {
        super.onPause()
        choreographer.removeFrameCallback(frameCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        choreographer.removeFrameCallback(frameCallback)
    }

    private fun loadGlb(glbName: String) {
        val buffer =  assets?.open(glbName).use { input ->
            val bytes = input?.let { ByteArray(it.available()) }
            input?.read(bytes)
            ByteBuffer.wrap(bytes)
        }
        modelViewer.loadModelGltfAsync(buffer) { uri -> readCompressedAsset("$uri") }
        updateRootTransform()
    }

    private fun updateRootTransform() {
        if (automation.viewerOptions.autoScaleEnabled) {
            modelViewer.transformToUnitCube()
        } else {
            modelViewer.clearRootTransform()
        }
    }

    private fun readAsset(assetName: String): ByteBuffer {
        val input = assets.open(assetName)
        val bytes = ByteArray(input.available())
        input.read(bytes)
        return ByteBuffer.wrap(bytes)
    }

    private fun loadGltf(name: String) {
        val buffer = readAsset("${name}.gltf")
        modelViewer.loadModelGltf(buffer) { uri -> readAsset("$uri") }
        modelViewer.transformToUnitCube()
    }



}