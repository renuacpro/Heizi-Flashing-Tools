package lib

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.reflect.KProperty

class mutableStateFlowOf<T:Any?>(
    private val flow: MutableStateFlow<T>
) {
    constructor(any:T) : this(MutableStateFlow(any))
    operator fun getValue(any: Any, property: KProperty<*>): T  {
        return flow.value
    }

    operator fun setValue(any: Any, property: KProperty<*>, value: T) {
        flow.value = value
    }

}

@Composable
fun <T> StateFlow<T>.toState(): State<T> {
    return collectAsState()
}


@Composable
fun ChipCheckBox (
    check:Boolean,
    text:String?=null,
    modifier: Modifier = Modifier,
    onCheck:(Boolean)->Unit = {},
) {
    OutlinedButton(
        colors = object: ButtonColors {
            @Composable
            override fun backgroundColor(enabled: Boolean): State<Color>
                    = rememberUpdatedState(if (check) Color.LightGray else Color.LightGray.copy(alpha = 0.3f))
            @Composable
            override fun contentColor(enabled: Boolean): State<Color>
                    = rememberUpdatedState(
//                if (enableState) Color.White else
                Color.Black
            )
        },
        shape = CircleShape,
        modifier = modifier,
        onClick = {
            onCheck(check)
        },border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Box(Modifier.width(24.dp).height(20.dp)) {
            Text( if(check)"\uD83D\uDC4C" else "\uD83D\uDC4B",modifier = Modifier,
//                fontSize = 12.sp
            )
        }
        Text(text?:"")
    }
}