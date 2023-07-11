package com.chrrissoft.broadcastreceivercompanion.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bluetooth
import androidx.compose.material.icons.rounded.Camera
import androidx.compose.material.icons.rounded.HideSource
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.chrrissoft.broadcastreceivercompanion.ui.state.CtxRegistration
import com.chrrissoft.broadcastreceivercompanion.ui.state.Permission
import com.chrrissoft.broadcastreceivercompanion.ui.state.ScreenState.ContextRegistration
import com.chrrissoft.broadcastreceivercompanion.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Permission(
    enabled: Boolean,
    permission: Permission,
    onPermissionChanged: (Permission) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==Permission.WithOut,
            onClick = { onPermissionChanged(Permission.WithOut) },
            label = { Text(style = typography.labelMedium, text = "With out") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.HideSource,
                )
            },
            border = inputChipBorderColor,
        )

        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==Permission.With.Matches,
            onClick = { onPermissionChanged(Permission.With.Matches) },
            label = { Text(style = typography.labelMedium, text = "Matches") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Bluetooth,
                )
            },
            border = inputChipBorderColor,
        )

        InputChip(
            enabled = enabled,
            colors = inputChipColors,
            selected = permission==Permission.With.NotMatches,
            onClick = { onPermissionChanged(Permission.With.NotMatches) },
            label = { Text(style = typography.labelMedium, text = "NotMatches") },
            trailingIcon = {
                Icon(
                    contentDescription = null,
                    imageVector = Icons.Rounded.Camera,
                )
            },
            border = inputChipBorderColor,
        )
    }
}

@Composable
fun ContextRegistration(
    enabled: Boolean,
    context: CtxRegistration,
    onContextChanged: (CtxRegistration) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(enabled) { onContextChanged(CtxRegistration.Activity) }
        ) {
            val textColor =
                if (context==CtxRegistration.Activity && enabled) MaterialTheme.colorScheme.tertiary
                else if (context!=CtxRegistration.Activity && enabled) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.secondary.copy(.5f)
            RadioButton(
                enabled = enabled,
                colors = radioColors,
                selected = context==CtxRegistration.Activity,
                onClick = { onContextChanged(CtxRegistration.Activity) }
            )
            Text(
                text = "Activity",
                style = typography.labelMedium.copy(color = textColor)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(enabled) { onContextChanged(CtxRegistration.Application) }
        ) {
            val textColor =
                if (context==CtxRegistration.Application && enabled) MaterialTheme.colorScheme.tertiary
                else if (context!=CtxRegistration.Application && enabled) MaterialTheme.colorScheme.secondary
                else MaterialTheme.colorScheme.secondary.copy(.5f)
            RadioButton(
                enabled = enabled,
                colors = radioColors,
                selected = context==CtxRegistration.Application,
                onClick = { onContextChanged(CtxRegistration.Application) },
            )
            Text(
                text = "App",
                style = typography.labelMedium.copy(color = textColor)
            )
        }
    }
}

@Composable
fun Checkbox(
    text: String,
    enabled: Boolean,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(enabled) { onCheckedChanged(!checked) }
    ) {
        Checkbox(
            checked = checked,
            enabled = enabled,
            colors = checkboxColors,
            onCheckedChange = onCheckedChanged,
        )
        val textColor =
            if (checked && enabled) MaterialTheme.colorScheme.tertiary
            else if (!checked && enabled) MaterialTheme.colorScheme.secondary
            else MaterialTheme.colorScheme.secondary.copy(.5f)
        Text(text = text, color = textColor, style = typography.labelMedium)
    }
}

@Composable
fun Container(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = modifier,
        colors = cardColors,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.tertiary,
            style = typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        Divider(
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp)
        )

        content()
    }
}

@Composable
fun ContextRegistration(
    title: String,
    state: ContextRegistration,
    onStateChanged: (ContextRegistration) -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor = if (state.registered) MaterialTheme.colorScheme.tertiary
    else MaterialTheme.colorScheme.tertiary.copy(.5f)
    Column(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp)
        ) {
            Checkbox(
                text = title,
                enabled = true,
                checked = state.registered,
                onCheckedChanged = {
                    onStateChanged(state.copy(registered = it))
                },
            )
            ContextRegistration(
                enabled = state.registered,
                context = state.context,
                onContextChanged = {
                    onStateChanged(state.copy(context = it))
                }
            )
        }

        Permission(
            enabled = state.registered,
            permission = state.permission,
            onPermissionChanged = {
                onStateChanged(state.copy(permission = it))
            },
            modifier = Modifier.padding(horizontal = 10.dp)
        )
    }
}
