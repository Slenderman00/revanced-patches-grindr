package app.revanced.patches.grindr.proxy.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.annotation.FuzzyPatternScanMethod
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode


object EndpointsFingerprint : MethodFingerprint(
    strings = listOf("https://grindr.mobi/"),
    customFingerprint = { methodDef, _ ->
        println(methodDef.name)
        1==1
    }
)