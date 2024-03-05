//# direct methods
//.method public constructor <init>(JLjava/lang/Long;Ljava/lang/String;Ljava/lang/Boolean;)V
//    .locals 0
//
//    .line 1
//    invoke-direct {p0}, Ljava/lang/Object;-><init>()V
//
//    .line 2
//    iput-wide p1, p0, Lcom/grindrapp/android/model/AlbumBody;->albumId:J
//
//    .line 3
//    iput-object p3, p0, Lcom/grindrapp/android/model/AlbumBody;->albumContentId:Ljava/lang/Long;
//
//    .line 4
//    iput-object p4, p0, Lcom/grindrapp/android/model/AlbumBody;->albumContentReply:Ljava/lang/String;
//
//    .line 5
//    iput-object p5, p0, Lcom/grindrapp/android/model/AlbumBody;->isUnshared:Ljava/lang/Boolean;
//
//    return-void
//.end method

package app.revanced.patches.grindr.unlockAlbums.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.annotation.FuzzyPatternScanMethod
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

//getAlbumViewable()Z

@FuzzyPatternScanMethod(2)
object AlbumBodyConstructorFingerprint : MethodFingerprint(
    "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.CONSTRUCTOR,
    opcodes = listOf(
        Opcode.INVOKE_DIRECT,
        Opcode.IPUT_WIDE,
        Opcode.IPUT_OBJECT,
        Opcode.IPUT_OBJECT,
        Opcode.IPUT_OBJECT,
        Opcode.RETURN_VOID
    ),
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.contains("grindrapp")
    }
)