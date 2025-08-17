package com.l3xxd.cos_alpha.utils;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilidad para aplicar control de acceso visual por rol.
 * Define qué nodos deben ser visibles según el rol del operador.
 */
public class RolAccessManager {

    // Mapa de roles → lista de IDs de nodos visibles
    private static final Map<String, List<String>> permisosPorRol = new HashMap<>();

    static {
        permisosPorRol.put("ADMINISTRADOR", List.of(
                "btnVentas", "btnInventario", "btnEmpleados", "btnPedidos", "btnFinanzas"
        ));
        permisosPorRol.put("CAJERO", List.of(
                "btnVentas", "btnInventario", "btnPedidos"
        ));
    }

    /**
     * Aplica visibilidad a los nodos según el rol del operador.
     * Oculta o muestra nodos según la lista de permisos definida.
     * @param rol rol del operador ("ADMINISTRADOR", "CAJERO", etc.)
     * @param nodos mapa de ID → Node (botones u otros componentes visuales)
     */
    public static void aplicarPermisos(String rol, Map<String, Node> nodos) {
        List<String> visibles = permisosPorRol.getOrDefault(rol, List.of());

        for (Map.Entry<String, Node> entry : nodos.entrySet()) {
            String id = entry.getKey();
            Node nodo = entry.getValue();

            boolean mostrar = visibles.contains(id);
            nodo.setVisible(mostrar);
            nodo.setManaged(mostrar); // evita espacio vacío en layout

            System.out.printf("[AccessManager] %s → %s%n", id, mostrar ? "✅ visible" : "❌ oculto");
        }
    }
}

