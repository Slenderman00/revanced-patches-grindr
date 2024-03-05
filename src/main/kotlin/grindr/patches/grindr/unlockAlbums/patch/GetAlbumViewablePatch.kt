package app.revanced.patches.grindr.unlockAlbums.patch

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
//import app.revanced.patcher.patch.PatchResult
import app.revanced.patches.grindr.unlockAlbums.fingerprints.GetAlbumViewableFingerprint
import app.revanced.patches.grindr.unlockAlbums.fingerprints.GetComponentFiveFingerprint
import app.revanced.patches.grindr.unlockAlbums.fingerprints.AlbumBriefConstructorFingerprint
import app.revanced.patches.grindr.unlockAlbums.fingerprints.AlbumBodyConstructorFingerprint

import app.revanced.patches.grindr.Constants.trueBytecode

class GetAlbumViewablePatch : BytecodePatch(
    setOf(
        GetAlbumViewableFingerprint,
        GetComponentFiveFingerprint,
        AlbumBriefConstructorFingerprint,
        AlbumBodyConstructorFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {
        //val getAlbumViewableMethod = GetAlbumViewableFingerprint.result!!.mutableMethod
//
        //getAlbumViewableMethod.replaceInstructions(
        //    0,
        //    trueBytecode
        //)
//
        //val getComponentFiveFingerprint = GetComponentFiveFingerprint.result!!.mutableMethod
//
        //getComponentFiveFingerprint.replaceInstructions(
        //    0,
        //    trueBytecode
        //)


        val bytecodePatch = "const/4 p6, 1"
        val albumBriefConstructorFingerprint = AlbumBriefConstructorFingerprint.result!!.mutableMethod

        albumBriefConstructorFingerprint.addInstructions(
            6,
            bytecodePatch
        )

        val bytecodePatch2 = "const/4 p4, 0"
        val albumBodyConstructorFingerprint = AlbumBodyConstructorFingerprint.result!!.mutableMethod

        albumBodyConstructorFingerprint.addInstructions(
            3,
            bytecodePatch2
        )
    }
}