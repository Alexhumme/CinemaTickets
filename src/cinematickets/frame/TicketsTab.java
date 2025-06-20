/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cinematickets.frame;

import cinematickets.CinemaTickets;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import model.Client;
import model.Function;
import model.Room;
import model.Seat;
import model.Ticket;
import model.generic.LinkedList;
import model.generic.Node;

/**
 *
 * @author AlexVB
 */
public class TicketsTab extends javax.swing.JPanel {
/**
 * Atributos de clase
 */
Ticket selectedTicket = null; // Ticket seleccionado actualmente
LinkedList<Seat> selectedSeats = new LinkedList<>(); // Lista de asientos seleccionados por el usuario

// Modelo de tabla para mostrar los tickets en una JTable
public DefaultTableModel ticketsTableModel = new DefaultTableModel(new Object[]{
    "ID", "Película", "Fecha/Hora", "Sala", "Asiento", "ID Cliente"
}, 0) {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // Hace que las celdas no sean editables
    }
};

/**
 * Constructor de la pestaña TicketsTab
 */
public TicketsTab() {
    initComponents(); // Inicializa componentes gráficos
    updateState();     // Método personalizado para refrescar estado inicial (si existe)
    tblSeats.setModel(ticketsTableModel); // Asocia el modelo de tabla
}

/**
 * Método para guardar uno o varios tickets según los asientos seleccionados.
 */
public void handleSaveTicket() {
    Function function = (Function) cmbxFunctions.getSelectedItem(); // Función seleccionada
    
    if (function == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar una funcion.");
        return;
    }

    Client client = (Client) cmbxClients.getSelectedItem(); // Cliente seleccionado

    if (client == null) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
        return;
    }

    if (selectedSeats.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un asiento.");
        return;
    }

    // Recorre cada asiento seleccionado y genera un ticket por cada uno
    for (Seat seat : selectedSeats) {
        function.addOccupiedSeat(seat); // Marca el asiento como ocupado en la función

        Ticket ticket = new Ticket(
            CinemaTickets.getInstance().ticketsCounter, // ID autoincremental
            function,
            client,
            seat
        );

        // Agrega el ticket al sistema
        CinemaTickets.getInstance().tickets.add(ticket);
        CinemaTickets.getInstance().ticketsCounter++; // Incrementa contador de IDs
    }

    loadTickets();      // Refresca la tabla de tickets
    clearTicketForm();  // Limpia el formulario de selección
}

/**
 * Carga todas las funciones disponibles en el ComboBox.
 */
private void loadFunctions() {
    cmbxFunctions.removeAllItems(); // Limpia el combo si ya tenía datos

    LinkedList<Function> functions = CinemaTickets.getInstance().functions;
    Node<Function> f = functions.head;

    while (f != null) {
        cmbxFunctions.addItem(f.data); // Agrega cada función al ComboBox
        f = f.next;
    }
}

/**
 * Carga todos los clientes disponibles en el ComboBox.
 */
private void loadClients() {
    cmbxClients.removeAllItems(); // Limpia el combo

    LinkedList<Client> clients = CinemaTickets.getInstance().clients;
    Node<Client> c = clients.head;

    while (c != null) {
        cmbxClients.addItem(c.data); // Agrega cada cliente al ComboBox
        c = c.next;
    }
}

/**
 * Carga los asientos para la función seleccionada y los dibuja en el panel visual.
 */
private void loadSeats() {
    int count = 0;
    panelSeats.removeAll();        // Limpia el panel donde se dibujan los asientos
    selectedSeats.removeAll();     // Limpia la lista de asientos seleccionados

    // Si no se está editando un ticket, carga los asientos de la función seleccionada
    if (selectedTicket == null) {
        Function function = (Function) cmbxFunctions.getSelectedItem();
        if (function != null) {
            Room room = function.getRoom();
            if (room != null) {
                // Configura el layout del panel en forma de cuadrícula
                panelSeats.setLayout(new java.awt.GridLayout(
                        room.getHeight(),
                        room.getWidth(), 1, 1));

                // Por cada asiento de la sala, crea un componente visual SeatSquare
                for (Seat seat : room.getSeats()) {
                    TicketsTab.SeatSquare sSquare = new TicketsTab.SeatSquare(
                        seat, 
                        false, // No está seleccionado inicialmente
                        function.getOccupiedSeats().contains(seat) // Marcar si está ocupado
                    );
                    panelSeats.add(sSquare); // Agrega el componente al panel
                    count++;
                }
            }
        }
    }

    panelSeats.revalidate(); // Actualiza el contenedor
    panelSeats.repaint();
}

/**
 * Carga todos los tickets registrados y los muestra en la tabla.
 */
private void loadTickets() {
    ticketsTableModel.setRowCount(0); // Limpia la tabla

    // Recorre todos los tickets del sistema y agrega una fila por cada uno
    for (Ticket ticket : CinemaTickets.getInstance().tickets) {
        ticketsTableModel.addRow(new Object[]{
            ticket.getId(),
            ticket.getFunction().getMovie().getTitle(),
            ticket.getFunction().getDateTimeFormatted(), // Fecha formateada
            ticket.getFunction().getRoom().getId(),
            ticket.getSeat().getAsiento(), // o ticket.getSeat().toString()
            ticket.getClient().getId()
        });
    }
}

/**
 * Limpia el formulario de selección de función, cliente, etc.
 */
private void clearTicketForm() {
    cmbxFunctions.setSelectedItem(null);
    cmbxClients.setSelectedItem(null);
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSeats = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btnSaveTicket = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbxFunctions = new javax.swing.JComboBox<>();
        cmbxClients = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelSeats = new javax.swing.JPanel();

        tblSeats.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Pelicula", "Fecha", "Sala", "Silla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblSeats);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Solicitar tickets");

        btnSaveTicket.setText("Guardar");
        btnSaveTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveTicketActionPerformed(evt);
            }
        });

        jLabel3.setText("Funcion");

        cmbxFunctions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbxFunctionsActionPerformed(evt);
            }
        });

        jLabel4.setText("Cliente");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxClients, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmbxClients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(cmbxFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(new java.awt.GridBagLayout());

        panelSeats.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelSeats.setForeground(new java.awt.Color(204, 204, 204));
        panelSeats.setLayout(new java.awt.GridLayout(1, 0));
        jPanel3.add(panelSeats, new java.awt.GridBagConstraints());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSaveTicket))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveTicket)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveTicketActionPerformed
        handleSaveTicket();
    }//GEN-LAST:event_btnSaveTicketActionPerformed

    private void cmbxFunctionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbxFunctionsActionPerformed
        loadSeats();
    }//GEN-LAST:event_cmbxFunctionsActionPerformed

    void updateState() {
        loadSeats();
        loadFunctions();
        loadClients();
        loadTickets();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSaveTicket;
    private javax.swing.JComboBox<Client> cmbxClients;
    private javax.swing.JComboBox<Function> cmbxFunctions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelSeats;
    private javax.swing.JTable tblSeats;
    // End of variables declaration//GEN-END:variables

    private class SeatSquare extends JPanel {

        public Seat seat;
        public Boolean active;
        public boolean used;

        public SeatSquare(Seat seat, boolean active, boolean used) {
            this.seat = seat;
            this.active = active;
            this.used = used;

            this.setSize(10, 10);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel label = new JLabel(seat.getAsiento());
            label.setFont(new Font("Arial", Font.BOLD, 8));
            this.add(label);

            updateColor();

            // Listener para seleccionar/deseleccionar
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    toggleSelection();
                }
            });
        }

        private void toggleSelection() {
            if (selectedSeats.contains(seat)) {
                selectedSeats.removeByData(seat);
                active = false;
            } else {
                selectedSeats.add(seat);
                active = true;
            }
            updateColor();
        }

        public final void updateColor() {
            this.setBackground(active ? Color.BLUE : Color.GRAY);
            if(used) {
                this.setBackground(Color.lightGray);
            }

        }
        
    }

}
