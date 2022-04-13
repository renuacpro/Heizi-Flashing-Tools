package me.heizi.flashing_tool.fastboot

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.application
import me.heizi.flashing_tool.fastboot.repositories.Fastboot
import me.heizi.flashing_tool.fastboot.screen.*
import me.heizi.kotlinx.logger.debug

fun composeApplication() {
    application {
        Fastboot.error
        var isScannerDialogShow by remember { mutableStateOf(false) }
        val trays = remember {
            object : Trays(this) {
                override fun exit() {
                    exitApplication()
                }
                override fun onTrayIconSelected() {
                    isScannerDialogShow = false
                    isScannerDialogShow = true
                }
            }
        }
        val scannerViewModel = remember {
            object : FlowCollectedScannerViewModel() {
                override fun onDeviceSelected(serial: String) {
                    openedDeviceDialog.add(serial)
                    debug("open",serial)
                    isScannerDialogShow = false
                }
            }
        }
//        trays.state.sendNotification(
//            rememberNotification(
//                "欢迎使用预览版HFT-Fastboot设备管理器", "软件已经启动，在状态栏内可以找到软件的图标，该软件会在后台三秒一次轮询检测Fastboot设备，在使用完成请及时退出（关闭本窗口不可关闭程序）" +
//                        "。在您的Fastboot设备电脑插入后，可以双击图标启动Fastboot设备管理器。", Notification.Type.Info
//            )
//        )
        if (openedDeviceDialog.isNotEmpty()) DialogOpen()

        if (isScannerDialogShow) ScannerDialog(scannerViewModel) {
            isScannerDialogShow = false
        }

        trays.Render()
    }
}