package app.revanced.patches.grindr.version.patch

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult

import app.revanced.patches.grindr.deviceinfo.fingerprints.*
import app.revanced.patches.grindr.version.fingerprints.*
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.fingerprint.MethodFingerprint
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod

import app.revanced.patcher.patch.options.PatchOption.PatchExtensions.stringPatchOption

import app.revanced.patches.grindr.firebase.patch.FirebaseGetCertPatchGrindr

fun genUserAgent(grindrVersion: String, grindrVersionIdentifier: String): String {
    return "grindr3/$grindrVersion.$grindrVersionIdentifier;$grindrVersionIdentifier;Free;Android 14;pixel_9_pro_xl;Google"
}

@Patch(
    name = "Spoof versions",
    description = "Spoofs the grindr version number",
    dependencies = [FirebaseGetCertPatchGrindr::class],
    compatiblePackages = [
        CompatiblePackage("com.grindrapp.android", ["24.9.0",
                "24.10.0",
                "24.11.0",
                "24.12.0",
                "24.13.0",]),
    ],
)
class SpoofVersionPatch : BytecodePatch(
    setOf(
        AppConfigurationFingerprint,
        DeviceinfoFingerprint
    )
) {

        private var grindrVersion =
        stringPatchOption(
            key = "grindr-version",
            default = "24.19.0",
            title = "Grindr version",
            description = "The grindr version to spoof.",
            required = true,
        ) { it!!.matches("^\\d+\\.\\d+\\.\\d+$".toRegex()) }

        private var grindrVersionIdentifier =
        stringPatchOption(
            key = "grindr-version-identifier",
            default = "132462",
            title = "Grindr version identifier",
            description = "The grindr version identifier to spoof.",
            required = true,
        ) { it!!.matches("^\\d{6}$".toRegex()) }

    override fun execute(context: BytecodeContext) {        
        val userAgentPatch = String.format("const-string v0, \"%s\"",genUserAgent("$grindrVersion", "$grindrVersionIdentifier"))
        println(userAgentPatch)

        val deviceinfoFingerprint = DeviceinfoFingerprint.result!!.mutableMethod
        val appConfigurationFingerprintMethod = AppConfigurationFingerprint.result!!.mutableMethod
        appConfigurationFingerprintMethod.replaceInstructions(11, """const-string v9, "${grindrVersion}"""")
        appConfigurationFingerprintMethod.replaceInstructions(88, """const-string v1, "${grindrVersion}.${grindrVersionIdentifier}"""")

        deviceinfoFingerprint.addInstructions(112, userAgentPatch)
    }
}