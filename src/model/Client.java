/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 * Clase Cliente para el sistema de boletas de cine Representa la información de
 * un cliente que compra boletas
 */
public class Client {

    // Atributos privados
    private int id;           // ID único del cliente
    private String name;      // Nombre del cliente
    private String lastName;  // Apellido del cliente
    private int birthday;    // Fecha de nacimiento    
    private int birthmonth;    // Fecha de nacimiento
    private int birthyear;    // Fecha de nacimiento
    private String email;    // Correo electrónico

    // Constructor por defecto
    public Client() {
    }

    // Constructor con parámetros
    public Client(int id, String name, String lastName, int birthday, int birthmonth, int birthyear, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthday = birthday;
        this.birthmonth = birthmonth;
        this.birthyear = birthyear;
        this.email = email;
    }

    // Métodos getter y setter para id
    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    // Métodos getter y setter para name
    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    // Métodos getter y setter para lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    // Métodos getter y setter para birthday
    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int value) {
        this.birthday = value;
    }

    public int getBirthmonth() {
        return birthmonth;
    }

    public void setBirthmonth(int value) {
        this.birthmonth = value;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int value) {
        this.birthyear = value;
    }

    // Métodos getter y setter para email
    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    // Método toString para mostrar información del cliente
    @Override
    public String toString() {
        return getFullName();
    }

    // Método para obtener el nombre completo
    public String getFullName() {
        return name + " " + lastName;
    }

    // Método para validar si el email tiene formato válido (básico)
    public boolean isValidEmail() {
        return email != null && email.contains("@") && email.contains(".");
    }

    // Método equals para comparar clientes por ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Client client = (Client) obj;
        return id == client.id;
    }

    // Método hashCode
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
