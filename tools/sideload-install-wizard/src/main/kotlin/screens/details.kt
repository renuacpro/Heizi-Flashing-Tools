package me.heizi.flashing_tool.sideloader.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import me.heizi.apk.parser.ktx.Image
import me.heizi.flashing_tool.sideloader.colors
import me.heizi.flashing_tool.sideloader.current
import me.heizi.flashing_tool.sideloader.isSideload
import me.heizi.kotlinx.compose.desktop.core.components.AboutExtendCard
import net.dongliu.apk.parser.bean.ApkIcon


/**
 *
 * # some tex...
 *
 */
@Composable
fun TextEllipsisEnd(text:String, style:TextStyle= LocalTextStyle.current) =
    Text(text,style=style, maxLines = 1, overflow = TextOverflow.Ellipsis)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Info(
    modifier: Modifier = Modifier.defaultMinSize(minWidth = 372.dp).fillMaxHeight().fillMaxWidth(0.45f),
    packageDetail:Map<String,Array<String>>,
    icon:ApkIcon<*>?=null,
    titleName:String,
    packageName:String?=null,
    version:String?=null,
    switchMode:()->Unit
) = Column{
    val title = if (!isSideload)"安装APK" else "REC线刷"
    val scroll = rememberScrollState()
    TopAppBar({ Text(title) })
    Card(modifier) {
        Column(Modifier.paddingButBottom(8.dp).verticalScroll(scroll)) {
            AppTitle(
                icon,
                titleName,
                packageName,
                version
            )
            FilledTonalButton(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                onClick = switchMode,
                content = {
                    Text(if (isSideload) "这是APK？" else "这是刷机包?") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.current.secondary,
                    contentColor = colors.current.onSecondary
                )
            )
            Detail(
                Modifier.paddingButBottom(16.dp),
                packageDetail
            )
        }
    }
}


@Composable
private fun IconCard(icon: ApkIcon<*>) {
    var displayIcon = icon
    // icon is adaptive icon then using foreground
    if (icon is ApkIcon.Adaptive) displayIcon = icon.foreground
    // if icon is not empty then display it
    if (displayIcon !is ApkIcon.Empty) {
        Card(Modifier.size(126.dp),
//            colors = CardDefaults.cardColors(background)
        ) {
            Image(displayIcon, modifier = Modifier.fillMaxSize(),)
        }
    }
}
@Composable
private fun AppTitle(
    icon: ApkIcon<*>?,
    title:String,
    packageName: String?,
    version: String?,
    modifier: Modifier = Modifier
) = BoxWithConstraints(modifier) {

    @Composable
    fun content(padding: PaddingValues) = AppTitleInner(icon,title,packageName,version,padding)

    if (maxWidth > 156.dp) Row {
        content(PaddingValues(start=8.dp))
    } else Column {
        content(PaddingValues())
    }
}
@Composable
private fun AppTitleInner(
    icon: ApkIcon<*>?,
    title:String,
    packageName: String?,
    version: String?,
    padding: PaddingValues
) = CompositionLocalProvider(LocalTextStyle.provides(MaterialTheme.typography.bodyMedium.copy())) {
    if (icon!=null) IconCard(icon)
    Column(Modifier.padding(padding).sizeIn(maxHeight = 126.dp), verticalArrangement = Arrangement.SpaceBetween) {
        TextEllipsisEnd(title,MaterialTheme.typography.displayLarge)
        Column {
            version?.let { TextEllipsisEnd(it) }
            packageName?.let{ TextEllipsisEnd(it) }
        }
    }
}


@Composable
private fun Detail(
    modifier: Modifier,
    data:Map<String,Array<String>>
) = Column(modifier) {
    var i = 0
    for ((title,detail) in data) {
        if (data.size>2&&i++==data.size-1)
            AboutExtendCard(false)
        if (title == "name") continue
        Text(title, style = MaterialTheme.typography.labelMedium)
        for (s in detail) {
            Text(s)
        }
        Spacer(Modifier.padding(bottom = 8.dp))
    }
    if (data.size<3) AboutExtendCard()
}