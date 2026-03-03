package com.example.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

    private final List<Libro> librosBuscados = new ArrayList<>();

    private final LibroRepository libroRepository;
    private final LibroService libroService;
    private final AutorService autorService;


    public LiterAluraApplication(LibroRepository libroRepository, LibroService libroService, AutorService autorService) {
        this.libroRepository = libroRepository;
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiterAluraApplication.class, args);
    }

    public void run(String... args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("==================");
            System.out.println("1 - Buscar por titulo");
            System.out.println("2 - Listar libros buscados");
            System.out.println("3 - Listar libros en ingles");
            System.out.println("4 - Listar libros en español");
            System.out.println("5 - listar autores");
            System.out.println("6 - Listar autores vives en año");
            System.out.println("0 - Salir");
            System.out.print("opcion elejida: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("ingrese el titulo de libro: ");
                    String title = scanner.nextLine();
                    Libro libro = Gutendex.buscarTitulo(title);
                    System.out.println(libro);
                    librosBuscados.add(libro);
                    libroRepository.save(libro);
                    break;
                case "2":
                    System.out.println(librosBuscados);
                    break;
                case "3":
                    List<Libro> libros = libroService.getLibrosPorIdioma("en");
                    System.out.println(libros);
                    break;
                case "4":
                    List<Libro> librosES = libroService.getLibrosPorIdioma("es");
                    System.out.println(librosES);
                    break;
                case "5":
                    List<Autor> autores = autorService.getTodosAutores();
                    System.out.println(autores);
                    break;

                case "6":
                    System.out.print("ingrese el año");
                    Integer anio = Integer.parseInt(scanner.nextLine());
                    List<Autor> autoresVivos = autorService.getAutoresVivosEnAnio(anio);
                    System.out.println(autoresVivos);
                    break;
                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("opcion invalida.");
            }
        }

        scanner.close();
    }
}
