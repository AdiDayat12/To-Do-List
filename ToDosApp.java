import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ToDosApp {
    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
        ToDoDatabase toDoDatabase = new ToDoDatabase(connection.getConnection());

        System.out.println("---- WELCOME TO SIMPLE TO-DO LIST APP ----");
        while (true) {
            System.out.println("Enter Your Choice :\n1 -> Add Task\n2 -> Change Task\n3 -> Delete Task \n4 -> Display All Tasks\n0 -> Exit");
            Scanner scanner = new Scanner(System.in);

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid Input. Enter Your Choice : ");
                scanner.next();
            }

            int choice = scanner.nextInt();
            if (choice == 1) {
                toDoDatabase.addTask(Objects.requireNonNull(createTask(scanner)));
            } else if (choice == 2) {
                String[] param = updateTask(scanner);
                toDoDatabase.updateTask(param[0], param[1]);
            } else if (choice == 3) {
                toDoDatabase.deleteTask(removeTask(scanner));
            } else if (choice == 4) {
                displayAllTasks(toDoDatabase);
            } else if (choice == 0) {
                System.out.println("Exiting the program. Thank You !!!");
                break;
            }
        }

    }
    public static ToDos createTask (Scanner scanner){
        System.out.println("Enter Your Task : ");
        String task = scanner.next();
        System.out.println("Enter Task Priority Level : \n1 -> High\n2 -> Medium\n3 -> Low");
        while (!scanner.hasNextInt()){
            System.out.println("Invalid Input. Enter Your Task Priority Level : ");
            scanner.next();
        }

        int level = scanner.nextInt();

        return switch (level) {
            case 1 -> new ToDos.ToDoBuilder(task, Priority.HIGH).build();
            case 2 -> new ToDos.ToDoBuilder(task, Priority.MEDIUM).build();
            case 3 -> new ToDos.ToDoBuilder(task, Priority.LOW).build();
            default -> null;
        };
    }
    public static String [] updateTask (Scanner scanner){
        System.out.println("Enter The Task You Are Going To Change : ");
        String prevTask = scanner.next();
        System.out.println("Enter New Task : ");
        String newTask = scanner.next();
        return new String [] {prevTask, newTask};
    }

    public static String  removeTask (Scanner scanner){
        System.out.println("Enter The Task You Are Going To Remove");
        return scanner.next();
    }
    public static void displayAllTasks (ToDoDatabase doDatabase){
        List<List<String>>  tasks = doDatabase.getTasks();
        if (tasks == null || tasks.isEmpty()){
            System.out.println("No Task Found");
        }

        System.out.println("*".repeat(32));
        System.out.println("Task List :");
        assert tasks != null;
        for (List<String> task : tasks){
                System.out.println("Task : " + task.getFirst() + " , Priority : " + task.getLast());
        }
        System.out.println("#".repeat(32));
    }
}
