package app.revanced.patches.grindr

internal object Constants {
    const val PACKAGE_NAME = "com.grindrapp.android"
    const val SPOOFED_PACKAGE_SIGNATURE = "823F5A17C33B16B4775480B31607E7DF35D67AF8"

    val trueBytecode = """
        const/4 v0, 0x1
        return v0
    """

    val falseBytecode = """
        const/4 v0, 0x0
        return v0
    """
}