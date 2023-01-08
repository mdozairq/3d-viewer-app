package Utility

import com.google.android.filament.utils.ModelViewer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer

var url = "https://github.com/kelvinwatson/glb-files/raw/main/DamagedHelmet.glb"
//runBlocking {
//    async {
//        customViewer!!.loadRemoteGlb(url)
//    }
//}

private lateinit var modelViewer: ModelViewer

suspend fun loadRemoteGlb(url: String) {
    GlobalScope.launch(Dispatchers.IO) {
        URL(url).openStream().use { inputStream: InputStream ->
            val inputStream = BufferedInputStream(inputStream)
            ByteArrayOutputStream().use { output ->
                inputStream.copyTo(output)
                val byteArr = output.toByteArray()
                val byteBuffer = ByteBuffer.wrap(byteArr)
                val rewound = byteBuffer.rewind()
                withContext(Dispatchers.Main) {
                    modelViewer.destroyModel()
                    modelViewer.loadModelGlb(rewound)
                    modelViewer.transformToUnitCube()
                    output.close()
                    inputStream.close()
                }
            }
        }
    }
}

private suspend fun loadGlbRemote() {
//    lifecycleScope.launch {
    withContext(Dispatchers.IO) {
        val url =
            URL("https://github.com/kelvinwatson/glb-files/raw/main/DamagedHelmet.glb")
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        urlConnection.connect()
        val inputStream = BufferedInputStream(urlConnection.getInputStream())
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val byteArrayOutputStream = ByteArrayOutputStream()
        var bytesRead: Int
        while ((inputStream.read(buffer).also { bytesRead = it }) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead)
        }
        val byteArr = byteArrayOutputStream.toByteArray()
        val byteBuffer = ByteBuffer.wrap(byteArr)
        withContext(Dispatchers.Main) {
            modelViewer.destroyModel()
            modelViewer.loadModelGlb(byteBuffer.rewind())
            modelViewer.transformToUnitCube()
        }
    }
}