package tom.lenormand.todolistv2.db;

/**
 * Created by tomle on 12/12/2017.
 */

public class Task {
    private String intitule;
    private String task;

    public Task(String intitule, String task)
    {
        this.intitule = intitule;
        this.task = task;
    }

    public String getIntitule()
    {
        return intitule;
    }

    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }
}
