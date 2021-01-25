package com.sample.mvvm_data_binding_koin.util

import android.content.res.AssetManager
import android.text.TextUtils
import java.io.*

object FileUtil {

    @JvmStatic
    fun writeStringToFile(string: String, file: File) {
        if (TextUtils.isEmpty(string)) {
            return
        }
        var writer: OutputStreamWriter? = null
        try {
            writer = OutputStreamWriter(FileOutputStream(file, false))
            writer.write(string)
            writer.flush()
        } catch (e: java.lang.Exception) {
        } finally {
            closeQuietly(writer)
        }
    }


    @JvmStatic
    fun readStringFromFile(file: File?): String? {
        if (!isFileExist(file)) {
            return null
        }
        var reader: BufferedReader? = null
        val builder = java.lang.StringBuilder()
        try {
            reader =
                BufferedReader(InputStreamReader(FileInputStream(file)))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (builder.isNotEmpty()) {
                    builder.append("\n")
                }
                builder.append(line)
            }
        } catch (e: java.lang.Exception) {
        } finally {
            closeQuietly(reader)
        }
        return builder.toString()
    }

    @JvmStatic
    fun readStringFromAssetManager(assetManager: AssetManager?, path: String?): String? {
        var inputStream: InputStream? = null
        return try {
            inputStream = assetManager!!.open(path!!)
            val inputStreamReader = InputStreamReader(inputStream, Charsets.UTF_8)
            val reader = BufferedReader(inputStreamReader)
            val builder = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null)
                builder.append(line)

            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            closeQuietly(inputStream)
        }
    }

    @Deprecated("inputStream 在同一個地方 open 跟 close 感覺比較好。 另創建 fun readStringFromAssetManager() 取代")
    @JvmStatic
    fun readStringFromInputStream(inputStream: InputStream?): String? {
        var inputStreamReader: InputStreamReader? = null
        var builder: StringBuilder? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, "utf-8")
            val reader = BufferedReader(inputStreamReader)
            builder = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            closeQuietly(inputStream)
        }
        return builder?.toString()
    }


    fun deleteFile(file: File?): Boolean {
        val flag = false
        return if (file == null || !file.exists()) {
            flag
        } else {
            if (file.isFile) {
                delete(file)
            } else {
                deleteDirectory(file)
            }
        }
    }


    /**
     * 刪除檔案
     * **/
    @JvmStatic
    private fun delete(file: File): Boolean {
        return isFileExist(file) && file.delete()
    }

    /**
     * 刪除文件夾
     * **/
    @JvmStatic
    private fun deleteDirectory(dirFile: File): Boolean {
        if (!isFileExist(dirFile) || !dirFile.isDirectory) {
            return false
        }
        var flag = true
        val files = dirFile.listFiles()
        for (file in files!!) {
            flag = if (file.isFile) {
                delete(file)
            } else {
                deleteDirectory(file)
            }
        }
        if (!flag) {
            return false
        }
        return dirFile.delete()
    }

    @JvmStatic
    fun isFileExist(file: File?): Boolean {
        return file != null && file.exists()
    }

    @JvmStatic
    private fun closeQuietly(closeable: Closeable?) {
        if (closeable != null) {
            try {
                closeable.close()
            } catch (e: IOException) {
            }
        }
    }
}