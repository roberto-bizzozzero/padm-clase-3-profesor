package com.robertobizzozzero.clase_3_profesor

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Checkbox
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.robertobizzozzero.clase_3_profesor.ui.theme.Clase3profesorTheme

/*
        EXPLICACION VARIABLES DE ESTADO
*/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Clase3profesorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AplicacionTareas()
                }
            }
        }
    }
}
data class Tarea(val texto: String, val estaCompletada: Boolean = false)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Clase3profesorTheme {
        AplicacionTareas()
    }
}

//------------------------------------------------------------------------------------------------//

@SuppressLint("UnrememberedMutableState")
@Composable
fun AplicacionTareas() {
    var textoTarea by remember { mutableStateOf("") }
    var listadoTareas by remember { mutableStateOf(listOf<Tarea>())}

    // Estado derivado
    val conteoTareasCompletadas by remember(listadoTareas) {
        derivedStateOf { listadoTareas.count { it.estaCompletada }}
    }

    Column(
        modifier = Modifier.padding(7.dp)
    ) {
        Text("Tareas Completadas: $conteoTareasCompletadas")
        EntradaTarea(
            textoTarea = textoTarea,
            onTextoTareaCambiado = { textoTarea = it },
            onAgregarTarea = {
                if (textoTarea.isNotBlank()) {
                    listadoTareas = listadoTareas + Tarea(textoTarea)
                    textoTarea = ""
                }
            }
        )
        ListaTareas(listadoTareas, onTareaCambioCompletado = { indice, estaCompletada ->
            listadoTareas = listadoTareas.mapIndexed { i, tarea ->
                if (i == indice) tarea.copy(estaCompletada = estaCompletada) else tarea
            }
        })

    }






    // VERSION DENTRO
    //var listadoTareas by remember { mutableStateOf(listOf<String>()) }
    /*Column {
        TextField(value = textoTarea,
            onValueChange = { //cuando el valor del textfield cambia, entonces se ejecuta
                //Log.i("VARIABLE", "valor ingreso: $it, valor de la variable: $textoTarea")
                textoTarea = it //el "it" es el nuevo valor que el usuario a ingresado
            },
            label = {
                Text("Ingresa una tarea")
            } //basicamente un placeholder
        )


        Button(onClick = { //al hacer click
            if (textoTarea.isNotBlank()) {
                listadoTareas = listadoTareas + textoTarea //se añade lo ingresado en el array
                textoTarea = "" //se vacia el campo
            }
        }) {
            Text("Agregar") //texto del boton
        }


        listadoTareas.forEach { tarea -> //recorre el array con las tareas guardadas
            Text(tarea)
        }

    }*/
}




@Composable
fun ListaTareas(
    tareas: List<Tarea>,
    onTareaCambioCompletado: (Int, Boolean) -> Unit
) {
    tareas.forEachIndexed { indice, tarea ->
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Checkbox(
                checked = tarea.estaCompletada,
                onCheckedChange = { onTareaCambioCompletado(indice, it) }
            )
            Text(tarea.texto)
        }
    }
}




@Composable
fun EntradaTarea(
    textoTarea: String,
    onTextoTareaCambiado: (String) -> Unit,
    onAgregarTarea: () -> Unit
) {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        TextField(
            value = textoTarea,
            onValueChange = onTextoTareaCambiado,
            label = { Text("Ingresa una tarea") }
        )
        Button(onClick = onAgregarTarea) {
            Text("Agregar")
        }
    }
}

