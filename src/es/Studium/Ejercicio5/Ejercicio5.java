package es.Studium.Ejercicio5;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class Ejercicio5 extends Frame implements WindowListener, ActionListener
	{
		private static final long serialVersionUID = 1L;

		TextField idEmpleado = new TextField(25);
		TextField nombreEmpleado = new TextField(25);
		Button next = new Button("Próximo");
		Button previous = new Button("Anterior");
		Button primero = new Button("Primero");
		Button ultimo = new Button("Último");
		Label etiqueta = new Label("Ir a ...");
		TextField nRegistro = new TextField(5);
		Button ir = new Button("Ir");

		int contador = 0;
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Tema7.Empresa?useSSL=false";
		String login = "root";
		String password = "Studium.2019;";
		String sentencia = "SELECT * FROM Empleados";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		public Ejercicio5()
		{
			setLayout(new FlowLayout());
			setSize(250, 160);
			setResizable(false);
			add(idEmpleado);
			add(nombreEmpleado);
			add(primero);
			add(previous);
			add(next);
			add(ultimo);
			add(etiqueta);
			add(nRegistro);
			add(ir);

			next.addActionListener(this);
			previous.addActionListener(this);
			primero.addActionListener(this);
			ultimo.addActionListener(this);
			ir.addActionListener(this);
			addWindowListener(this);

			// Cargar el Driver
			try {
				Class.forName(driver);
			} catch (ClassNotFoundException e) {
				System.out.println("Se ha producido un error al cargar el Driver");
			}

			// Establecer la conexión con la base de datos
			try {
				connection = DriverManager.getConnection(url, login, password);
			} catch (SQLException e) {
				System.out.println("Se produjo un error al conectar a la Base de Datos");
			}

			//Realizar la consulta
			try {
				statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = statement.executeQuery(sentencia);

				// Para averiguar el número de registros obtenidos
				while (rs.next()) {
					contador++;
				}

				rs.first();

				//Poner en los TextField los valores obtenidos del 1º
				idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
				nombreEmpleado.setText(rs.getString("nombreEmpleado"));
			} catch (SQLException e) {
				System.out.println("Error en la sentencia SQL");
			}

			setVisible(true);
		}
		public static void main(String[] args) {
			new Ejercicio5();
		}
		public void windowActivated(WindowEvent windowEvent) {
		}
		public void windowClosed(WindowEvent windowEvent) {
		}
		public void windowClosing(WindowEvent windowEvent) {

			// cerrar los elementos de la base de datos

			try {
				rs.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println("error al cerrar " + e.toString());
			}
			System.exit(0);
		}
		public void windowDeactivated(WindowEvent windowEvent) {
		}
		public void windowDeiconified(WindowEvent windowEvent) {
		}
		public void windowIconified(WindowEvent windowEvent) {
		}
		public void windowOpened(WindowEvent windowEvent) {
		}
		public void actionPerformed(ActionEvent actionEvent) {
			// Hemos pulsado Próximo
			if (next.equals(actionEvent.getSource())) {
				try {
					// Si no hemos llegado al final
					if (rs.next()) {
						// Poner en los TextField los valores obtenidos
						idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
						nombreEmpleado.setText(rs.getString("nombreEmpleado"));
					} else {
						rs.previous();
						idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
						nombreEmpleado.setText(rs.getString("nombreEmpleado"));
					}
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL" + e.getMessage());
				}
			}
			if (previous.equals(actionEvent.getSource())) {
				try {
					// Si no hemos llegado al final
					if (rs.previous()) {
						// Poner en los TextField los valores obtenidos
						idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
						nombreEmpleado.setText(rs.getString("nombreEmpleado"));
					} else {
						rs.next();
						idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
						nombreEmpleado.setText(rs.getString("nombreEmpleado"));
					}
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL" + e.getMessage());
				}
			}
			if (primero.equals(actionEvent.getSource())) {
				try {
					rs.first();
					idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
					nombreEmpleado.setText(rs.getString("nombreEmpleado"));
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL" + e.getMessage());
				}
			}
			if (ultimo.equals(actionEvent.getSource())) {
				try {
					rs.last();
					idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
					nombreEmpleado.setText(rs.getString("nombreEmpleado"));
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL" + e.getMessage());
				}
			}
			if (ir.equals(actionEvent.getSource())) {
				try {
					// Comprobar que el número indicado está dentro de los límites
					if (contador >= Integer.parseInt(nRegistro.getText())) {
						rs.absolute(Integer.parseInt(nRegistro.getText()));
						idEmpleado.setText(Integer.toString(rs.getInt("idEmpleado")));
						nombreEmpleado.setText(rs.getString("nombreEmpleado"));
					} else {
						System.out.println("El número introducido no es válido.");
					}
				} catch (SQLException e) {
					System.out.println("Error en la sentencia SQL" + e.getMessage());
				}
			}
		}
	}