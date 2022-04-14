package happy.mjstudio.harlequin.presentation.util

import kotlinx.coroutines.channels.BufferOverflow.DROP_OLDEST
import kotlinx.coroutines.flow.MutableSharedFlow

fun <T : Any> EventSharedFlow(value: T) = MutableSharedFlow<T>(1, 0, DROP_OLDEST)
fun EventSharedFlow() = MutableSharedFlow<Unit>(1, 0, DROP_OLDEST)
fun MutableSharedFlow<Unit>.emit() = tryEmit(Unit)