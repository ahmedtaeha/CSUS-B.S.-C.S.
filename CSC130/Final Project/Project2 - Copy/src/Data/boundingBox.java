package Data;

public class boundingBox {
    private  Vector2D v2d;
    private int height,width;

    public boundingBox(int x,int y , int height, int width){
        v2d = new Vector2D(x,y);
        this.height = height;
        this.width = width;
    }
}
