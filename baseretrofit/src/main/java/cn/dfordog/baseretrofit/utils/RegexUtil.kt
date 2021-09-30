package cn.dfordog.baseretrofit.utils

object RegexUtil {

    const val REGEX_TYPE_WORD_NUM = 0
    const val REGEX_TYPE_PHONE = 1

    private const val REGEX_WORD_NUM = "^[A-Za-z0-9]+\$" //字母、数字、字母加数字
    private const val REGEX_PHONE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[0135678])|(18[0,5-9]))\\d{8}\$" //手机号

    /**
     * 判断当前是否符合
     */
    fun regex(str: String,type: Int): Boolean {
        return when(type){
            REGEX_TYPE_WORD_NUM -> str.matches(Regex(REGEX_WORD_NUM))
            REGEX_TYPE_PHONE -> str.matches(Regex(REGEX_PHONE))
            else -> false
        }
    }
}