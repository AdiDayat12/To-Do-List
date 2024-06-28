public class ToDos {
    private String tasks;
    private Priority priority;

    private ToDos (ToDoBuilder toDoBuilder){
        this.tasks = toDoBuilder.tasks;
        this.priority = toDoBuilder.priority;
    }

    public String getTasks (){
        return this.tasks;
    }

    public String  getPriority (){
        return this.priority.toString();
    }

    public static class ToDoBuilder {
        private final String tasks;
        private final Priority priority;

        public ToDoBuilder (String tasks, Priority priority){
            this.tasks = tasks;
            this.priority = priority;
        }

        public ToDos build (){
            return new ToDos(this);
        }
    }
}
