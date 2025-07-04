/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cinematickets.frame;

import cinematickets.CinemaTickets;
import java.awt.Color;
import java.awt.LayoutManager;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Client;
import model.Room;
import model.Seat;
import model.generic.LinkedList;
import model.generic.Node;

/**
 *
 * @author AlexVB
 */

public class RoomsTab extends javax.swing.JPanel {
// Variable que almacenará el total de sillas calculadas (ancho * alto)
int totalSillas = 0;

// Sala actualmente seleccionada (por ejemplo, para editar)
Room selectedRoom;

/**
 * Constructor de la pestaña RoomsTab.
 * Se encarga de inicializar los componentes gráficos.
 */
public RoomsTab() {
    initComponents(); // Carga e inicializa los elementos visuales de la pestaña
}

/**
 * Método que se ejecuta cuando el usuario desea guardar una nueva sala.
 * Realiza validaciones, crea la sala, genera los asientos y la guarda en el sistema.
 */
public void handleSaveRoom() {
    try {
        String id = txtFieldId.getText().trim(); // Obtiene el ID ingresado

        // Validación: el campo ID no puede estar vacío
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un ID para la sala.");
            return;
        }

        // Verifica si ya existe una sala con ese mismo ID (para evitar duplicados)
        for (Node<Room> node = CinemaTickets.getInstance().rooms.head; node != null; node = node.next) {
            if (node.data.getId().equalsIgnoreCase(id)) {
                JOptionPane.showMessageDialog(this, "Ya existe una sala con ese ID.");
                return;
            }
        }

        // Obtiene las dimensiones ingresadas (ancho y alto)
        int width = (int) spnWidth.getValue();
        int height = (int) spnHeight.getValue();

        // Validación: las dimensiones deben ser mayores a cero
        if (width <= 0 || height <= 0) {
            JOptionPane.showMessageDialog(this, "Las dimensiones de la sala deben ser mayores a 0.");
            return;
        }

        // Crea el objeto Room (sala) con el ID, ancho y alto
        Room room = new Room(id, width, height);

        // Crear los asientos de la sala según las dimensiones
        // Se usa el alfabeto para nombrar las filas: A, B, C, ...
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        // Validación: no se pueden crear más filas que letras disponibles
        if (height > alphabet.length) {
            JOptionPane.showMessageDialog(this, "No se pueden crear más de " + alphabet.length + " filas de asientos.");
            return;
        }

        // Recorre filas y columnas para generar cada asiento
        for (int i = 0; i < height; i++) {
            char rowChar = alphabet[i]; // Letra de la fila
            for (int j = 1; j <= width; j++) {
                String sid = rowChar + String.valueOf(j); // Ejemplo: A1, A2, B1, etc.
                Seat seat = new Seat(sid); // Crea asiento con ese ID
                room.addSeat(seat);        // Agrega asiento a la sala
            }
        }

        // Agrega la sala creada a la lista de salas del sistema
        CinemaTickets.getInstance().rooms.add(room);
        JOptionPane.showMessageDialog(this, "Sala guardada correctamente.");

        loadRooms();      // Recarga la lista de salas en pantalla
        clearRoomForm();  // Limpia el formulario para una nueva entrada

    } catch (Exception e) {
        // En caso de error, muestra mensaje y traza en consola
        JOptionPane.showMessageDialog(this, "Error al guardar la sala: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Limpia los campos del formulario para registrar una nueva sala.
 */
private void clearRoomForm() {
    txtFieldId.setText("");           // Limpia el campo de ID
    spnWidth.setValue(1);             // Restaura valor por defecto para ancho
    spnHeight.setValue(1);            // Restaura valor por defecto para alto
    txtFieldId.requestFocus();        // Pone el cursor en el campo de ID
}

/**
 * Método que actualiza el total de sillas según el ancho y alto ingresado.
 * También genera visualmente los asientos dentro del panel correspondiente.
 */
public void updateTotal () {
    int width = (int) spnWidth.getValue();
    int height = (int) spnHeight.getValue();

    // Calcula el total de sillas como producto del ancho por el alto
    totalSillas = width * height;

    // Muestra ese número en el label correspondiente
    lblTotal.setText("total: "+ totalSillas);

    // Limpia visualmente el panel donde van los asientos
    panelSeats.removeAll();

    // Crea un componente visual (JPanel gris) por cada asiento
    for(int i = 0; i < totalSillas ; i++) {
        JPanel seat = new JPanel();
        seat.setBackground(Color.GRAY);
        seat.setSize(10, 10);
        panelSeats.add(seat);
    }

    // Organiza los asientos en una cuadrícula según alto y ancho
    panelSeats.setLayout(new java.awt.GridLayout(height, width, 1, 1));
}

/**
 * Carga todas las salas registradas desde la lista interna al JList de la interfaz.
 */
public void loadRooms () {
    LinkedList<Room> rooms = CinemaTickets.getInstance().rooms;

    // Crea un nuevo modelo de lista para la JList
    DefaultListModel model = new DefaultListModel<>();

    // Recorre la lista enlazada de salas y agrega cada una al modelo
    Node<Room> current = rooms.getHead(); // Obtiene la cabeza de la lista
    while (current != null) {
        model.addElement(current.data.toString()); // Usa el método toString() de Room
        current = current.next;
    }

    // Asigna el modelo cargado al componente visual listRooms
    listRooms.setModel(model);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roomsBar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listRooms = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        formContainer = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSave = new javax.swing.JButton();
        row1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFieldId = new javax.swing.JTextField();
        row2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        row3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        spnWidth = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        spnHeight = new javax.swing.JSpinner();
        lblTotal = new javax.swing.JLabel();
        panelSeatsContainer = new javax.swing.JPanel();
        panelSeats = new javax.swing.JPanel();

        listRooms.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listRooms.setName("Rooms"); // NOI18N
        jScrollPane1.setViewportView(listRooms);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Salas");

        javax.swing.GroupLayout roomsBarLayout = new javax.swing.GroupLayout(roomsBar);
        roomsBar.setLayout(roomsBarLayout);
        roomsBarLayout.setHorizontalGroup(
            roomsBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
        );
        roomsBarLayout.setVerticalGroup(
            roomsBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomsBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        formContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblTitle.setText("Registrar sala");

        btnSave.setText("Guardar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jLabel2.setText("Identificador");

        txtFieldId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout row1Layout = new javax.swing.GroupLayout(row1);
        row1.setLayout(row1Layout);
        row1Layout.setHorizontalGroup(
            row1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldId)
                .addContainerGap())
        );
        row1Layout.setVerticalGroup(
            row1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(row1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFieldId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Dimensiones de la sala (en sillas)");

        javax.swing.GroupLayout row2Layout = new javax.swing.GroupLayout(row2);
        row2.setLayout(row2Layout);
        row2Layout.setHorizontalGroup(
            row2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(row2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        row2Layout.setVerticalGroup(
            row2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel4.setText("Ancho");

        spnWidth.setModel(new javax.swing.SpinnerNumberModel(1, 1, 26, 1));
        spnWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnWidthStateChanged(evt);
            }
        });

        jLabel5.setText("Alto");

        spnHeight.setModel(new javax.swing.SpinnerNumberModel(1, 1, 26, 1));
        spnHeight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnHeightStateChanged(evt);
            }
        });

        lblTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotal.setText("total: 0");

        javax.swing.GroupLayout row3Layout = new javax.swing.GroupLayout(row3);
        row3.setLayout(row3Layout);
        row3Layout.setHorizontalGroup(
            row3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotal)
                .addContainerGap())
        );
        row3Layout.setVerticalGroup(
            row3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(row3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(row3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(row3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(spnHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTotal))
                    .addGroup(row3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(spnWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelSeatsContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSeatsContainer.setLayout(new java.awt.GridBagLayout());

        panelSeats.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelSeats.setLayout(new java.awt.GridLayout(1, 0));
        panelSeatsContainer.add(panelSeats, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout formContainerLayout = new javax.swing.GroupLayout(formContainer);
        formContainer.setLayout(formContainerLayout);
        formContainerLayout.setHorizontalGroup(
            formContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formContainerLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave))
                    .addComponent(row1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(row2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(row3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(formContainerLayout.createSequentialGroup()
                        .addComponent(lblTitle)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelSeatsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        formContainerLayout.setVerticalGroup(
            formContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(row1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(row2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(row3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelSeatsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSave)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomsBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(formContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roomsBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtFieldIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldIdActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        handleSaveRoom();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void spnWidthStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnWidthStateChanged
        updateTotal();
    }//GEN-LAST:event_spnWidthStateChanged

    private void spnHeightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnHeightStateChanged
        updateTotal();
    }//GEN-LAST:event_spnHeightStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel formContainer;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JList<String> listRooms;
    private javax.swing.JPanel panelSeats;
    private javax.swing.JPanel panelSeatsContainer;
    private javax.swing.JPanel roomsBar;
    private javax.swing.JPanel row1;
    private javax.swing.JPanel row2;
    private javax.swing.JPanel row3;
    private javax.swing.JSpinner spnHeight;
    private javax.swing.JSpinner spnWidth;
    private javax.swing.JTextField txtFieldId;
    // End of variables declaration//GEN-END:variables

    void updateState() {
    }
}
