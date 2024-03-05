//.method public final component5()Z
//    .locals 1
//
//    iget-boolean v0, p0, Lcom/grindrapp/android/model/albums/AlbumBrief;->albumViewable:Z
//
//    return v0
//.end method

package app.revanced.patches.grindr.unlockAlbums.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.annotation.FuzzyPatternScanMethod
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

//getAlbumViewable()Z

@FuzzyPatternScanMethod(2)
object GetComponentFiveFingerprint : MethodFingerprint(
    "Z",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    opcodes = listOf(
        Opcode.IGET_BOOLEAN,
        Opcode.RETURN
    ),
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.contains("grindrapp") && methodDef.name.contains("component5")
    }
)