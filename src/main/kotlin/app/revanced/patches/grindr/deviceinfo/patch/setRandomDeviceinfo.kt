package app.revanced.patches.grindr.deviceinfo.patch

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult

import app.revanced.patches.grindr.deviceinfo.fingerprints.*
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.fingerprint.MethodFingerprint
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod

import java.util.UUID

import app.revanced.patches.grindr.firebase.patch.FirebaseGetCertPatchGrindr

import kotlin.random.Random

fun genLDevInfo(): String {
    val identifier = UUID.randomUUID()
    val hexIdentifier = identifier.toString().replace("-", "").lowercase()
    val randomInteger = Random.nextLong(1000000000, 9999999999 + 1)
    return "$hexIdentifier;GLOBAL;2;$randomInteger;2277x1080;${identifier.toString().lowercase()}"
}

@Patch(
    name = "Random device info",
    description = "Sets a random device info.",
    dependencies = [FirebaseGetCertPatchGrindr::class],
    compatiblePackages = [
        CompatiblePackage("com.grindrapp.android", ["24.9.0",
                "24.10.0",
                "24.11.0",
                "24.12.0",
                "24.13.0",]),
    ],
)
class UnlockUnlimitedPatch : BytecodePatch(
    setOf(
        DeviceinfoFingerprint,
    )
) {
    override fun execute(context: BytecodeContext) {
        val bytecode_patch = String.format("const-string v0, \"%s\"", genLDevInfo())
    
        val deviceinfoFingerprint = DeviceinfoFingerprint.result!!.mutableMethod

        println(bytecode_patch)
        deviceinfoFingerprint.replaceInstructions(83, bytecode_patch)
    }
    
}