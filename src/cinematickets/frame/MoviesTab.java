/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package cinematickets.frame;

import cinematickets.CinemaTickets;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Category;
import model.Classification;
import model.Movie;
import model.generic.LinkedList;
import model.generic.Node;

/**
 *
 * @author AlexVB
 */
public class MoviesTab extends javax.swing.JPanel {
// Lista enlazada donde se almacenan las categorías seleccionadas para una película
LinkedList<Category> selectedCategories = new LinkedList<>();

// Ruta del póster seleccionado para una película
String selectedPosterSrc;

// Película actualmente seleccionada (por ejemplo, al editar)
Movie selectedMovie = null;

/**
 * Constructor de la pestaña de películas.
 * Se encarga de inicializar la interfaz gráfica y cargar las clasificaciones y categorías.
 */
public MoviesTab() {
    initComponents(); // Inicializa los componentes gráficos

    loadClassifications(); // Carga las clasificaciones de películas en el comboBox
    listCategories.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); // Permite seleccionar múltiples categorías
    loadCategories(); // Carga las categorías disponibles en la interfaz
}

/**
 * Método encargado de guardar una nueva película en el sistema.
 * Realiza validaciones, construye el objeto Movie y lo agrega a la lista.
 */
public void handleSaveMovie() {
    try {
        // Obtiene el título y sinopsis ingresados por el usuario
        String title = txtFieldTitle.getText().trim();
        String sinopsis = txtAreaSinopsis.getText().trim();
        Classification classification = (Classification) cmbxClassification.getSelectedItem();

        // Validación: el título no puede estar vacío
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar el título de la película.");
            return;
        }

        // Validación: debe haber al menos una categoría seleccionada
        if (selectedCategories == null || selectedCategories.size() == 0) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una categoría.");
            return;
        }

        // Verifica si está marcada como cartelera (onBillboard)
        boolean onBillboard = cbxOnBillboard.isSelected();

        // Verificación de duplicados por título (evita títulos repetidos)
        CinemaTickets mainApp = CinemaTickets.getInstance();
        for (Node<Movie> node = mainApp.movies.head; node != null; node = node.next) {
            if (node.data.getTitle().equalsIgnoreCase(title)) {
                JOptionPane.showMessageDialog(this, "Ya existe una película con ese título.");
                return;
            }
        }

        // Crea el nuevo objeto Movie con los datos ingresados
        Movie movie = new Movie(
                title,
                sinopsis,
                selectedPosterSrc,
                classification,
                selectedCategories,
                onBillboard
        );

        // Agrega la película a la lista de películas del sistema
        mainApp.movies.add(movie);
        JOptionPane.showMessageDialog(this, "Película guardada correctamente.");

        movie.printData(); // (opcional) Imprime datos para debugging
        loadMovies();      // Recarga la interfaz gráfica con la nueva lista
        clearMovieForm();  // Limpia los campos del formulario

    } catch (Exception e) {
        // En caso de error, muestra un mensaje al usuario
        JOptionPane.showMessageDialog(this, "Error al guardar la película: " + e.getMessage());
        e.printStackTrace(); // Muestra el error en consola para depuración
    }
}

/**
 * Actualiza los datos de la película seleccionada.
 */
private void handleUpdateMovie() {
    // Verifica si hay una película seleccionada
    if (selectedMovie == null) {
        return;
    }

    String title = txtFieldTitle.getText().trim();
    Classification classification = (Classification) cmbxClassification.getSelectedItem();

    // Validación: título no puede estar vacío
    if (title.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese un título.");
        return;
    }

    // Actualiza los datos del objeto seleccionado
    selectedMovie.setTitle(title);
    selectedMovie.setClassification(classification);
    selectedMovie.setCategories(selectedCategories);
    selectedMovie.setPosterSrc(selectedPosterSrc);

    // Limpia selección y recarga la lista de películas
    selectedMovie = null;
    clearMovieForm();
    loadMovies();
}

/**
 * Elimina la película actualmente seleccionada (si hay alguna).
 */
private void handleDeleteMovie() {
    if (selectedMovie == null) {
        return;
    }

    // Confirmación del usuario antes de eliminar
    int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea eliminar la película seleccionada?", "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        // Elimina la película de la lista
        CinemaTickets.getInstance().movies.removeByData(selectedMovie);
        selectedMovie = null;
        clearMovieForm();
        loadMovies(); // Actualiza la interfaz
    }
}

/**
 * Carga los datos de una película en el formulario para edición.
 */
private void loadMovieToForm(Movie movie) {
    txtFieldTitle.setText(movie.getTitle());
    cmbxClassification.setSelectedItem(movie.getClassification());

    // Carga las categorías de la película al modelo visual
    DefaultListModel model = new DefaultListModel<>();
    for (Category c : movie.categories) {
        model.addElement(c);
    }
    listCategories.setModel(model);

    // Muestra la ruta del póster seleccionado
    if (movie.getPosterSrc() != null) {
        btnOpenPosterSelector.setText(movie.getPosterSrc());
    } else {
        btnOpenPosterSelector.setText("");
    }

    selectedMovie = movie; // Marca como seleccionada la película actual
    btnDeleteMovie.setEnabled(true); // Activa el botón de eliminar
}

/**
  * Carga todas las clasificaciones disponibles en el comboBox.
  */
private void loadClassifications() {
    cmbxClassification.removeAllItems(); // Limpia items anteriores

    for (Classification c : Classification.values()) {
        cmbxClassification.addItem(c);
    }
}

/**
  * Carga las categorías disponibles tanto en el comboBox como en la lista.
  */
private void loadCategories() {
    listCategories.removeAll(); // Limpia la lista de selección múltiple
    cmbxCategory.removeAllItems(); // Limpia el comboBox

    for (Category c : Category.values()) {
        cmbxCategory.addItem(c);
    }
}

/**
 * Carga y dibuja todas las películas en el panel correspondiente.
 */
private void loadMovies() {
    moviesRow.removeAll(); // Limpia el panel donde se dibujan las tarjetas de películas

    LinkedList movies = CinemaTickets.getInstance().movies;
    Node<Movie> temp = movies.head;

    // Añade espacio inicial
    moviesRow.add(Box.createRigidArea(new Dimension(10, 0)));

    while (temp != null) {
        moviesRow.add(new MovieCard(temp.data)); // Añade tarjeta por cada película
        moviesRow.add(Box.createRigidArea(new Dimension(10, 0))); // Espacio entre tarjetas
        temp = temp.next;
    }

    // Actualiza el panel visualmente
    moviesRow.revalidate();
    moviesRow.repaint();
}

/**
 * Limpia todos los campos del formulario de películas.
 */
public void clearMovieForm() {
    txtFieldTitle.setText("");                     // Limpia campo de título
    txtAreaSinopsis.setText("");                   // Limpia sinopsis
    selectedPosterSrc = "";                        // Limpia ruta del póster
    btnOpenPosterSelector.setText("Seleccionar..."); // Restaura texto del botón
    cmbxCategory.setSelectedIndex(0);              // Reinicia categoría
    cmbxClassification.setSelectedIndex(0);        // Reinicia clasificación
    selectedCategories.removeAll();                // Borra categorías seleccionadas
    listCategories.setModel(new DefaultListModel<>()); // Limpia lista visual
    cbxOnBillboard.setSelected(false);             // Desmarca checkbox

    btnDeleteMovie.setEnabled(false);              // Desactiva botón de eliminar
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSlider1 = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        movieForm = new javax.swing.JPanel();
        moviesTabTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFieldTitle = new javax.swing.JTextField();
        cmbxClassification = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        cbxOnBillboard = new javax.swing.JCheckBox();
        btnSaveMovie = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnOpenPosterSelector = new javax.swing.JButton();
        btnDeleteMovie = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaSinopsis = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        cmbxCategory = new javax.swing.JComboBox<>();
        btnAddCateory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCategories = new javax.swing.JList<>();
        moviesRow = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setPreferredSize(new java.awt.Dimension(500, 400));

        movieForm.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        moviesTabTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        moviesTabTitle.setText("Registrar Pelicula");

        jLabel2.setText("Titulo");

        jLabel3.setText("Clasificacion");

        jLabel4.setText("Categorias");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxClassification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFieldTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbxClassification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        cbxOnBillboard.setText("En cartelera");
        cbxOnBillboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxOnBillboardActionPerformed(evt);
            }
        });

        btnSaveMovie.setText("Guardar");
        btnSaveMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveMovieActionPerformed(evt);
            }
        });

        jLabel5.setText("Poster");

        btnOpenPosterSelector.setText("Seleccionar...");
        btnOpenPosterSelector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenPosterSelectorActionPerformed(evt);
            }
        });

        btnDeleteMovie.setForeground(new java.awt.Color(255, 0, 0));
        btnDeleteMovie.setText("Eliminar");
        btnDeleteMovie.setEnabled(false);
        btnDeleteMovie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMovieActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cbxOnBillboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOpenPosterSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(btnDeleteMovie, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSaveMovie)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxOnBillboard)
                    .addComponent(btnSaveMovie)
                    .addComponent(jLabel5)
                    .addComponent(btnOpenPosterSelector)
                    .addComponent(btnDeleteMovie))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        txtAreaSinopsis.setColumns(20);
        txtAreaSinopsis.setRows(5);
        txtAreaSinopsis.setToolTipText("Sinopsis de la pelicula...");
        txtAreaSinopsis.setWrapStyleWord(true);
        txtAreaSinopsis.setName("fr"); // NOI18N
        jScrollPane2.setViewportView(txtAreaSinopsis);

        btnAddCateory.setText("Agregar categoria");
        btnAddCateory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCateoryActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listCategories);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddCateory, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmbxCategory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbxCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddCateory))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout movieFormLayout = new javax.swing.GroupLayout(movieForm);
        movieForm.setLayout(movieFormLayout);
        movieFormLayout.setHorizontalGroup(
            movieFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(movieFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(movieFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(movieFormLayout.createSequentialGroup()
                        .addComponent(moviesTabTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        movieFormLayout.setVerticalGroup(
            movieFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, movieFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(moviesTabTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        moviesRow.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        moviesRow.setLayout(new javax.swing.BoxLayout(moviesRow, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(moviesRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(movieForm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(movieForm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(moviesRow, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxOnBillboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxOnBillboardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxOnBillboardActionPerformed

    private void btnSaveMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveMovieActionPerformed
        if (selectedMovie == null) {
            handleSaveMovie();
        } else {
            handleUpdateMovie();
        }
        loadMovies();
    }//GEN-LAST:event_btnSaveMovieActionPerformed

    private void btnAddCateoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCateoryActionPerformed
        Category category = (Category) cmbxCategory.getSelectedItem();

        // Evitar agregar duplicados
        if (!selectedCategories.contains(category)) {
            selectedCategories.add(category);
        }

        // Crear un nuevo modelo para mostrar en la JList
        DefaultListModel model = new DefaultListModel<>();

        // Recorrer tu LinkedList personalizada y agregar los elementos al modelo
        Node<Category> current = selectedCategories.getHead(); // asegúrate de tener un método para obtener el head
        while (current != null) {
            model.addElement(current.data);
            current = current.next;
        }

        listCategories.setModel(model);
    }//GEN-LAST:event_btnAddCateoryActionPerformed

    private void btnOpenPosterSelectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenPosterSelectorActionPerformed
        JFileChooser fchPoster = new JFileChooser();
        fchPoster.setDialogTitle("Seleccionar imagen para el póster");
        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Imágenes (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"
        );
        fchPoster.setFileFilter(imageFilter);

        int posterSelection = fchPoster.showOpenDialog(this);
        if (posterSelection == fchPoster.APPROVE_OPTION) {
            File fileToOpen = fchPoster.getSelectedFile();

            // Ruta absoluta o relativa
            String imagePath = fileToOpen.getAbsolutePath(); // O usa getPath() si quieres relativo

            // Puedes guardarlo en una variable para usarlo como ruta del póster
            selectedPosterSrc = imagePath;
            btnOpenPosterSelector.setText("(" + fileToOpen.getName() + ")");
        }
    }//GEN-LAST:event_btnOpenPosterSelectorActionPerformed

    private void btnDeleteMovieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMovieActionPerformed
        handleDeleteMovie();
    }//GEN-LAST:event_btnDeleteMovieActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCateory;
    private javax.swing.JButton btnDeleteMovie;
    private javax.swing.JButton btnOpenPosterSelector;
    private javax.swing.JButton btnSaveMovie;
    private javax.swing.JCheckBox cbxOnBillboard;
    private javax.swing.JComboBox<Category> cmbxCategory;
    private javax.swing.JComboBox<Classification> cmbxClassification;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JList<String> listCategories;
    private javax.swing.JPanel movieForm;
    private javax.swing.JPanel moviesRow;
    private javax.swing.JLabel moviesTabTitle;
    private javax.swing.JTextArea txtAreaSinopsis;
    private javax.swing.JTextField txtFieldTitle;
    // End of variables declaration//GEN-END:variables

    void updateState() {
        loadCategories();
        loadClassifications();
    }

    public class MovieCard extends javax.swing.JPanel {

        /**
         * Creates new form MovieCard
         */
        public Movie movie;

        /**
         * Creates new form MovieCard
         *
         * @param movie
         */
        public MovieCard(Movie movie) {
            initComponents();
            this.movie = movie;
            lblTitle.setText(movie.title);

            ImageIcon icon = new ImageIcon(movie.posterSrc);
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            lblPoster.setIcon(new ImageIcon(img));
        }

        /**
         * This method is called from within the constructor to initialize the
         * form. WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
        private void initComponents() {

            lblTitle = new javax.swing.JLabel();
            btnEdit = new javax.swing.JButton();
            lblPoster = new javax.swing.JLabel();

            setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
            setToolTipText("");
            setMaximumSize(new java.awt.Dimension(100, 120));
            setMinimumSize(new java.awt.Dimension(100, 120));

            lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
            lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lblTitle.setText("jLabel1");

            btnEdit.setBackground(new java.awt.Color(0, 102, 255));
            btnEdit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
            btnEdit.setForeground(new java.awt.Color(255, 255, 255));
            btnEdit.setText("Editar");
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnEditActionPerformed(evt);
                }
            });
            lblPoster.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            lblPoster.setIconTextGap(0);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                    .addContainerGap())
                            .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPoster, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(lblPoster, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                                    .addGap(0, 0, 0)
                                    .addComponent(lblTitle)
                                    .addGap(0, 0, 0))
            );
        }

        private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
            loadMovieToForm(movie);
        }

// </editor-fold>                        
        // Variables declaration - do not modify                     
        private javax.swing.JButton btnEdit;
        private javax.swing.JLabel lblPoster;
        private javax.swing.JLabel lblTitle;
        // End of variables declaration                   
    }

}
