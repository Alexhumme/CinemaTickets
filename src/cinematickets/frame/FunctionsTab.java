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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;
import model.Function;
import model.Movie;
import model.Room;
import model.Seat;
import model.generic.LinkedList;
import model.generic.Node;

/**
 *
 * @author AlexVB
 */
public class FunctionsTab extends javax.swing.JPanel {
/**
 * Esta clase maneja la pestaña de funciones de la interfaz.
 * Permite crear, actualizar, eliminar y visualizar funciones de cine.
 */
private Function selectedFunction; // Guarda la función seleccionada actualmente para editar o eliminar

// Modelo de la tabla que muestra las funciones
private DefaultTableModel modelFunctions = new DefaultTableModel(
    new Object[]{
        "Película",  // Título de la película
        "Fecha",     // Fecha de la función
        "Hora",      // Hora de inicio
        "Duración",  // Duración en minutos
        "Sala",      // Sala donde se proyecta
        "3D"         // Si es en 3D
    }, 0
);

public FunctionsTab() {
    initComponents(); // Inicializa los componentes gráficos de la interfaz
    updateState();    // Refresca el estado de los componentes
    // Agrega un listener para seleccionar una función haciendo clic en una fila de la tabla
    tblFunctions.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectFunctionFromTable(); // Método que carga la función seleccionada al formulario
        }
    });
}

private void handleSaveFunction() {
    try {
        // Obtener película seleccionada
        Movie movie = (Movie) cmbxMovies.getSelectedItem();
        if (movie == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una película.");
            return;
        }

        // Obtener la fecha seleccionada del componente DatePicker
        Date selectedDate = dPickerDate.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fecha.");
            return;
        }
        LocalDate date = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Obtener la hora desde el spinner
        Date timeDate = (Date) spnTime.getValue();
        LocalTime time = timeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().withSecond(0).withNano(0);

        // Obtener duración
        int durationMinutes = (Integer) spnDuration.getValue();

        // Obtener sala seleccionada
        Room room = (Room) cmbxRooms.getSelectedItem();
        if (room == null) {
            JOptionPane.showMessageDialog(this, "Debe ingresar una sala.");
            return;
        }

        // Saber si es función 3D
        Boolean is3D = cbxIs3D.isSelected();

        // Validar si se solapa con otra función en la misma sala y fecha
        LocalDateTime newStart = LocalDateTime.of(date, time);
        LocalDateTime newEnd = newStart.plusMinutes(durationMinutes);

        for (Function f : CinemaTickets.getInstance().functions) {
            if (!f.getRoom().equals(room)) continue;
            if (!f.getDate().equals(date)) continue;

            LocalDateTime existingStart = LocalDateTime.of(f.getDate(), f.getTime());
            LocalDateTime existingEnd = existingStart.plusMinutes(f.getDuration());

            boolean overlaps = !newEnd.isBefore(existingStart) && !newStart.isAfter(existingEnd);
            if (overlaps) {
                JOptionPane.showMessageDialog(this, "Ya existe una función en esa sala que se superpone con el horario.");
                return;
            }
        }

        // Crear y agregar nueva función
        Function function = new Function(movie, date, time, durationMinutes, room, is3D);
        CinemaTickets.getInstance().functions.add(function);

        JOptionPane.showMessageDialog(this, "Función guardada correctamente.");
        clearFunctionForm(); // Limpia el formulario
        loadFunctions();     // Recarga la tabla de funciones

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al guardar la función: " + e.getMessage());
        e.printStackTrace();
    }
}

private void handleUpdateFunction() {
    if (selectedFunction == null) {
        JOptionPane.showMessageDialog(this, "No hay función seleccionada para actualizar.");
        return;
    }

    Movie movie = (Movie) cmbxMovies.getSelectedItem();
    Room room = (Room) cmbxRooms.getSelectedItem();
    Date date = dPickerDate.getDate();
    int duration = (int) spnDuration.getValue();
    Boolean is3D = cbxIs3D.isSelected();

    if (movie == null || room == null || date == null) {
        JOptionPane.showMessageDialog(this, "Debes completar todos los campos.");
        return;
    }

    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Function updated = new Function(movie, localDate, duration, room, is3D);

    updateFunction(updated);
    loadFunctions();
    JOptionPane.showMessageDialog(this, "Función actualizada correctamente.");
    clearFunctionForm();
}

public void updateFunction(Function updatedFunction) {
    if (selectedFunction != null) {
        selectedFunction.setMovie(updatedFunction.getMovie());
        selectedFunction.setRoom(updatedFunction.getRoom());
        selectedFunction.setDate(updatedFunction.getDate());
        selectedFunction.setTime(updatedFunction.getTime());
    }
    loadFunctions(); // Actualiza la tabla con los cambios
}

private void handleDeleteFunction() {
    if (selectedFunction != null) {
        CinemaTickets.getInstance().functions.removeByData(selectedFunction);
        JOptionPane.showMessageDialog(this, "Función eliminada.");
        clearFunctionForm();
    } else {
        JOptionPane.showMessageDialog(this, "No hay cliente seleccionado.");
    }
    loadFunctions();
}

// Limpia el formulario de funciones
private void clearFunctionForm() {
    cmbxMovies.setSelectedIndex(-1);
    cmbxRooms.setSelectedIndex(-1);
    dPickerDate.setDate(null);
    spnTime.removeAll();
    spnDuration.setValue(0);

    selectedFunction = null;
    btnDeleteFunction.setEnabled(false);
}

// Carga las películas disponibles en el combobox
private void loadMovies() {
    cmbxMovies.removeAllItems();
    LinkedList<Movie> movies = CinemaTickets.getInstance().movies;
    Node<Movie> m = movies.head;
    while (m != null) {
        cmbxMovies.addItem(m.data);
        m = m.next;
    }
}

// Configura el spinner de hora
private void loadHours() {
    spnTime.setModel(new SpinnerDateModel());
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(spnTime, "HH:mm");
    spnTime.setEditor(timeEditor);
    spnTime.setValue(new Date());
}

// Carga las salas en el combobox
private void loadRooms() {
    cmbxRooms.removeAllItems();
    LinkedList<Room> rooms = CinemaTickets.getInstance().rooms;
    Node<Room> r = rooms.head;
    while (r != null) {
        cmbxRooms.addItem(r.data);
        r = r.next;
    }
}

// Muestra los asientos de la sala en forma de grilla
private void loadSeats() {
    int count = 0;
    panelSeats.removeAll();
    Room room = (selectedFunction == null) ? (Room) cmbxRooms.getSelectedItem() : selectedFunction.getRoom();

    if (room != null) {
        panelSeats.setLayout(new java.awt.GridLayout(room.getHeight(), room.getWidth(), 1, 1));
        for (Seat seat : room.getSeats()) {
            SeatSquare sSquare = new SeatSquare(seat, false);
            panelSeats.add(sSquare);

            if (selectedFunction != null && selectedFunction.getOccupiedSeats().contains(seat)) {
                sSquare.setActive(true); // Marca como ocupada si ya está reservada
            }
            count++;
        }
    }

    panelSeats.revalidate();
    panelSeats.repaint();
}

// Carga las funciones en la tabla
private void loadFunctions() {
    tblFunctions.setModel(modelFunctions);
    modelFunctions.setRowCount(0); // Limpiar tabla

    LinkedList<Function> functions = CinemaTickets.getInstance().functions;
    for (Function f : functions) {
        modelFunctions.addRow(new Object[]{
            f.getMovie().getTitle(),
            f.getDate().toString(),
            f.getTime().toString(),
            f.getDuration() + " min",
            f.getRoom().getId(),
            f.getIs3D() ? "Sí" : "No"
        });
    }

    tblFunctions.revalidate();
    tblFunctions.repaint();
}

// Método para identificar la función seleccionada desde la tabla
private void selectFunctionFromTable() {
    int selectedRow = tblFunctions.getSelectedRow();
    if (selectedRow < 0) return;

    String salaStr = tblFunctions.getValueAt(selectedRow, 4).toString();
    String fechaStr = tblFunctions.getValueAt(selectedRow, 1).toString();
    String horaStr = tblFunctions.getValueAt(selectedRow, 2).toString();

    for (Function f : CinemaTickets.getInstance().functions) {
        String fFecha = f.getDateFormatted();
        LocalDate parsedDate = LocalDate.parse(fechaStr);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedFechaStr = parsedDate.format(formatter);
        String fHora = f.getTimeFormatted();
        String fSala = f.getRoom().getId();

        if (fFecha.equals(formattedFechaStr) && fHora.equals(horaStr) && fSala.equals(salaStr)) {
            selectedFunction = f;
            loadFunctionToForm(f);
            btnDeleteFunction.setEnabled(true);
            return;
        }
    }

    JOptionPane.showMessageDialog(null, "No se encontró la función.");
}

// Carga los datos de una función al formulario
private void loadFunctionToForm(Function f) {
    cmbxMovies.setSelectedItem(f.getMovie());
    cmbxRooms.setSelectedItem(f.getRoom());

    LocalDate localDate = f.getDate();
    Date date = java.sql.Date.valueOf(localDate);
    dPickerDate.setDate(date);

    LocalTime localTime = f.getTime();
    LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), localTime);
    Date timeAsDate = java.util.Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

    spnTime.setValue(timeAsDate);
    loadSeats();
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
        tblFunctions = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spnTime = new javax.swing.JSpinner();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cmbxMovies = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        spnDuration = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbxRooms = new javax.swing.JComboBox<>();
        cbxIs3D = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        panelSeats = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnDeleteFunction = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblFunctions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pelicula", "Fecha", "Hora", "Sala"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFunctions);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Registrar funcion");

        jLabel2.setText("Fecha");

        jLabel3.setText("Hora");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(139, 139, 139)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spnTime)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(spnTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Pelicula");

        jLabel6.setText("Duracion (mins)");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxMovies, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spnDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(spnDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cmbxMovies, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Sala");

        cmbxRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbxRoomsActionPerformed(evt);
            }
        });

        cbxIs3D.setText("Funcion en 3D");
        cbxIs3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxIs3DActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxRooms, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cbxIs3D)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbxRooms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxIs3D))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel6.setLayout(new java.awt.GridBagLayout());

        panelSeats.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelSeats.setLayout(new java.awt.GridLayout(1, 0));
        jPanel6.add(panelSeats, new java.awt.GridBagConstraints());

        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnDeleteFunction.setForeground(new java.awt.Color(255, 0, 51));
        btnDeleteFunction.setText("Eliminar");
        btnDeleteFunction.setEnabled(false);
        btnDeleteFunction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFunctionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeleteFunction)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnDeleteFunction))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator1)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void cbxIs3DActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxIs3DActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxIs3DActionPerformed

    private void cmbxRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbxRoomsActionPerformed
        loadSeats();
    }//GEN-LAST:event_cmbxRoomsActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (selectedFunction == null) {
            handleSaveFunction();
        } else {
            handleUpdateFunction();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnDeleteFunctionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteFunctionActionPerformed
        handleDeleteFunction();
    }//GEN-LAST:event_btnDeleteFunctionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeleteFunction;
    private javax.swing.JCheckBox cbxIs3D;
    private javax.swing.JComboBox<Movie> cmbxMovies;
    private javax.swing.JComboBox<Room> cmbxRooms;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelSeats;
    private javax.swing.JSpinner spnDuration;
    private javax.swing.JSpinner spnTime;
    private javax.swing.JTable tblFunctions;
    // End of variables declaration//GEN-END:variables

    public void updateState() {
        loadMovies();
        loadRooms();
        loadHours();
        loadSeats();
        loadFunctions();
    }

    private static class SeatSquare extends JPanel {

        public Seat seat;
        public Boolean active;

        public SeatSquare(Seat seat, Boolean active) {
            this.seat = seat;
            this.active = active;

            this.setSize(10, 10);

            JLabel label = new JLabel(seat.getAsiento());
            label.setFont(new Font("Arial", 1, 8));
            this.add(label);

            updateColor();
        }

        public void setActive(Boolean state) {
            active = state;
            updateColor();
        }

        public final void updateColor() {
            this.setBackground(active ? Color.BLUE : Color.GRAY);
        }
    }
}
