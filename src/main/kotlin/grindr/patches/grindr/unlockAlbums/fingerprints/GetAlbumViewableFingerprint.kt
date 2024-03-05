package app.revanced.patches.grindr.unlockAlbums.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.annotation.FuzzyPatternScanMethod
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

//getAlbumViewable()Z

@FuzzyPatternScanMethod(2)
object GetAlbumViewableFingerprint : MethodFingerprint(
    "Z",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.contains("grindrapp") && methodDef.name.contains("getAlbumViewable")
    }
)