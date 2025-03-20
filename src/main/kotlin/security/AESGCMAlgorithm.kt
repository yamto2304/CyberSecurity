package security

import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.security.SecureRandom
import java.util.Base64
import org.bouncycastle.crypto.digests.SHA256Digest
import org.bouncycastle.crypto.generators.HKDFBytesGenerator
import org.bouncycastle.crypto.params.HKDFParameters

class AESGCMAlgorithm(private val key: ByteArray) : Algorithm {
    override val initializationVector: ByteArray
        get() = ByteArray(12).also { SecureRandom().nextBytes(it) }
    override fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keySpec = SecretKeySpec(key, "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, GCMParameterSpec(128, initializationVector))
        val ciphertext = cipher.doFinal(plainText.toByteArray())
        val combined = initializationVector + ciphertext
        return Base64.getEncoder().encodeToString(combined)
    }

    override fun decrypt(cipherText: String): String {
        val combined = Base64.getDecoder().decode(cipherText)
        val ciphertext = combined.copyOfRange(12, combined.size)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val keySpec = SecretKeySpec(key, "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec, GCMParameterSpec(128, initializationVector))
        val plaintext = cipher.doFinal(ciphertext)
        return String(plaintext)
    }

    override fun deriveKey(sharedSecret: ByteArray, salt: ByteArray?, info: ByteArray?, length: Int): ByteArray {
        val hkdf = HKDFBytesGenerator(SHA256Digest())
        hkdf.init(HKDFParameters(sharedSecret, salt, info))
        val output = ByteArray(length)
        hkdf.generateBytes(output, 0, length)
        return output // Khóa AES-256 (32 byte)
    }
}