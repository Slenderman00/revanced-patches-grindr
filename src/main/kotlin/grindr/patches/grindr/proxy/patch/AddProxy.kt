package app.revanced.patches.grindr.proxy.patch

import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult

import app.revanced.patches.grindr.proxy.fingerprints.*
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.fingerprint.MethodFingerprint
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions

import app.revanced.patches.grindr.Constants.PROXY_PATH
import app.revanced.patches.grindr.firebase.patch.FirebaseGetCertPatchGrindr



@Patch(
    name = "Add Proxy",
    description = "Adds a proxy infront of the endpoint.",
    dependencies = [FirebaseGetCertPatchGrindr::class],
    compatiblePackages = [
        CompatiblePackage("com.grindrapp.android", ["9.18.4"]),
    ],
)
class AddProxy : BytecodePatch(
    setOf(
        EndpointsFingerprint,
    )
) {
    override fun execute(context: BytecodeContext) {
        var newInstruction = """
            const-string v0, "${PROXY_PATH}"
        """

        val endpointsMethod = EndpointsFingerprint.result!!.mutableMethod

        endpointsMethod.replaceInstructions(
            0, newInstruction
        )
    }
}
