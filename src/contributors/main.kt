package contributors


fun main() {
    setDefaultFontSize(13f)
    ContributorsUI().apply {
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}