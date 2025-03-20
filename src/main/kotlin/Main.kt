import java.math.BigInteger
import model.DiffieHellmanComponent
import model.DiffieHellmanParty
import org.bouncycastle.crypto.generators.HKDFBytesGenerator
import org.bouncycastle.crypto.params.HKDFParameters
import org.bouncycastle.crypto.digests.SHA256Digest

fun main() {
    simulateAllDHComponent()
}

fun simulateAllDHComponent() {
    for (component in Constants.listDHComponent) {
        simulateExchangeKeyWithDHComponent(component)
    }
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