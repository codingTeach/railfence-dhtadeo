import javax.swing.JOptionPane

fun cifrarRailFence(texto: String, clave: Int): String {
    val rail = Array(clave) { CharArray(texto.length) { '\n' } }
    var direccionAbajo = false
    var fila = 0
    var columna = 0

    for (caracter in texto) {
        if (fila == 0 || fila == clave - 1) direccionAbajo = !direccionAbajo
        rail[fila][columna++] = caracter
        fila += if (direccionAbajo) 1 else -1
    }

    return buildString {
        for (i in 0 until clave) {
            for (j in 0 until texto.length) {
                if (rail[i][j] != '\n') append(rail[i][j])
            }
        }
    }
}

fun descifrarRailFence(cifrado: String, clave: Int): String {
    val rail = Array(clave) { CharArray(cifrado.length) { '\n' } }
    var direccionAbajo = true
    var fila = 0
    var columna = 0

    for (i in cifrado.indices) {
        if (fila == 0) direccionAbajo = true
        if (fila == clave - 1) direccionAbajo = false
        rail[fila][columna++] = '*'
        fila += if (direccionAbajo) 1 else -1
    }

    var indice = 0
    for (i in 0 until clave) {
        for (j in cifrado.indices) {
            if (rail[i][j] == '*' && indice < cifrado.length) {
                rail[i][j] = cifrado[indice++]
            }
        }
    }

    val resultado = StringBuilder()
    fila = 0
    columna = 0
    for (i in cifrado.indices) {
        if (fila == 0) direccionAbajo = true
        if (fila == clave - 1) direccionAbajo = false
        if (rail[fila][columna] != '*') resultado.append(rail[fila][columna++])
        fila += if (direccionAbajo) 1 else -1
    }
    return resultado.toString()
}

fun main() {
    while (true) {
        val opciones = arrayOf("Cifrar", "Descifrar", "Salir")
        val eleccion = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Rail Fence Cipher",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0])

        if (eleccion == 2 || eleccion == JOptionPane.CLOSED_OPTION) break

        val mensaje = JOptionPane.showInputDialog("Ingrese el mensaje:") ?: continue
        val clave = JOptionPane.showInputDialog("Ingrese la clave:")?.toIntOrNull() ?: continue

        val resultado = if (eleccion == 0) {
            cifrarRailFence(mensaje, clave)
        } else {
            descifrarRailFence(mensaje, clave)
        }

        JOptionPane.showMessageDialog(null, "Resultado: $resultado")
    }
}