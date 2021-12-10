package Graphs;

public class GeoLocationData implements api.GeoLocation {
     double x_cor ,y_cor ,z_cor;
    public GeoLocationData(){
        this.x_cor =0;
        this.y_cor =0;
        this.z_cor =0;
    }
    public GeoLocationData(double x, double y, double z){
        this.x_cor =x;
        this.y_cor =y;
        this.z_cor =z;
    }
    @Override
    public double x() {

        return this.x_cor;
    }

    @Override
    public double y() {
        return this.y_cor;
    }

    @Override
    public double z() {

        return this.z_cor;
    }

    @Override
    public double distance(api.GeoLocation g) {

        return 0;
    }
}
