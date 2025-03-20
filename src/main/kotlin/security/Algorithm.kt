package security

interface Algorithm {
    fun encrypt(plainText: String): String
    fun decrypt(cipherText: String): String
    fun deriveKey(sharedSecret: ByteArray, salt: ByteArray?, info: ByteArray?, length: Int): ByteArray

    val initializationVector: ByteArray

    companion object {
        const val AES_KEY_SIZE = 256
    }
}