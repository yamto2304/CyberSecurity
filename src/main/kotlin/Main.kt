import java.math.BigInteger
import model.DiffieHellmanComponent
import model.DiffieHellmanParty

fun main() {
    // Tham số công khai (có thể thay đổi)
    val basicDHComponent = DiffieHellmanComponent(
        prime = BigInteger("23"),
        generator = BigInteger("5")
    )

    val advanceDHComponent2048 = DiffieHellmanComponent(
        prime = BigInteger(Constants.HEX_PRIME_2048, 16),
        generator = Constants.GENERATOR
    )

    val advanceDHComponent3072 = DiffieHellmanComponent(
        prime = BigInteger(Constants.HEX_PRIME_3072, 16),
        generator = Constants.GENERATOR
    )

    val advanceDHComponent4096= DiffieHellmanComponent(
        prime = BigInteger(Constants.HEX_PRIME_4096, 16),
        generator = Constants.GENERATOR
    )

    val advanceDHComponent6144 = DiffieHellmanComponent(
        prime = BigInteger(Constants.HEX_PRIME_6144, 16),
        generator = Constants.GENERATOR
    )

    val advanceDHComponent8192 = DiffieHellmanComponent(
        prime = BigInteger(Constants.HEX_PRIME_8192, 16),
        generator = Constants.GENERATOR
    )

//    simulateExchangeKeyWithDHComponent(basicDHComponent)
//    simulateExchangeKeyWithDHComponent(advanceDHComponent2048)
//    simulateExchangeKeyWithDHComponent(advanceDHComponent3072)
//    simulateExchangeKeyWithDHComponent(advanceDHComponent4096)
//    simulateExchangeKeyWithDHComponent(advanceDHComponent6144)
    simulateExchangeKeyWithDHComponent(advanceDHComponent8192)
}

fun simulateExchangeKeyWithDHComponent(component: DiffieHellmanComponent) {
    // Tạo Alice và Bob
    val aliceBasic = DiffieHellmanParty(component)
    val bobBasic = DiffieHellmanParty(component)

    // Trao đổi khóa công khai và tính khóa bí mật chung
    val aliceSecretBasic = aliceBasic.measureComputeSharedSecretKeyTime(bobBasic.publicKey)
    val bobSecretBasic = bobBasic.measureComputeSharedSecretKeyTime(aliceBasic.publicKey)

//    val alice2048 = DiffieHellmanParty(advanceDHComponent2048)
//    val bob2048 = DiffieHellmanParty(advanceDHComponent2048)

//    val aliceSecret2048 = alice2048.measureComputeSharedSecretKeyTime(bob2048.publicKey)
//    val bobSecret2048 = bob2048.measureComputeSharedSecretKeyTime(alice2048.publicKey)
    println("==========================================================================\n")
    // In kết quả
    println("Tham số công khai:")
    println("prime p = ${component.prime}")
    println("modulo g = ${component.generator}\n")

    println("Alice Basic:")
    println("- Private key: ${aliceBasic.showPrivateKey()}")
    println("- Public key:  ${aliceBasic.publicKey}\n")

    println("Bob Basic:")
    println("- Private key: ${bobBasic.showPrivateKey()}")
    println("- Public key:  ${bobBasic.publicKey}\n")

    println("Khóa bí mật chung:")
    println("Alice tính được: ${aliceSecretBasic.first} in ${aliceSecretBasic.second} nano seconds")
    println("Bob tính được:   ${bobSecretBasic.first} in ${bobSecretBasic.second} nano seconds \n")
    println("Khóa basic trùng khớp? ${aliceSecretBasic.first == bobSecretBasic.first}")

    println("==========================================================================\n")
}