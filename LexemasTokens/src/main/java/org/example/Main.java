package org.example;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static List<Token> analizadorLexico(String codigoFuente) {
        List<Token> lexemasYTokens = new ArrayList<>();
        String[] otorgarTokens = {
                "\\bif\\b", "IF",
                "\\belse\\b", "ELSE",
                "\\bpublic\\b", "ALCANCE",
                "\\bprivate\\b", "ALCANCE",
                "\\;", "PUNTO_Y_COMA",
                "\\b[a-z_0-9]\\w*\\b", "IDENTIFICADOR",
                "\\+", "SUMA",
                "-", "RESTA",
                "\\*", "MULTIPLICACION",
                "/", "DIVISION",
                "=", "ASIGNACION",
                "\\(", "PARENTESIS_IZQ",
                "\\)", "PARENTESIS_DER",
                "\\{", "CORCHET_IZQ",
                "\\}", "CORCHET_DER",
                "\\b[A-Z][a-z]\\w*\\b", "CLASES",
        };

        StringBuilder tokensUnidos = new StringBuilder();
        for (int i = 0; i < otorgarTokens.length; i += 2) {
            tokensUnidos.append(otorgarTokens[i]);
            if (i < otorgarTokens.length - 2) {
                tokensUnidos.append("|");
            }
        }

        Pattern patron = Pattern.compile(tokensUnidos.toString());
        Scanner scanner = new Scanner(codigoFuente);
        while (scanner.hasNext()) {
            if (scanner.hasNext(patron)) {
                String lexema = scanner.next(patron);
                String token = obtenerToken(lexema, otorgarTokens);
                lexemasYTokens.add(new Token(lexema, token));
            } else {
                scanner.next();
            }
        }

        return lexemasYTokens;
    }

    private static String obtenerToken(String lexema, String[] otorgarTokens) {
        for (int i = 0; i < otorgarTokens.length; i += 2) {
            Pattern patron = Pattern.compile(otorgarTokens[i]);
            Matcher matcher = patron.matcher(lexema);
            if (matcher.matches()) {
                return otorgarTokens[i + 1];
            }
        }
        return "DESCONOCIDO";
    }

    public static void main(String[] args) {
        String rutaArchivo = "C:\\Users\\jeffe\\OneDrive\\Escritorio\\Universidad\\Semestre 7\\Compiladores\\LexemasTokens\\src\\main\\java\\org\\example\\Sumador.txt";

        try {
            String codigoFuente = leerCodigoFuenteDesdeArchivo(rutaArchivo);

            List<Token> resultadoAnalisisLexico = analizadorLexico(codigoFuente);

            // Mostrar los lexemas y tokens encontrados
            for (Token token : resultadoAnalisisLexico) {
                System.out.println("Lexema: " + token.getLexema() + ", Token: " + token.getToken());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Archivo no encontrado.");
        }
    }

    private static String leerCodigoFuenteDesdeArchivo(String rutaArchivo) throws FileNotFoundException {
        StringBuilder codigoFuente = new StringBuilder();
        Scanner scanner = new Scanner(new File(rutaArchivo));

        while (scanner.hasNextLine()) {
            codigoFuente.append(scanner.nextLine()).append("\n");
        }

        return codigoFuente.toString();
    }
}

