package com.example.coffeevibe.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import aws.sdk.kotlin.services.s3.S3Client
import aws.sdk.kotlin.services.s3.deleteObject
import aws.sdk.kotlin.services.s3.model.PutObjectRequest
import aws.smithy.kotlin.runtime.content.ByteStream
import aws.smithy.kotlin.runtime.net.url.Url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

object VkCloudStorage {
    private const val TAG = "VkCloudStorage"
    private const val BUCKET_NAME = "coffee"
    private const val ENDPOINT = "https://hb.ru-msk.vkcloud-storage.ru"
    private const val REGION = "ru-msk"

    private const val ACCESS_KEY = "hgnyrnpJRuESvAwCRTnvYB"
    private const val SECRET_KEY = "2eU6DbGTqnwKij4nHXLBLYZyPRfo946JGnTUqHr6myz3"

    private var s3Client: S3Client? = null

    private fun getS3Client(): S3Client {
        if (s3Client == null) {
            System.setProperty("aws.accessKeyId", ACCESS_KEY)
            System.setProperty("aws.secretAccessKey", SECRET_KEY)
            s3Client = S3Client {
                endpointUrl = Url.parse(ENDPOINT)
                region = REGION
                forcePathStyle = true
            }
        }
        return s3Client!!
    }

    suspend fun uploadImage(context: Context, imageUri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(imageUri)
            if (inputStream == null) {
                Log.e(TAG, "Failed to open input stream for URI: $imageUri")
                return@withContext null
            }

            val extension = getFileExtension(context, imageUri) ?: "jpg"
            val key2 = "products/${UUID.randomUUID()}.$extension"

            val byteArray = inputStream.use { it.readBytes() }

            val request = PutObjectRequest {
                bucket = BUCKET_NAME
                key = key2
                body = ByteStream.fromBytes(byteArray)
                contentType = getContentType(extension)
            }

            getS3Client().use { client ->
                client.putObject(request)
                Log.d(TAG, "Image uploaded successfully: $key2")
            }

            "$ENDPOINT/$BUCKET_NAME/$key2"
        } catch (e: Exception) {
            Log.e(TAG, "Error uploading image", e)
            null
        }
    }

    suspend fun deleteImage(imageUrl: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val key1 = imageUrl.substringAfter("$ENDPOINT/$BUCKET_NAME/")
            if (key1.isEmpty() || key1 == imageUrl) {
                Log.w(TAG, "Invalid image URL for deletion: $imageUrl")
                return@withContext false
            }

            getS3Client().use { client ->
                client.deleteObject {
                    bucket = BUCKET_NAME
                    key = key1
                }
                Log.d(TAG, "Image deleted successfully: $key1")
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting image", e)
            false
        }
    }

    private fun getFileExtension(context: Context, uri: Uri): String? {
        return context.contentResolver.getType(uri)?.let { mimeType ->
            when {
                mimeType.contains("jpeg") || mimeType.contains("jpg") -> "jpg"
                mimeType.contains("png") -> "png"
                mimeType.contains("gif") -> "gif"
                mimeType.contains("webp") -> "webp"
                else -> null
            }
        }
    }

    private fun getContentType(extension: String): String {
        return when (extension.lowercase()) {
            "jpg", "jpeg" -> "image/jpeg"
            "png" -> "image/png"
            "gif" -> "image/gif"
            "webp" -> "image/webp"
            else -> "application/octet-stream"
        }
    }
}