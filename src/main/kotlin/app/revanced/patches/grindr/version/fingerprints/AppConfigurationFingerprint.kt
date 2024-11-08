package app.revanced.patches.grindr.version.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.annotation.FuzzyPatternScanMethod
import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object AppConfigurationFingerprint : MethodFingerprint(
    returnType = "V",
    parameters = listOf(
        "Lcom/grindrapp/android/base/config/AppConfiguration\$b;",
        "Lcom/grindrapp/android/base/config/AppConfiguration\$f;",
        "Lcom/grindrapp/android/base/config/AppConfiguration\$d;",
        "Lcom/grindrapp/android/base/config/AppConfiguration\$e;",
        "Lcom/grindrapp/android/base/config/AppConfiguration\$c;",
        "Lcom/grindrapp/android/base/config/AppConfiguration\$a;"
    ),
    opcodes = listOf(
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.MOVE_OBJECT_FROM16,
        Opcode.CONST_STRING
    )
)