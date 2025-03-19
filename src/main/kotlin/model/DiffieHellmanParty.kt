package model

import java.math.BigInteger
import java.security.SecureRandom

/**
 * Một bên tham gia giao thức Diffie-Hellman.
 *
 * @property component Tham số công khai
 */
class DiffieHellmanParty(
    private val component: DiffieHellmanComponent
) {
    private val privateKey: BigInteger by lazy {
        BigInteger(256, SecureRandom())
    }

    val publicKey: BigInteger = component.generator.modPow(privateKey, component.prime)

    /**
     * Tính khóa bí mật chung dựa trên khóa công khai của bên kia.
     *
     * @param otherPublicKey Khóa công khai của bên kia
     * @return Khóa bí mật chung
     */
    fun computeSharedSecretKey(otherPublicKey: BigInteger): BigInteger {
        return otherPublicKey.modPow(privateKey, component.prime)
    }

    /**
     * Tính khóa bí mật chung dựa trên khóa công khai của bên kia và đồng thời đo thời gian tính toán.
     *
     * @param otherPublicKey Khóa công khai của bên kia
     * @return Pair<BigInteger, Long> Khóa bí mật chung và thời gian tính toán (nano giây)
     */
    fun measureComputeSharedSecretKeyTime(otherPublicKey: BigInteger): Pair<BigInteger, Long> {
        val startTime = System.nanoTime()
        val sharedSecretKey = computeSharedSecretKey(otherPublicKey)
        val endTime = System.nanoTime()
        val duration = endTime - startTime
        return Pair(sharedSecretKey, duration)
    }

    fun showPrivateKey(): BigInteger {
        return privateKey
    }


    override fun toString(): String {
        return "DiffieHellmanParty(prime=${component.prime}, generator=${component.generator}, privateKey=$privateKey, publicKey=$publicKey)"
    }
}

/**
 * Dữ liệu 2 bên tiến hành thống nhất về các tham số công khai trước khi bắt đầu trao đổi khóa.
 *
 * @property prime Số nguyên tố (lớn)
 * @property generator phần tử nguyên thủy (modulo)
 */
data class DiffieHellmanComponent(
    val prime: BigInteger,
    val generator: BigInteger
)