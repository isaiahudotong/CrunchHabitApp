package eie.android.crunch;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by emilyw on 7/18/15.
 */
@ParseClassName("Obstacle")
public class Obstacle extends ParseObject {

    public Obstacle() {
        // A default constructor is required.
    }

    public void setObstacle(String obstacle) {
        put("obstacle_desc", obstacle);

    }

    public String getObstacle() {
        return getString("obstacle_desc");
    }


}