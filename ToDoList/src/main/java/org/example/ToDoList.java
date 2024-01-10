import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ToDoList {
    private ArrayList<String> tasks;

    public ToDoList(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    public void addTask(String task) {
        tasks.add(task);
        System.out.println("Görev eklendi: " + task);
    }

    public void listTasks() {
        System.out.println("Görevler:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void completeTask(int index) {
        if (index >= 1 && index <= tasks.size()) {
            String completedTask = tasks.remove(index - 1);
            System.out.println("Tamamlanan görev: " + completedTask);
        } else {
            System.out.println("Geçersiz görev numarası.");
        }
    }

    public ArrayList<String> getTasks() {
        return tasks;
    }

    public static void main(String[] args) {
        ArrayList<String> tasks = TaskManager.loadTasks();
        ToDoList toDoList = new ToDoList(tasks);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Görev Ekle");
            System.out.println("2. Görevleri Listele");
            System.out.println("3. Görev Tamamla");
            System.out.println("4. Çıkış");

            System.out.print("Seçiminiz: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Görevi girin: ");
                    scanner.nextLine();
                    String task = scanner.nextLine();
                    toDoList.addTask(task);
                    break;
                case 2:
                    toDoList.listTasks();
                    break;
                case 3:
                    System.out.print("Tamamlanan görevin numarasını girin: ");
                    int index = scanner.nextInt();
                    toDoList.completeTask(index);
                    break;
                case 4:
                    TaskManager.saveTasks(toDoList.getTasks());
                    System.out.println("Uygulamadan çıkılıyor...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Geçersiz seçim. Tekrar deneyin.");
            }
        }
    }
}

class TaskManager {
    private static final String FILE_NAME = "tasks.txt";

    public static void saveTasks(ArrayList<String> tasks) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                writer.println(task);
            }
            System.out.println("Görevler dosyaya kaydedildi.");
        } catch (IOException e) {
            System.err.println("Dosya kaydetme hatası: " + e.getMessage());
        }
    }

    public static ArrayList<String> loadTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(new FileReader(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                tasks.add(scanner.nextLine());
            }
            System.out.println("Görevler dosyadan yüklendi.");
        } catch (IOException e) {
            System.err.println("Dosya yükleme hatası: " + e.getMessage());
        }
        return tasks;
    }
}
