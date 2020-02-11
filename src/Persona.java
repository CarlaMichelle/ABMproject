import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Persona {

	public static void main(String[] args) {

		Connection conexion = null;

		try {

			conexion = AdminConexion.ObtenerConexion();
			Scanner sc = new Scanner(System.in);
			int opcion = mostrarMenu(sc);

			while (opcion != 0) {

				switch (opcion) {

				case 1:

					darAlta(conexion, sc);

					break;

				case 2:

					modificar(conexion, sc);

					break;

				case 3:

					darBaja(conexion, sc);

					break;

				case 4:

					mostrarLista(conexion);

					break;

				case 5:

					buscarEnLista(conexion, sc);

					break;

				case 0:

					break;

				default:

					System.out.println("Opcion incorrecta");

					break;

				}
				opcion = mostrarMenu(sc);
			}
			conexion.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// ---------------------------DAR ALTA------------------------------------

	private static void darAlta(Connection conexion, Scanner sc) {

		System.out.println("=====ALTA DE PERSONA =====");
		System.out.println("");
		System.out.println("Ingrese NOMBRE");
		String nombre = sc.next();
		System.out.println("Ingrese FECHA DE NACIMIENTO  DD/MM/AAAA");
		String fNac = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		try {

			Date fechaNac = sdf.parse(fNac);
			int edad = calcularEdad(fechaNac);
			Statement stmt = conexion.createStatement();

			stmt.executeUpdate("INSERT INTO PERSONA VALUES ('" + nombre
					+ " ', " + edad + ", " + fechaNac + ")");

			System.out.println("Usuario agregado con exito");
			System.out.println("==========================");
			System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
			System.out.println("1.Agregar otra persona");
			System.out.println("2.Mostrar mas opciones");
			System.out.println("0.Salir del sistema");
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				darAlta(conexion, sc);
				break;

			case 2:
				mostrarMasOpciones(conexion, sc);

			case 0:
				System.out.println("GRACIAS POR USAR EL SISTEMA");
				break;

			default:
				System.out.println("OPCION INVALIDA");
				break;
			}
		}

		catch (ParseException e1) {
			e1.printStackTrace();
			System.out.println("Error al agregar");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

	}

	// ------------------------------MODIFICAR----------------------------------

	private static void modificar(Connection conexion, Scanner sc) {

		System.out.println("===MODIFICACION===");
		System.out.println("");
		System.out.println("Ingrese el numero de ID del usuario a modificar");
		int nIde = sc.nextInt();
		System.out.println("¿QUE DESEA MODIFICAR DEL USUARIO?");
		System.out.println("ingrese una opcion");
		System.out.println("1.NOMBRE");
		System.out.println("2.EDAD");
		System.out.println("3.FECHA DE NACIMIENTO");

		try {


			int opcion = sc.nextInt();
			Statement stmt = conexion.createStatement();

			switch (opcion) {

			case 1:

				System.out.println("=MODIFICAR NOMBRE=");
				System.out.println("Ingrese nuevo nombre");
				String nuevoNombre = sc.next();
				stmt.executeQuery("UPDATE persona SET nombre: '" + nuevoNombre
						+ " ' WHERE id= " + nIde + " ");
				conexion.close();

				System.out
						.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(conexion, sc);
				break;

			case 2:

				System.out.println("=MODIFICAR EDAD=");
				System.out.println("Ingrese nueva edad");
				int nuevaEdad = sc.nextInt();
				stmt.executeUpdate("UPDATE persona SET edad: " + nuevaEdad
						+ " 'WHERE id= " + nIde + "");

				System.out
						.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(conexion, sc);

				break;

			case 3:

				System.out.println("=MODIFICAR FECHA DE NACIMIENTOD=");
				System.out
						.println("Ingrese nueva fecha de nacimiento (formato: dd/MM/yyyy");

				String nuevaFecha = sc.next();

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

				Date nuevaFechaNac = sdf.parse(nuevaFecha);

				stmt.executeUpdate("UPDATE persona SET fecha de nacimiento: "
						+ nuevaFechaNac + " 'WHERE id= " + nIde + "");
				conexion.close();

				System.out
						.println("Su modificacion ha sido realizada exitosamente");
				mostrarMasOpciones(conexion, sc);

				break;

			
		}
	} catch (SQLException e) {
		System.out.println("Error en la modificacion");
	} catch (ParseException e) {
				e.printStackTrace();
	}

}

	// --------------------------DAR BAJA------------------------------

	private static void darBaja(Connection conexion, Scanner sc) {
		
		
		try{
			
			
			conexion = AdminConexion.ObtenerConexion();
			Statement stmt = conexion.createStatement();
		
		System.out.println ("===DAR DE BAJA===");
		System.out.println ("");
		System.out.println ("Ingrese el numero de ID del usuario a dar de bajar");
		int nIde = sc.nextInt();
		stmt.executeUpdate ("DELETE FROM persona WHERE id= " + nIde + " ");
		
		System.out.println("Baja realizada con exito");
		System.out.println("==========================");
		System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
		System.out.println("1.Dar de baja otra persona");
		System.out.println("2.Mostrar mas opciones");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:
			darBaja(conexion, sc);
			break;

		case 2:
			mostrarMasOpciones(conexion, sc);

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;
		}
		

		conexion.close();
		
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	}

	// --------------------------MOSTRAR LISTA--------------------------

	private static void mostrarLista(Connection conexion) {

		System.out.println("=====LISTA=====");
		System.out.println("");
		System.out.println("ID===NOMBRE===EDAD===FECHA DE NACIMIENTO");
		Statement stmt;

		try {

			stmt = conexion.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PERSONA");

			while (rs.next()) {

				Date fNac = rs.getDate(4);

				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " "
						+ rs.getInt(3) + " " + fNac);

			}

			System.out.println("===LISTA FINAL===");

			System.out.println();

		} catch (SQLException e) {

			System.out.println("Error al mostrar lista");

		}

	}

	// -----------------------------BUSCAR----------------------------------

	private static void buscarEnLista(Connection conexion, Scanner sc) {

		System.out.println("===BUSCAR===");
		System.out.println("");
		System.out.println("Ingrese INICIALES a buscar");
		String buscarIni = sc.next();

		try {
			Statement stmt = conexion.createStatement();
			System.out.println("Usuario encontrado: ");
			ResultSet rs = stmt.executeQuery("SELECT * FROM persona WHERE nombre LIKE '"
					+ buscarIni	+ "%';");
			
			while(rs.next()) {
				System.out.println(rs.getInt(1)+ "" + rs.getString(2) + "" + rs.getInt(3) + "" + rs.getDate(4));
			}

			System.out.println("==========================");
			System.out.println("¿QUE DESEA HACER EN EL SISTEMA)");
			System.out.println("1.Buscar  otra persona");
			System.out.println("2.Mostrar mas opciones");
			System.out.println("0.Salir del sistema");
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				buscarEnLista(conexion, sc);
				break;

			case 2:
				mostrarMasOpciones(conexion, sc);

			case 0:
				System.out.println("GRACIAS POR USAR EL SISTEMA");
				break;

			default:
				System.out.println("OPCION INVALIDA");
				break;

			}
		} catch (SQLException e) {

			System.out.println("Error al buscar registro");

		}
	}

	// -------------------MENU MAS OPCIONES--------------------

	private static void mostrarMasOpciones(Connection conexion, Scanner sc) {
		System.out.println("OTRAS OPCIONES");
		System.out.println("1.Ver lista actual");
		System.out.println("0.Salir del sistema");
		int opcion = sc.nextInt();

		switch (opcion) {
		case 1:

			mostrarLista(conexion);
			break;

		case 0:
			System.out.println("GRACIAS POR USAR EL SISTEMA");
			break;

		default:
			System.out.println("OPCION INVALIDA");
			break;

		}
	}

	// ------------------CALCULO DE EDAD----------------------

	private static int calcularEdad(Date fechaNac) {
		GregorianCalendar fc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();

		fc.setTime(fechaNac);

		int anActual = hoy.get(Calendar.YEAR);
		int anNac = fc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNac = fc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNac = fc.get(Calendar.DATE);

		int dif = anActual - anNac;

		if (mesActual < mesNac) {

			dif = dif - 1;

		}

		else {

			if (mesActual == mesNac && diaActual < diaNac) {

				dif = dif - 1;
			}
		}
		return dif;
	}

	// --------------------MOSTRAR MENU--------------------------------

	private static int mostrarMenu(Scanner sc) {

		System.out.println("BIENVENIDO AL SISTEMA DE PERSONAS (ABM)");

		System.out.println("=========================");

		System.out.println("¿QUE DESEA HACER EN EL SISTEMA");

		System.out.println("");

		System.out.println("1: DAR DE ALTA ");

		System.out.println("2: MODIFICAR ");

		System.out.println("3: DAR DE BAJA");

		System.out.println("4: MOSTRAR LISTADO");

		System.out.println("5: BUSCAR EN LISTA");

		System.out.println("0: SALIR DEL SISTEMA");

		int opcion = 0;

		opcion = sc.nextInt();

		return opcion;

	}

}
