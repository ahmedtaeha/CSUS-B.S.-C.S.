package Data;

public class spriteInfo {
    // Fields
    // TODO: Add private class fields to store x, y (use Vector2D for this) and tag (String) values given in class constructor

    private Vector2D v2d;
    private String tag;
    public int height,width;

    // Constructor
    public spriteInfo(Vector2D v2d,int width,int height, String tag){
        // TODO: Save the constructor parameters into class fields
        this.v2d = v2d;
        this.tag = tag;
        this.height = height;
        this.width = width;
    }

    // Methods
    public String getTag(){
        // TODO: Remove my placeholder code below (which is there to prevent an error) and replace it with returning the value of your private field tag
        return tag;
    }

    public Vector2D getCoords(){
        // TODO: Remove my placeholder code below (which is there to prevent an error) and replace it with returning the value of your private field v2d
        return v2d;
    }

    public void setTag(String newTag){
        // TODO: Update the value of tag to be the value in newTag (Absolute assignment)
        this.tag = newTag;
    }

    public void setCoords(Vector2D newV2D){
        // TODO: Update the value of v2d to be the value in newV2D (Absolute assignment)
        setCoords(newV2D.getX(),newV2D.getY());
    }

    public void setCoords(int x, int y){
        // TODO: Overload the setCoords method to allow another way to set the coordinates. Place the x, y integers into v2d by changing the values of v2d to hold x and y (Absolute assignment)
        this.v2d.setX(x);
        this.v2d.setY(y);
    }

    public String toString(){
        // TODO: Create a "toString" method to test. Assume an x, y of 100, 50 and a tag of "star", this should return: [100, 50, star]
        // Remove my placeholder code below (which is there to prevent an error) and replace it with your proper toString method with specifications above
        return String.format("[%d, %d, %s]",v2d.getX(), v2d.getY(),tag);
    }

    public boolean iscollision(spriteInfo o){
        int r1 = getCoords().getX() + width;
        int l1 = getCoords().getX();
        int t1 = getCoords().getY() ;
        int b1 = getCoords().getY()+ height;

        //the other collider
        int r2 = o.getCoords().getX() + o.width;
        int l2 = o.getCoords().getX();
        int t2 = o.getCoords().getY() ;
        int b2 = o.getCoords().getY() + o.height;

        if (r2 > l1 && b2 > t1 && r1 > l2 && b1> t2)
            return true;

        return false;
    }
}
