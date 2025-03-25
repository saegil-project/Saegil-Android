package com.seagil.mypage.mypage


/**
 * UI State that represents MypageScreen
 **/
class MypageState

/**
 * Mypage Actions emitted from the UI Layer
 * passed to the coordinator to handle
 **/
data class MypageActions(
    val onClick: () -> Unit = {}
)


