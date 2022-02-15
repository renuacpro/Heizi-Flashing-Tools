package me.heizi.flashing_tool.fastboot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.toPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.singleWindowApplication
import fastbootIconBuffered
import me.heizi.flashing_tool.vd.fb.Title
import org.jetbrains.skiko.toImage
import kotlin.system.exitProcess

fun main() {
    singleWindowApplication {
        object : ScannerViewModel {
            override val devices: List<String>
                get() = listOf("LMV600TM4bf4e87d")

            override fun onDeviceSelected(serial: String) {
                exitProcess(0)
            }

        } .ScannerScreen()
    }
}

@Composable
fun ScannerDialog(viewModel: ScannerViewModel,onCloseRequest:()->Unit) {
    Dialog(onCloseRequest,title = "设备查询",icon = fastbootIconBuffered.toPainter()) {
        viewModel.ScannerScreen()
    }
}

interface ScannerViewModel {
    val devices:List<String>
    fun onDeviceSelected(serial:String)
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScannerViewModel.ScannerScreen(
) {
    Image(fastbootIconBuffered.toImage().toComposeImageBitmap(),"background",modifier = Modifier.fillMaxHeight().fillMaxWidth())
    Column(Modifier.fillMaxWidth().fillMaxHeight().padding(16.dp)) {
        Title("已连接设备:${devices.size}")

        LazyColumn(modifier = Modifier.fillMaxWidth().background(Color.LightGray.copy(alpha = 0.3f))) {
            items(devices) {
                ListItem(modifier = Modifier.clickable {
                    onDeviceSelected(it)
                }) { Text(it) }
            }
        }
    }
}