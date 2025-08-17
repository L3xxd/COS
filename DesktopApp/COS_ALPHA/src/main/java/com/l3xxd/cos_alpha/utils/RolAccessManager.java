package com.l3xxd.cos_alpha.utils;

import javafx.scene.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolAccessManager {

    private static final Map<String, List<String>> permisosPorRol = new HashMap<>();

    static {
        permisosPorRol.put("ADMINISTRADOR", List.of(
                "btnVentas", "btnInventario", "btnEmpleados", "btnPedidos", "btnFinanzas"
        ));
        permisosPorRol.put("CAJERO", List.of(
                "btnVentas", "btnInventario","btnPedidos"
        ));
    }

    /**
     * Aplica visibilidad a los nodos según el rol.
     * @param rol rol del operador
     * @param nodos mapa de ID → Node
     */
    public static void aplicarPermisos(String rol, Map<String, Node> nodos) {
        List<String> visibles = permisosPorRol.getOrDefault(rol, List.of());

        for (Map.Entry<String, Node> entry : nodos.entrySet()) {
            String id = entry.getKey();
            Node nodo = entry.getValue();

            boolean mostrar = visibles.contains(id);
            nodo.setVisible(mostrar);
            nodo.setManaged(mostrar); // evita espacio vacío

            System.out.println("[AccessManager] " + id + " → " + (mostrar ? "✅ visible" : "❌ oculto"));
        }
    }
}
