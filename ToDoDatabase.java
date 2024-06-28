import com.mysql.cj.xdevapi.SelectStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToDoDatabase {
    private Connection conn;

    public ToDoDatabase (Connection conn){
        this.conn = conn;
    }

    public List<List<String>> getTasks (){
        String sql = "SELECT * FROM task_list";

        List<List<String>> tasks = new ArrayList<>();
        try (PreparedStatement statement = conn.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                List<String> task = new ArrayList<>();
                task.add(resultSet.getString("task"));
                task.add(resultSet.getString("priority"));
                tasks.add(task);
            }
        }catch (SQLException e){
            System.out.println("SQL error : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return tasks;
    }

    public void addTask (ToDos toDos){
        String sql = "INSERT INTO  task_list (task, priority) VALUES (?,?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql) ){
            preparedStatement.setString(1, toDos.getTasks().toLowerCase());
            preparedStatement.setString(2, toDos.getPriority());
            preparedStatement.executeUpdate();
            System.out.println("Task was successfully added!\n");
        } catch (SQLException e) {
            System.out.println("Failed to add task : " + e.getMessage() + "\n");
            throw new RuntimeException(e);
        }
    }

    public void updateTask (String prevTask, String newTask){

        String sqlInsert = "SELECT task FROM task_list WHERE task = ?";
        String sqlUpdate = "UPDATE task_list SET task = ? WHERE task = ?";
        try (PreparedStatement selectStatement = conn.prepareStatement(sqlInsert);
             PreparedStatement updateStatement = conn.prepareStatement(sqlUpdate)){

            selectStatement.setString(1, prevTask.toLowerCase());

            ResultSet resultSet = selectStatement.executeQuery();
            //check if previous task is there in database
            if (resultSet.next()){
                updateStatement.setString(1, newTask.toLowerCase());
                updateStatement.setString(2, prevTask);
                updateStatement.executeUpdate();
                System.out.println("Task was successfully updated.\n");
            }else {
                throw new IllegalArgumentException("Previous task '" + prevTask + "' not found.\n");
            }

        }catch (SQLException e){
            throw new RuntimeException("Error updating task: " + e.getMessage() + "\n");
        }
    }

    public void deleteTask (String taskToRemove){
        String selectSql = "SELECT task FROM task_list WHERE task = ?";
        String deleteSql = "DELETE FROM task_list WHERE task = ?";

        String taskToRemoveLower = taskToRemove.toLowerCase();

        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql);
             PreparedStatement deleteStatement = conn.prepareStatement(deleteSql)){

            selectStatement.setString(1, taskToRemoveLower);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()){
                deleteStatement.setString(1, taskToRemoveLower);
                deleteStatement.executeUpdate();
                System.out.println("Data was successfully removed.\n");
            }
        }catch (SQLException e){
            System.out.println("The task  '" + taskToRemove + "' not found. Remove failed.\n");
        }
    }
}
