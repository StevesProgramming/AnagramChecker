import javax.sound.midi.Soundbank;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String log_file_path = "text-files/anagram_logs.txt";

        String[] total_data = get_test_data_from_file(log_file_path);
        String[] input_data = new String[3];
        for (int i = 0; i < total_data.length; i++) {
            String[] temp_arr = total_data[i].split(" ");
            for (int j = 0; j < 3; j++) {
                input_data[j] = temp_arr[j];
            }

            boolean is_data_valid = false;

            is_data_valid = check_data_is_valid(input_data, log_file_path);
            if (!is_data_valid) {
                System.out.println("Error: Input data is invalid\n");
                return;
            }


            String[] cache_array = write_to_cache(input_data, log_file_path);
            boolean cached_word_found = check_against_cache(cache_array, input_data, log_file_path);
            if(!cached_word_found) {
                check_for_anagram(input_data, log_file_path);
            }
        }
    }

    public static String[] get_test_data_from_file(String log_file_path) throws IOException {
        String log = "INFO: Attempting to retrieve data from file\n";
        write_to_a_file(log, log_file_path);

        BufferedReader read_lines_in_file = new BufferedReader(new FileReader("text-files/input_data_file.txt"));
        int lines = 0;
        while (read_lines_in_file.readLine() != null) lines++;
        read_lines_in_file.close();
        String[] total_data = new String[lines];

        int counter = 0;
        try {
            File input_data_file = new File("text-files/input_data_file.txt");
            Scanner reader = new Scanner(input_data_file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                total_data[counter] = data;
                counter++;
                System.out.println(Arrays.toString(total_data));
            }
            log = "INFO: Data retrieved from file successfully\n";
            write_to_a_file(log, log_file_path);
            reader.close();



        } catch (FileNotFoundException e) {
            log = "Error: File Not Found\n";
            write_to_a_file(log, log_file_path);
            e.printStackTrace();
        }

        return total_data;
    }

    public static boolean check_data_is_valid(String[] input_data, String log_file_path) {
        String log = "INFO: Checking the data from file is valid\n";
        write_to_a_file(log, log_file_path);

        String word1 = input_data[1].toLowerCase();
        String word2 = input_data[2].toLowerCase();
        boolean word1_is_valid = word1.matches("^[a-zA-Z]+$");
        boolean word2_is_valid = word2.matches("^[a-zA-Z]+$");

        if (word1_is_valid && word2_is_valid) {
            log = "INFO: Data from file is valid\n";
            write_to_a_file(log, log_file_path);
            return true;
        }
        log = "INFO: Data from file is valid\n";
        write_to_a_file(log, log_file_path);
        return false;
    }

    public static void check_for_anagram(String[] input_data, String log_file_path) throws IOException {
        String log = "INFO: Checking if the words entered are anagrams\n";
        write_to_a_file(log, log_file_path);

        String word1 = input_data[1].toLowerCase();
        String word2 = input_data[2].toLowerCase();
        String username = input_data[0];
        String anagram_result = "";

        if (word1.length() == word2.length()) {
            boolean is_input_an_anagram = order_word_alphabetically(word1, word2, log_file_path);
            if (is_input_an_anagram) {
                anagram_result = "are an anagram";
                results_file_output(word1, word2, username, anagram_result, log_file_path);
                return;
            }
        }
        anagram_result = "are not an anagram";
        results_file_output(word1, word2, username, anagram_result, log_file_path);
    }

    public static boolean order_word_alphabetically(String word1, String word2, String log_file_path) {
        String log = "INFO: Ordering words alphabetically\n";
        write_to_a_file(log, log_file_path);

        char[] word_1_Array = word1.toCharArray();
        char[] word_2_Array = word2.toCharArray();

        Arrays.sort(word_1_Array);
        Arrays.sort(word_2_Array);

        return Arrays.equals(word_1_Array, word_2_Array);
    }

    public static void results_file_output(String word1, String word2, String username, String anagram_result, String log_file_path) throws IOException {
        String log = "INFO: Constructing results output message\n";
        write_to_a_file(log, log_file_path);

        String header_text = "------------- Output -------------\n";
        String body_test =  "Word 1: " + word1 + "\n" +
                            "Word 2: " + word2 + "\n" +
                            username + " these words " +  anagram_result + "\n\n";

        String result_log = header_text + body_test;
        String file_path = "text-files/results.txt";
        write_to_a_file(result_log, file_path);
    }

    public static void write_to_a_file(String message, String file_path){
        try {
            FileWriter results_file_path = new FileWriter(file_path, true);
            BufferedWriter results_file = new BufferedWriter(results_file_path);
            results_file.write(message);
            results_file.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String[] write_to_cache(String[] input_data, String log_file_path){
        String log = "INFO: Writing data to cache\n";
        write_to_a_file(log, log_file_path);

        String word1 = input_data[1].toLowerCase();
        String word2 = input_data[2].toLowerCase();

        // Making map with a list
        Map<String, ArrayList<String>> cache = new HashMap<String, ArrayList<String>>();

        // How to start and populate the list
        cache.put(word1, new ArrayList<String>());
        cache.get(word1).add(word2);

        String cache_map = cache.get(word1).toString();

        // Getting the data into an array
        cache_map = cache_map.replace("[", "");
        cache_map = cache_map.replace("]", "");
        cache_map = cache_map.replace(",", "");
        cache_map = cache_map.replaceAll("[^a-zA-Z]$", "");
        String[] cache_array = cache_map.split(" ");

        return cache_array;
    }

    public static boolean check_against_cache(String[] cache_array, String[] input_data, String log_file_path) throws IOException {
        String log = "INFO: Checking input against cache\n";
        write_to_a_file(log, log_file_path);

        String word1 = input_data[1].toLowerCase();
        String word2 = input_data[2].toLowerCase();
        String username = input_data[0];
        String anagram_result = "";
        for (int i = 0; i < input_data.length; i++) {
            for (int j = 0; j < cache_array.length; j++){
                if (input_data[i] == cache_array[j]){
                    anagram_result = "are an anagram";
                    results_file_output(word1, word2, username, anagram_result, log_file_path);
                    return true;
                }
            }
        }

        return false;
    }
}


